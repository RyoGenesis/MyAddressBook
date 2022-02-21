package com.latihan.myaddressbook_2301853962.models;

public class Employee {

    private int employeeId;
    private EmployeeName name;
    private EmployeeLocation location;
    private String email;
    private Registered registered;
    private String phone;
    private String cell;
    private EmployeePicture picture;

    public int getEmployeeId() {
        return employeeId;
    }

    public EmployeeName getName() {
        return name;
    }

    public EmployeeLocation getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public Registered getRegistered() {
        return registered;
    }

    public String getPhone() {
        return phone;
    }

    public String getCell() {
        return cell;
    }

    public EmployeePicture getPicture() {
        return picture;
    }
}
