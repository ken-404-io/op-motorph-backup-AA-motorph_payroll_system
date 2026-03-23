package com.motorph.view.dialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.motorph.controller.EmployeeController;
import com.motorph.model.AttendanceRecord;
import com.motorph.model.Employee;
import com.motorph.util.AppConstants;

public class AttendanceViewerDialog extends JDialog {

    private final Employee employee;
    private final EmployeeController employeeController;

    private JPanel mainPanel;
    private JPanel filterPanel;
    private JPanel analyticsPanel;
    private JPanel tablePanel;
    private JPanel summaryPanel;

    private JComboBox<String> monthComboBox;
    private JComboBox<Integer> yearComboBox;
    private JButton filterButton;
    private JButton resetButton;
    private JButton exportButton;
    private JButton closeButton;

    private JTable attendanceTable;
    private DefaultTableModel tableModel;
    private JScrollPane tableScrollPane;

    private JPanel totalDaysLabel;
    private JPanel presentDaysLabel;
    private JPanel lateDaysLabel;
    private JPanel totalHoursLabel;
    private JPanel avgHoursLabel;
    private JPanel attendanceRateLabel;
    private JProgressBar attendanceRateBar;

    private List<AttendanceRecord> allRecords;
    private List<AttendanceRecord> filteredRecords;

    private YearMonth currentFilter = null;

    private static final String[] COLUMN_NAMES = {
            "Date", "Day", "Time In", "Time Out", "Break Time",
            "Total Hours", "Regular", "Overtime", "Status", "Remarks"
    };

    public AttendanceViewerDialog(Frame parent, Employee employee, EmployeeController employeeController) {
        super(parent, "📅 Attendance Records - " + employee.getFullName(), true);
        this.employee = employee;
        this.employeeController = employeeController;

        initializeData();
        initComponents();
        layoutComponents();
        setupEventHandlers();
        addButtonTooltips();
        loadInitialData();

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(parent);
    }

    private void initializeData() {
        LocalDate startDate = LocalDate.of(2020, 1, 1);
        LocalDate endDate = LocalDate.now().plusDays(30);

        allRecords = employeeController.getAttendanceRecords(
                employee.getEmployeeId(), startDate, endDate);
        filteredRecords = allRecords;
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        createFilterPanel();
        createAnalyticsPanel();
        createTablePanel();
        createSummaryPanel();

        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(AppConstants.BACKGROUND_COLOR);
    }

    private void createFilterPanel() {
        filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.X_AXIS));
        filterPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("📊 Filter & Controls"),
                new EmptyBorder(10, 10, 10, 10)));

        JLabel employeeLabel = new JLabel("👤 " + employee.getFullName() + " (ID: " + employee.getEmployeeId() + ")");
        employeeLabel.setFont(AppConstants.SUBHEADING_FONT);
        employeeLabel.setForeground(AppConstants.PRIMARY_COLOR);

        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setFont(AppConstants.NORMAL_FONT);

        String[] months = { "All Months", "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December" };
        monthComboBox = new JComboBox<>(months);
        monthComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        monthComboBox.setPreferredSize(new Dimension(130, 35));
        monthComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setFont(AppConstants.NORMAL_FONT);

        Integer[] years = generateYearRange();
        yearComboBox = new JComboBox<>(years);
        yearComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        yearComboBox.setPreferredSize(new Dimension(90, 35));
        yearComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        yearComboBox.setSelectedItem(LocalDate.now().getYear());

        filterButton = createActionButton("🔍 Apply Filter", AppConstants.PRIMARY_BUTTON_COLOR);
        resetButton = createActionButton("🔄 Reset View", AppConstants.SECONDARY_BUTTON_COLOR);
        exportButton = createActionButton("📄 Export Data", AppConstants.SUCCESS_COLOR);

        filterPanel.add(employeeLabel);
        filterPanel.add(Box.createHorizontalStrut(25));
        filterPanel.add(Box.createHorizontalGlue());

        filterPanel.add(monthLabel);
        filterPanel.add(Box.createHorizontalStrut(8));
        filterPanel.add(monthComboBox);
        filterPanel.add(Box.createHorizontalStrut(20));

        filterPanel.add(yearLabel);
        filterPanel.add(Box.createHorizontalStrut(8));
        filterPanel.add(yearComboBox);
        filterPanel.add(Box.createHorizontalStrut(20));

        filterPanel.add(filterButton);
        filterPanel.add(Box.createHorizontalStrut(12));
        filterPanel.add(resetButton);
        filterPanel.add(Box.createHorizontalStrut(12));
        filterPanel.add(exportButton);
    }

    private JButton createActionButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(true);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setFocusable(true);
        button.setPreferredSize(new Dimension(140, 35));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(backgroundColor.darker(), 1),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            private Color originalColor = backgroundColor;

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                Color hoverColor = new Color(
                        Math.min(255, originalColor.getRed() + 20),
                        Math.min(255, originalColor.getGreen() + 20),
                        Math.min(255, originalColor.getBlue() + 20));
                button.setBackground(hoverColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(hoverColor.darker(), 2),
                        BorderFactory.createEmptyBorder(7, 15, 7, 15)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(originalColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(originalColor.darker(), 1),
                        BorderFactory.createEmptyBorder(8, 16, 8, 16)));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                Color pressedColor = originalColor.darker();
                button.setBackground(pressedColor);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (button.contains(e.getPoint())) {
                    Color hoverColor = new Color(
                            Math.min(255, originalColor.getRed() + 20),
                            Math.min(255, originalColor.getGreen() + 20),
                            Math.min(255, originalColor.getBlue() + 20));
                    button.setBackground(hoverColor);
                } else {
                    button.setBackground(originalColor);
                }
            }
        });

        return button;
    }

    private Integer[] generateYearRange() {
        int currentYear = LocalDate.now().getYear();
        Integer[] years = new Integer[6];
        for (int i = 0; i < 6; i++) {
            years[i] = currentYear - 3 + i;
        }
        return years;
    }

    private void createAnalyticsPanel() {
        analyticsPanel = new JPanel(new GridLayout(2, 4, 15, 10));
        analyticsPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        analyticsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("📈 Attendance Analytics"),
                new EmptyBorder(15, 15, 15, 15)));

        totalDaysLabel = createSimpleMetricCard("📅", "Total Days", "0");
        presentDaysLabel = createSimpleMetricCard("✅", "Present", "0");
        lateDaysLabel = createSimpleMetricCard("⏰", "Late Arrivals", "0");
        totalHoursLabel = createSimpleMetricCard("🕐", "Total Hours", "0.0");
        avgHoursLabel = createSimpleMetricCard("📊", "Avg Hours/Day", "0.0");
        attendanceRateLabel = createSimpleMetricCard("📈", "Attendance Rate", "0%");

        JPanel progressPanel = createSimpleProgressCard();
        JPanel trendPanel = createSimpleTrendCard();

        analyticsPanel.add(totalDaysLabel);
        analyticsPanel.add(presentDaysLabel);
        analyticsPanel.add(lateDaysLabel);
        analyticsPanel.add(totalHoursLabel);
        analyticsPanel.add(avgHoursLabel);
        analyticsPanel.add(attendanceRateLabel);
        analyticsPanel.add(progressPanel);
        analyticsPanel.add(trendPanel);
    }

    private JLabel createMetricCard(String icon, String title, String value, Color accentColor) {
        JLabel card = new JLabel(String.format(
                "<html><div style='text-align: center;'>" +
                        "<div style='font-size: 16px; margin-bottom: 5px;'>%s</div>" +
                        "<div style='font-size: 11px; color: #64748B; margin-bottom: 3px;'>%s</div>" +
                        "<div style='font-size: 18px; font-weight: bold; color: #1e293b;'>%s</div>" +
                        "</div></html>",
                icon, title, value));
        card.setHorizontalAlignment(SwingConstants.CENTER);
        return card;
    }

    private JPanel createCardPanel(JLabel label) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.BORDER_COLOR, 1),
                new EmptyBorder(10, 10, 10, 10)));
        card.add(label, BorderLayout.CENTER);
        return card;
    }

    private JPanel createSimpleMetricCard(String icon, String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.BORDER_COLOR, 1),
                new EmptyBorder(10, 10, 10, 10)));

        JLabel content = new JLabel(String.format(
                "<html><div style='text-align: center;'>" +
                        "<div style='font-size: 16px; margin-bottom: 5px;'>%s</div>" +
                        "<div style='font-size: 11px; color: #64748B; margin-bottom: 3px;'>%s</div>" +
                        "<div style='font-size: 18px; font-weight: bold; color: #1e293b;'>%s</div>" +
                        "</div></html>",
                icon, title, value));
        content.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private JPanel createSimpleProgressCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.BORDER_COLOR, 1),
                new EmptyBorder(10, 10, 10, 10)));

        JLabel title = new JLabel("📊 Rate Progress");
        title.setFont(AppConstants.SMALL_FONT);
        title.setForeground(AppConstants.TEXT_SECONDARY);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        attendanceRateBar = new JProgressBar(0, 100);
        attendanceRateBar.setStringPainted(true);
        attendanceRateBar.setString("0%");
        attendanceRateBar.setForeground(AppConstants.SUCCESS_COLOR);
        attendanceRateBar.setBackground(AppConstants.BACKGROUND_COLOR);

        card.add(title, BorderLayout.NORTH);
        card.add(attendanceRateBar, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createSimpleTrendCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.BORDER_COLOR, 1),
                new EmptyBorder(10, 10, 10, 10)));

        JLabel trendIcon = new JLabel("📈", SwingConstants.CENTER);
        trendIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));

        JLabel trendLabel = new JLabel("Trend Analysis", SwingConstants.CENTER);
        trendLabel.setFont(AppConstants.SMALL_FONT);
        trendLabel.setForeground(AppConstants.TEXT_SECONDARY);

        card.add(trendIcon, BorderLayout.CENTER);
        card.add(trendLabel, BorderLayout.SOUTH);

        return card;
    }

    private void createTablePanel() {
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(AppConstants.BACKGROUND_COLOR);
        tablePanel.setBorder(BorderFactory.createTitledBorder("📋 Attendance Records"));

        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 5:
                    case 6:
                    case 7:
                        return Double.class;
                    default:
                        return String.class;
                }
            }
        };

        attendanceTable = new JTable(tableModel);
        setupTable();

        tableScrollPane = new JScrollPane(attendanceTable);
        tableScrollPane.setPreferredSize(new Dimension(800, 300));
        tableScrollPane.getViewport().setBackground(Color.WHITE);

        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
    }

    private void setupTable() {
        attendanceTable.setFont(AppConstants.TABLE_FONT);
        attendanceTable.setRowHeight(28);
        attendanceTable.setGridColor(AppConstants.BORDER_COLOR);
        attendanceTable.setSelectionBackground(AppConstants.SELECTION_COLOR);
        attendanceTable.setAutoCreateRowSorter(true);
        attendanceTable.setFillsViewportHeight(true);

        int[] columnWidths = { 80, 60, 80, 80, 70, 80, 70, 70, 80, 120 };
        for (int i = 0; i < columnWidths.length && i < attendanceTable.getColumnCount(); i++) {
            TableColumn column = attendanceTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        attendanceTable.setDefaultRenderer(Double.class, new HoursRenderer());
        attendanceTable.getColumnModel().getColumn(8).setCellRenderer(new StatusRenderer());
        attendanceTable.getColumnModel().getColumn(1).setCellRenderer(new DayRenderer());

        attendanceTable.getTableHeader().setBackground(AppConstants.TABLE_HEADER_BACKGROUND);
        attendanceTable.getTableHeader().setForeground(AppConstants.TEXT_COLOR);
        attendanceTable.getTableHeader().setFont(AppConstants.TABLE_HEADER_FONT);
    }

    private void createSummaryPanel() {
        summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        summaryPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JLabel summaryLabel = new JLabel("📊 Showing all records");
        summaryLabel.setFont(AppConstants.SMALL_FONT);
        summaryLabel.setForeground(AppConstants.TEXT_SECONDARY);

        closeButton = createActionButton("✖ Close Dialog", AppConstants.SECONDARY_BUTTON_COLOR);

        summaryPanel.add(summaryLabel, BorderLayout.WEST);
        summaryPanel.add(closeButton, BorderLayout.EAST);
    }

    private void layoutComponents() {
        mainPanel.add(filterPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        centerPanel.add(analyticsPanel, BorderLayout.NORTH);
        centerPanel.add(tablePanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(summaryPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void setupEventHandlers() {
        filterButton.addActionListener(this::applyFilter);
        resetButton.addActionListener(this::resetFilter);
        exportButton.addActionListener(this::exportData);
        closeButton.addActionListener(e -> dispose());

        monthComboBox.addActionListener(e -> updateFilterButtonState());
        yearComboBox.addActionListener(e -> updateFilterButtonState());
    }

    private void updateFilterButtonState() {
        boolean hasFilter = monthComboBox.getSelectedIndex() > 0 ||
                !yearComboBox.getSelectedItem().equals(LocalDate.now().getYear());
        filterButton.setEnabled(true);
        resetButton.setEnabled(hasFilter);
    }

    private void loadInitialData() {
        monthComboBox.setSelectedIndex(LocalDate.now().getMonthValue());
        yearComboBox.setSelectedItem(LocalDate.now().getYear());
        applyFilter(null);
    }

    private void applyFilter(ActionEvent e) {
        int monthIndex = monthComboBox.getSelectedIndex();
        int year = (Integer) yearComboBox.getSelectedItem();

        if (monthIndex == 0) {
            currentFilter = null;
            filteredRecords = allRecords.stream()
                    .filter(record -> record.getDate().getYear() == year)
                    .collect(Collectors.toList());
        } else {
            currentFilter = YearMonth.of(year, monthIndex);
            filteredRecords = allRecords.stream()
                    .filter(record -> {
                        LocalDate date = record.getDate();
                        return date.getYear() == year && date.getMonthValue() == monthIndex;
                    })
                    .collect(Collectors.toList());
        }

        updateTableData();
        updateAnalytics();
        updateSummaryLabel();
    }

    private void resetFilter(ActionEvent e) {
        monthComboBox.setSelectedIndex(0);
        yearComboBox.setSelectedItem(LocalDate.now().getYear());
        currentFilter = null;
        filteredRecords = allRecords;

        updateTableData();
        updateAnalytics();
        updateSummaryLabel();
    }

    private void updateTableData() {
        tableModel.setRowCount(0);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd");

        for (AttendanceRecord record : filteredRecords) {
            Object[] row = new Object[COLUMN_NAMES.length];

            row[0] = record.getDate().format(dateFormatter);
            row[1] = record.getDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            row[2] = record.getTimeIn().toString();
            row[3] = record.getTimeOut().toString();
            row[4] = "1:00";
            row[5] = record.getTotalHours();
            row[6] = Math.min(record.getTotalHours(), 8.0);
            row[7] = Math.max(0, record.getTotalHours() - 8.0);
            row[8] = record.isLate() ? "Late" : "On Time";
            row[9] = record.isLate() ? "Late arrival" : "";

            tableModel.addRow(row);
        }
    }

    private void updateAnalytics() {
        if (filteredRecords.isEmpty()) {
            updateMetricCard(totalDaysLabel, "📅", "Total Days", "0");
            updateMetricCard(presentDaysLabel, "✅", "Present", "0");
            updateMetricCard(lateDaysLabel, "⏰", "Late Arrivals", "0");
            updateMetricCard(totalHoursLabel, "🕐", "Total Hours", "0.0");
            updateMetricCard(avgHoursLabel, "📊", "Avg Hours/Day", "0.0");
            updateMetricCard(attendanceRateLabel, "📈", "Attendance Rate", "0%");
            attendanceRateBar.setValue(0);
            attendanceRateBar.setString("0%");
            return;
        }

        int totalDays = filteredRecords.size();
        long lateDays = filteredRecords.stream().mapToLong(r -> r.isLate() ? 1 : 0).sum();
        double totalHours = filteredRecords.stream().mapToDouble(AttendanceRecord::getTotalHours).sum();
        double avgHours = totalHours / totalDays;

        int expectedDays = calculateExpectedWorkingDays();
        double attendanceRate = expectedDays > 0 ? (double) totalDays / expectedDays * 100 : 100;
        attendanceRate = Math.min(100, attendanceRate);

        updateMetricCard(totalDaysLabel, "📅", "Total Days", String.valueOf(totalDays));
        updateMetricCard(presentDaysLabel, "✅", "Present", String.valueOf(totalDays));
        updateMetricCard(lateDaysLabel, "⏰", "Late Arrivals", String.valueOf(lateDays));
        updateMetricCard(totalHoursLabel, "🕐", "Total Hours", String.format("%.1f", totalHours));
        updateMetricCard(avgHoursLabel, "📊", "Avg Hours/Day", String.format("%.1f", avgHours));
        updateMetricCard(attendanceRateLabel, "📈", "Attendance Rate", String.format("%.1f%%", attendanceRate));

        attendanceRateBar.setValue((int) attendanceRate);
        attendanceRateBar.setString(String.format("%.1f%%", attendanceRate));

        if (attendanceRate >= 95) {
            attendanceRateBar.setForeground(AppConstants.SUCCESS_COLOR);
        } else if (attendanceRate >= 85) {
            attendanceRateBar.setForeground(AppConstants.WARNING_COLOR);
        } else {
            attendanceRateBar.setForeground(AppConstants.DELETE_BUTTON_COLOR);
        }
    }

    private void updateMetricCard(JPanel cardPanel, String icon, String title, String value) {
        Component[] components = cardPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                label.setText(String.format(
                        "<html><div style='text-align: center;'>" +
                                "<div style='font-size: 16px; margin-bottom: 5px;'>%s</div>" +
                                "<div style='font-size: 11px; color: #64748B; margin-bottom: 3px;'>%s</div>" +
                                "<div style='font-size: 18px; font-weight: bold; color: #1e293b;'>%s</div>" +
                                "</div></html>",
                        icon, title, value));
                break;
            }
        }
    }

    private int calculateExpectedWorkingDays() {
        if (currentFilter != null) {
            YearMonth yearMonth = currentFilter;
            LocalDate start = yearMonth.atDay(1);
            LocalDate end = yearMonth.atEndOfMonth();

            int workingDays = 0;
            LocalDate current = start;
            while (!current.isAfter(end)) {
                if (current.getDayOfWeek().getValue() <= 5) {
                    workingDays++;
                }
                current = current.plusDays(1);
            }
            return workingDays;
        } else {
            return filteredRecords.size();
        }
    }

    private void updateSummaryLabel() {
        JLabel summaryLabel = (JLabel) summaryPanel.getComponent(0);

        String filterText;
        if (currentFilter != null) {
            filterText = "Showing records for " +
                    currentFilter.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) +
                    " " + currentFilter.getYear();
        } else if (monthComboBox.getSelectedIndex() == 0) {
            filterText = "Showing all records for " + yearComboBox.getSelectedItem();
        } else {
            filterText = "Showing all records";
        }

        summaryLabel.setText("📊 " + filterText + " (" + filteredRecords.size() + " records)");
    }

    private void exportData(ActionEvent e) {
        JOptionPane.showMessageDialog(this,
                "Export functionality would save the filtered attendance data to CSV/Excel format.\n" +
                        "This feature can be implemented based on specific requirements.",
                "Export Data",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void addButtonTooltips() {
        filterButton.setToolTipText("Apply the selected month and year filter to view specific attendance records");
        resetButton.setToolTipText("Reset the view to show all attendance records for the current year");
        exportButton.setToolTipText("Export the filtered attendance data to CSV format for external use");
        closeButton.setToolTipText("Close the attendance viewer dialog and return to the employee list");
    }

    private class HoursRenderer extends DefaultTableCellRenderer {
        @Override
        protected void setValue(Object value) {
            if (value instanceof Double) {
                setText(String.format("%.2f", (Double) value));
            } else {
                setText("");
            }
            setHorizontalAlignment(SwingConstants.RIGHT);
        }
    }

    private class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            setHorizontalAlignment(SwingConstants.CENTER);
            if (!isSelected) {
                if ("Late".equals(value)) {
                    setBackground(new Color(254, 226, 226));
                    setForeground(new Color(153, 27, 27));
                } else {
                    setBackground(new Color(220, 252, 231));
                    setForeground(new Color(21, 128, 61));
                }
            }

            return this;
        }
    }

    private class DayRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(AppConstants.SMALL_FONT);

            if (!isSelected && ("Sat".equals(value) || "Sun".equals(value))) {
                setForeground(AppConstants.TEXT_SECONDARY);
            }

            return this;
        }
    }

    public static void showAttendanceViewer(Frame parent, Employee employee, EmployeeController employeeController) {
        AttendanceViewerDialog dialog = new AttendanceViewerDialog(parent, employee, employeeController);
        dialog.setVisible(true);
    }
}
