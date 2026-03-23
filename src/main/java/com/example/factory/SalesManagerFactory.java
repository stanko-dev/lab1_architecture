// File: com/example/factory/SalesManagerFactory.java
package com.example.factory;

import com.example.model.Employee;
import com.example.model.SalesManager;

public class SalesManagerFactory implements EmployeeFactory {
    @Override
    public Employee createEmployee(
            int id,
            String name,
            String department,
            double baseSalary,
            double bonus,
            double hourlyRate,
            int hoursPerMonth,
            double commissionRate,
            double monthlySales,
            int onCallHours,
            double onCallRate
    ) {
        return new SalesManager(id, name, department, baseSalary, commissionRate, monthlySales);
    }
}
