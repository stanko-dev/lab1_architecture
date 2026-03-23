// File: com/example/repository/SqlEmployeeRepository.java
package com.example.repository;

import com.example.config.H2ConnectionManager;
import com.example.factory.EmployeeFactory;
import com.example.model.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqlEmployeeRepository implements EmployeeRepository {
    private final H2ConnectionManager connectionManager;
    private final EmployeeFactory employeeFactory;

    public SqlEmployeeRepository(H2ConnectionManager connectionManager, EmployeeFactory employeeFactory) {
        this.connectionManager = connectionManager;
        this.employeeFactory = employeeFactory;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY id";

        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                employees.add(employeeFactory.createEmployee(
                        resultSet.getString("type"),
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
                ));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to read employees from H2", exception);
        }

        return employees;
    }
}
