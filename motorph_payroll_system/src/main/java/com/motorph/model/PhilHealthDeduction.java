package com.motorph.model;

import com.motorph.util.AppConstants;

// Inheritance + Polymorphism
public class PhilHealthDeduction extends BaseDeduction {

    public PhilHealthDeduction() {
        super("PhilHealth");
    }

    @Override
    public double calculate(double grossPay) {
        return grossPay * AppConstants.PHILHEALTH_EMPLOYEE_CONTRIBUTION_RATE;
    }
}
