package com.motorph.model;

/**
 * ABSTRACTION: Defines the contract for all payroll deductions.
 * Each deduction type must implement its own calculation logic.
 */
public interface Deduction {
    double calculate(double grossPay);
    String getDeductionName();
