package com.motorph.model;

import com.motorph.service.PayrollProcessor;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class PaySlip {

    private Employee employee;
    private LocalDate startDate;
    private LocalDate endDate;

    private double regularHours;
    private double overtimeHours;
    private double grossPay;
    private double netPay;

    private Map<String, Double> deductions;
    private Map<String, Double> allowances;

    public PaySlip(Employee employee, LocalDate startDate, LocalDate endDate) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deductions = new HashMap<>();
        this.allowances = new HashMap<>();
    }

    public void generate(List<AttendanceRecord> attendanceRecords, PayrollProcessor calculator) {

        Map<String, Double> payDetails = getGrossPayDetails(attendanceRecords);

        this.regularHours = payDetails.get("regularHours");
        this.overtimeHours = payDetails.get("overtimeHours");
        this.grossPay = payDetails.get("totalPay");

        Map<String, Double> allowanceDetails = getProRatedAllowanceDetails();

        allowances.put("rice", allowanceDetails.get("riceSubsidy"));
        allowances.put("phone", allowanceDetails.get("phoneAllowance"));
        allowances.put("clothing", allowanceDetails.get("clothingAllowance"));

        double totalAllowances = allowanceDetails.get("totalAllowances");

        double sss = calculator.calculateSSSContribution(grossPay);
        double philhealth = calculator.calculatePhilHealthContribution(grossPay);
        double pagibig = calculator.calculatePagIbigContribution(grossPay);

        deductions.put("sss", sss);
        deductions.put("philhealth", philhealth);
        deductions.put("pagibig", pagibig);

        double taxableIncome = grossPay - (sss + philhealth + pagibig);

        double tax = calculator.calculateWithholdingTax(taxableIncome);
        deductions.put("withholdingTax", tax);

        double totalDeductions = getTotalDeductions();

        this.netPay = grossPay - totalDeductions + totalAllowances;
    }

    private Map<String, Double> getGrossPayDetails(List<AttendanceRecord> attendanceRecords) {

        double regularHours = 0;
        double overtimeHours = 0;

        for (AttendanceRecord record : attendanceRecords) {
            if (record.getEmployeeId() == employee.getEmployeeId()) {

                LocalDate date = record.getDate();

                if (!date.isBefore(startDate) && !date.isAfter(endDate)) {

                    double hours = record.getTotalHours();

                    if (hours <= 8) {
                        regularHours += hours;
                    } else {
                        regularHours += 8;
                        overtimeHours += (hours - 8);
                    }
                }
            }
        }

        double regularPay = employee.calculateRegularPay(regularHours);
        double overtimePay = employee.calculateOvertimePay(overtimeHours);

        Map<String, Double> result = new HashMap<>();
        result.put("regularHours", regularHours);
        result.put("overtimeHours", overtimeHours);
        result.put("regularPay", regularPay);
        result.put("overtimePay", overtimePay);
        result.put("totalPay", regularPay + overtimePay);

        return result;
    }

    private Map<String, Double> getProRatedAllowanceDetails() {

        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        double workingDays = totalDays * 5 / 7.0;

        double ricePerDay = employee.getRiceSubsidy() / 21;
        double phonePerDay = employee.getPhoneAllowance() / 21;
        double clothingPerDay = employee.getClothingAllowance() / 21;

        double rice = ricePerDay * workingDays;
        double phone = phonePerDay * workingDays;
        double clothing = clothingPerDay * workingDays;

        Map<String, Double> result = new HashMap<>();
        result.put("riceSubsidy", rice);
        result.put("phoneAllowance", phone);
        result.put("clothingAllowance", clothing);
        result.put("totalAllowances", rice + phone + clothing);

        return result;
    }

    public double getTotalDeductions() {
        return deductions.values()
                .stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public Employee getEmployee() { return employee; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public double getRegularHours() { return regularHours; }
    public double getOvertimeHours() { return overtimeHours; }
    public double getGrossPay() { return grossPay; }
    public double getNetPay() { return netPay; }
    public Map<String, Double> getDeductions() { return deductions; }
    public Map<String, Double> getAllowances() { return allowances; }
}
