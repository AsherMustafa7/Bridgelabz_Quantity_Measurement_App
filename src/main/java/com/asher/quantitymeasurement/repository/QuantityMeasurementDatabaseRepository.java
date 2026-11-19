
package com.asher.quantitymeasurement.repository;

import com.asher.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.asher.quantitymeasurement.exception.DatabaseException;
import com.asher.quantitymeasurement.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementDatabaseRepository
        implements IQuantityMeasurementRepository {

    private final ConnectionPool connectionPool;

    public QuantityMeasurementDatabaseRepository() {
        this(ConnectionPool.getInstance());
    }

    public QuantityMeasurementDatabaseRepository(
            ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {

        if (entity == null) {
            throw new IllegalArgumentException(
                    "Measurement entity cannot be null.");
        }

        String insertMeasurement =
                "INSERT INTO quantity_measurement_entity (" +
                "this_value, this_unit, this_measurement_type, " +
                "that_value, that_unit, that_measurement_type, " +
                "operation, result_value, result_unit, " +
                "result_measurement_type, result_string, " +
                "is_error, error_message" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String insertHistory =
                "INSERT INTO quantity_measurement_history " +
                "(measurement_id, operation) VALUES (?, ?)";

        try (Connection connection = connectionPool.getConnection()) {

            connection.setAutoCommit(false);

            try {
                long measurementId;

                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     insertMeasurement,
                                     Statement.RETURN_GENERATED_KEYS)) {

                    setNullableDouble(statement, 1,
                            entity.getThisValue(), entity.getThisUnit());

                    statement.setString(2, entity.getThisUnit());
                    statement.setString(
                            3, entity.getThisMeasurementType());

                    setNullableDouble(statement, 4,
                            entity.getThatValue(), entity.getThatUnit());

                    statement.setString(5, entity.getThatUnit());
                    statement.setString(
                            6, entity.getThatMeasurementType());

                    statement.setString(7, entity.getOperation());

                    setNullableDouble(statement, 8,
                            entity.getResultValue(), entity.getResultUnit());

                    statement.setString(9, entity.getResultUnit());
                    statement.setString(
                            10, entity.getResultMeasurementType());

                    statement.setString(11, entity.getResultString());
                    statement.setBoolean(12, entity.isError());
                    statement.setString(13, entity.getErrorMessage());

                    statement.executeUpdate();

                    try (ResultSet keys = statement.getGeneratedKeys()) {
                        if (!keys.next()) {
                            throw new SQLException(
                                    "Unable to retrieve generated measurement ID.");
                        }

                        measurementId = keys.getLong(1);
                    }
                }

                try (PreparedStatement statement =
                             connection.prepareStatement(insertHistory)) {

                    statement.setLong(1, measurementId);
                    statement.setString(2, entity.getOperation());
                    statement.executeUpdate();
                }

                connection.commit();

            } catch (SQLException | RuntimeException exception) {

                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    exception.addSuppressed(rollbackException);
                }

                throw exception;

            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException exception) {
            throw new DatabaseException(
                    "Unable to save measurement.", exception);
        }
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {

        String sql =
                "SELECT * FROM quantity_measurement_entity ORDER BY id ASC";

        return executeQuery(sql);
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(
            String operation) {

        if (operation == null || operation.isBlank()) {
            throw new IllegalArgumentException(
                    "Operation cannot be null or blank.");
        }

        String sql =
                "SELECT * FROM quantity_measurement_entity " +
                "WHERE operation = ? ORDER BY id ASC";

        return executeQuery(sql, operation);
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(
            String measurementType) {

        String sql =
                "SELECT * FROM quantity_measurement_entity " +
                "WHERE this_measurement_type = ? " +
                "OR that_measurement_type = ? " +
                "OR result_measurement_type = ? " +
                "ORDER BY id ASC";

        return executeQuery(
                sql,
                measurementType,
                measurementType,
                measurementType
        );
    }

    @Override
    public long getTotalCount() {

        String sql =
                "SELECT COUNT(*) FROM quantity_measurement_entity";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getLong(1);
            }

            return 0;

        } catch (SQLException exception) {
            throw new DatabaseException(
                    "Unable to count measurements.", exception);
        }
    }

    @Override
    public void deleteAll() {

        String deleteHistory =
                "DELETE FROM quantity_measurement_history";

        String deleteMeasurements =
                "DELETE FROM quantity_measurement_entity";

        try (Connection connection = connectionPool.getConnection()) {

            connection.setAutoCommit(false);

            try (PreparedStatement historyStatement =
                         connection.prepareStatement(deleteHistory);
                 PreparedStatement measurementStatement =
                         connection.prepareStatement(deleteMeasurements)) {

                historyStatement.executeUpdate();
                measurementStatement.executeUpdate();

                connection.commit();

            } catch (SQLException exception) {

                connection.rollback();
                throw exception;

            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException exception) {
            throw new DatabaseException(
                    "Unable to delete measurements.", exception);
        }
    }

    private List<QuantityMeasurementEntity> executeQuery(
            String sql,
            String... parameters) {

        List<QuantityMeasurementEntity> measurements =
                new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            for (int i = 0; i < parameters.length; i++) {
                statement.setString(i + 1, parameters[i]);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    measurements.add(mapEntity(resultSet));
                }
            }

            return measurements;

        } catch (SQLException exception) {
            throw new DatabaseException(
                    "Unable to retrieve measurements.", exception);
        }
    }

    private QuantityMeasurementEntity mapEntity(
            ResultSet resultSet) throws SQLException {

        return new QuantityMeasurementEntity(
                getDoubleOrZero(resultSet, "this_value"),
                resultSet.getString("this_unit"),
                resultSet.getString("this_measurement_type"),

                getDoubleOrZero(resultSet, "that_value"),
                resultSet.getString("that_unit"),
                resultSet.getString("that_measurement_type"),

                resultSet.getString("operation"),

                getDoubleOrZero(resultSet, "result_value"),
                resultSet.getString("result_unit"),
                resultSet.getString("result_measurement_type"),

                resultSet.getString("result_string"),

                resultSet.getBoolean("is_error"),
                resultSet.getString("error_message")
        );
    }

    private double getDoubleOrZero(
            ResultSet resultSet,
            String column) throws SQLException {

        double value = resultSet.getDouble(column);
        return resultSet.wasNull() ? 0.0 : value;
    }

    private void setNullableDouble(
            PreparedStatement statement,
            int index,
            double value,
            String unit) throws SQLException {

        if (unit == null) {
            statement.setNull(index, Types.DOUBLE);
        } else {
            statement.setDouble(index, value);
        }
    }

    @Override
    public String getPoolStatistics() {
        return connectionPool.getPoolStatistics();
    }

    @Override
    public void releasingResources() {
        connectionPool.close();
    }
}
