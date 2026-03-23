package com.motorph.view.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.motorph.util.AppConstants;
import com.motorph.util.AppUtils;

public class EmployeeNumberInputDialog extends JDialog {
    private JTextField employeeNumberField;
    private int employeeNumber = -1;
    private boolean confirmed = false;

    public EmployeeNumberInputDialog(JFrame parent, String title) {
        super(parent, title, true);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(AppConstants.BACKGROUND_COLOR);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setBackground(AppConstants.BACKGROUND_COLOR);

        JLabel label = new JLabel("Enter Employee No:");
        label.setFont(AppConstants.NORMAL_FONT);
        employeeNumberField = new JTextField(10);
        employeeNumberField.setFont(AppConstants.NORMAL_FONT);

        inputPanel.add(label);
        inputPanel.add(employeeNumberField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(AppConstants.BACKGROUND_COLOR);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.setBackground(AppConstants.BUTTON_COLOR);
        okButton.setFont(AppConstants.NORMAL_FONT);
        cancelButton.setBackground(AppConstants.BUTTON_COLOR);
        cancelButton.setFont(AppConstants.NORMAL_FONT);

        okButton.addActionListener(e -> {
            try {
                this.employeeNumber = AppUtils.validateEmployeeNumber(employeeNumberField.getText());
                this.confirmed = true;
                dispose();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            this.confirmed = false;
            dispose();
        });

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        pack();
        setResizable(false);
        setLocationRelativeTo(getParent());

        getRootPane().setDefaultButton(okButton);
    }

    public int getEmployeeNumber() { return confirmed ? employeeNumber : -1; }
    public boolean isConfirmed() { return confirmed; }
}
