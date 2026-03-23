// File: com/example/model/Manager.java
package com.example.model;

public class Manager extends Employee {
    private double baseSalary;
    private double bonus;

    public Manager(int id, String name, String department, double baseSalary, double bonus) {
        super(id, name, department);
        this.baseSalary = baseSalary;
        this.bonus = bonus;
    }

    public Manager(Manager other) {
        super(other);
        this.baseSalary = other.baseSalary;
        this.bonus = other.bonus;
    }

    @Override
    public double compensation() {
        return baseSalary + bonus;
    }

    @Override
    public Manager clone() {
        return new Manager(this);
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public double getBonus() {
        return bonus;
    }
}
