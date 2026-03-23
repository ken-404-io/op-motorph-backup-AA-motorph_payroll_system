package com.motorph.model;

// Inheritance + Polymorphism
public class WithholdingTaxDeduction extends BaseDeduction {

    public WithholdingTaxDeduction() {
        super("Withholding Tax");
    }

    @Override
    public double calculate(double taxableIncome) {
        final double BRACKET_1 = 20833.00;
        final double BRACKET_2 = 33333.00;
        final double BRACKET_3 = 66667.00;
        final double BRACKET_4 = 166667.00;
        final double BRACKET_5 = 666667.00;

        if (taxableIncome <= BRACKET_1) {
            return 0.0;
        } else if (taxableIncome <= BRACKET_2) {
            return (taxableIncome - BRACKET_1) * 0.20;
        } else if (taxableIncome <= BRACKET_3) {
            return 2500.00 + (taxableIncome - BRACKET_2) * 0.25;
        } else if (taxableIncome <= BRACKET_4) {
            return 10833.33 + (taxableIncome - BRACKET_3) * 0.30;
        } else if (taxableIncome <= BRACKET_5) {
            return 40833.33 + (taxableIncome - BRACKET_4) * 0.32;
        } else {
            return 200833.33 + (taxableIncome - BRACKET_5) * 0.35;
        }
    }
}
