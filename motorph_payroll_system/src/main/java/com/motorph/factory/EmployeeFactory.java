package com.motorph.factory;

import java.time.LocalDate;

import com.motorph.model.ContractualEmployee;
import com.motorph.model.Employee;
import com.motorph.model.RegularEmployee;

// Polymorphism
public class EmployeeFactory {

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
        if ("Contractual".equalsIgnoreCase(status)) {
            return new ContractualEmployee(
                    employeeId, lastName, firstName, birthday, address, phoneNumber,
                    sssNumber, philhealthNumber, tinNumber, pagibigNumber,
                    status, position, supervisor, basicSalary, riceSubsidy,
                    phoneAllowance, clothingAllowance, grossSemiMonthlyRate
            );
        } else {
            return new RegularEmployee(
                    employeeId, lastName, firstName, birthday, address, phoneNumber,
                    sssNumber, philhealthNumber, tinNumber, pagibigNumber,
                    status, position, supervisor, basicSalary, riceSubsidy,
                    phoneAllowance, clothingAllowance, grossSemiMonthlyRate
            );
        }
    }
}
