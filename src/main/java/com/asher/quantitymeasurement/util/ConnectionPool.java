
package com.asher.quantitymeasurement.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPool {

    private static ConnectionPool instance;

    private final HikariDataSource dataSource;

    public ConnectionPool(ApplicationConfig config) {

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(config.getDatabaseUrl());
        hikariConfig.setUsername(config.getDatabaseUsername());
        hikariConfig.setPassword(config.getDatabasePassword());
        hikariConfig.setDriverClassName(config.getDatabaseDriver());

        hikariConfig.setMaximumPoolSize(config.getMaximumPoolSize());
        hikariConfig.setMinimumIdle(config.getMinimumIdle());
        hikariConfig.setConnectionTimeout(config.getConnectionTimeout());
        hikariConfig.setIdleTimeout(config.getIdleTimeout());
        hikariConfig.setMaxLifetime(config.getMaxLifetime());

        this.dataSource = new HikariDataSource(hikariConfig);

        try {
            initializeSchema();
        } catch (Exception exception) {
            dataSource.close();
            throw new IllegalStateException(
                    "Unable to initialize database schema.",
                    exception
            );
        }
    }

    public static synchronized ConnectionPool getInstance() {

        if (instance == null || instance.dataSource.isClosed()) {
            instance = new ConnectionPool(new ApplicationConfig());
        }

        return instance;
    }

    private void initializeSchema() throws Exception {

        InputStream schema = getClass()
                .getClassLoader()
                .getResourceAsStream("db/schema.sql");

        if (schema == null) {
            throw new IllegalStateException(
                    "db/schema.sql was not found.");
        }

        try (InputStream input = schema;
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(
                             input, StandardCharsets.UTF_8));
             Connection connection = getConnection()) {

            StringBuilder sql = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sql.append(line).append('\n');
            }

            String[] statements = sql.toString().split(";");

            for (String statementText : statements) {

                String command = statementText.trim();

                if (command.isEmpty()) {
                    continue;
                }

                if (command.toUpperCase().startsWith("CREATE INDEX")
                        && indexExists(connection, command)) {
                    continue;
                }

                try (Statement statement = connection.createStatement()) {
                    statement.execute(command);
                }
            }
        }
    }

    private boolean indexExists(
            Connection connection,
            String sql) throws SQLException {

        String[] parts = sql.trim().split("\\s+");

        if (parts.length < 5) {
            return false;
        }

        String indexName = parts[2];
        String tableName = parts[4];

        // Remove the column portion: table_name(column_name)
        int bracketIndex = tableName.indexOf('(');

        if (bracketIndex >= 0) {
            tableName = tableName.substring(0, bracketIndex);
        }

        DatabaseMetaData metadata = connection.getMetaData();

        // Resolve the actual table name and schema from database metadata.
        try (ResultSet tables = metadata.getTables(
                connection.getCatalog(),
                null,
                "%",
                new String[]{"TABLE"})) {

            while (tables.next()) {

                String actualTableName = tables.getString("TABLE_NAME");

                if (!actualTableName.equalsIgnoreCase(tableName)) {
                    continue;
                }

                String catalog = tables.getString("TABLE_CAT");
                String schema = tables.getString("TABLE_SCHEM");

                try (ResultSet indexes = metadata.getIndexInfo(
                        catalog,
                        schema,
                        actualTableName,
                        false,
                        false)) {

                    while (indexes.next()) {

                        String existingIndex =
                                indexes.getString("INDEX_NAME");

                        if (indexName.equalsIgnoreCase(existingIndex)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public String getPoolStatistics() {

        return "Active Connections: "
                + dataSource.getHikariPoolMXBean().getActiveConnections()
                + ", Idle Connections: "
                + dataSource.getHikariPoolMXBean().getIdleConnections()
                + ", Total Connections: "
                + dataSource.getHikariPoolMXBean().getTotalConnections();
    }

    public void close() {

        if (!dataSource.isClosed()) {
            dataSource.close();
        }

        synchronized (ConnectionPool.class) {
            if (instance == this) {
                instance = null;
            }
        }
    }
}
