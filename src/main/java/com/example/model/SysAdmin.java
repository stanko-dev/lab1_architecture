// File: com/example/model/SysAdmin.java
package com.example.model;

public class SysAdmin extends Employee {
    private double baseSalary;
    private int onCallHours;
    private double onCallRate;

    public SysAdmin(int id, String name, String department, double baseSalary, int onCallHours, double onCallRate) {
        super(id, name, department);
        this.baseSalary = baseSalary;
        this.onCallHours = onCallHours;
        this.onCallRate = onCallRate;
    }

    public SysAdmin(SysAdmin other) {
        super(other);
        this.baseSalary = other.baseSalary;
        this.onCallHours = other.onCallHours;
        this.onCallRate = other.onCallRate;
    }

    @Override
    public double compensation() {
        return baseSalary + (onCallHours * onCallRate);
    }

    @Override
    public SysAdmin clone() {
        return new SysAdmin(this);
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public int getOnCallHours() {
        return onCallHours;
    }

    public double getOnCallRate() {
        return onCallRate;
    }
}
