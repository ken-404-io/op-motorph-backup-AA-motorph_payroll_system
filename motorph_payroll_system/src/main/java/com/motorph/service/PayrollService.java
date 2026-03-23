package com.motorph.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.motorph.model.AttendanceRecord;
import com.motorph.model.Employee;
import com.motorph.model.PaySlip;

public class PayrollService implements PayrollServiceInterface {

    private final List<Employee> employees;
    private final List<AttendanceRecord> attendanceRecords;
    private final PayrollProcessor payrollCalculator;

    public PayrollService(List<Employee> employees,
                          List<AttendanceRecord> attendanceRecords,
                          PayrollProcessor payrollCalculator) {

        this.employees = employees;
        this.attendanceRecords = attendanceRecords;
        this.payrollCalculator = payrollCalculator;
    }

    /**
     * Generate a payslip for a specific employee
     */
    public PaySlip generatePayslip(int employeeId,
                                   LocalDate startDate,
                                   LocalDate endDate) {

        // Find employee
        Employee employee = employees.stream()
                .filter(emp -> emp.getEmployeeId() == employeeId)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Employee with ID " + employeeId + " not found"));

        // Get attendance records
        List<AttendanceRecord> records = attendanceRecords.stream()
                .filter(record -> record.getEmployeeId() == employeeId)
                .filter(record -> {
                    LocalDate date = record.getDate();
                    return date != null &&
                            !date.isBefore(startDate) &&
                            !date.isAfter(endDate);
                })
                .toList();


        // Create payslip
        PaySlip paySlip = new PaySlip(employee, startDate, endDate);

        // Let PaySlip handle everything
        paySlip.generate(records, payrollCalculator);

        return paySlip;

    }

    /**
     * Generate payroll for all employees
     */
    public List<PaySlip> generatePayroll(LocalDate startDate, LocalDate endDate) {

        List<PaySlip> paySlips = new ArrayList<>();

        for (Employee employee : employees) {
            try {
                PaySlip paySlip = generatePayslip(
                        employee.getEmployeeId(),
                        startDate,
                        endDate
                );
                paySlips.add(paySlip);

            } catch (Exception e) {
                System.err.println("Error generating payslip for employee "
                        + employee.getEmployeeId() + ": " + e.getMessage());
            }
        }

        return paySlips;
    }
}
