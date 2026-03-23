// File: com/example/service/EmployeeService.java
package com.example.service;

import com.example.model.Employee;
import com.example.repository.SqlEmployeeRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeService {
    private final SqlEmployeeRepository sqlRepository;
    private final String databaseDepartment;
    private final List<Employee> manualEmployees;

    public EmployeeService(SqlEmployeeRepository sqlRepository, String databaseDepartment, List<Employee> manualEmployees) {
        this.sqlRepository = sqlRepository;
        this.databaseDepartment = databaseDepartment;
        this.manualEmployees = new ArrayList<>(manualEmployees);
    }

    public List<Employee> getAllEmployees() {
        List<Employee> combined = sqlRepository.getEmployeesByDepartment(databaseDepartment)
                .stream()
                .map(Employee::clone)
                .collect(Collectors.toCollection(ArrayList::new));

        manualEmployees.stream()
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
