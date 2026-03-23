package com.motorph.repository;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.motorph.model.Employee;
import com.opencsv.CSVWriter;

/**
 * Utility class for writing employee data to CSV files.
 * Use this when you need to create or overwrite an employee CSV file.
 */
public class CSVCreateAndWrite {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Writes a list of employees to a CSV file, including a header row.
     * Overwrites the file if it already exists.
     *
     * @param employees The list of employees to write
     * @param filePath  The path of the CSV file to create or overwrite
     * @throws IOException if the file cannot be written
     */
    public void writeEmployees(List<Employee> employees, String filePath) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath);
             CSVWriter writer = new CSVWriter(fileWriter)) {

            writer.writeNext(buildHeader());

            for (Employee employee : employees) {
                writer.writeNext(buildRow(employee));
            }
        }
    }

    private String[] buildHeader() {
        return new String[]{
            "Employee Number", "Last Name", "First Name", "Birthday",
            "Address", "Phone Number", "SSS Number", "Philhealth Number",
            "TIN Number", "Pagibig Number", "Status", "Position",
            "Immediate Supervisor", "Basic Salary", "Rice Subsidy",
            "Phone Allowance", "Clothing Allowance", "Gross Semi-monthly Rate", "Hourly Rate"
        };
    }

    private String[] buildRow(Employee employee) {
        return new String[]{
            String.valueOf(employee.getEmployeeId()),
            employee.getLastName(),
            employee.getFirstName(),
            employee.getBirthday() != null ? employee.getBirthday().format(DATE_FORMAT) : "",
            employee.getAddress() != null ? employee.getAddress() : "",
            employee.getPhoneNumber() != null ? employee.getPhoneNumber() : "",
            employee.getSssNumber() != null ? employee.getSssNumber() : "",
            employee.getPhilhealthNumber() != null ? employee.getPhilhealthNumber() : "",
            employee.getTinNumber() != null ? employee.getTinNumber() : "",
            employee.getPagibigNumber() != null ? employee.getPagibigNumber() : "",
            employee.getStatus(),
            employee.getPosition(),
            employee.getSupervisor() != null ? employee.getSupervisor() : "",
            String.valueOf(employee.getBasicSalary()),
            String.valueOf(employee.getRiceSubsidy()),
            String.valueOf(employee.getPhoneAllowance()),
            String.valueOf(employee.getClothingAllowance()),
            String.valueOf(employee.getGrossSemiMonthlyRate()),
            String.valueOf(employee.getHourlyRate())
        };
    }
}
