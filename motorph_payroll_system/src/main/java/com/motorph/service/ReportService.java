package com.motorph.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.motorph.model.Employee;
import com.motorph.model.PaySlip;

public class ReportService {
    private final EmployeeService employeeService;
    private final PayrollService payrollService;

    public ReportService(EmployeeService employeeService, PayrollService payrollService) {
        this.employeeService = employeeService;
        this.payrollService = payrollService;
    }

    public PaySlip generatePayslipReport(int employeeId, LocalDate startDate, LocalDate endDate)
            throws IllegalArgumentException {
        return payrollService.generatePayslip(employeeId, startDate, endDate);
    }

    public List<Map<String, Object>> generateSummaryReport(String reportType, LocalDate startDate, LocalDate endDate) {
        List<Map<String, Object>> summaryData = new ArrayList<>();
        List<Employee> employees = employeeService.getAllEmployees();

        for (Employee employee : employees) {
            try {
                PaySlip paySlip = payrollService.generatePayslip(
                        employee.getEmployeeId(), startDate, endDate);

                Map<String, Object> employeeSummary = new HashMap<>();
                employeeSummary.put("employeeId", employee.getEmployeeId());
                employeeSummary.put("name", employee.getFullName());
                employeeSummary.put("totalHours", paySlip.getRegularHours() + paySlip.getOvertimeHours());
                employeeSummary.put("grossPay", paySlip.getGrossPay());
                employeeSummary.put("netPay", paySlip.getNetPay());

                summaryData.add(employeeSummary);
            } catch (Exception e) {
                System.err.println("Error generating summary for employee " +
                        employee.getEmployeeId() + ": " + e.getMessage());
            }
        }

        return summaryData;
    }
}
