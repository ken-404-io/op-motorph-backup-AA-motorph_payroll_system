package com.motorph.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.motorph.controller.AuthenticationController;

public class Login extends JFrame implements LoginPanel.LoginCallback {
    private static final Logger logger = Logger.getLogger(Login.class.getName());

    private final AuthenticationController authController;
    private LoginPanel loginPanel;
    private Runnable onLoginSuccessCallback;

    public Login() {
        this.authController = new AuthenticationController();
        initFrame();
    }

    public Login(Runnable onLoginSuccessCallback) {
        this.authController = new AuthenticationController();
        this.onLoginSuccessCallback = onLoginSuccessCallback;
        initFrame();
    }

    private void initFrame() {
        setTitle("MotorPH Payroll System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        loginPanel = new LoginPanel(authController, this);
        add(loginPanel, BorderLayout.CENTER);

        setSize(500, 600);
        setMinimumSize(new Dimension(450, 550));
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            setIconImage(new javax.swing.ImageIcon("motorPH_logo.png").getImage());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Could not load application icon", e);
        }

        logger.log(Level.INFO, "Login frame initialized");
    }

    @Override
    public void onLoginSuccess() {
        logger.log(Level.INFO, "Login successful, transitioning to main application");

        setVisible(false);

        if (onLoginSuccessCallback != null) {
            SwingUtilities.invokeLater(onLoginSuccessCallback);
        }

        dispose();
    }

    @Override
    public void onExit() {
        logger.log(Level.INFO, "User chose to exit application");
        System.exit(0);
    }

    public void showLogin() {
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
            loginPanel.setFocusToUsername();
        });
    }

    public AuthenticationController getAuthenticationController() {
        return authController;
    }

    public void clearLoginForm() {
        if (loginPanel != null) {
            loginPanel.clearForm();
        }
    }
}
