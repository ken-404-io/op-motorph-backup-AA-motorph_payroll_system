# OOP Implementation Fixes — MotorPH Payroll System

**Project:** MotorPH Payroll System
**Date:** March 23, 2026
**Purpose:** This document details the Object-Oriented Programming fixes applied to the MotorPH Payroll System backend in response to the feedback that abstraction, inheritance, and polymorphism were not present in the previous version of the program.

---

## Background

The previous version of the system was noted to have a properly layered backend (following an MVC-like structure with models, services, and controllers), and encapsulation was already applied. However, the three remaining pillars of OOP — abstraction, inheritance, and polymorphism — were identified as missing or not properly implemented. This document describes exactly what was added and changed to address each missing pillar.

---

## Fix 1 — Abstraction

Abstraction was introduced through the creation of interfaces and abstract classes that define contracts without exposing implementation details.

The `Payable` interface was created under `com.motorph.model` to define the contract that any entity capable of pay computation must fulfill. It declares three methods: `calculateGrossPay()`, `calculateDeductions()`, and `calculateNetPay()`. No implementation is provided at the interface level — the interface only states what must exist.

The `Deduction` interface was created under `com.motorph.model` to define the contract for all payroll deduction types. It declares `calculate(double grossPay)` and `getDeductionName()`. This means any class that represents a deduction is required to provide its own calculation logic without the rest of the system needing to know how that logic works internally.

The `EmployeeServiceInterface` was created under `com.motorph.service` to define the contract for all employee-related operations such as finding, adding, updating, deleting, and retrieving attendance records. The controllers in the system depend on this interface, not on the concrete service class.

The `PayrollServiceInterface` was created under `com.motorph.service` to define the contract for payroll operations such as generating a payslip or running a full payroll. Again, the controller layer interacts with the interface, not the implementation.

The `Employee` class itself was converted from a plain concrete class into an abstract class. It declares `calculateGrossPay()`, `calculateDeductions()`, and `calculateNetPay()` as abstract methods, meaning it cannot be instantiated directly and forces every concrete employee type to provide its own implementations.

The `BaseDeduction` class was created as an abstract class that implements the `Deduction` interface. It provides the shared `deductionName` field and its getter, while leaving the `calculate()` method abstract for each subclass to implement.

---

## Fix 2 — Inheritance

Inheritance was introduced through two clear hierarchies that did not exist in the previous version.

The first hierarchy is the Employee hierarchy. The `Employee` class, now abstract, serves as the parent. Two concrete subclasses were created: `RegularEmployee` and `ContractualEmployee`. Both extend `Employee` using the `extends` keyword and call the parent constructor using `super()` to initialize all shared fields. All common employee data — such as employee ID, name, birthday, address, government numbers, salary, and allowances — is defined once in `Employee` and inherited by both subclasses. Neither subclass duplicates these fields.

The second hierarchy is the Deduction hierarchy. `BaseDeduction` is an abstract class that implements the `Deduction` interface and provides shared behavior. Four concrete deduction classes extend `BaseDeduction`: `SSSDeduction`, `PhilHealthDeduction`, `PagIBIGDeduction`, and `WithholdingTaxDeduction`. Each calls `super("DeductionName")` in its constructor to set the shared deduction name, and each provides its own implementation of `calculate()`.

The `EmployeeFactory` class was also introduced to properly instantiate the correct subclass based on an employee's status field, ensuring the inheritance hierarchy is used correctly throughout the system.

---

## Fix 3 — Polymorphism

Polymorphism was introduced in two primary areas of the system.

The first is through the Employee hierarchy. Both `RegularEmployee` and `ContractualEmployee` override the three abstract methods declared in `Employee`. When `calculateGrossPay()` is called on an `Employee` reference at runtime, the JVM determines the actual object type and executes the correct logic. For `RegularEmployee`, gross pay is computed as half the basic salary. For `ContractualEmployee`, gross pay is computed based on the standard work days and hourly rate. The rest of the system — particularly the payroll processing logic — simply calls `employee.calculateGrossPay()` without needing to check which type of employee it is dealing with.

The second is through the Deduction hierarchy. All four deduction classes override `calculate(double grossPay)` with entirely different logic. `SSSDeduction` uses a bracket-based lookup table with over thirty salary ranges. `PhilHealthDeduction` uses a fixed percentage of gross pay. `PagIBIGDeduction` uses a capped percentage. `WithholdingTaxDeduction` uses a progressive tax bracket system. The payroll processor iterates over a list of `Deduction` references and calls `calculate()` on each — the specific strategy applied depends entirely on which object is in the list at that moment.

The `EmployeeFactory` further demonstrates polymorphism by returning an `Employee` reference regardless of whether a `RegularEmployee` or `ContractualEmployee` was actually created. The calling code works with the abstract type and is decoupled from the concrete type.

---

## Summary of Changes

The following classes and interfaces were added or significantly modified to implement the three missing OOP pillars:

`com.motorph.model.Payable` — new interface for pay computation abstraction
`com.motorph.model.Deduction` — new interface for deduction abstraction
`com.motorph.model.Employee` — converted to abstract class; abstract methods added
`com.motorph.model.RegularEmployee` — new concrete subclass of Employee
`com.motorph.model.ContractualEmployee` — new concrete subclass of Employee
`com.motorph.model.BaseDeduction` — new abstract class implementing Deduction
`com.motorph.model.SSSDeduction` — new concrete subclass of BaseDeduction
`com.motorph.model.PhilHealthDeduction` — new concrete subclass of BaseDeduction
`com.motorph.model.PagIBIGDeduction` — new concrete subclass of BaseDeduction
`com.motorph.model.WithholdingTaxDeduction` — new concrete subclass of BaseDeduction
`com.motorph.factory.EmployeeFactory` — new class applying Factory Pattern with polymorphism
`com.motorph.service.EmployeeServiceInterface` — new service interface for abstraction
`com.motorph.service.PayrollServiceInterface` — new service interface for abstraction

Encapsulation, which was already identified as present in the previous version, was retained and maintained across all new and existing classes. All fields remain private with access controlled through public getter methods.

---

*This document was prepared to accompany the updated MotorPH Payroll System submission.*
