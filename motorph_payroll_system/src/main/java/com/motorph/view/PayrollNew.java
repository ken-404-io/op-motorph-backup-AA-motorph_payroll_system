package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.motorph.controller.PayrollController;
import com.motorph.model.PaySlip;
import com.motorph.model.PayrollRun;
import com.motorph.model.PayrollStatus;
import com.motorph.util.AppConstants;
import com.motorph.util.AppUtils;
import com.motorph.view.dialog.DateRangeDialog;

public class PayrollNew extends JPanel {

    private final MainFrame mainFrame;
    private final PayrollController payrollController;
    private final NumberFormat currencyFormat;

    private JPanel statusPanel;
    private JPanel controlPanel;
    private JPanel tablePanel;
    private JTable payrollTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;
    private JLabel totalsLabel;
    private JButton uploadButton;
    private JButton calculateButton;
    private JButton reviewButton;
    private JButton approveButton;
    private JButton postButton;
    private JButton generateReportButton;
    private JButton backButton;

    private PayrollRun currentPayrollRun;

    public PayrollNew(MainFrame mainFrame, PayrollController payrollController) {
        this.mainFrame = mainFrame;
        this.payrollController = payrollController;
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        initPanel();
    }

    private void initPanel() {
        setLayout(new BorderLayout(0, 15));
        setBackground(AppConstants.BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMainContentPanel(), BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);

        updateUIState();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel titleLabel = new JLabel("💼 Payroll Management System", SwingConstants.LEFT);
        titleLabel.setFont(AppConstants.TITLE_FONT);
        titleLabel.setForeground(AppConstants.TEXT_COLOR);

        statusPanel = createStatusPanel();

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(statusPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setBackground(AppConstants.BACKGROUND_COLOR);

        statusLabel = new JLabel("No active payroll run");
        statusLabel.setFont(AppConstants.NORMAL_FONT);
        statusLabel.setForeground(AppConstants.TEXT_SECONDARY);

        panel.add(statusLabel);
        return panel;
    }

    private JPanel createMainContentPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(AppConstants.BACKGROUND_COLOR);

        mainPanel.add(createWorkflowPanel(), BorderLayout.NORTH);
        mainPanel.add(createPayrollTablePanel(), BorderLayout.CENTER);
        mainPanel.add(createSummaryPanel(), BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel createWorkflowPanel() {
        JPanel workflowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        workflowPanel.setBackground(AppConstants.CARD_BACKGROUND);
        workflowPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.BORDER_COLOR, 1),
                new EmptyBorder(15, 20, 15, 20)));

        uploadButton = AppUtils.createPrimaryButton("📁 Load Attendance Data");
        uploadButton.addActionListener(e -> loadAttendanceData());

        calculateButton = AppUtils.createPrimaryButton("🧮 Calculate Payroll");
        calculateButton.addActionListener(e -> calculatePayroll());
        calculateButton.setEnabled(false);

        reviewButton = AppUtils.createSecondaryButton("🔍 Review Details");
        reviewButton.addActionListener(e -> reviewPayrollDetails());
        reviewButton.setEnabled(false);

        workflowPanel.add(uploadButton);
        workflowPanel.add(Box.createHorizontalStrut(10));
        workflowPanel.add(calculateButton);
        workflowPanel.add(Box.createHorizontalStrut(10));
        workflowPanel.add(reviewButton);

        return workflowPanel;
    }

    private JPanel createPayrollTablePanel() {
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(AppConstants.CARD_BACKGROUND);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.BORDER_COLOR, 1),
                new EmptyBorder(10, 10, 10, 10)));

        String[] columns = {
                "Emp ID", "Employee Name", "Position", "Regular Hours", "OT Hours",
                "Gross Pay", "SSS", "PhilHealth", "Pag-IBIG", "Tax", "Total Deductions", "Net Pay", "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        payrollTable = new JTable(tableModel);
        setupTableAppearance();

        JScrollPane scrollPane = new JScrollPane(payrollTable);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(AppConstants.CARD_BACKGROUND);

        JLabel tableTitle = new JLabel("📊 Payroll Summary Table", SwingConstants.LEFT);
        tableTitle.setFont(AppConstants.SUBHEADING_FONT);
        tableTitle.setForeground(AppConstants.TEXT_COLOR);
        tableTitle.setBorder(new EmptyBorder(0, 0, 10, 0));

        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private void setupTableAppearance() {
        payrollTable.setFont(AppConstants.TABLE_FONT);
        payrollTable.setRowHeight(AppConstants.TABLE_ROW_HEIGHT);
        payrollTable.setGridColor(AppConstants.TABLE_BORDER_COLOR);
        payrollTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        payrollTable.setBackground(AppConstants.CARD_BACKGROUND);
        payrollTable.setSelectionBackground(AppConstants.TABLE_ROW_HOVER);

        payrollTable.getTableHeader().setFont(AppConstants.TABLE_HEADER_FONT);
        payrollTable.getTableHeader().setBackground(AppConstants.TABLE_HEADER_BACKGROUND);
        payrollTable.getTableHeader().setForeground(AppConstants.TEXT_COLOR);
        payrollTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, AppConstants.BORDER_COLOR));

        payrollTable.setDefaultRenderer(Object.class, new PayrollTableCellRenderer());

        int[] columnWidths = { 80, 150, 120, 100, 80, 120, 80, 80, 80, 80, 120, 120, 100 };
        for (int i = 0; i < columnWidths.length && i < payrollTable.getColumnCount(); i++) {
            payrollTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
    }

    private JPanel createSummaryPanel() {
        JPanel summaryPanel = new JPanel(new GridBagLayout());
        summaryPanel.setBackground(AppConstants.CARD_BACKGROUND);
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.BORDER_COLOR, 1),
                new EmptyBorder(15, 20, 15, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        JLabel summaryTitle = new JLabel("📈 Payroll Summary");
        summaryTitle.setFont(AppConstants.SUBHEADING_FONT);
        summaryTitle.setForeground(AppConstants.TEXT_COLOR);
        summaryPanel.add(summaryTitle, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 4;
        totalsLabel = new JLabel("Select a pay period to view summary");
        totalsLabel.setFont(AppConstants.NORMAL_FONT);
        totalsLabel.setForeground(AppConstants.TEXT_SECONDARY);
        summaryPanel.add(totalsLabel, gbc);

        return summaryPanel;
    }

    private JPanel createControlPanel() {
        controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        controlPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        controlPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        approveButton = AppUtils.createPrimaryButton("✅ Approve Payroll");
        approveButton.setBackground(AppConstants.PAYROLL_APPROVED_COLOR);
        approveButton.addActionListener(e -> approvePayroll());
        approveButton.setEnabled(false);

        postButton = AppUtils.createPrimaryButton("📤 POST to Accounting");
        postButton.setBackground(AppConstants.PAYROLL_POSTED_COLOR);
        postButton.addActionListener(e -> postToAccounting());
        postButton.setEnabled(false);

        generateReportButton = AppUtils.createSecondaryButton("📋 Generate Report");
        generateReportButton.addActionListener(e -> generatePayrollReport());
        generateReportButton.setEnabled(false);

        backButton = AppUtils.createSecondaryButton("⬅ Back to Main Menu");
        backButton.addActionListener(e -> mainFrame.showMainMenu());

        controlPanel.add(approveButton);
        controlPanel.add(postButton);
        controlPanel.add(generateReportButton);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(backButton);

        return controlPanel;
    }

    private void loadAttendanceData() {
        String[] options = { "Use Default File", "Upload CSV File", "Cancel" };
        int choice = JOptionPane.showOptionDialog(
                this,
                "Choose how to load attendance data:",
                "Load Attendance Data",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        switch (choice) {
            case 0:
                loadDefaultAttendanceFile();
                break;
            case 1:
                uploadCSVFile();
                break;
            default:
                return;
        }
    }

    private void loadDefaultAttendanceFile() {
        try {
            String attendanceFilePath = AppConstants.getAttendanceFilePath();
            File attendanceFile = new File(attendanceFilePath);

            if (!attendanceFile.exists()) {
                JOptionPane.showMessageDialog(this,
                        "Default attendance file not found at: " + attendanceFilePath +
                                "\nPlease upload a CSV file instead.",
                        "File Not Found",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            DateRangeDialog dateDialog = new DateRangeDialog(mainFrame, "Select Pay Period");
            dateDialog.setVisible(true);

            if (!dateDialog.isConfirmed()) {
                return;
            }

            LocalDate startDate = dateDialog.getStartDate();
            LocalDate endDate = dateDialog.getEndDate();

            currentPayrollRun = new PayrollRun(startDate, endDate, "HR Staff");
            currentPayrollRun.setStatus(PayrollStatus.DRAFT);

            updateUIState();
            calculateButton.setEnabled(true);

            statusLabel.setText("📁 Attendance data loaded - Ready to calculate");
            statusLabel.setForeground(AppConstants.SUCCESS_COLOR);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading attendance file: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void uploadCSVFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        fileChooser.setDialogTitle("Select Attendance CSV File");

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            if (validateCSVFile(selectedFile)) {
                DateRangeDialog dateDialog = new DateRangeDialog(mainFrame, "Select Pay Period");
                dateDialog.setVisible(true);

                if (!dateDialog.isConfirmed()) {
                    return;
                }

                LocalDate startDate = dateDialog.getStartDate();
                LocalDate endDate = dateDialog.getEndDate();

                currentPayrollRun = new PayrollRun(startDate, endDate, "HR Staff");
                currentPayrollRun.setStatus(PayrollStatus.DRAFT);

                updateUIState();
                calculateButton.setEnabled(true);

                statusLabel.setText("📁 CSV uploaded successfully - Ready to calculate");
                statusLabel.setForeground(AppConstants.SUCCESS_COLOR);
            }
        }
    }

    private boolean validateCSVFile(File csvFile) {
        try {
            if (!csvFile.exists() || !csvFile.canRead()) {
                JOptionPane.showMessageDialog(this,
                        "File cannot be read or does not exist.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error validating CSV file: " + e.getMessage(),
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void calculatePayroll() {
        if (currentPayrollRun == null) {
            JOptionPane.showMessageDialog(this,
                    "Please load attendance data first.",
                    "No Data",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            statusLabel.setText("🧮 Calculating payroll...");
            statusLabel.setForeground(AppConstants.WARNING_COLOR);

            List<PaySlip> paySlips = payrollController.generatePayroll(
                    currentPayrollRun.getStartDate(),
                    currentPayrollRun.getEndDate());

            currentPayrollRun.setPaySlips(paySlips);
            currentPayrollRun.setStatus(PayrollStatus.PENDING_REVIEW);

            populatePayrollTable(paySlips);
            updateSummaryPanel();
            updateUIState();

            statusLabel.setText("✅ Payroll calculated - Ready for review");
            statusLabel.setForeground(AppConstants.SUCCESS_COLOR);

        } catch (Exception e) {
            statusLabel.setText("❌ Calculation failed");
            statusLabel.setForeground(AppConstants.PAYROLL_REJECTED_COLOR);

            JOptionPane.showMessageDialog(this,
                    "Error calculating payroll: " + e.getMessage(),
                    "Calculation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void populatePayrollTable(List<PaySlip> paySlips) {
        tableModel.setRowCount(0);

        for (PaySlip paySlip : paySlips) {
            Object[] row = {
                    paySlip.getEmployee().getEmployeeId(),
                    paySlip.getEmployee().getFullName(),
                    paySlip.getEmployee().getPosition(),
                    String.format("%.2f", paySlip.getRegularHours()),
                    String.format("%.2f", paySlip.getOvertimeHours()),
                    currencyFormat.format(paySlip.getGrossPay()),
                    currencyFormat.format(paySlip.getDeductions().getOrDefault("sss", 0.0)),
                    currencyFormat.format(paySlip.getDeductions().getOrDefault("philhealth", 0.0)),
                    currencyFormat.format(paySlip.getDeductions().getOrDefault("pagibig", 0.0)),
                    currencyFormat.format(paySlip.getDeductions().getOrDefault("withholdingTax", 0.0)),
                    currencyFormat.format(paySlip.getTotalDeductions()),
                    currencyFormat.format(paySlip.getNetPay()),
                    "Calculated"
            };
            tableModel.addRow(row);
        }

        payrollTable.revalidate();
        payrollTable.repaint();
    }

    private void updateSummaryPanel() {
        if (currentPayrollRun == null || currentPayrollRun.getPaySlips() == null) {
            totalsLabel.setText("No data available");
            return;
        }

        String summaryText = String.format(
                "📊 Employees: %d | 💰 Total Gross: %s | 📉 Total Deductions: %s | 💵 Total Net: %s | 📅 Period: %s",
                currentPayrollRun.getEmployeeCount(),
                currencyFormat.format(currentPayrollRun.getTotalGrossPay()),
                currencyFormat.format(currentPayrollRun.getTotalDeductions()),
                currencyFormat.format(currentPayrollRun.getTotalNetPay()),
                currentPayrollRun.getPayPeriodString());

        totalsLabel.setText(summaryText);
        totalsLabel.setForeground(AppConstants.TEXT_COLOR);
    }

    private void reviewPayrollDetails() {
        if (currentPayrollRun == null) {
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Review functionality:\n" +
                        "• Use table to review individual employee calculations\n" +
                        "• Check totals in summary panel\n" +
                        "• Verify pay period and employee count\n" +
                        "• If corrections needed, recalculate or reject",
                "Payroll Review",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void approvePayroll() {
        if (currentPayrollRun == null || currentPayrollRun.getStatus() != PayrollStatus.PENDING_REVIEW) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to approve this payroll?\n" +
                        "This will finalize the calculations and enable posting to accounting.",
                "Approve Payroll",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            currentPayrollRun.setStatus(PayrollStatus.APPROVED);
            currentPayrollRun.setApprovedBy("HR Manager");
            currentPayrollRun.setApprovedAt(java.time.LocalDateTime.now());

            updateUIState();
            statusLabel.setText("✅ Payroll approved - Ready for posting");
            statusLabel.setForeground(AppConstants.PAYROLL_APPROVED_COLOR);
        }
    }

    private void postToAccounting() {
        if (currentPayrollRun == null || currentPayrollRun.getStatus() != PayrollStatus.APPROVED) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "POST payroll to accounting system?\n\n" +
                        "This will:\n" +
                        "• Submit payroll data to accounting for payment processing\n" +
                        "• Generate payslips for all employees\n" +
                        "• Lock this payroll run from further changes\n" +
                        "• Generate final reports\n\n" +
                        "Continue?",
                "POST to Accounting",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                statusLabel.setText("📤 Posting to accounting...");
                statusLabel.setForeground(AppConstants.WARNING_COLOR);

                currentPayrollRun.setStatus(PayrollStatus.POSTED);

                generatePayrollReport();

                updateUIState();
                statusLabel.setText("📤 Posted to accounting - Reports generated");
                statusLabel.setForeground(AppConstants.PAYROLL_POSTED_COLOR);

                JOptionPane.showMessageDialog(this,
                        "Payroll successfully posted to accounting!\n" +
                                "Reports have been generated and saved.",
                        "Posted Successfully",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                statusLabel.setText("❌ Posting failed");
                statusLabel.setForeground(AppConstants.PAYROLL_REJECTED_COLOR);

                JOptionPane.showMessageDialog(this,
                        "Error posting to accounting: " + e.getMessage(),
                        "Posting Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void generatePayrollReport() {
        if (currentPayrollRun == null) {
            JOptionPane.showMessageDialog(this,
                    "No payroll data to generate report.",
                    "No Data",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String fileName = String.format("PayrollReport_%s_%s.txt",
                    currentPayrollRun.getRunId(),
                    LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            File reportsDir = new File("reports");
            if (!reportsDir.exists()) {
                reportsDir.mkdirs();
            }

            File reportFile = new File(reportsDir, fileName);

            try (FileWriter writer = new FileWriter(reportFile)) {
                writer.write("=".repeat(80) + "\n");
                writer.write("                    MOTORPH PAYROLL REPORT\n");
                writer.write("=".repeat(80) + "\n");
                writer.write(String.format("Report Date: %s\n", LocalDate.now()));
                writer.write(String.format("Pay Period: %s\n", currentPayrollRun.getPayPeriodString()));
                writer.write(String.format("Payroll Run ID: %s\n", currentPayrollRun.getRunId()));
                writer.write(String.format("Status: %s\n", currentPayrollRun.getStatus()));
                writer.write(String.format("Processed By: %s\n", currentPayrollRun.getProcessedBy()));
                writer.write("-".repeat(80) + "\n");

                writer.write("PAYROLL SUMMARY:\n");
                writer.write(String.format("Total Employees: %d\n", currentPayrollRun.getEmployeeCount()));
                writer.write(String.format("Total Gross Pay: %s\n",
                        currencyFormat.format(currentPayrollRun.getTotalGrossPay())));
                writer.write(String.format("Total Deductions: %s\n",
                        currencyFormat.format(currentPayrollRun.getTotalDeductions())));
                writer.write(String.format("Total Net Pay: %s\n",
                        currencyFormat.format(currentPayrollRun.getTotalNetPay())));
                writer.write("-".repeat(80) + "\n");

                writer.write("EMPLOYEE DETAILS:\n");
                writer.write(String.format("%-8s %-25s %-15s %-12s %-12s %-12s\n",
                        "Emp ID", "Name", "Position", "Gross Pay", "Deductions", "Net Pay"));
                writer.write("-".repeat(80) + "\n");

                if (currentPayrollRun.getPaySlips() != null) {
                    for (PaySlip paySlip : currentPayrollRun.getPaySlips()) {
                        writer.write(String.format("%-8d %-25s %-15s %-12s %-12s %-12s\n",
                                paySlip.getEmployee().getEmployeeId(),
                                paySlip.getEmployee().getFullName(),
                                paySlip.getEmployee().getPosition(),
                                currencyFormat.format(paySlip.getGrossPay()),
                                currencyFormat.format(paySlip.getTotalDeductions()),
                                currencyFormat.format(paySlip.getNetPay())));
                    }
                }

                writer.write("=".repeat(80) + "\n");
                writer.write("End of Report\n");
            }

            JOptionPane.showMessageDialog(this,
                    "Report generated successfully!\nSaved to: " + reportFile.getAbsolutePath(),
                    "Report Generated",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error generating report: " + e.getMessage(),
                    "Report Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateUIState() {
        if (currentPayrollRun == null) {
            calculateButton.setEnabled(false);
            reviewButton.setEnabled(false);
            approveButton.setEnabled(false);
            postButton.setEnabled(false);
            generateReportButton.setEnabled(false);
            return;
        }

        PayrollStatus status = currentPayrollRun.getStatus();

        switch (status) {
            case DRAFT:
                calculateButton.setEnabled(true);
                reviewButton.setEnabled(false);
                approveButton.setEnabled(false);
                postButton.setEnabled(false);
                generateReportButton.setEnabled(false);
                break;
            case PENDING_REVIEW:
                calculateButton.setEnabled(true);
                reviewButton.setEnabled(true);
                approveButton.setEnabled(true);
                postButton.setEnabled(false);
                generateReportButton.setEnabled(true);
                break;
            case APPROVED:
                calculateButton.setEnabled(false);
                reviewButton.setEnabled(true);
                approveButton.setEnabled(false);
                postButton.setEnabled(true);
                generateReportButton.setEnabled(true);
                break;
            case POSTED:
                calculateButton.setEnabled(false);
                reviewButton.setEnabled(true);
                approveButton.setEnabled(false);
                postButton.setEnabled(false);
                generateReportButton.setEnabled(true);
                break;
        }

        if (currentPayrollRun != null) {
            statusLabel.setText(String.format("Run ID: %s | Status: %s",
                    currentPayrollRun.getRunId(),
                    currentPayrollRun.getStatus().getDisplayName()));
        }
    }

    private class PayrollTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (!isSelected) {
                if (row % 2 == 0) {
                    cell.setBackground(AppConstants.CARD_BACKGROUND);
                } else {
                    cell.setBackground(AppConstants.TABLE_ALT_ROW);
                }
            }

            if (column >= 5 && column <= 11) {
                setHorizontalAlignment(SwingConstants.RIGHT);
            } else {
                setHorizontalAlignment(SwingConstants.LEFT);
            }

            if (column == 12) {
                String status = value.toString();
                if ("Calculated".equals(status)) {
                    setForeground(AppConstants.SUCCESS_COLOR);
                } else if ("Pending".equals(status)) {
                    setForeground(AppConstants.WARNING_COLOR);
                }
            } else {
                setForeground(AppConstants.TEXT_COLOR);
            }

            return cell;
        }
    }
}
