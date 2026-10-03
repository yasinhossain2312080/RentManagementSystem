package com.example.rentmanagementsystem;

import java.io.Serializable;
import java.time.LocalDate;

public class RentPayments implements Serializable {
    private static final long serialVersion = 1L;

    private String tenantName,TenantId,month,PaymentMethod,PaymentStatus;
    private Double monthlyRent,PreviousDue,electricityBill,WaterBill,GasBill,TotalAmount,paidAmount,CurrentDue;
    private LocalDate PaymentDate;

    public RentPayments(String tenantName, String tenantId, String month, String paymentMethod, String paymentStatus, Double monthlyRent, Double previousDue, Double electricityBill, Double waterBill, Double gasBill, Double totalAmount,Double paidAmount, Double currentDue, LocalDate paymentDate) {
        this.tenantName = tenantName;
        TenantId = tenantId;
        this.month = month;
        PaymentMethod = paymentMethod;
        PaymentStatus = paymentStatus;
        this.monthlyRent = monthlyRent;
        PreviousDue = previousDue;
        this.electricityBill = electricityBill;
        WaterBill = waterBill;
        GasBill = gasBill;
        TotalAmount = totalAmount;
        this.paidAmount = paidAmount;
        CurrentDue = currentDue;
        PaymentDate = paymentDate;

    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String tenantId) {
        TenantId = tenantId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        PaymentStatus = paymentStatus;
    }

    public Double getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(Double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public Double getPreviousDue() {
        return PreviousDue;
    }

    public void setPreviousDue(Double previousDue) {
        PreviousDue = previousDue;
    }

    public Double getElectricityBill() {
        return electricityBill;
    }

    public void setElectricityBill(Double electricityBill) {
        this.electricityBill = electricityBill;
    }

    public Double getWaterBill() {
        return WaterBill;
    }

    public void setWaterBill(Double waterBill) {
        WaterBill = waterBill;
    }

    public Double getGasBill() {
        return GasBill;
    }

    public void setGasBill(Double gasBill) {
        GasBill = gasBill;
    }

    public Double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        TotalAmount = totalAmount;
    }

    public Double getCurrentDue() {
        return CurrentDue;
    }

    public void setCurrentDue(Double currentDue) {
        CurrentDue = currentDue;
    }

    public LocalDate getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        PaymentDate = paymentDate;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    @Override
    public String toString() {
        return "RentPayments{" +
                "tenantName='" + tenantName + '\'' +
                ", TenantId='" + TenantId + '\'' +
                ", month='" + month + '\'' +
                ", PaymentMethod='" + PaymentMethod + '\'' +
                ", PaymentStatus='" + PaymentStatus + '\'' +
                ", monthlyRent=" + monthlyRent +
                ", PreviousDue=" + PreviousDue +
                ", electricityBill=" + electricityBill +
                ", WaterBill=" + WaterBill +
                ", GasBill=" + GasBill +
                ", TotalAmount=" + TotalAmount +
                ", paidAmount=" + paidAmount +
                ", CurrentDue=" + CurrentDue +
                ", PaymentDate=" + PaymentDate +
                '}';
    }
}
