package com.motorph.view.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.motorph.model.Employee;
import com.motorph.util.AppConstants;

public class SearchResultDialog extends JDialog {

    public SearchResultDialog(JFrame parent, List<Employee> employees, String title) {
        super(parent, title, true);
        initComponents(employees);
    }

    private void initComponents(List<Employee> employees) {
        String[] columnNames = {"Emp#", "Name", "Position", "Status", "Hourly Rate"};

        Object[][] data = new Object[employees.size()][5];
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            String name = employee.getFullName();
            String position = employee.getPosition().length() > 18
                    ? employee.getPosition().substring(0, 15) + "..."
                    : employee.getPosition();
            data[i][0] = employee.getEmployeeId();
            data[i][1] = name;
            data[i][2] = position;
            data[i][3] = employee.getStatus();
            data[i][4] = String.format("%.2f", employee.getHourlyRate());
        }

        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        table.setFont(AppConstants.NORMAL_FONT);
        table.getTableHeader().setFont(AppConstants.NORMAL_FONT);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 300));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        JButton closeButton = new JButton("Close");
        closeButton.setFont(AppConstants.NORMAL_FONT);
        closeButton.setBackground(AppConstants.BUTTON_COLOR);
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getParent());
    }
}
