package com.motorph.util;

import java.awt.Color;
import java.awt.Font;

public final class AppConstants {

    public static final Color BACKGROUND_COLOR = new Color(241, 245, 249);
    public static final Color PANEL_BACKGROUND = Color.WHITE;
    public static final Color CARD_BACKGROUND = Color.WHITE;
    public static final Color NAVIGATION_BACKGROUND = new Color(248, 250, 252);
    public static final Color SIDEBAR_BACKGROUND = new Color(51, 65, 85);
    public static final Color TABLE_HEADER_BACKGROUND = new Color(248, 250, 252);
    public static final Color TABLE_ROW_HOVER = new Color(248, 250, 252);
    public static final Color TABLE_ALT_ROW = new Color(248, 250, 252);

    public static final Color PRIMARY_BUTTON_COLOR = new Color(99, 102, 241);
    public static final Color PRIMARY_BUTTON_HOVER = new Color(79, 70, 229);
    public static final Color SECONDARY_BUTTON_COLOR = new Color(107, 114, 128);
    public static final Color SECONDARY_BUTTON_HOVER = new Color(75, 85, 99);
    public static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    public static final Color DELETE_BUTTON_COLOR = new Color(239, 68, 68);
    public static final Color DELETE_BUTTON_HOVER = new Color(220, 38, 38);
    public static final Color SUCCESS_COLOR = new Color(34, 197, 94);
    public static final Color WARNING_COLOR = new Color(245, 158, 11);

    public static final Color BUTTON_COLOR = PRIMARY_BUTTON_COLOR;
    public static final Color BUTTON_HOVER_COLOR = PRIMARY_BUTTON_HOVER;

    public static final Color TEXT_COLOR = new Color(51, 65, 85);
    public static final Color TEXT_SECONDARY = new Color(100, 116, 139);
    public static final Color TEXT_MUTED = new Color(148, 163, 184);
    public static final Color BORDER_COLOR = new Color(226, 232, 240);
    public static final Color TABLE_BORDER_COLOR = new Color(226, 232, 240);

    public static final Color STATUS_ACTIVE_BG = new Color(220, 252, 231);
    public static final Color STATUS_ACTIVE_TEXT = new Color(21, 128, 61);
    public static final Color STATUS_INACTIVE_BG = new Color(241, 245, 249);
    public static final Color STATUS_INACTIVE_TEXT = new Color(51, 65, 85);

    public static final Color ACTION_VIEW_COLOR = new Color(14, 165, 233);
    public static final Color ACTION_EDIT_COLOR = new Color(34, 197, 94);
    public static final Color ACTION_DELETE_COLOR = new Color(239, 68, 68);

    public static final Color EDIT_BUTTON_COLOR = new Color(251, 146, 60);
    public static final Color BORDER_SOLID = new Color(203, 213, 225);
    public static final Color ACCENT_PRIMARY = new Color(99, 102, 241);

    public static final Color PAYROLL_DRAFT_COLOR = new Color(107, 114, 128);
    public static final Color PAYROLL_LOADED_COLOR = new Color(59, 130, 246);
    public static final Color PAYROLL_CALCULATED_COLOR = new Color(245, 158, 11);
    public static final Color PAYROLL_REVIEW_COLOR = new Color(168, 85, 247);
    public static final Color PAYROLL_PENDING_COLOR = new Color(245, 158, 11);
    public static final Color PAYROLL_APPROVED_COLOR = new Color(34, 197, 94);
    public static final Color PAYROLL_REJECTED_COLOR = new Color(239, 68, 68);
    public static final Color PAYROLL_POSTED_COLOR = new Color(99, 102, 241);

    public static final Color PRIMARY_COLOR = PRIMARY_BUTTON_COLOR;
    public static final Color SELECTION_COLOR = new Color(219, 234, 254);

    public static final int FRAME_WIDTH = 1200;
    public static final int FRAME_HEIGHT = 800;
    public static final int DIALOG_WIDTH = 600;
    public static final int DIALOG_HEIGHT = 400;
    public static final int BUTTON_HEIGHT = 36;
    public static final int INPUT_HEIGHT = 32;
    public static final int TABLE_ROW_HEIGHT = 40;
    public static final int CARD_BORDER_RADIUS = 8;
    public static final int BUTTON_BORDER_RADIUS = 6;

    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font SUBHEADING_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font TABLE_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font TABLE_HEADER_FONT = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public static final int STANDARD_WORK_DAYS_PER_MONTH = 21;
    public static final double REGULAR_HOURS_PER_DAY = 8.0;
    public static final double OVERTIME_RATE_MULTIPLIER = 1.25;

    public static final double PHILHEALTH_EMPLOYEE_CONTRIBUTION_RATE = 0.015;
    public static final double PHILHEALTH_TOTAL_CONTRIBUTION_RATE = 0.03;

    public static final double PAGIBIG_EMPLOYEE_CONTRIBUTION_RATE = 0.02;
    public static final double PAGIBIG_MAX_CONTRIBUTION_SALARY_CAP = 5000.00;
    public static final double PAGIBIG_FIXED_RATE_ABOVE_CAP = 100.00;

    public static final double SSS_EMPLOYEE_CONTRIBUTION_CAP_PERCENT = 0.045;

    public static final double WITHHOLDING_TAX_EXEMPTION_THRESHOLD = 20833.00;

    public static final String DATE_FORMAT_PATTERN = "MM/dd/yyyy";
    public static final String TIME_FORMAT_PATTERN = "H:mm";
    public static final String STANDARD_LOGIN_TIME_STRING = "08:00";

    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_NAME_LENGTH = 50;
    public static final int MIN_EMPLOYEE_ID = 1;
    public static final int MAX_EMPLOYEE_ID = 999999;
    public static final double MIN_SALARY = 0.0;
    public static final double MAX_SALARY = 10000000.0;

    public static final String PHONE_PATTERN = "^[0-9]{3}-[0-9]{3}-[0-9]{3,4}$";
    public static final String SSS_PATTERN = "^[0-9]{2}-[0-9]{7}-[0-9]$";
    public static final String PHILHEALTH_PATTERN = "^[0-9]{12}$";
    public static final String TIN_PATTERN = "^[0-9]{3}-[0-9]{3}-[0-9]{3}-[0-9]{3}$";
    public static final String PAGIBIG_PATTERN = "^[0-9]{12}$";

    public static final String APP_TITLE = "MotorPH Payroll System";
    public static final String LOGIN_TITLE = "Login - MotorPH Payroll System";
    public static final String DASHBOARD_TITLE = "Dashboard - MotorPH Payroll System";

    public static final String EMPLOYEE_ADDED_SUCCESS = "Employee added successfully!";
    public static final String EMPLOYEE_UPDATED_SUCCESS = "Employee updated successfully!";
    public static final String EMPLOYEE_DELETED_SUCCESS = "Employee deleted successfully!";

    public static final String LOGIN_FAILED = "Invalid username or password.";
    public static final String EMPLOYEE_NOT_FOUND = "Employee not found.";
    public static final String DUPLICATE_EMPLOYEE_ID = "Employee ID already exists.";
    public static final String INVALID_INPUT = "Please check your input and try again.";
    public static final String FILE_ERROR = "Error accessing file. Please check file permissions.";
    public static final String NETWORK_ERROR = "Network connection error. Please try again.";

    public static final String CONFIRM_DELETE_EMPLOYEE = "Are you sure you want to delete this employee?";
    public static final String CONFIRM_LOGOUT = "Are you sure you want to logout?";

    public static final String DEFAULT_EMPLOYEES_FILE_PATH = "data/employeeDetails.csv";
    public static final String DEFAULT_ATTENDANCE_FILE_PATH = "data/attendanceRecord.csv";

    public static final String EMPLOYEES_FILE_NAME = "employeeDetails.csv";
    public static final String ATTENDANCE_FILE_NAME = "attendanceRecord.csv";

    public static String getEmployeeFilePath() {
        java.io.File file = new java.io.File(DEFAULT_EMPLOYEES_FILE_PATH);
        if (file.exists()) {
            return DEFAULT_EMPLOYEES_FILE_PATH;
        }

        String projectDir = System.getProperty("user.dir");
        if (projectDir.endsWith("motorph_payroll_system")) {
            file = new java.io.File(projectDir, "data/" + EMPLOYEES_FILE_NAME);
        } else {
            file = new java.io.File(projectDir, "motorph_payroll_system/data/" + EMPLOYEES_FILE_NAME);
        }

        if (file.exists()) {
            return file.getAbsolutePath();
        }

        return DEFAULT_EMPLOYEES_FILE_PATH;
    }

    public static String getAttendanceFilePath() {
        java.io.File file = new java.io.File(DEFAULT_ATTENDANCE_FILE_PATH);
        if (file.exists()) {
            return DEFAULT_ATTENDANCE_FILE_PATH;
        }

        String projectDir = System.getProperty("user.dir");
        if (projectDir.endsWith("motorph_payroll_system")) {
            file = new java.io.File(projectDir, "data/" + ATTENDANCE_FILE_NAME);
        } else {
            file = new java.io.File(projectDir, "motorph_payroll_system/data/" + ATTENDANCE_FILE_NAME);
        }

        if (file.exists()) {
            return file.getAbsolutePath();
        }

        return DEFAULT_ATTENDANCE_FILE_PATH;
    }

    private AppConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
