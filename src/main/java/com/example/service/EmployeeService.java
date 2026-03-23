// File: com/example/service/EmployeeService.java
package com.example.service;

import com.example.model.Employee;
import com.example.repository.ExcelEmployeeRepository;
import com.example.repository.MongoEmployeeRepository;
import com.example.repository.SqlEmployeeRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeService {
    private final SqlEmployeeRepository sqlRepository;
    private final MongoEmployeeRepository mongoRepository;
    private final ExcelEmployeeRepository excelRepository;
    private final String databaseDepartment;

    public EmployeeService(
            SqlEmployeeRepository sqlRepository,
            MongoEmployeeRepository mongoRepository,
            ExcelEmployeeRepository excelRepository,
            String databaseDepartment
    ) {
        this.sqlRepository = sqlRepository;
        this.mongoRepository = mongoRepository;
        this.excelRepository = excelRepository;
        this.databaseDepartment = databaseDepartment;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> combined = sqlRepository.getEmployeesByDepartment(databaseDepartment)
                .stream()
                .map(Employee::clone)
                .collect(Collectors.toCollection(ArrayList::new));

        mongoRepository.getAllEmployees().stream()
                .map(Employee::clone)
                .forEach(combined::add);

        excelRepository.getAllEmployees().stream()
                .map(Employee::clone)
                .forEach(combined::add);

        return combined;
    }

    public double calculateAverageSalary() {
        return getAllEmployees().stream()
                .mapToDouble(Employee::compensation)
                .average()
                .orElse(0.0);
    }

    public Map<String, Long> countByDepartment() {
        return getAllEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, LinkedHashMap::new, Collectors.counting()));
    }
}
