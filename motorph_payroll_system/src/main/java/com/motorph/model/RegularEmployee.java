package com.motorph.model;

import java.time.LocalDate;

public class RegularEmployee extends Employee {

    // Short constructor - used when only basic fields are available
    public RegularEmployee(int employeeId, String lastName, String firstName, String position,
                           String status, double basicSalary, double riceSubsidy,
                           double phoneAllowance, double clothingAllowance) {
        super(employeeId, lastName, firstName, position, status,
              basicSalary, riceSubsidy, phoneAllowance, clothingAllowance);
    }

    // Full constructor - used when all employee details are available
    public RegularEmployee(
            int employeeId,
            String lastName,
            String firstName,
            LocalDate birthday,
            String address,
            String phoneNumber,
            String sssNumber,
            String philhealthNumber,
            String tinNumber,
            String pagibigNumber,
            String status,
            String position,
            String supervisor,
            double basicSalary,
            double riceSubsidy,
            double phoneAllowance,
            double clothingAllowance,
            double grossSemiMonthlyRate) {
        super(
            employeeId, lastName, firstName, birthday, address, phoneNumber,
            sssNumber, philhealthNumber, tinNumber, pagibigNumber,
            status, position, supervisor,
            basicSalary, riceSubsidy, phoneAllowance, clothingAllowance,
            grossSemiMonthlyRate
        );
    }

    @Override
    public double calculateGrossPay() {
        return getBasicSalary() / 2;
    }

    @Override
    public double calculateDeductions() {
        return calculateGrossPay() * 0.20;
    }

    @Override
    public double calculateNetPay() {
        return calculateGrossPay() - calculateDeductions();
    }
}
