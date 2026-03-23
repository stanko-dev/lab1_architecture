// File: com/example/config/H2ConnectionManager.java
package com.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class H2ConnectionManager {
    private static volatile H2ConnectionManager instance;
    private final SqlDatabaseConfig config;

    private H2ConnectionManager(SqlDatabaseConfig config) {
        this.config = config;
        initializeDatabase();
    }

    public static H2ConnectionManager getInstance(SqlDatabaseConfig config) {
        if (instance == null) {
            synchronized (H2ConnectionManager.class) {
                if (instance == null) {
                    instance = new H2ConnectionManager(config);
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(config.getJdbcUrl(), config.getUsername(), config.getPassword());
    }

    private void initializeDatabase() {
        String createTableSql = """
                CREATE TABLE IF NOT EXISTS employees (
                    id INT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    department VARCHAR(255) NOT NULL,
                    type VARCHAR(50) NOT NULL,
                    base_salary DOUBLE,
                    bonus DOUBLE,
                    hourly_rate DOUBLE,
                    hours_per_month INT,
                    commission_rate DOUBLE,
                    monthly_sales DOUBLE,
                    on_call_hours INT,
                    on_call_rate DOUBLE
                )
                """;

        String mergeSql = """
                MERGE INTO employees (
                    id, name, department, type, base_salary, bonus, hourly_rate, hours_per_month,
                    commission_rate, monthly_sales, on_call_hours, on_call_rate
                ) KEY(id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(createTableSql);

            try (PreparedStatement preparedStatement = connection.prepareStatement(mergeSql)) {
                insertEmployee(preparedStatement, 1, "Alice Carter", "IT", "MANAGER",
                        7000.0, 1500.0, null, null, null, null, null, null);
                insertEmployee(preparedStatement, 2, "Brian Mills", "IT", "SYS_ADMIN",
                        5200.0, null, null, null, null, null, 20, 60.0);
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to initialize H2 database", exception);
        }
    }

    private void insertEmployee(
            PreparedStatement statement,
            int id,
            String name,
            String department,
            String type,
            Double baseSalary,
            Double bonus,
            Double hourlyRate,
            Integer hoursPerMonth,
            Double commissionRate,
            Double monthlySales,
            Integer onCallHours,
            Double onCallRate
    ) throws SQLException {
        statement.setInt(1, id);
        statement.setString(2, name);
        statement.setString(3, department);
        statement.setString(4, type);
        setNullableDouble(statement, 5, baseSalary);
        setNullableDouble(statement, 6, bonus);
        setNullableDouble(statement, 7, hourlyRate);
        setNullableInteger(statement, 8, hoursPerMonth);
        setNullableDouble(statement, 9, commissionRate);
        setNullableDouble(statement, 10, monthlySales);
        setNullableInteger(statement, 11, onCallHours);
        setNullableDouble(statement, 12, onCallRate);
        statement.executeUpdate();
    }

    private void setNullableDouble(PreparedStatement statement, int index, Double value) throws SQLException {
        if (value == null) {
            statement.setNull(index, java.sql.Types.DOUBLE);
        } else {
            statement.setDouble(index, value);
        }
    }

    private void setNullableInteger(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value == null) {
            statement.setNull(index, java.sql.Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }
}
