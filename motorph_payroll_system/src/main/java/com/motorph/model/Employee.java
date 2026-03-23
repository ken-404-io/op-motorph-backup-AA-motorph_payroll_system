package com.motorph.model;

import com.motorph.util.AppConstants;
import java.time.LocalDate;

// Abstraction
public abstract class Employee {

    // Encapsulation
    private int employeeId;
    private String lastName;
    private String firstName;
    private LocalDate birthday;
    private String address;
    private String phoneNumber;
    private String sssNumber;
    private String philhealthNumber;
    private String tinNumber;
    private String pagibigNumber;
    private String position;
    private String status;
    private String supervisor;

    private double basicSalary;
    private double riceSubsidy;
    private double phoneAllowance;
    private double clothingAllowance;
    private double grossSemiMonthlyRate;
    private double hourlyRate;

    public Employee(
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
            double grossSemiMonthlyRate
    ) {
        this.employeeId = employeeId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.sssNumber = sssNumber;
        this.philhealthNumber = philhealthNumber;
        this.tinNumber = tinNumber;
        this.pagibigNumber = pagibigNumber;
        this.status = status;
        this.position = position;
        this.supervisor = supervisor;
        this.basicSalary = basicSalary;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.grossSemiMonthlyRate = grossSemiMonthlyRate;
        calculateHourlyRate();
    }

    public Employee(int employeeId, String lastName, String firstName, String position,
                    String status, double basicSalary, double riceSubsidy,
                    double phoneAllowance, double clothingAllowance) {
        this.employeeId = employeeId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.position = position;
        this.status = status;
        this.basicSalary = basicSalary;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        calculateHourlyRate();
    }

    // Abstraction
    public abstract double calculateGrossPay();
    public abstract double calculateDeductions();
    public abstract double calculateNetPay();

    public final void calculateHourlyRate() {
        this.hourlyRate = basicSalary /
                (AppConstants.STANDARD_WORK_DAYS_PER_MONTH *
                        AppConstants.REGULAR_HOURS_PER_DAY);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public double calculateRegularPay(double hours) {
        return hours * hourlyRate;
    }

    public double calculateOvertimePay(double hours) {
        return hours * hourlyRate * 1.25;
    }

    public int getEmployeeId() { return employeeId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getBirthday() { return birthday; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getSssNumber() { return sssNumber; }
    public String getPhilhealthNumber() { return philhealthNumber; }
    public String getTinNumber() { return tinNumber; }
    public String getPagibigNumber() { return pagibigNumber; }
    public String getPosition() { return position; }
    public String getStatus() { return status; }
    public String getSupervisor() { return supervisor; }
    public double getBasicSalary() { return basicSalary; }
    public double getRiceSubsidy() { return riceSubsidy; }
    public double getPhoneAllowance() { return phoneAllowance; }
    public double getClothingAllowance() { return clothingAllowance; }
    public double getHourlyRate() { return hourlyRate; }
    public double getGrossSemiMonthlyRate() { return grossSemiMonthlyRate; }
}
