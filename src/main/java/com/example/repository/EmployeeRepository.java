// File: com/example/repository/EmployeeRepository.java
package com.example.repository;

import com.example.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> getAllEmployees();
}
