// File: com/example/factory/ManagerFactory.java
package com.example.factory;

import com.example.model.Employee;
import com.example.model.Manager;

public class ManagerFactory implements EmployeeFactory {
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
        return new Manager(id, name, department, baseSalary, bonus);
    }
}
