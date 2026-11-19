
package com.asher.quantitymeasurement.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfig {

    private final Properties properties = new Properties();

    public ApplicationConfig() {
        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input != null) {
                properties.load(input);
            }

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Unable to load application.properties.",
                    exception
            );
        }
    }

    public String get(String key, String defaultValue) {
        return System.getProperty(
                key,
                properties.getProperty(key, defaultValue)
        );
    }

    public String getRepositoryType() {
        return get("quantity.repository.type", "database");
    }

    public String getDatabaseUrl() {
        return get(
                "db.url",
                "jdbc:mysql://localhost:3306/quantity_measurement"
        );
    }

    public String getDatabaseUsername() {
        return get("db.username", "root");
    }

    public String getDatabasePassword() {
        return get("db.password", "");
    }

    public String getDatabaseDriver() {
        return get(
                "db.driver",
                "com.mysql.cj.jdbc.Driver"
        );
    }

    public int getMaximumPoolSize() {
        return Integer.parseInt(
                get("db.pool.maximum-pool-size", "10")
        );
    }

    public int getMinimumIdle() {
        return Integer.parseInt(
                get("db.pool.minimum-idle", "2")
        );
    }

    public long getConnectionTimeout() {
        return Long.parseLong(
                get("db.pool.connection-timeout", "30000")
        );
    }

    public long getIdleTimeout() {
        return Long.parseLong(
                get("db.pool.idle-timeout", "600000")
        );
    }

    public long getMaxLifetime() {
        return Long.parseLong(
                get("db.pool.max-lifetime", "1800000")
        );
    }
}
