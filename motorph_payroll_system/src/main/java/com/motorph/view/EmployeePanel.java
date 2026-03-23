package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.motorph.controller.EmployeeController;
import com.motorph.model.Employee;
import com.motorph.util.AppConstants;
import com.motorph.util.AppUtils;
import com.motorph.view.dialog.AttendanceViewerDialog;
import com.motorph.view.dialog.EmployeeDialog;

public class EmployeePanel extends JPanel {

    private final MainFrame mainFrame;
    private final EmployeeController employeeController;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    private JButton viewAttendanceBtn;
    private JButton viewDetailsBtn;
    private JButton editEmployeeBtn;
    private JButton deleteEmployeeBtn;

    private int selectedEmployeeRow = -1;

    private static final String[] COLUMN_NAMES = {
            "Emp. No.", "Name", "Position", "Department", "Status"
    };

    public EmployeePanel(MainFrame mainFrame, EmployeeController employeeController) {
        this.mainFrame = mainFrame;
        this.employeeController = employeeController;
        initPanel();
        loadEmployeeData();
    }

    private void initPanel() {
        setLayout(new BorderLayout());
        setBackground(AppConstants.BACKGROUND_COLOR);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(AppConstants.BACKGROUND_COLOR);
        container.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        JPanel contentCard = AppUtils.createCardPanel();
        contentCard.setLayout(new BorderLayout(0, 24));

        JPanel headerPanel = createHeaderPanel();
        contentCard.add(headerPanel, BorderLayout.NORTH);

        JPanel tablePanel = createTablePanel();
        contentCard.add(tablePanel, BorderLayout.CENTER);

        container.add(contentCard, BorderLayout.CENTER);
        add(container, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);

        JPanel topSection = createTopNavigationPanel();

        JPanel titleSection = new JPanel(new BorderLayout());
        titleSection.setBackground(Color.WHITE);
        titleSection.setBorder(BorderFactory.createEmptyBorder(16, 0, 24, 0));

        JLabel titleLabel = new JLabel("Employee Management System");
        titleLabel.setFont(AppConstants.TITLE_FONT);
        titleLabel.setForeground(AppConstants.TEXT_COLOR);
        titleSection.add(titleLabel, BorderLayout.WEST);

        JPanel ribbonPanel = createRibbonActionBar();

        header.add(topSection, BorderLayout.NORTH);
        header.add(titleSection, BorderLayout.CENTER);
        header.add(ribbonPanel, BorderLayout.SOUTH);

        return header;
    }

    private JPanel createTopNavigationPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

        JLabel logoLabel = new JLabel("MotorPH");
        logoLabel.setFont(AppConstants.SUBHEADING_FONT);
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setOpaque(true);
        logoLabel.setBackground(AppConstants.TEXT_COLOR);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        JButton backButton = AppUtils.createSecondaryButton("← Back to Main Menu");
        backButton.addActionListener(e -> backToMainMenu());

        topPanel.add(logoLabel, BorderLayout.WEST);
        topPanel.add(backButton, BorderLayout.EAST);

        return topPanel;
    }

    private JPanel createRibbonActionBar() {
        JPanel ribbonPanel = new JPanel(new BorderLayout());
        ribbonPanel.setBackground(AppConstants.NAVIGATION_BACKGROUND);
        ribbonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)));

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        actionsPanel.setBackground(AppConstants.NAVIGATION_BACKGROUND);

        JButton addButton = AppUtils.createPrimaryButton("+ Add New");
        addButton.addActionListener(e -> openNewEmployeeDialog());
        actionsPanel.add(addButton);

        JPanel separator = new JPanel();
        separator.setPreferredSize(new java.awt.Dimension(1, 32));
        separator.setBackground(AppConstants.BORDER_COLOR);
        separator.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
        actionsPanel.add(separator);

        viewAttendanceBtn = AppUtils.createSecondaryButton("📅 View Attendance");
        viewDetailsBtn = AppUtils.createSecondaryButton("👁 View Details");
        editEmployeeBtn = AppUtils.createSecondaryButton("✏ Edit");
        deleteEmployeeBtn = AppUtils.createDangerButton("🗑 Delete");

        viewAttendanceBtn.setEnabled(false);
        viewDetailsBtn.setEnabled(false);
        editEmployeeBtn.setEnabled(false);
        deleteEmployeeBtn.setEnabled(false);

        viewAttendanceBtn.addActionListener(e -> viewAttendanceForSelected());
        viewDetailsBtn.addActionListener(e -> viewDetailsForSelected());
        editEmployeeBtn.addActionListener(e -> editSelectedEmployee());
        deleteEmployeeBtn.addActionListener(e -> deleteSelectedEmployee());

        actionsPanel.add(viewAttendanceBtn);
        actionsPanel.add(Box.createHorizontalStrut(8));
        actionsPanel.add(viewDetailsBtn);
        actionsPanel.add(Box.createHorizontalStrut(8));
        actionsPanel.add(editEmployeeBtn);
        actionsPanel.add(Box.createHorizontalStrut(8));
        actionsPanel.add(deleteEmployeeBtn);

        JPanel searchPanel = createSearchPanel();

        ribbonPanel.add(actionsPanel, BorderLayout.WEST);
        ribbonPanel.add(searchPanel, BorderLayout.EAST);

        return ribbonPanel;
    }

    private void updateRibbonButtons(boolean hasSelection) {
        viewAttendanceBtn.setEnabled(hasSelection);
        viewDetailsBtn.setEnabled(hasSelection);
        editEmployeeBtn.setEnabled(hasSelection);
        deleteEmployeeBtn.setEnabled(hasSelection);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        searchPanel.setBackground(Color.WHITE);

        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        searchIcon.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 8));

        searchField = new JTextField(25) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                g2.setColor(AppConstants.BORDER_COLOR);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        searchField.setFont(AppConstants.NORMAL_FONT);
        searchField.setBorder(BorderFactory.createEmptyBorder(8, 40, 8, 12));
        searchField.setOpaque(false);
        searchField.setText("Search by name or employee no...");
        searchField.setForeground(AppConstants.TEXT_MUTED);

        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (searchField.getText().equals("Search by name or employee no...")) {
                    searchField.setText("");
                    searchField.setForeground(AppConstants.TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search by name or employee no...");
                    searchField.setForeground(AppConstants.TEXT_MUTED);
                }
            }
        });

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterEmployees();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterEmployees();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterEmployees();
            }
        });

        JPanel searchContainer = new JPanel();
        searchContainer.setLayout(new BorderLayout());
        searchContainer.setBackground(Color.WHITE);
        searchContainer.add(searchField, BorderLayout.CENTER);
        searchContainer.add(searchIcon, BorderLayout.WEST);

        searchPanel.add(searchContainer);
        return searchPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        createEmployeeTable();

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(AppConstants.BORDER_COLOR, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private void createEmployeeTable() {
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };

        employeeTable = new JTable(tableModel);
        employeeTable.setFont(AppConstants.TABLE_FONT);
        employeeTable.setRowHeight(AppConstants.TABLE_ROW_HEIGHT);
        employeeTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        employeeTable.setFillsViewportHeight(true);
        employeeTable.getTableHeader().setReorderingAllowed(false);
        employeeTable.setShowGrid(true);
        employeeTable.setGridColor(AppConstants.TABLE_BORDER_COLOR);
        employeeTable.setIntercellSpacing(new java.awt.Dimension(1, 1));

        employeeTable.getTableHeader().setBackground(AppConstants.TABLE_HEADER_BACKGROUND);
        employeeTable.getTableHeader().setForeground(AppConstants.TEXT_COLOR);
        employeeTable.getTableHeader().setFont(AppConstants.TABLE_HEADER_FONT);
        employeeTable.getTableHeader()
                .setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppConstants.BORDER_COLOR));

        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(250);
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(120);

        employeeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = employeeTable.getSelectedRow();
                selectedEmployeeRow = selectedRow;
                updateRibbonButtons(selectedRow >= 0);
            }
        });

        setupTableRenderers();
    }

    private void setupTableRenderers() {
        DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    setBackground(new Color(224, 231, 255));
                    setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(79, 70, 229)));
                } else {
                    setBackground(row % 2 == 0 ? Color.WHITE : AppConstants.TABLE_ALT_ROW);
                    setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
                }

                setFont(AppConstants.TABLE_FONT);

                return this;
            }
        };

        DefaultTableCellRenderer statusRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));

                if (isSelected) {
                    panel.setBackground(new Color(224, 231, 255));
                    panel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(79, 70, 229)));
                } else {
                    panel.setBackground(row % 2 == 0 ? Color.WHITE : AppConstants.TABLE_ALT_ROW);
                }

                if (value != null) {
                    String status = value.toString();
                    boolean isActive = "Active".equalsIgnoreCase(status);
                    JLabel statusBadge = AppUtils.createStatusBadge(status, isActive);
                    panel.add(statusBadge);
                }

                return panel;
            }
        };

        for (int i = 0; i < 4; i++) {
            employeeTable.getColumnModel().getColumn(i).setCellRenderer(defaultRenderer);
        }
        employeeTable.getColumnModel().getColumn(4).setCellRenderer(statusRenderer);
    }

    private void loadEmployeeData() {
        tableModel.setRowCount(0);

        List<Employee> employees = employeeController.getAllEmployees();

        for (Employee employee : employees) {
            Object[] rowData = {
                    employee.getEmployeeId(),
                    employee.getFullName(),
                    employee.getPosition() != null ? employee.getPosition() : "N/A",
                    getDepartmentFromPosition(employee.getPosition()),
                    employee.getStatus() != null ? employee.getStatus() : "Active"
            };
            tableModel.addRow(rowData);
        }
    }

    private String getDepartmentFromPosition(String position) {
        if (position == null) return "N/A";

        String pos = position.toLowerCase();
        if (pos.contains("chief executive") || pos.contains("chief operating")) {
            return "Executive";
        } else if (pos.contains("chief finance") || pos.contains("finance")) {
            return "Finance";
        } else if (pos.contains("hr") || pos.contains("human resources")) {
            return "Human Resources";
        } else if (pos.contains("accounting")) {
            return "Accounting";
        } else if (pos.contains("marketing")) {
            return "Marketing";
        } else if (pos.contains("it") || pos.contains("technology")) {
            return "IT";
        }
        return "General";
    }

    private void viewAttendanceForSelected() {
        if (selectedEmployeeRow >= 0) {
            try {
                int employeeNumber = Integer.parseInt(tableModel.getValueAt(selectedEmployeeRow, 0).toString());
                Employee employee = employeeController.findEmployeeById(employeeNumber);

                if (employee != null) {
                    AttendanceViewerDialog.showAttendanceViewer(
                            (Frame) SwingUtilities.getWindowAncestor(this),
                            employee,
                            employeeController);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Employee not found: ID " + employeeNumber,
                            "Employee Not Found",
                            JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error loading attendance data: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewDetailsForSelected() {
        if (selectedEmployeeRow >= 0) {
            viewEmployeeAtRow(selectedEmployeeRow);
        }
    }

    private void editSelectedEmployee() {
        if (selectedEmployeeRow >= 0) {
            editEmployeeAtRow(selectedEmployeeRow);
        }
    }

    private void deleteSelectedEmployee() {
        if (selectedEmployeeRow >= 0) {
            deleteEmployeeAtRow(selectedEmployeeRow);
        }
    }

    private void openNewEmployeeDialog() {
        if (EmployeeDialog.showAddDialog(mainFrame, employeeController)) {
            loadEmployeeData();
        }
    }

    private void backToMainMenu() {
        mainFrame.showMainMenu();
    }

    private void viewEmployeeAtRow(int row) {
        try {
            int employeeNumber = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            Employee employee = employeeController.findEmployeeById(employeeNumber);
            if (employee != null) {
                showEmployeeDetails(employee);
            } else {
                AppUtils.showError(this, "Employee not found: ID " + employeeNumber);
            }
        } catch (Exception e) {
            AppUtils.handleException("viewing employee details", e);
        }
    }

    private void showEmployeeDetails(Employee employee) {
        com.motorph.view.dialog.EmployeeDetailsDialog dialog = new com.motorph.view.dialog.EmployeeDetailsDialog(
                (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this),
                employee,
                employeeController);
        dialog.setVisible(true);
    }

    private void editEmployeeAtRow(int row) {
        try {
            int employeeNumber = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            Employee employee = employeeController.findEmployeeById(employeeNumber);

            if (employee != null) {
                if (EmployeeDialog.showEditDialog(mainFrame, employeeController, employee)) {
                    loadEmployeeData();
                }
            } else {
                AppUtils.showError(this, "Employee not found: ID " + employeeNumber);
            }
        } catch (Exception e) {
            AppUtils.handleException("loading employee for editing", e);
        }
    }

    private void deleteEmployeeAtRow(int row) {
        try {
            int employeeNumber = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            Employee employee = employeeController.findEmployeeById(employeeNumber);

            if (employee != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete employee:\n" +
                                employee.getFullName() + " (ID: " + employeeNumber + ")?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = employeeController.deleteEmployee(employeeNumber);

                    if (success) {
                        JOptionPane.showMessageDialog(this,
                                "Employee " + employee.getFullName() + " (ID: " + employeeNumber
                                        + ") deleted successfully!",
                                "Delete Successful",
                                JOptionPane.INFORMATION_MESSAGE);

                        loadEmployeeData();

                        employeeTable.clearSelection();
                        updateRibbonButtons(false);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Failed to delete employee. Please try again.",
                                "Delete Failed",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Employee not found: ID " + employeeNumber,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error accessing employee data: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterEmployees() {
        String searchText = searchField.getText().toLowerCase().trim();

        if (searchText.isEmpty() || searchText.equals("search by name or employee no...")) {
            loadEmployeeData();
            return;
        }

        tableModel.setRowCount(0);

        List<Employee> employees = employeeController.getAllEmployees();
        for (Employee employee : employees) {
            boolean matches = false;

            if (String.valueOf(employee.getEmployeeId()).toLowerCase().contains(searchText)) {
                matches = true;
            }

            if (employee.getFullName() != null &&
                    employee.getFullName().toLowerCase().contains(searchText)) {
                matches = true;
            }

            if (employee.getPosition() != null &&
                    employee.getPosition().toLowerCase().contains(searchText)) {
                matches = true;
            }

            if (matches) {
                Object[] rowData = {
                        employee.getEmployeeId(),
                        employee.getFullName(),
                        employee.getPosition() != null ? employee.getPosition() : "N/A",
                        getDepartmentFromPosition(employee.getPosition()),
                        employee.getStatus() != null ? employee.getStatus() : "Active"
                };
                tableModel.addRow(rowData);
            }
        }
    }
}
