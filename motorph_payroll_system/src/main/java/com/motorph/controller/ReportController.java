package com.motorph.controller;

import com.motorph.model.PaySlip;
import com.motorph.service.ReportService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    public PaySlip generatePayslipReport(int employeeId, LocalDate startDate, LocalDate endDate)
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

        return reportService.generatePayslipReport(employeeId, startDate, endDate);
    }

    public List<Map<String, Object>> generateSummaryReport(String reportType, LocalDate startDate, LocalDate endDate)
            throws IllegalArgumentException {
        if (reportType == null || (!reportType.equals("Weekly") && !reportType.equals("Monthly"))) {
            throw new IllegalArgumentException("Report type must be 'Weekly' or 'Monthly'");
        }

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates are required");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }

        return reportService.generateSummaryReport(reportType, startDate, endDate);
    }
}
