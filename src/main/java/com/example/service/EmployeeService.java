// File: com/example/service/EmployeeService.java
package com.example.service;

import com.example.model.Employee;
import com.example.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeService {
    private final EmployeeRepository repository;
    private final List<Employee> manualEmployees;

    public EmployeeService(EmployeeRepository repository, List<Employee> manualEmployees) {
        this.repository = repository;
        this.manualEmployees = new ArrayList<>(manualEmployees);
    }

    public List<Employee> getAllEmployees() {
        List<Employee> combined = repository.getAllEmployees()
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
