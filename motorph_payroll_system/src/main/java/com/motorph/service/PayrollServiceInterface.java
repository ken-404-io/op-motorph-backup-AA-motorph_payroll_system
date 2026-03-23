package com.motorph.service;

import java.time.LocalDate;
import java.util.List;

import com.motorph.model.PaySlip;

// Abstraction
public interface PayrollServiceInterface {
    PaySlip generatePayslip(int employeeId, LocalDate startDate, LocalDate endDate);
    List<PaySlip> generatePayroll(LocalDate startDate, LocalDate endDate);
}
