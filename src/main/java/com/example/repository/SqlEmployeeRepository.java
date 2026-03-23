// File: com/example/repository/SqlEmployeeRepository.java
package com.example.repository;

import com.example.config.H2ConnectionManager;
import com.example.factory.EmployeeFactory;
import com.example.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SqlEmployeeRepository implements EmployeeRepository {
    private final H2ConnectionManager connectionManager;
    private final Map<String, EmployeeFactory> employeeFactories;

    public SqlEmployeeRepository(H2ConnectionManager connectionManager, Map<String, EmployeeFactory> employeeFactories) {
        this.connectionManager = connectionManager;
        this.employeeFactories = employeeFactories;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY id";

        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                employees.add(createEmployee(resultSet));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to read employees from H2", exception);
        }

        return employees;
    }

    public List<Employee> getEmployeesByDepartment(String department) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE department = ? ORDER BY id";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, department);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    employees.add(createEmployee(resultSet));
                }
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to read employees from H2 department: " + department, exception);
        }

        return employees;
    }

    private Employee createEmployee(ResultSet resultSet) throws SQLException {
        String type = resultSet.getString("type").toUpperCase(Locale.ROOT);
        EmployeeFactory factory = employeeFactories.get(type);
        if (factory == null) {
            throw new IllegalArgumentException("Unsupported employee type: " + type);
        }

        return factory.createEmployee(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("department"),
                resultSet.getDouble("base_salary"),
                resultSet.getDouble("bonus"),
                resultSet.getDouble("hourly_rate"),
                resultSet.getInt("hours_per_month"),
                resultSet.getDouble("commission_rate"),
                resultSet.getDouble("monthly_sales"),
                resultSet.getInt("on_call_hours"),
                resultSet.getDouble("on_call_rate")
        );
    }
}
