package com.motorph.model;

// Abstraction
public interface Deduction {
    double calculate(double grossPay);
    String getDeductionName();
}
