package com.motorph.controller;

import java.time.LocalDate;
import java.util.List;

import com.motorph.model.PaySlip;
import com.motorph.service.PayrollServiceInterface;

/**
 * Controller for payroll-related operations.
 * Depends on the PayrollServiceInterface, not the concrete class (OOP abstraction).
 */
public class PayrollController {

    private final PayrollServiceInterface payrollService;

    public PayrollController(PayrollServiceInterface payrollService) {
        this.payrollService = payrollService;
    }

    /**
     * Generates a payslip for an employee for the given pay period.
     *
     * @param employeeId The employee ID
     * @param startDate  Start date for the pay period
     * @param endDate    End date for the pay period
     * @return PaySlip object
     * @throws IllegalArgumentException if any parameter is invalid
     */
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

    /**
     * Generates payroll for all employees for the given date range.
     *
     * @param startDate Start date for the pay period
     * @param endDate   End date for the pay period
     * @return List of PaySlip objects
     * @throws IllegalArgumentException if any parameter is invalid
     */
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
