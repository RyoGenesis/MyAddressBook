package com.latihan.myaddressbook_2301853962.models;

import java.util.List;

public class APIResponse {

    private String nim;
    private String nama;
    private List<Employee> employees;

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
