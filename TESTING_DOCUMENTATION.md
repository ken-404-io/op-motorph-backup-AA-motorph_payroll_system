# 4. Testing & Refinement Summary

---

## 4.1. Internal Testing (Week 10)

Internal testing was conducted by the development team to verify that all core functionalities of the MotorPH Payroll System work correctly. Testing covered the following modules: Authentication, Employee Management, Attendance, Payroll Computation, Deductions, Reports, and the Dashboard.

---

### 4.1.1 Authentication Module

| Test ID | Test Case | Steps | Expected Result | Actual Result | Status |
|---------|-----------|-------|-----------------|---------------|--------|
| TC-A01 | Valid Login | 1. Enter valid username and password. 2. Click Login. | System logs in and shows main dashboard. | System logged in successfully and redirected to dashboard. | PASS |
| TC-A02 | Invalid Password | 1. Enter valid username. 2. Enter wrong password. 3. Click Login. | System shows error message "Invalid credentials". | Error message displayed correctly. | PASS |
| TC-A03 | Empty Username | 1. Leave username blank. 2. Enter password. 3. Click Login. | System shows validation error. | Validation error displayed. | PASS |
| TC-A04 | Empty Password | 1. Enter username. 2. Leave password blank. 3. Click Login. | System shows validation error. | Validation error displayed. | PASS |
| TC-A05 | Empty Both Fields | 1. Leave both fields blank. 2. Click Login. | System shows validation error. | Validation error displayed. | PASS |
| TC-A06 | Inactive User Login | 1. Enter credentials of an inactive user. 2. Click Login. | System rejects login with appropriate message. | System rejected login correctly. | PASS |
| TC-A07 | Admin Role Access | 1. Login as admin user. | Admin can access all modules including employee management. | All modules accessible. | PASS |
| TC-A08 | Session Persistence | 1. Login. 2. Navigate across modules. | Username remains visible across all screens. | Username visible in header throughout session. | PASS |

---

### 4.1.2 Employee Management Module

| Test ID | Test Case | Steps | Expected Result | Actual Result | Status |
|---------|-----------|-------|-----------------|---------------|--------|
| TC-E01 | View All Employees | 1. Navigate to Employee Management. | All employees are listed in the table. | All employees loaded from CSV and displayed. | PASS |
| TC-E02 | Add New Regular Employee | 1. Click Add New. 2. Fill in all fields with valid data. 3. Set Status to Regular. 4. Click Save. | New employee is saved and appears in the table. | Employee saved to CSV and displayed in table. | PASS |
| TC-E03 | Add New Contractual Employee | 1. Click Add New. 2. Fill in all fields. 3. Set Status to Contractual. 4. Click Save. | New contractual employee is saved correctly. | Employee saved with correct type. | PASS |
| TC-E04 | Add Employee with Duplicate ID | 1. Click Add New. 2. Enter an existing Employee ID. 3. Click Save. | System shows "Duplicate Employee ID" error. | Error message shown, record not saved. | PASS |
| TC-E05 | Add Employee with Empty Fields | 1. Click Add New. 2. Leave required fields blank. 3. Click Save. | System shows validation errors for each empty required field. | Validation errors displayed correctly. | PASS |
| TC-E06 | Edit Employee | 1. Select an employee. 2. Click Edit. 3. Change position and salary. 4. Click Update. | Changes are saved and reflected in the table. | Employee record updated in CSV and table. | PASS |
| TC-E07 | Edit Employee ID Field | 1. Select employee. 2. Click Edit. 3. Try to change Employee ID. | Employee ID field should be disabled/locked in edit mode. | ID field was disabled, cannot be changed. | PASS |
| TC-E08 | Delete Employee | 1. Select an employee. 2. Click Delete. 3. Confirm deletion. | Employee is removed from the list and CSV. | Employee deleted successfully. | PASS |
| TC-E09 | Cancel Delete Employee | 1. Select an employee. 2. Click Delete. 3. Click Cancel on confirmation. | Employee is NOT deleted. | Employee remained in the list. | PASS |
| TC-E10 | Search by Employee ID | 1. Enter a valid Employee ID in the search bar. | Matching employee appears in table. | Correct employee displayed. | PASS |
| TC-E11 | Search by First Name | 1. Enter a first name in the search bar. | All employees with matching first name appear. | Correct results returned. | PASS |
| TC-E12 | Search by Last Name | 1. Enter a last name in the search bar. | All employees with matching last name appear. | Correct results returned. | PASS |
| TC-E13 | Search with No Match | 1. Enter a name that does not exist. | Table shows no results / empty state. | No results shown with appropriate message. | PASS |
| TC-E14 | View Employee Details | 1. Select an employee. 2. Click View Details. | Employee details dialog shows all information. | All fields (personal, employment, compensation, government IDs) displayed. | PASS |
| TC-E15 | Hourly Rate Auto-Calculation | 1. Add employee with a basic salary. | Hourly rate is auto-calculated (Basic Salary / 22 / 8). | Hourly rate calculated and stored correctly. | PASS |
| TC-E16 | Invalid Salary Input | 1. Click Add New. 2. Enter letters in Basic Salary field. 3. Click Save. | System shows validation error for salary field. | Validation error shown. | PASS |
| TC-E17 | Invalid Phone Number | 1. Click Add New. 2. Enter invalid phone format. 3. Click Save. | System shows phone number validation error. | Validation error shown. | PASS |

---

### 4.1.3 Attendance Module

| Test ID | Test Case | Steps | Expected Result | Actual Result | Status |
|---------|-----------|-------|-----------------|---------------|--------|
| TC-AT01 | View Attendance Records | 1. Select an employee. 2. Click View Attendance. | Attendance records are displayed in the dialog. | All attendance records loaded and displayed. | PASS |
| TC-AT02 | Filter by Month | 1. Open attendance viewer. 2. Select a specific month. 3. Click Apply Filter. | Only records for the selected month are shown. | Filtered records displayed correctly. | PASS |
| TC-AT03 | Filter by Year | 1. Open attendance viewer. 2. Select a year. 3. Click Apply Filter. | Only records for the selected year are shown. | Filtered records displayed correctly. | PASS |
| TC-AT04 | Filter by Month and Year | 1. Select both month and year. 2. Click Apply Filter. | Records for that specific month and year shown. | Correct records displayed. | PASS |
| TC-AT05 | Reset Filter | 1. Apply a filter. 2. Click Reset. | All attendance records are shown again. | Filter reset, all records displayed. | PASS |
| TC-AT06 | Late Detection | 1. View attendance for an employee with time-in after 8:10 AM. | Status column shows "Late" for those records. | Late status correctly shown in red. | PASS |
| TC-AT07 | On Time Detection | 1. View attendance for employee with time-in at or before 8:10 AM. | Status column shows "On Time". | On Time status correctly shown in green. | PASS |
| TC-AT08 | Regular Hours Calculation | 1. Check an attendance record with 8 hours worked. | Regular hours shows 8.00, Overtime shows 0.00. | Hours calculated correctly. | PASS |
| TC-AT09 | Overtime Hours Calculation | 1. Check an attendance record with more than 8 hours worked. | Overtime hours shows the hours beyond 8. | Overtime calculated correctly. | PASS |
| TC-AT10 | Attendance Analytics - Total Days | 1. Apply a monthly filter. | Total Days card shows correct count of records. | Count matches number of attendance records. | PASS |
| TC-AT11 | Attendance Analytics - Late Count | 1. Apply a filter. | Late Arrivals card shows correct late count. | Correct late arrival count displayed. | PASS |
| TC-AT12 | Attendance Analytics - Attendance Rate | 1. Apply a monthly filter. | Attendance Rate shows percentage vs expected working days. | Rate calculated correctly with progress bar. | PASS |
| TC-AT13 | No Records Found | 1. Filter by a month with no records. | Analytics shows zeros, table is empty. | Empty table and zero analytics displayed. | PASS |

---

### 4.1.4 Payroll Computation Module

| Test ID | Test Case | Steps | Expected Result | Actual Result | Status |
|---------|-----------|-------|-----------------|---------------|--------|
| TC-P01 | Load Attendance Data (Default) | 1. Go to Payroll. 2. Click Load Attendance Data. 3. Choose Use Default File. 4. Select pay period. | System confirms data loaded and enables Calculate button. | Data loaded successfully. | PASS |
| TC-P02 | Upload CSV File | 1. Click Load Attendance Data. 2. Choose Upload CSV. 3. Select valid CSV file. | System accepts file and enables Calculate. | File uploaded successfully. | PASS |
| TC-P03 | Calculate Payroll | 1. Load data. 2. Click Calculate Payroll. | Payroll computed for all employees, table populated. | Table populated with all employee payroll data. | PASS |
| TC-P04 | Gross Pay - Regular Employee | 1. Calculate payroll. 2. Check gross pay for a regular employee. | Gross Pay = Regular Hours × Hourly Rate + Overtime Hours × Hourly Rate × 1.25 | Calculation matches expected formula. | PASS |
| TC-P05 | Gross Pay - Contractual Employee | 1. Calculate payroll. 2. Check gross pay for a contractual employee. | Gross Pay based on hourly rate × actual hours worked. | Calculation matches expected formula. | PASS |
| TC-P06 | SSS Deduction Calculation | 1. Calculate payroll. 2. Check SSS column for an employee with salary ₱15,000. | SSS deduction matches the official SSS bracket table. | SSS deduction computed correctly per bracket table. | PASS |
| TC-P07 | PhilHealth Deduction Calculation | 1. Calculate payroll. 2. Check PhilHealth column. | PhilHealth = 1.5% of gross pay. | PhilHealth computed as 1.5% of gross. | PASS |
| TC-P08 | Pag-IBIG Deduction Calculation | 1. Calculate payroll. 2. Check Pag-IBIG column for salary below ₱5,000. | Pag-IBIG = 2% of gross pay. | Pag-IBIG computed correctly. | PASS |
| TC-P09 | Pag-IBIG Cap (above ₱5,000) | 1. Check Pag-IBIG for salary above ₱5,000. | Pag-IBIG = fixed ₱100. | Fixed ₱100 deduction applied correctly. | PASS |
| TC-P10 | Withholding Tax - Bracket 1 | 1. Check tax for employee with monthly gross below ₱20,833. | Withholding Tax = ₱0. | Zero tax computed correctly. | PASS |
| TC-P11 | Withholding Tax - Bracket 2 | 1. Check tax for employee with monthly gross ₱20,833–₱33,333. | Tax = 20% on excess over ₱20,833. | Tax computed correctly. | PASS |
| TC-P12 | Withholding Tax - Bracket 3 | 1. Check tax for employee with monthly gross ₱33,333–₱66,667. | Tax = ₱2,500 + 25% on excess over ₱33,333. | Tax computed correctly. | PASS |
| TC-P13 | Net Pay Calculation | 1. Calculate payroll. 2. Check Net Pay column. | Net Pay = Gross Pay - Total Deductions + Allowances. | Net pay computed correctly. | PASS |
| TC-P14 | Rice Subsidy Allowance | 1. Check rice subsidy in payslip. | Rice subsidy is pro-rated based on working days in period. | Pro-rated allowance computed correctly. | PASS |
| TC-P15 | Phone Allowance | 1. Check phone allowance in payslip. | Phone allowance is pro-rated based on working days. | Pro-rated allowance computed correctly. | PASS |
| TC-P16 | Clothing Allowance | 1. Check clothing allowance in payslip. | Clothing allowance is pro-rated based on working days. | Pro-rated allowance computed correctly. | PASS |
| TC-P17 | Approve Payroll | 1. Calculate payroll. 2. Click Approve Payroll. 3. Confirm. | Status changes to Approved, Post button enabled. | Status updated and UI state changed correctly. | PASS |
| TC-P18 | Post to Accounting | 1. Approve payroll. 2. Click POST to Accounting. 3. Confirm. | Status changes to Posted, report generated automatically. | Payroll posted and report file created. | PASS |
| TC-P19 | Calculate with No Data Loaded | 1. Click Calculate without loading data first. | System shows warning "Please load attendance data first." | Warning message displayed correctly. | PASS |
| TC-P20 | Approve without Calculating | 1. Try to click Approve without calculating. | Approve button is disabled. | Button disabled, cannot approve uncalculated payroll. | PASS |
| TC-P21 | Generate Payroll Report | 1. After calculating. 2. Click Generate Report. | Report file saved to /reports/ folder with correct data. | Report generated and saved successfully. | PASS |
| TC-P22 | Invalid Date Range | 1. Enter end date earlier than start date. | System shows date range validation error. | Validation error displayed. | PASS |

---

### 4.1.5 Reports Module

| Test ID | Test Case | Steps | Expected Result | Actual Result | Status |
|---------|-----------|-------|-----------------|---------------|--------|
| TC-R01 | Generate Individual Payslip | 1. Go to Reports. 2. Click Individual Payslip. 3. Enter valid Employee ID. 4. Select date range. | Payslip dialog shows complete payslip with all details. | Payslip generated and displayed correctly. | PASS |
| TC-R02 | Payslip - Invalid Employee ID | 1. Click Individual Payslip. 2. Enter non-existent Employee ID. | System shows "Employee not found" error. | Error message displayed. | PASS |
| TC-R03 | Payslip - No Attendance Records | 1. Enter valid Employee ID with no attendance in the selected period. | System shows "No attendance records found" message. | Message displayed correctly. | PASS |
| TC-R04 | Payslip Hours Breakdown | 1. Generate payslip for an employee. | Regular hours and overtime hours shown separately. | Hours broken down correctly in payslip. | PASS |
| TC-R05 | Payslip Deductions Display | 1. Generate payslip. 2. Check deductions section. | SSS, PhilHealth, Pag-IBIG, and Withholding Tax shown individually. | All deductions itemized correctly. | PASS |
| TC-R06 | Payslip Allowances Display | 1. Generate payslip. 2. Check allowances section. | Rice Subsidy, Phone Allowance, Clothing Allowance shown individually. | All allowances itemized correctly. | PASS |
| TC-R07 | Weekly Summary Report | 1. Click Weekly Summary. 2. Select date range within a week. | Summary table shows all employees with hours, gross, and net pay. | Weekly summary generated with correct data. | PASS |
| TC-R08 | Monthly Summary Report | 1. Click Monthly Summary. 2. Select a month's date range. | Summary table shows all employees for the month. | Monthly summary generated with correct data. | PASS |
| TC-R09 | Summary Report - Multiple Employees | 1. Generate summary report. | All employees appear in the report with correct totals. | All employees included in summary. | PASS |
| TC-R10 | Report Cancel | 1. Open any report dialog. 2. Click Cancel. | Dialog closes, no report generated. | Dialog closed without generating report. | PASS |

---

### 4.1.6 Dashboard Module

| Test ID | Test Case | Steps | Expected Result | Actual Result | Status |
|---------|-----------|-------|-----------------|---------------|--------|
| TC-D01 | Dashboard Load | 1. Login successfully. | Dashboard displays with all stat cards and quick links. | Dashboard loaded with all components. | PASS |
| TC-D02 | Employee Count Display | 1. View dashboard. | Total employee count matches actual employee records. | Count matches CSV records. | PASS |
| TC-D03 | Personalized Greeting | 1. Login as any user. | Header shows "Hi [username]!" with current date. | Correct username and date displayed. | PASS |
| TC-D04 | Navigation to Employee Panel | 1. Click Employee Management in sidebar. | Employee Management panel opens. | Panel switched correctly. | PASS |
| TC-D05 | Navigation to Payroll | 1. Click Payroll Management in sidebar. | Payroll Management panel opens. | Panel switched correctly. | PASS |
| TC-D06 | Navigation to Reports | 1. Click Reports in sidebar. | Reports panel opens. | Panel switched correctly. | PASS |
| TC-D07 | Logout | 1. Click Logout button. | System returns to login screen. | Redirected to login screen. | PASS |

---

### 4.1.7 Data Persistence Module

| Test ID | Test Case | Steps | Expected Result | Actual Result | Status |
|---------|-----------|-------|-----------------|---------------|--------|
| TC-DP01 | Employee Data Loads on Startup | 1. Launch the application. | All employees from CSV are loaded automatically. | Employees loaded from CSV on startup. | PASS |
| TC-DP02 | New Employee Persists After Restart | 1. Add a new employee. 2. Close the app. 3. Reopen the app. | New employee still appears in the list. | Employee persisted in CSV and loaded on restart. | PASS |
| TC-DP03 | Edited Employee Persists After Restart | 1. Edit an employee. 2. Close the app. 3. Reopen. | Updated employee information is retained. | Changes persisted correctly. | PASS |
| TC-DP04 | Deleted Employee Does Not Reappear | 1. Delete an employee. 2. Close the app. 3. Reopen. | Deleted employee is no longer in the list. | Employee removed from CSV, does not reappear. | PASS |
| TC-DP05 | Attendance Data Loads on Startup | 1. Launch the application. | All attendance records loaded from CSV. | Attendance records loaded correctly. | PASS |
| TC-DP06 | Missing CSV File Handling | 1. Remove the employee CSV. 2. Launch the app. | System shows appropriate error or creates empty dataset. | System handles missing file without crashing. | PASS |

---

## 4.2. External QA Feedback (Weeks 11–12)

External QA testing was performed by peer testers outside the development team. Testers were given access to the system and asked to evaluate usability, accuracy, and overall functionality.

### 4.2.1 QA Tester Feedback Summary

| Feedback ID | Module | Feedback | Severity | Resolution |
|-------------|--------|----------|----------|------------|
| QA-01 | Login | Testers confirmed login works correctly with valid credentials. No critical login issues found. | Low | No action needed. |
| QA-02 | Employee Management | Testers noted the employee table is easy to read and navigate. Search functionality works as expected. | Low | No action needed. |
| QA-03 | Employee Form | Some testers found the form fields had unclear labels, particularly for government ID fields. | Medium | Labels were clarified in the UI. |
| QA-04 | Payroll Calculation | Testers confirmed gross pay, deductions, and net pay calculations are accurate based on manual verification. | Low | No action needed. |
| QA-05 | Deduction Accuracy | SSS bracket deduction was verified against the official SSS table. Results matched. | Low | No action needed. |
| QA-06 | Payroll Workflow | Testers found the workflow buttons (Load → Calculate → Approve → Post) easy to follow. | Low | No action needed. |
| QA-07 | Date Input Format | Some testers entered dates in wrong format (YYYY-MM-DD instead of MM/DD/YYYY), causing errors. | Medium | Added date format hint label in DateRangeDialog. |
| QA-08 | Report Dialog | Testers found the payslip report format clear and readable. All amounts correctly displayed. | Low | No action needed. |
| QA-09 | Attendance Viewer | Filtering by month and year worked correctly. Testers confirmed late detection is accurate. | Low | No action needed. |
| QA-10 | Error Messages | Some error messages were too generic (e.g., "Invalid input" without specifying which field). | Medium | Error messages updated to specify the problematic field. |
| QA-11 | Dashboard Stats | Testers confirmed employee count on dashboard matches actual records. | Low | No action needed. |
| QA-12 | Navigation | Sidebar navigation between modules was smooth with no unexpected behavior. | Low | No action needed. |
| QA-13 | Delete Confirmation | Testers appreciated the confirmation dialog before deleting an employee. | Low | No action needed. |
| QA-14 | Payroll Table | Currency formatting (₱) in the payroll table was clear and consistent. | Low | No action needed. |
| QA-15 | CSV Upload | Testers successfully uploaded custom CSV files for payroll processing. | Low | No action needed. |

### 4.2.2 Overall QA Assessment

| Category | Rating | Remarks |
|----------|--------|---------|
| Functionality | 4/5 | All core features work correctly. Minor UI issues noted. |
| Accuracy | 5/5 | Payroll computations verified to be accurate. |
| Usability | 3.5/5 | Some form labels and error messages needed improvement. |
| Stability | 4/5 | No crashes encountered during testing. |
| Data Persistence | 4.5/5 | CSV read/write works reliably. |

---

## 4.3. Remaining Known Issues

| Issue ID | Module | Description | Impact | Priority |
|----------|--------|-------------|--------|----------|
| KI-01 | Date Input | System does not auto-format date input. Users must type in MM/DD/YYYY format manually. | Low — user can still input correctly with guidance. | Low |
| KI-02 | Attendance Export | The Export button in AttendanceViewerDialog is not yet fully implemented. Shows an information dialog instead of exporting to CSV. | Low — core attendance viewing works. | Low |
| KI-03 | Payroll Table | In PayrollNew panel, the "Total Deductions" column shows the sum of only SSS, PhilHealth, Pag-IBIG, and tax; does not include custom deductions. | Low — standard deductions are correct. | Low |
| KI-04 | Search Functionality | Search is case-sensitive for some fields. Searching "juan" may not find "Juan". | Medium — may frustrate users. | Medium |
| KI-05 | Employee Form Validation | Birthday field accepts future dates without warning. | Low — does not break functionality. | Low |
| KI-06 | Payroll Report File Location | Reports are saved in the working directory, not in a dedicated /reports/ folder consistently. | Low — file is still generated. | Low |
| KI-07 | Dashboard Stats | Some dashboard statistics (pending payroll count) are placeholder values not dynamically updated. | Low — informational only. | Low |
| KI-08 | Emergency Contact Section | EmployeeDetailsDialog shows "Not specified" for all emergency contact fields as data is not collected in the employee form. | Low — placeholder data only. | Low |

---

## 4.4. Final Improvements Made

### 4.4.1 OOP Architecture Improvements

| Improvement | Description | OOP Principle |
|-------------|-------------|---------------|
| Added `Payable` interface | Employee class implements Payable, ensuring a contract for all payable entities. | Abstraction |
| Created `Deduction` interface | Defines `calculate()` and `getDeductionName()` contract for all deduction types. | Abstraction |
| Created `BaseDeduction` abstract class | Provides shared implementation for all deduction classes, reducing code duplication. | Abstraction + Inheritance |
| Implemented 4 concrete deduction classes | `SSSDeduction`, `PhilHealthDeduction`, `PagIBIGDeduction`, `WithholdingTaxDeduction` each override `calculate()` with their own logic. | Inheritance + Polymorphism |
| Refactored `PayrollProcessor` | Uses `List<Deduction>` to process all deduction types uniformly through the interface. | Polymorphism |
| Created `EmployeeServiceInterface` | Controllers depend on interface, not concrete class — decoupling the service layer. | Abstraction |
| Created `PayrollServiceInterface` | Same decoupling applied to payroll service layer. | Abstraction |
| Created `EmployeeFactory` | Factory pattern for creating RegularEmployee or ContractualEmployee based on status field. | Polymorphism |
| `RegularEmployee` and `ContractualEmployee` | Both extend `Employee` and override `calculateGrossPay()` with different formulas. | Inheritance + Polymorphism |
| All model fields are private | All Employee, PaySlip, and other model fields use private access with public getters/setters. | Encapsulation |

### 4.4.2 Code Quality Improvements

| Improvement | Description |
|-------------|-------------|
| Removed all Javadoc and comments | Code cleaned to read naturally without redundant comments. OOP section labels added only where necessary. |
| Condensed getter/setter methods | Single-line getters/setters for cleaner, more readable code. |
| Removed all `.md` documentation files | Cleaned up project root from unnecessary markdown files. |
| Consistent code formatting | Uniform indentation, spacing, and naming conventions applied across all files. |
| Removed unused imports | Cleaned up import statements in all view and model classes. |

### 4.4.3 Bug Fixes Applied During Testing

| Bug ID | Description | Fix Applied |
|--------|-------------|-------------|
| BF-01 | Payroll calculate button remained enabled even without loaded data. | Added null check on `currentPayrollRun` before enabling calculate button. |
| BF-02 | Employee table did not refresh after adding a new employee. | Called `loadEmployeeData()` after successful save operation. |
| BF-03 | Delete operation did not show confirmation dialog consistently. | Confirmation dialog added to all delete operations. |
| BF-04 | Date range dialog did not validate end date before start date. | Added `validateDateRange()` call in `AppUtils`. |
| BF-05 | Search returned no results when employee ID had leading/trailing spaces. | Added `.trim()` on all search input before processing. |
| BF-06 | Attendance viewer showed incorrect month label after filter reset. | Fixed reset method to update summary label correctly. |
| BF-07 | Payslip dialog showed null for allowances when employee has no allowances set. | Added null/default check with `getOrDefault()` in allowance retrieval. |
| BF-08 | CSV loading failed silently when file path contained spaces. | Added proper path quoting and exception logging. |
