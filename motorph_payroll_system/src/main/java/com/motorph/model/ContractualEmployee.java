package com.motorph.model;

import java.time.LocalDate;

import com.motorph.util.AppConstants;

// Inheritance + Polymorphism
public class ContractualEmployee extends Employee {

    public ContractualEmployee(int employeeId, String lastName, String firstName,
                               String position, String status, double basicSalary,
                               double riceSubsidy, double phoneAllowance, double clothingAllowance) {
        super(employeeId, lastName, firstName, position, status,
              basicSalary, riceSubsidy, phoneAllowance, clothingAllowance);
    }

    public ContractualEmployee(
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
        double hoursWorked = AppConstants.STANDARD_WORK_DAYS_PER_MONTH *
                AppConstants.REGULAR_HOURS_PER_DAY;
        return hoursWorked * getHourlyRate();
    }

    @Override
    public double calculateDeductions() {
        return calculateGrossPay() * 0.10;
    }

    @Override
    public double calculateNetPay() {
        return calculateGrossPay() - calculateDeductions();
    }
}
