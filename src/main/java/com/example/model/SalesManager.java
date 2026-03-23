// File: com/example/model/SalesManager.java
package com.example.model;

public class SalesManager extends Employee {
    private double baseSalary;
    private double commissionRate;
    private double monthlySales;

    public SalesManager(int id, String name, String department, double baseSalary, double commissionRate, double monthlySales) {
        super(id, name, department);
        this.baseSalary = baseSalary;
        this.commissionRate = commissionRate;
        this.monthlySales = monthlySales;
    }

    public SalesManager(SalesManager other) {
        super(other);
        this.baseSalary = other.baseSalary;
        this.commissionRate = other.commissionRate;
        this.monthlySales = other.monthlySales;
    }

    @Override
    public double compensation() {
        return baseSalary + (commissionRate * monthlySales);
    }

    @Override
    public SalesManager clone() {
        return new SalesManager(this);
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public double getMonthlySales() {
        return monthlySales;
    }
}
