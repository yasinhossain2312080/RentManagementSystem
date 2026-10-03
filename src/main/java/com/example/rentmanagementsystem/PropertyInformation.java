package com.example.rentmanagementsystem;

import java.io.Serializable;

public class PropertyInformation implements Serializable {

    private static final long serialVersionUIO = 1L;

    private String ownerName,propertyName,propertyAddress,contactNumber;
    private Integer totalFloor,totalGarment,totalShop;

    public PropertyInformation(String ownerName, String propertyName, String propertyAddress, String contactNumber, Integer totalFloor, Integer totalGarment, Integer totalShop) {
        this.ownerName = ownerName;
        this.propertyName = propertyName;
        this.propertyAddress = propertyAddress;
        this.contactNumber = contactNumber;
        this.totalFloor = totalFloor;
        this.totalGarment = totalGarment;
        this.totalShop = totalShop;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Integer getTotalFloor() {
        return totalFloor;
    }

    public void setTotalFloor(Integer totalFloor) {
        this.totalFloor = totalFloor;
    }

    public Integer getTotalGarment() {
        return totalGarment;
    }

    public void setTotalGarment(Integer totalGarment) {
        this.totalGarment = totalGarment;
    }

    public Integer getTotalShop() {
        return totalShop;
    }

    public void setTotalShop(Integer totalShop) {
        this.totalShop = totalShop;
    }

    @Override
    public String toString() {
        return "PropertyInformation{" +
                "ownerName='" + ownerName + '\'' +
                ", propertyName='" + propertyName + '\'' +
                ", propertyAddress='" + propertyAddress + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", totalFloor=" + totalFloor +
                ", totalGarment=" + totalGarment +
                ", totalShop=" + totalShop +
                '}';
    }
}
