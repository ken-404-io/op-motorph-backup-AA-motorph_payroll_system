package com.motorph.factory;

import java.time.LocalDate;

import com.motorph.model.ContractualEmployee;
import com.motorph.model.Employee;
import com.motorph.model.RegularEmployee;

/**
 * POLYMORPHISM + FACTORY PATTERN: Creates the correct Employee subclass
 * based on the employee's status. The caller doesn't need to know
 * which concrete class is created - they just get an Employee.
 */
public class EmployeeFactory {

    /**
     * Creates the appropriate Employee subclass based on the status field.
     * This demonstrates POLYMORPHISM because the returned type is Employee,
     * but the actual object could be RegularEmployee or ContractualEmployee.
     */
    public static Employee createEmployee(
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
        // POLYMORPHISM: The factory decides which subclass to instantiate
        if ("Contractual".equalsIgnoreCase(status)) {
            return new ContractualEmployee(
                    employeeId, lastName, firstName,
                    position, status, basicSalary,
                    riceSubsidy, phoneAllowance, clothingAllowance
            );
        } else {
            // Default to RegularEmployee for "Regular" or any other status
            return new RegularEmployee(
                    employeeId, lastName, firstName, birthday, address, phoneNumber,
                    sssNumber, philhealthNumber, tinNumber, pagibigNumber,
                    status, position, supervisor, basicSalary, riceSubsidy,
                    phoneAllowance, clothingAllowance, grossSemiMonthlyRate
            );
        }
    }
}
