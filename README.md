MotorPH Payroll System
OOP Implementation Documentation Summary
Project Overview

The MotorPH Payroll System was refactored from a procedural CP2 implementation into a fully Object-Oriented Programming (OOP) architecture. The goal of the refactoring was to improve maintainability, modularity, and scalability by separating responsibilities into well-defined classes and layers.

The system manages employee records, attendance data, payroll calculations, and payroll reporting through a graphical user interface (GUI).

System Architecture

The system follows a layered architecture:

GUI (Swing)
     ↓
Controllers
     ↓
Services
     ↓
Processing Logic
     ↓
Data Models

Layer Responsibilities

View (GUI)

Provides user interface components.

Handles user interaction.

Displays payroll results and reports.

Controller

Acts as the mediator between GUI and backend services.

Processes user actions and triggers system operations.

Service Layer

Contains business logic.

Coordinates payroll computation and employee data processing.

Processor / Logic Layer

Handles payroll computation formulas and deduction calculations.

Model Layer

Represents system entities such as employees, attendance records, and payslips.

Key Object-Oriented Concepts Applied
Encapsulation

All employee attributes and payroll data are encapsulated within classes with controlled access through getters and setters.

Example:

Employee
 ├─ employeeId
 ├─ firstName
 ├─ lastName
 ├─ salary
 └─ hourlyRate

Inheritance

The Employee class serves as a base class for different employee types.

Employee
   │
   ├── RegularEmployee
   └── ContractualEmployee

Each subclass overrides payroll computation behavior depending on employment type.

Each subclass overrides payroll computation behavior depending on employment type.

Example: calculateGrossPay()

- RegularEmployee computes salary based on monthly rate.
- ContractualEmployee computes salary based on hourly rate.

PayrollProcessor also supports method overloading for additional payroll scenarios.

Abstraction

Core payroll computation methods are defined at a higher abstraction level and implemented within appropriate classes.

Example methods include:

calculateGrossPay()
computeSSS()
computePhilHealth()
computePagIBIG()
computeTax()
computeNetPay()

Core System Components
Employee (Parent Class)

Represents a general employee and contains shared payroll attributes.

Responsibilities:

Store employee personal information

Store salary and allowance details

Compute hourly rate

Provide shared payroll methods

RegularEmployee (Subclass)

Represents employees with fixed monthly salaries.

Responsibilities:

Override gross pay computation

Apply monthly salary calculation logic

ContractualEmployee (Subclass)

Represents hourly-based employees.

Responsibilities:

Override gross pay calculation

Compute salary based on hours worked

PayrollProcessor

Handles payroll calculation logic and deduction sequencing.

Responsibilities:

Calculate gross salary

Compute SSS contributions

Compute PhilHealth contributions

Compute Pag-IBIG contributions

Compute withholding tax

Calculate net pay

PayrollService

Coordinates payroll generation using attendance records and employee data.

Responsibilities:

Generate payslips

Process payroll for multiple employees

Integrate attendance data with payroll calculations

PaySlip

Represents a payroll record for a specific employee and pay period.

Responsibilities:

Calculate working hours

Apply allowances

Apply deductions

Generate final net pay

GUI Components

The graphical interface is implemented using Java Swing and provides several management modules.

Dashboard

Displays system overview and payroll statistics.

Employee Management

Allows administrators to:

View employee records

Search employees

Manage employee data

Attendance Viewer

Displays employee attendance records with:

Month/year filtering

Attendance analytics

Export functionality

Payroll Management

Handles payroll processing workflow:

Load Attendance Data
      ↓
Calculate Payroll
      ↓
Review Payroll Details
      ↓
Approve Payroll
      ↓
Generate Payroll Report
Payroll Computation Workflow

The payroll computation process follows this sequence:

Attendance Records
        ↓
Total Hours Calculation
        ↓
Gross Pay Computation
        ↓
Deduction Calculation
   • SSS
   • PhilHealth
   • Pag-IBIG
   • Withholding Tax
        ↓
Allowance Computation
        ↓
Net Pay Calculation
        ↓
Payroll Report Generation
Testing and Validation

A smoke test was performed to verify the successful integration of the OOP backend with the GUI.

The following checks were validated:

GUI launches successfully

Employee objects instantiate correctly

Attendance records load properly

Payroll calculations execute without runtime errors

Payroll results display in the GUI

Payroll reports generate successfully

System Improvements from CP2

The refactoring introduced several improvements over the original procedural implementation.

CP2 Implementation	OOP Implementation
Payroll logic inside GUI	Payroll logic moved to backend classes
Procedural calculations	Encapsulated payroll methods
Limited code organization	Layered architecture
Hard-coded logic	Reusable payroll processor
Difficult maintenance	Modular and scalable design
Conclusion

The MotorPH Payroll System was successfully refactored into an Object-Oriented architecture. The system now provides improved maintainability, scalability, and separation of concerns. Payroll computations are handled through dedicated classes, while the GUI focuses solely on user interaction and data presentation.

The integration of inheritance, polymorphism, encapsulation, and abstraction demonstrates a complete application of Object-Oriented Programming principles within a real-world payroll management system.

Date: March 22, 2026
