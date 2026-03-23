package com.motorph.model;

// Inheritance
public abstract class BaseDeduction implements Deduction {

    private final String deductionName;

    protected BaseDeduction(String deductionName) {
        this.deductionName = deductionName;
    }

    @Override
    public String getDeductionName() {
        return deductionName;
    }

    @Override
    public String toString() {
        return deductionName;
    }
}
