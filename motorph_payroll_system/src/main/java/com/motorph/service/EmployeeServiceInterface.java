package com.motorph.service;

import java.time.LocalDate;
import java.util.List;

import com.motorph.model.AttendanceRecord;
import com.motorph.model.Employee;

/**
 * ABSTRACTION: Defines the contract for employee-related operations.
 * Any implementation must provide these methods, hiding internal details.
 */
public interface EmployeeServiceInterface {
    Employee findEmployeeById(int employeeId);
    List<Employee> searchEmployees(String searchTerm);
    List<Employee> getAllEmployees();
    boolean addEmployee(Employee employee);
    boolean updateEmployee(Employee employee);
    boolean deleteEmployee(int employeeId);
    List<AttendanceRecord> getAttendanceRecords(int employeeId, LocalDate startDate, LocalDate endDate);
    List<AttendanceRecord> getAllAttendanceRecords();
    List<AttendanceRecord> getAttendanceRecordsForDate(LocalDate date);
    List<AttendanceRecord> getAttendanceRecordsInRange(LocalDate startDate, LocalDate endDate);
}
