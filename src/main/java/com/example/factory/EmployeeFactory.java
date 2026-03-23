// File: com/example/factory/EmployeeFactory.java
package com.example.factory;

import com.example.model.Employee;
import com.example.model.Manager;
import com.example.model.OfficeClerk;
import com.example.model.SalesManager;
import com.example.model.SysAdmin;

public class EmployeeFactory {

    public Manager createManager(int id, String name, String department, double baseSalary, double bonus) {
        return new Manager(id, name, department, baseSalary, bonus);
    }

    public OfficeClerk createOfficeClerk(int id, String name, String department, double hourlyRate, int hoursPerMonth) {
        return new OfficeClerk(id, name, department, hourlyRate, hoursPerMonth);
    }

    public SalesManager createSalesManager(
            int id,
            String name,
            String department,
            double baseSalary,
            double commissionRate,
            double monthlySales
    ) {
        return new SalesManager(id, name, department, baseSalary, commissionRate, monthlySales);
    }

    public SysAdmin createSysAdmin(
            int id,
            String name,
            String department,
            double baseSalary,
            int onCallHours,
            double onCallRate
    ) {
        return new SysAdmin(id, name, department, baseSalary, onCallHours, onCallRate);
    }

    public Employee createEmployee(
            String type,
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
        return switch (type.toUpperCase()) {
            case "MANAGER" -> createManager(id, name, department, baseSalary, bonus);
            case "OFFICE_CLERK" -> createOfficeClerk(id, name, department, hourlyRate, hoursPerMonth);
            case "SALES_MANAGER" -> createSalesManager(id, name, department, baseSalary, commissionRate, monthlySales);
            case "SYS_ADMIN" -> createSysAdmin(id, name, department, baseSalary, onCallHours, onCallRate);
            default -> throw new IllegalArgumentException("Unsupported employee type: " + type);
        };
    }
}
