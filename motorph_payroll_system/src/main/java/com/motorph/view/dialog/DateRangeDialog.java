package com.motorph.view.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.motorph.util.AppUtils;
import com.motorph.util.AppConstants;

public class DateRangeDialog extends JDialog {
    private JTextField startDateField;
    private JTextField endDateField;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean confirmed = false;

    public DateRangeDialog(JFrame parent, String title) {
        super(parent, title, true);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(AppConstants.BACKGROUND_COLOR);

        JLabel startDateLabel = new JLabel("Start Date (MM/DD/YYYY):");
        startDateLabel.setFont(AppConstants.NORMAL_FONT);
        startDateField = new JTextField(10);
        startDateField.setFont(AppConstants.NORMAL_FONT);
        formPanel.add(startDateLabel);
        formPanel.add(startDateField);

        JLabel endDateLabel = new JLabel("End Date (MM/DD/YYYY):");
        endDateLabel.setFont(AppConstants.NORMAL_FONT);
        endDateField = new JTextField(10);
        endDateField.setFont(AppConstants.NORMAL_FONT);
        formPanel.add(endDateLabel);
        formPanel.add(endDateField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.setBackground(AppConstants.BUTTON_COLOR);
        okButton.setFont(AppConstants.NORMAL_FONT);
        cancelButton.setBackground(AppConstants.BUTTON_COLOR);
        cancelButton.setFont(AppConstants.NORMAL_FONT);

        okButton.addActionListener(e -> {
            try {
                LocalDate start = AppUtils.validateDate(startDateField.getText());
                LocalDate end = AppUtils.validateDate(endDateField.getText());
                AppUtils.validateDateRange(start, end);

                this.startDate = start;
                this.endDate = end;
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

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setResizable(false);
        setLocationRelativeTo(getParent());
    }

    public LocalDate getStartDate() { return confirmed ? startDate : null; }
    public LocalDate getEndDate() { return confirmed ? endDate : null; }
    public boolean isConfirmed() { return confirmed; }
}
