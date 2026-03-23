package com.motorph.service;

import java.time.LocalDate;
import java.util.List;

import com.motorph.model.PaySlip;

/**
 * Defines the contract for payroll-related operations.
 * Any implementation must provide these methods, hiding internal details.
 */
public interface PayrollServiceInterface {
    PaySlip generatePayslip(int employeeId, LocalDate startDate, LocalDate endDate);
    List<PaySlip> generatePayroll(LocalDate startDate, LocalDate endDate);
}
