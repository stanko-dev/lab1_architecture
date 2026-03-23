// File: com/example/factory/OfficeClerkFactory.java
package com.example.factory;

import com.example.model.Employee;
import com.example.model.OfficeClerk;

public class OfficeClerkFactory implements EmployeeFactory {
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
        return new OfficeClerk(id, name, department, hourlyRate, hoursPerMonth);
    }
}
