package com.motorph.model;

// Abstraction
public interface Payable {
    double calculateGrossPay();
    double calculateDeductions();
    double calculateNetPay();
}
