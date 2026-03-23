// File: com/example/model/OfficeClerk.java
package com.example.model;

public class OfficeClerk extends Employee {
    private double hourlyRate;
    private int hoursPerMonth;

    public OfficeClerk(int id, String name, String department, double hourlyRate, int hoursPerMonth) {
        super(id, name, department);
        this.hourlyRate = hourlyRate;
        this.hoursPerMonth = hoursPerMonth;
    }

    public OfficeClerk(OfficeClerk other) {
        super(other);
        this.hourlyRate = other.hourlyRate;
        this.hoursPerMonth = other.hoursPerMonth;
    }

    @Override
    public double compensation() {
        return hourlyRate * hoursPerMonth;
    }

    @Override
    public OfficeClerk clone() {
        return new OfficeClerk(this);
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public int getHoursPerMonth() {
        return hoursPerMonth;
    }
}
