package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.motorph.controller.EmployeeController;
import com.motorph.controller.PayrollController;
import com.motorph.controller.ReportController;
import com.motorph.util.AppConstants;
import com.motorph.util.AppUtils;

public class MainFrame extends JFrame {
    private final EmployeeController employeeController;
    private final PayrollController payrollController;
    private final ReportController reportController;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private HeaderPanel headerPanel;
    private JPanel sideNavPanel;
    private Dashboard dashboardPanel;
    private EmployeePanel employeePanel;
    private PayrollNew payrollPanel;
    private Reports reportsPanel;

    public MainFrame(EmployeeController employeeController,
            PayrollController payrollController,
            ReportController reportController) {
        super(AppConstants.APP_TITLE);

        this.employeeController = employeeController;
        this.payrollController = payrollController;
        this.reportController = reportController;

        initUI();
    }

    private void initUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.err.println("Could not set system look and feel: " + e.getMessage());
        }

        setSize(1400, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        headerPanel = new HeaderPanel();

        sideNavPanel = createSideNavigationPanel();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(AppConstants.BACKGROUND_COLOR);

        dashboardPanel = new Dashboard(this, employeeController);
        employeePanel = new EmployeePanel(this, employeeController);
        payrollPanel = new PayrollNew(this, payrollController);
        reportsPanel = new Reports(this, reportController);

        cardPanel.add(dashboardPanel, "MainMenu");
        cardPanel.add(employeePanel, "EmployeeList");
        cardPanel.add(payrollPanel, "PayrollManagement");
        cardPanel.add(reportsPanel, "Reports");

        cardLayout.show(cardPanel, "MainMenu");

        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.add(headerPanel, BorderLayout.NORTH);
        mainContentPanel.add(cardPanel, BorderLayout.CENTER);

        add(sideNavPanel, BorderLayout.WEST);
        add(mainContentPanel, BorderLayout.CENTER);
    }

    private JPanel createSideNavigationPanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(AppConstants.SIDEBAR_BACKGROUND);
        sidePanel.setPreferredSize(new Dimension(280, 0));
        sidePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(148, 163, 184)));

        JPanel brandPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        brandPanel.setBackground(AppConstants.SIDEBAR_BACKGROUND);
        brandPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel brandLabel = new JLabel("MotorPH");
        brandLabel.setFont(AppConstants.TITLE_FONT);
        brandLabel.setForeground(Color.WHITE);
        brandPanel.add(brandLabel);

        sidePanel.add(brandPanel);
        sidePanel.add(Box.createVerticalStrut(20));

        addNavButton(sidePanel, "🏠 Dashboard", "MainMenu");
        addNavButton(sidePanel, "👥 Employee Management", "EmployeeList");
        addNavButton(sidePanel, "💰 Payroll Management", "PayrollManagement");
        addNavButton(sidePanel, "📊 Reports", "Reports");

        sidePanel.add(Box.createVerticalGlue());

        JButton logoutBtn = AppUtils.createDangerButton("🚪 Logout");
        logoutBtn.addActionListener(e -> logout());
        JPanel logoutPanel = new JPanel(new FlowLayout());
        logoutPanel.setBackground(AppConstants.SIDEBAR_BACKGROUND);
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));
        logoutPanel.add(logoutBtn);
        sidePanel.add(logoutPanel);

        return sidePanel;
    }

    private void addNavButton(JPanel parent, String text, String panelName) {
        JButton button = AppUtils.createNavigationButton(text);
        if (panelName != null) {
            button.addActionListener(e -> showPanel(panelName));
        }

        button.setPreferredSize(new Dimension(240, 45));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(AppConstants.SIDEBAR_BACKGROUND);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        buttonPanel.add(button);
        parent.add(buttonPanel);
    }

    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }

    public void showMainMenu() {
        cardLayout.show(cardPanel, "MainMenu");
    }

    public void showEmployeeManagement() {
        cardLayout.show(cardPanel, "EmployeeList");
    }

    public void showEmployeeList() {
        cardLayout.show(cardPanel, "EmployeeList");
    }

    public void showPayrollManagement() {
        cardLayout.show(cardPanel, "PayrollManagement");
    }

    public void showReports() {
        cardLayout.show(cardPanel, "Reports");
    }

    public void logout() {
        com.motorph.controller.AuthenticationController authController = new com.motorph.controller.AuthenticationController();

        boolean logoutSuccess = authController.logout();

        if (logoutSuccess) {
            this.dispose();
            System.exit(0);
        }
    }
}
