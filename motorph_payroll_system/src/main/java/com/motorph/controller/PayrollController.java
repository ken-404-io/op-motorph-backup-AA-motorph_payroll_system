package com.motorph.controller;

import java.time.LocalDate;
import java.util.List;

import com.motorph.model.PaySlip;
import com.motorph.service.PayrollServiceInterface;

public class PayrollController {

    private final PayrollServiceInterface payrollService;

    public PayrollController(PayrollServiceInterface payrollService) {
        this.payrollService = payrollService;
    }

    public PaySlip generatePayslip(int employeeId, LocalDate startDate, LocalDate endDate)
            throws IllegalArgumentException {

        if (employeeId <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates are required");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }

        try {
            return payrollService.generatePayslip(employeeId, startDate, endDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error generating payslip: " + e.getMessage());
        }
    }

    public List<PaySlip> generatePayroll(LocalDate startDate, LocalDate endDate)
            throws IllegalArgumentException {

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates are required");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }

        try {
            return payrollService.generatePayroll(startDate, endDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error generating payroll: " + e.getMessage());
        }
    }
}
