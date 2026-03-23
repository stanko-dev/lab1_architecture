// File: com/example/factory/SysAdminFactory.java
package com.example.factory;

import com.example.model.Employee;
import com.example.model.SysAdmin;

public class SysAdminFactory implements EmployeeFactory {
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
        return new SysAdmin(id, name, department, baseSalary, onCallHours, onCallRate);
    }
}
