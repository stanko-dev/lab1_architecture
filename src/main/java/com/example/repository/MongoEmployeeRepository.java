// File: com/example/repository/MongoEmployeeRepository.java
package com.example.repository;

import com.example.config.MongoConnectionManager;
import com.example.factory.EmployeeFactory;
import com.example.model.Employee;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoEmployeeRepository implements EmployeeRepository {
    private final MongoConnectionManager connectionManager;
    private final EmployeeFactory employeeFactory;

    public MongoEmployeeRepository(MongoConnectionManager connectionManager, EmployeeFactory employeeFactory) {
        this.connectionManager = connectionManager;
        this.employeeFactory = employeeFactory;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        MongoCollection<Document> collection = connectionManager.getCollection();

        for (Document document : collection.find()) {
            employees.add(employeeFactory.createEmployee(
                    document.getString("type"),
                    document.getInteger("id"),
                    document.getString("name"),
                    document.getString("department"),
                    getDouble(document, "base_salary"),
                    getDouble(document, "bonus"),
                    getDouble(document, "hourly_rate"),
                    document.getInteger("hours_per_month", 0),
                    getDouble(document, "commission_rate"),
                    getDouble(document, "monthly_sales"),
                    document.getInteger("on_call_hours", 0),
                    getDouble(document, "on_call_rate")
            ));
        }

        return employees;
    }

    private double getDouble(Document document, String key) {
        Object value = document.get(key);
        if (value instanceof Number number) {
            return number.doubleValue();
        }
        return 0.0;
    }
}
