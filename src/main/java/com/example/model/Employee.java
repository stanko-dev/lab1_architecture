// File: com/example/model/Employee.java
package com.example.model;

public abstract class Employee implements Cloneable {
    private int id;
    private String name;
    private String department;

    protected Employee(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    protected Employee(Employee other) {
        this(other.id, other.name, other.department);
    }

    public abstract double compensation();

    @Override
    public abstract Employee clone();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
