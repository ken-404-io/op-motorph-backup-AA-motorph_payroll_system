package com.motorph.controller;

import java.time.LocalDate;
import java.util.List;

import com.motorph.model.AttendanceRecord;
import com.motorph.model.Employee;
import com.motorph.service.EmployeeServiceInterface;

public class EmployeeController {

    private final EmployeeServiceInterface employeeService;

    public EmployeeController(EmployeeServiceInterface employeeService) {
        this.employeeService = employeeService;
    }

    public Employee findEmployeeById(int employeeId) throws IllegalArgumentException {
        if (employeeId <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }

        Employee employee = employeeService.findEmployeeById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee with ID " + employeeId + " not found");
        }

        return employee;
    }

    public List<Employee> searchEmployees(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            throw new IllegalArgumentException("Search term cannot be empty");
        }

        return employeeService.searchEmployees(searchTerm);
    }

    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    public boolean addEmployee(Employee employee) throws IllegalArgumentException {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        if (employee.getEmployeeId() <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }

        if (employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }

        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }

        if (employee.getPosition() == null || employee.getPosition().trim().isEmpty()) {
            throw new IllegalArgumentException("Position is required");
        }

        if (employee.getBasicSalary() <= 0) {
            throw new IllegalArgumentException("Basic salary must be greater than zero");
        }
        return employeeService.addEmployee(employee);
    }

    public boolean updateEmployee(Employee employee) throws IllegalArgumentException {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        if (employee.getEmployeeId() <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }

        if (employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }

        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }

        if (employee.getPosition() == null || employee.getPosition().trim().isEmpty()) {
            throw new IllegalArgumentException("Position is required");
        }

        if (employee.getBasicSalary() <= 0) {
            throw new IllegalArgumentException("Basic salary must be greater than zero");
        }

        return employeeService.updateEmployee(employee);
    }

    public boolean deleteEmployee(int employeeId) throws IllegalArgumentException {
        if (employeeId <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }

        return employeeService.deleteEmployee(employeeId);
    }

    public List<AttendanceRecord> getAttendanceRecords(int employeeId, LocalDate startDate, LocalDate endDate)
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

        return employeeService.getAttendanceRecords(employeeId, startDate, endDate);
    }

    public List<AttendanceRecord> getAllAttendanceRecords() {
        return employeeService.getAllAttendanceRecords();
    }

    public List<AttendanceRecord> getAttendanceRecordsForDate(LocalDate date) {
        return employeeService.getAttendanceRecordsForDate(date);
    }

    public List<AttendanceRecord> getAttendanceRecordsInRange(LocalDate startDate, LocalDate endDate) {
        return employeeService.getAttendanceRecordsInRange(startDate, endDate);
    }
}
