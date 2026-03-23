package com.motorph.service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.motorph.model.AttendanceRecord;
import com.motorph.model.Employee;

// Inheritance
public class EmployeeService implements EmployeeServiceInterface {
    private final List<Employee> employees;
    private final List<AttendanceRecord> attendanceRecords;
    private final String csvFilePath;

    public EmployeeService(List<Employee> employees, List<AttendanceRecord> attendanceRecords, String csvFilePath) {
        this.employees = employees;
        this.attendanceRecords = attendanceRecords;
        this.csvFilePath = csvFilePath;
    }

    public Employee findEmployeeById(int employeeId) {
        return employees.stream()
                .filter(emp -> emp.getEmployeeId() == employeeId)
                .findFirst()
                .orElse(null);
    }

    public List<Employee> searchEmployees(String searchTerm) {
        String term = searchTerm.toLowerCase();

        return employees.stream()
                .filter(employee -> {
                    String empId = String.valueOf(employee.getEmployeeId()).toLowerCase();
                    String lastName = employee.getLastName().toLowerCase();
                    String firstName = employee.getFirstName().toLowerCase();
                    return empId.contains(term) || lastName.contains(term) || firstName.contains(term);
                })
                .collect(Collectors.toList());
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    public boolean addEmployee(Employee employee) {
        if (findEmployeeById(employee.getEmployeeId()) != null) {
            return false;
        }

        employees.add(employee);

        try {
            appendEmployeeToCSV(employee);
            return true;
        } catch (Exception e) {
            employees.remove(employee);
            throw new RuntimeException("Failed to save employee to CSV: " + e.getMessage(), e);
        }
    }

    private void appendEmployeeToCSV(Employee employee) throws Exception {
        try (java.io.FileWriter fileWriter = new java.io.FileWriter(csvFilePath, true);
                com.opencsv.CSVWriter writer = new com.opencsv.CSVWriter(fileWriter)) {
            String[] data = {
                    String.valueOf(employee.getEmployeeId()),
                    employee.getLastName(),
                    employee.getFirstName(),
                    employee.getBirthday() != null ? formatDateForCSV(employee.getBirthday()) : "",
                    employee.getAddress() != null ? employee.getAddress() : "",
                    employee.getPhoneNumber() != null ? employee.getPhoneNumber() : "",
                    employee.getSssNumber() != null ? employee.getSssNumber() : "",
                    employee.getPhilhealthNumber() != null ? employee.getPhilhealthNumber() : "",
                    employee.getTinNumber() != null ? employee.getTinNumber() : "",
                    employee.getPagibigNumber() != null ? employee.getPagibigNumber() : "",
                    employee.getStatus(),
                    employee.getPosition(),
                    employee.getSupervisor() != null ? employee.getSupervisor() : "N/A",
                    formatMoneyForCSV(employee.getBasicSalary()),
                    formatMoneyForCSV(employee.getRiceSubsidy()),
                    formatMoneyForCSV(employee.getPhoneAllowance()),
                    formatMoneyForCSV(employee.getClothingAllowance()),
                    formatMoneyForCSV(employee.getGrossSemiMonthlyRate()),
                    String.valueOf(employee.getHourlyRate())
            };
            writer.writeNext(data);
        }
    }

    public boolean updateEmployee(Employee employee) {
        Employee existingEmployee = findEmployeeById(employee.getEmployeeId());
        if (existingEmployee == null) {
            return false;
        }

        int index = employees.indexOf(existingEmployee);
        employees.set(index, employee);

        try {
            saveAllEmployeesToCSV();
            return true;
        } catch (Exception e) {
            employees.set(index, existingEmployee);
            throw new RuntimeException("Failed to update employee in CSV: " + e.getMessage(), e);
        }
    }

    public boolean deleteEmployee(int employeeId) {
        Employee existingEmployee = findEmployeeById(employeeId);
        if (existingEmployee == null) {
            return false;
        }

        employees.remove(existingEmployee);

        try {
            saveAllEmployeesToCSV();
            return true;
        } catch (Exception e) {
            employees.add(existingEmployee);
            throw new RuntimeException("Failed to delete employee from CSV: " + e.getMessage(), e);
        }
    }

    private void saveAllEmployeesToCSV() throws Exception {
        try (java.io.FileWriter fileWriter = new java.io.FileWriter(csvFilePath);
                com.opencsv.CSVWriter writer = new com.opencsv.CSVWriter(fileWriter)) {

            String[] header = {
                    "Employee Number", "Last Name", "First Name", "Birthday", "Address",
                    "Phone Number", "SSS Number", "Philhealth Number", "TIN Number",
                    "Pagibig Number", "Status", "Position", "Immediate Supervisor",
                    "Basic Salary", "Rice Subsidy", "Phone Allowance", "Clothing Allowance",
                    "Gross Semi-monthly Rate", "Hourly Rate"
            };
            writer.writeNext(header);
            for (Employee employee : employees) {
                String[] data = {
                        String.valueOf(employee.getEmployeeId()),
                        employee.getLastName(),
                        employee.getFirstName(),
                        employee.getBirthday() != null ? formatDateForCSV(employee.getBirthday()) : "",
                        employee.getAddress() != null ? employee.getAddress() : "",
                        employee.getPhoneNumber() != null ? employee.getPhoneNumber() : "",
                        employee.getSssNumber() != null ? employee.getSssNumber() : "",
                        employee.getPhilhealthNumber() != null ? employee.getPhilhealthNumber() : "",
                        employee.getTinNumber() != null ? employee.getTinNumber() : "",
                        employee.getPagibigNumber() != null ? employee.getPagibigNumber() : "",
                        employee.getStatus(),
                        employee.getPosition(),
                        employee.getSupervisor() != null ? employee.getSupervisor() : "N/A",
                        formatMoneyForCSV(employee.getBasicSalary()),
                        formatMoneyForCSV(employee.getRiceSubsidy()),
                        formatMoneyForCSV(employee.getPhoneAllowance()),
                        formatMoneyForCSV(employee.getClothingAllowance()),
                        formatMoneyForCSV(employee.getGrossSemiMonthlyRate()),
                        String.valueOf(employee.getHourlyRate())
                };
                writer.writeNext(data);
            }
        }
    }

    public List<AttendanceRecord> getAttendanceRecords(int employeeId, LocalDate startDate, LocalDate endDate) {
        return attendanceRecords.stream()
                .filter(record -> record.getEmployeeId() == employeeId)
                .filter(record -> {
                    LocalDate recordDate = record.getDate();
                    return recordDate != null &&
                            !recordDate.isBefore(startDate) &&
                            !recordDate.isAfter(endDate);
                }).collect(Collectors.toList());
    }

    public List<AttendanceRecord> getAllAttendanceRecords() {
        return new ArrayList<>(attendanceRecords);
    }

    public List<AttendanceRecord> getAttendanceRecordsForDate(LocalDate date) {
        return attendanceRecords.stream()
                .filter(record -> record.getDate().equals(date))
                .collect(Collectors.toList());
    }

    public List<AttendanceRecord> getAttendanceRecordsInRange(LocalDate startDate, LocalDate endDate) {
        return attendanceRecords.stream()
                .filter(record -> !record.getDate().isBefore(startDate) && !record.getDate().isAfter(endDate))
                .collect(Collectors.toList());
    }

    private String formatDateForCSV(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return date.format(formatter);
    }

    private String formatMoneyForCSV(double amount) {
        if (amount >= 1000) {
            NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
            return "\"" + formatter.format(amount) + "\"";
        } else {
            return "\"" + (int) amount + "\"";
        }
    }
}
