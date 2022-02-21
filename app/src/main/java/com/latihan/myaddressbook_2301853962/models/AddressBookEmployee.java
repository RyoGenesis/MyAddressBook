package com.latihan.myaddressbook_2301853962.models;

public class AddressBookEmployee {

    private int employeeID;
    private String urlImg;
    private String name;
    private String city;
    private String country;
    private String phone;
    private String email;

    public AddressBookEmployee(int employeeID, String urlImg, String name, String city, String country, String phone, String email) {
        this.employeeID = employeeID;
        this.urlImg = urlImg;
        this.name = name;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.email = email;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
