# MotorPH Payroll System

A Java-based payroll management system for MotorPH that handles employee records, attendance tracking, payroll processing, and report generation.

---

## Table of Contents

1. [Project Overview](#project-overview)
2. [Architecture](#architecture)
3. [UML Class Diagram](#uml-class-diagram)
4. [Package Structure](#package-structure)
5. [Design Patterns](#design-patterns)

---

## Project Overview

The MotorPH Payroll System automates payroll computation including:
- Employee management (add, update, delete, search)
- Attendance tracking and hour computation
- Gross pay and net pay calculation with government-mandated deductions (SSS, PhilHealth, Pag-IBIG, Withholding Tax)
- Payslip and payroll report generation
- Role-based authentication (Admin / Employee)

---

## Architecture

The system follows a layered MVC-like architecture:

```
┌──────────────────────────────────────┐
│              View (GUI)              │  ← Swing-based UI (excluded from UML)
├──────────────────────────────────────┤
│           Controllers                │  ← AuthenticationController, EmployeeController,
│                                      │    PayrollController, ReportController
├──────────────────────────────────────┤
│             Services                 │  ← AuthenticationService, EmployeeService,
│                                      │    PayrollService, ReportService, PayrollProcessor
├──────────────────────────────────────┤
│           Repository                 │  ← DataRepository, CSVCreateAndWrite
├──────────────────────────────────────┤
│           Data / CSV Files           │  ← employeeDetails.csv, attendanceRecord.csv,
│                                      │    userCredentials.csv
└──────────────────────────────────────┘
```

---

## UML Class Diagram

> GUI classes are excluded per project requirements.

```mermaid
classDiagram
    %% ─── INTERFACES ──────────────────────────────────────────────────────────
    class Payable {
        <<interface>>
        +calculateGrossPay() double
        +calculateDeductions() double
        +calculateNetPay() double
    }

    class Deduction {
        <<interface>>
        +calculate(grossPay double) double
        +getDeductionName() String
    }

    class EmployeeServiceInterface {
        <<interface>>
        +findEmployeeById(employeeId int) Employee
        +searchEmployees(searchTerm String) List~Employee~
        +getAllEmployees() List~Employee~
        +addEmployee(employee Employee) boolean
        +updateEmployee(employee Employee) boolean
        +deleteEmployee(employeeId int) boolean
        +getAttendanceRecords(id int, start LocalDate, end LocalDate) List~AttendanceRecord~
        +getAllAttendanceRecords() List~AttendanceRecord~
        +getAttendanceRecordsForDate(date LocalDate) List~AttendanceRecord~
        +getAttendanceRecordsInRange(start LocalDate, end LocalDate) List~AttendanceRecord~
    }

    class PayrollServiceInterface {
        <<interface>>
        +generatePayslip(employeeId int, start LocalDate, end LocalDate) PaySlip
        +generatePayroll(start LocalDate, end LocalDate) List~PaySlip~
    }

    %% ─── ABSTRACT CLASSES ────────────────────────────────────────────────────
    class Employee {
        <<abstract>>
        -int employeeId
        -String lastName
        -String firstName
        -LocalDate birthday
        -String address
        -String phoneNumber
        -String sssNumber
        -String philhealthNumber
        -String tinNumber
        -String pagibigNumber
        -String position
        -String status
        -String supervisor
        -double basicSalary
        -double riceSubsidy
        -double phoneAllowance
        -double clothingAllowance
        -double grossSemiMonthlyRate
        -double hourlyRate
        +calculateGrossPay()* double
        +calculateDeductions()* double
        +calculateNetPay()* double
        +calculateHourlyRate() void
        +getFullName() String
        +calculateRegularPay(hours double) double
        +calculateOvertimePay(hours double) double
        +getEmployeeId() int
        +getFirstName() String
        +getLastName() String
        +getBirthday() LocalDate
        +getAddress() String
        +getPhoneNumber() String
        +getSssNumber() String
        +getPhilhealthNumber() String
        +getTinNumber() String
        +getPagibigNumber() String
        +getPosition() String
        +getStatus() String
        +getSupervisor() String
        +getBasicSalary() double
        +getRiceSubsidy() double
        +getPhoneAllowance() double
        +getClothingAllowance() double
        +getHourlyRate() double
        +getGrossSemiMonthlyRate() double
    }

    class BaseDeduction {
        <<abstract>>
        -String deductionName
        +getDeductionName() String
        +toString() String
        +calculate(grossPay double)* double
    }

    %% ─── CONCRETE MODEL CLASSES ──────────────────────────────────────────────
    class RegularEmployee {
        +calculateGrossPay() double
        +calculateDeductions() double
        +calculateNetPay() double
    }

    class ContractualEmployee {
        +calculateGrossPay() double
        +calculateDeductions() double
        +calculateNetPay() double
    }

    class AttendanceRecord {
        -int employeeId
        -LocalDate date
        -LocalTime timeIn
        -LocalTime timeOut
        +getDuration() Duration
        +isLate() boolean
        +getTotalHours() double
        +getRegularHours() double
        +getOvertimeHours() double
        +getEmployeeId() int
        +setEmployeeId(employeeId int) void
        +getDate() LocalDate
        +setDate(date LocalDate) void
        +getTimeIn() LocalTime
        +setTimeIn(timeIn LocalTime) void
        +getTimeOut() LocalTime
        +setTimeOut(timeOut LocalTime) void
    }

    class User {
        -String username
        -String password
        -int employeeId
        -String role
        -boolean isActive
        +getUsername() String
        +setUsername(username String) void
        +getPassword() String
        +setPassword(password String) void
        +getEmployeeId() int
        +setEmployeeId(employeeId int) void
        +getRole() String
        +setRole(role String) void
        +isActive() boolean
        +setActive(isActive boolean) void
        +isValid() boolean
        +isAdmin() boolean
        +toString() String
        +equals(obj Object) boolean
        +hashCode() int
    }

    class SSSDeduction {
        -Map~Double,Double~ sssTable
        +calculate(grossPay double) double
    }

    class PhilHealthDeduction {
        +calculate(grossPay double) double
    }

    class PagIBIGDeduction {
        +calculate(grossPay double) double
    }

    class WithholdingTaxDeduction {
        +calculate(taxableIncome double) double
    }

    class PaySlip {
        -LocalDate startDate
        -LocalDate endDate
        -double regularHours
        -double overtimeHours
        -double grossPay
        -double netPay
        -Map~String,Double~ deductions
        -Map~String,Double~ allowances
        +generate(records List~AttendanceRecord~, calc PayrollProcessor) void
        +getTotalDeductions() double
        +getEmployee() Employee
        +getStartDate() LocalDate
        +getEndDate() LocalDate
        +getRegularHours() double
        +getOvertimeHours() double
        +getGrossPay() double
        +getNetPay() double
        +getDeductions() Map~String,Double~
        +getAllowances() Map~String,Double~
    }

    class PayrollStatus {
        <<enumeration>>
        DRAFT
        DATA_LOADED
        CALCULATED
        REVIEW
        PENDING_REVIEW
        APPROVED
        POSTED
        REJECTED
        -String displayName
        -String description
        +getDisplayName() String
        +getDescription() String
        +toString() String
    }

    class PayrollRun {
        -String runId
        -LocalDate startDate
        -LocalDate endDate
        -LocalDateTime createdAt
        -PayrollStatus status
        -List~PaySlip~ paySlips
        -double totalGrossPay
        -double totalNetPay
        -double totalDeductions
        -int employeeCount
        -String processedBy
        -LocalDateTime approvedAt
        -String approvedBy
        -String notes
        -String dataFile
        +getRunId() String
        +getStartDate() LocalDate
        +getEndDate() LocalDate
        +getCreatedAt() LocalDateTime
        +getStatus() PayrollStatus
        +setStatus(status PayrollStatus) void
        +getPaySlips() List~PaySlip~
        +setPaySlips(paySlips List~PaySlip~) void
        +getTotalGrossPay() double
        +getTotalNetPay() double
        +getTotalDeductions() double
        +getEmployeeCount() int
        +getProcessedBy() String
        +getApprovedAt() LocalDateTime
        +setApprovedAt(approvedAt LocalDateTime) void
        +getApprovedBy() String
        +setApprovedBy(approvedBy String) void
        +getNotes() String
        +setNotes(notes String) void
        +getDataFile() String
        +setDataFile(dataFile String) void
        +getPayPeriodString() String
    }

    %% ─── SERVICE CLASSES ─────────────────────────────────────────────────────
    class EmployeeService {
        -List~Employee~ employees
        -List~AttendanceRecord~ attendanceRecords
        -String csvFilePath
        +findEmployeeById(employeeId int) Employee
        +searchEmployees(searchTerm String) List~Employee~
        +getAllEmployees() List~Employee~
        +addEmployee(employee Employee) boolean
        +updateEmployee(employee Employee) boolean
        +deleteEmployee(employeeId int) boolean
        +getAttendanceRecords(id int, start LocalDate, end LocalDate) List~AttendanceRecord~
        +getAllAttendanceRecords() List~AttendanceRecord~
        +getAttendanceRecordsForDate(date LocalDate) List~AttendanceRecord~
        +getAttendanceRecordsInRange(start LocalDate, end LocalDate) List~AttendanceRecord~
    }

    class PayrollService {
        -List~Employee~ employees
        -List~AttendanceRecord~ attendanceRecords
        -PayrollProcessor payrollCalculator
        +generatePayslip(employeeId int, start LocalDate, end LocalDate) PaySlip
        +generatePayroll(start LocalDate, end LocalDate) List~PaySlip~
    }

    class PayrollProcessor {
        -Map~Double,Double~ sssTable
        +calculateGrossPay(employee Employee) double
        +calculateNetPay(grossPay double) double
        +calculateSSSContribution(grossPay double) double
        +calculatePhilHealthContribution(grossPay double) double
        +calculatePagIbigContribution(grossPay double) double
        +calculateWithholdingTax(taxableIncome double) double
    }

    class AuthenticationService {
        -List~User~ users
        +authenticateUser(username String, password String) User
        +validateLogin(username String, password String) boolean
        +getUserByUsername(username String) User
        +getActiveUsers() List~User~
        +getUserCount() int
        +isCredentialsFileAvailable() boolean
    }

    class ReportService {
        -EmployeeService employeeService
        -PayrollService payrollService
        +generatePayslipReport(employeeId int, start LocalDate, end LocalDate) PaySlip
        +generateSummaryReport(reportType String, start LocalDate, end LocalDate) List~Map~
    }

    %% ─── REPOSITORY CLASSES ──────────────────────────────────────────────────
    class DataRepository {
        -String employeesFilePath
        -String attendanceFilePath
        -List~Employee~ employees
        -List~AttendanceRecord~ attendanceRecords
        +getAllEmployees() List~Employee~
        +findEmployeeById(employeeId int) Employee
        +searchEmployees(searchTerm String) List~Employee~
        +getAllAttendanceRecords() List~AttendanceRecord~
        +getAttendanceRecords(employeeId int) List~AttendanceRecord~
        +getAttendanceRecords(id int, start LocalDate, end LocalDate) List~AttendanceRecord~
    }

    class CSVCreateAndWrite {
        +writeEmployees(employees List~Employee~, filePath String) void
    }

    %% ─── FACTORY ─────────────────────────────────────────────────────────────
    class EmployeeFactory {
        +createEmployee(employeeId int, lastName String, firstName String, birthday LocalDate, address String, phoneNumber String, sssNumber String, philhealthNumber String, tinNumber String, pagibigNumber String, status String, position String, supervisor String, basicSalary double, riceSubsidy double, phoneAllowance double, clothingAllowance double, grossSemiMonthlyRate double)$ Employee
    }

    %% ─── CONTROLLER CLASSES ──────────────────────────────────────────────────
    class AuthenticationController {
        -AuthenticationService authService
        +login(username String, password String) boolean
        +logout() boolean
        +isLoggedIn() boolean
        +getCurrentUser() User
        +getCurrentUsername() String
        +isCurrentUserAdmin() boolean
        +getSessionInfo() String
        +validateCredentials(username String, password String) boolean
        +getAuthenticationService() AuthenticationService
        +isCredentialsFileAvailable() boolean
        +getUserCount() int
    }

    class EmployeeController {
        -EmployeeServiceInterface employeeService
        +findEmployeeById(employeeId int) Employee
        +searchEmployees(searchTerm String) List~Employee~
        +getAllEmployees() List~Employee~
        +addEmployee(employee Employee) boolean
        +updateEmployee(employee Employee) boolean
        +deleteEmployee(employeeId int) boolean
        +getAttendanceRecords(id int, start LocalDate, end LocalDate) List~AttendanceRecord~
        +getAllAttendanceRecords() List~AttendanceRecord~
        +getAttendanceRecordsForDate(date LocalDate) List~AttendanceRecord~
        +getAttendanceRecordsInRange(start LocalDate, end LocalDate) List~AttendanceRecord~
    }

    class PayrollController {
        -PayrollServiceInterface payrollService
        +generatePayslip(employeeId int, start LocalDate, end LocalDate) PaySlip
        +generatePayroll(start LocalDate, end LocalDate) List~PaySlip~
    }

    class ReportController {
        -ReportService reportService
        +generatePayslipReport(employeeId int, start LocalDate, end LocalDate) PaySlip
        +generateSummaryReport(reportType String, start LocalDate, end LocalDate) List~Map~
    }

    %% ─── UTILITY CLASSES ─────────────────────────────────────────────────────
    class AppConstants {
        <<utility>>
        +STANDARD_WORK_DAYS_PER_MONTH$ int
        +REGULAR_HOURS_PER_DAY$ double
        +OVERTIME_RATE_MULTIPLIER$ double
        +PHILHEALTH_EMPLOYEE_CONTRIBUTION_RATE$ double
        +PHILHEALTH_TOTAL_CONTRIBUTION_RATE$ double
        +PAGIBIG_EMPLOYEE_CONTRIBUTION_RATE$ double
        +PAGIBIG_MAX_CONTRIBUTION_SALARY_CAP$ double
        +PAGIBIG_FIXED_RATE_ABOVE_CAP$ double
        +WITHHOLDING_TAX_EXEMPTION_THRESHOLD$ double
        +DEFAULT_EMPLOYEES_FILE_PATH$ String
        +DEFAULT_ATTENDANCE_FILE_PATH$ String
        +APP_TITLE$ String
        +DATE_FORMAT_PATTERN$ String
        +getEmployeeFilePath()$ String
        +getAttendanceFilePath()$ String
    }

    class AppUtils {
        <<utility>>
        -User currentUser$
        +setCurrentUser(user User)$ void
        +getCurrentUser()$ User
        +isUserLoggedIn()$ boolean
        +clearSession()$ void
        +validateEmployeeNumber(employeeNumberStr String)$ int
        +validateDate(dateStr String)$ LocalDate
        +validateDateRange(start LocalDate, end LocalDate)$ void
        +validateName(name String, fieldName String)$ String
        +validateSalary(salaryStr String, fieldName String)$ double
        +validatePhoneNumber(phone String)$ String
        +formatDate(date LocalDate)$ String
        +formatDateRange(start LocalDate, end LocalDate)$ String
        +showError(message String)$ void
        +showSuccess(message String)$ void
        +showConfirm(message String)$ boolean
        +handleException(operation String, e Exception)$ void
    }

    class Main {
        +main(args String[])$ void
        +initializeApplication()$ void
        +showLoginScreen()$ void
    }

    %% ─── INHERITANCE ─────────────────────────────────────────────────────────
    Employee <|-- RegularEmployee
    Employee <|-- ContractualEmployee
    BaseDeduction <|-- SSSDeduction
    BaseDeduction <|-- PhilHealthDeduction
    BaseDeduction <|-- PagIBIGDeduction
    BaseDeduction <|-- WithholdingTaxDeduction

    %% ─── INTERFACE REALIZATIONS ──────────────────────────────────────────────
    Payable <|.. Employee
    Deduction <|.. BaseDeduction
    EmployeeServiceInterface <|.. EmployeeService
    PayrollServiceInterface <|.. PayrollService

    %% ─── ASSOCIATIONS & DEPENDENCIES ─────────────────────────────────────────
    PaySlip "1" --> "1" Employee : contains
    PaySlip "1" --> "*" AttendanceRecord : uses
    PaySlip "1" --> "1" PayrollProcessor : uses

    PayrollRun "1" --> "*" PaySlip : contains
    PayrollRun "1" --> "1" PayrollStatus : has status

    EmployeeService "1" --> "*" Employee : manages
    EmployeeService "1" --> "*" AttendanceRecord : manages

    PayrollService "1" --> "*" Employee : references
    PayrollService "1" --> "*" AttendanceRecord : references
    PayrollService "1" --> "1" PayrollProcessor : uses

    ReportService "1" --> "1" EmployeeService : uses
    ReportService "1" --> "1" PayrollService : uses

    AuthenticationService "1" --> "*" User : manages

    DataRepository "1" --> "*" Employee : loads
    DataRepository "1" --> "*" AttendanceRecord : loads

    EmployeeFactory ..> RegularEmployee : creates
    EmployeeFactory ..> ContractualEmployee : creates

    AuthenticationController "1" --> "1" AuthenticationService : delegates to
    EmployeeController "1" --> "1" EmployeeServiceInterface : delegates to
    PayrollController "1" --> "1" PayrollServiceInterface : delegates to
    ReportController "1" --> "1" ReportService : delegates to

    Main ..> DataRepository : initializes
    Main ..> EmployeeService : creates
    Main ..> PayrollService : creates
    Main ..> AuthenticationController : creates
    Main ..> EmployeeController : creates
    Main ..> PayrollController : creates
    Main ..> ReportController : creates
```

---

## Package Structure

```
com.motorph
├── Main.java                          ← Application entry point
├── model/
│   ├── Employee.java                  ← Abstract base employee class
│   ├── RegularEmployee.java           ← Full-time employee
│   ├── ContractualEmployee.java       ← Contractual employee
│   ├── AttendanceRecord.java          ← Daily time record
│   ├── User.java                      ← System user / credentials
│   ├── PaySlip.java                   ← Generated payslip
│   ├── PayrollRun.java                ← Payroll batch run
│   ├── PayrollStatus.java             ← Payroll workflow enum
│   ├── Payable.java                   ← Interface for pay computation
│   ├── Deduction.java                 ← Deduction strategy interface
│   ├── BaseDeduction.java             ← Abstract deduction base
│   ├── SSSDeduction.java
│   ├── PhilHealthDeduction.java
│   ├── PagIBIGDeduction.java
│   └── WithholdingTaxDeduction.java
├── service/
│   ├── EmployeeServiceInterface.java
│   ├── PayrollServiceInterface.java
│   ├── EmployeeService.java
│   ├── PayrollService.java
│   ├── PayrollProcessor.java
│   ├── AuthenticationService.java
│   └── ReportService.java
├── controller/
│   ├── AuthenticationController.java
│   ├── EmployeeController.java
│   ├── PayrollController.java
│   └── ReportController.java
├── repository/
│   ├── DataRepository.java
│   └── CSVCreateAndWrite.java
├── factory/
│   └── EmployeeFactory.java
└── util/
    ├── AppConstants.java
    └── AppUtils.java
```

---

## Design Patterns

| Pattern | Classes |
|---|---|
| **Abstract Class** | `Employee`, `BaseDeduction` |
| **Factory** | `EmployeeFactory` → creates `RegularEmployee` or `ContractualEmployee` |
| **Strategy** | `Deduction` interface → `SSSDeduction`, `PhilHealthDeduction`, `PagIBIGDeduction`, `WithholdingTaxDeduction` |
| **Repository** | `DataRepository` for CSV-based data access |
| **Service Layer** | `EmployeeService`, `PayrollService`, `ReportService`, `AuthenticationService` |
| **MVC Controller** | `EmployeeController`, `PayrollController`, `ReportController`, `AuthenticationController` |
| **Composition** | `PaySlip` composes `Employee`; `PayrollRun` composes `PaySlip` list |
| **Enum State Machine** | `PayrollStatus` manages workflow states |

---

## UML Diagram Link

View the interactive class diagram online:
[https://mermaid.live/view#pako:eNqNWEtv2zAM_iuGTxuQouu6DcMOBYruMBQotsMOQQ-0RcdCZMmQ5LYI8t9Hyo_Yjp3GJ0vfx4-UKPqJFqKkKEN7WdXaVFh_2x0OpXZqe9VaY2ktiU9U6Z09FRTHVCqtSULhJoWxJO1qrJxX6wS5n_A1XcVq3qvjVr6mriXasO2-0BvRe0VDpkPc6m3bTR2aJ3dqHJCbRqoqq5i9lpOtFJFhVb4ItbPVVrWfNHl8kkKWGnQOAiEJpYUXMwv7xXQ7JVxKTQaTRZnDmHWaFCOTwnXY_Q](https://mermaid.live)

> To view the full diagram: go to [mermaid.live](https://mermaid.live), click **"Edit"**, and paste the Mermaid code block from the **UML Class Diagram** section above.
