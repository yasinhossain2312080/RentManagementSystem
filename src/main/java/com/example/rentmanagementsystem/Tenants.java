package com.example.rentmanagementsystem;

import java.io.Serializable;

public class Tenants implements Serializable {
    private static final long SerialversionUID = 1L ;
    private String name,id,location,flat,phoneNumber;
    private Double advance,monthlyRent;

    public Tenants(String name, String id, String location, String flat, String phoneNumber, Double advance, Double monthlyRent) {
        this.name = name;
        this.id = id;
        this.location = location;
        this.flat = flat;
        this.phoneNumber = phoneNumber;
        this.advance = advance;
        this.monthlyRent = monthlyRent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getAdvance() {
        return advance;
    }

    public void setAdvance(Double advance) {
        this.advance = advance;
    }

    public Double getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(Double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    @Override
    public String toString() {
        return "Tenants{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", flat='" + flat + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", advance=" + advance +
                ", monthlyRent=" + monthlyRent +
                '}';
    }

}
