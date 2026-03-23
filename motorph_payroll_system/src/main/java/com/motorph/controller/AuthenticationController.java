package com.motorph.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.motorph.model.User;
import com.motorph.service.AuthenticationService;
import com.motorph.util.AppUtils;

public class AuthenticationController {
    private static final Logger logger = Logger.getLogger(AuthenticationController.class.getName());

    private final AuthenticationService authService;

    public AuthenticationController() {
        this.authService = new AuthenticationService();
    }

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    public boolean login(String username, String password) {
        try {
            logger.log(Level.INFO, "Login attempt for username: {0}", username);

            if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
                logger.log(Level.WARNING, "Login attempt with empty credentials");
                return false;
            }

            User user = authService.authenticateUser(username, password);

            if (user != null) {
                AppUtils.setCurrentUser(user);
                logger.log(Level.INFO, "Login successful for user: {0}", username);
                return true;
            } else {
                logger.log(Level.WARNING, "Login failed for user: {0}", username);
                return false;
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Login error for user: " + username, e);
            return false;
        }
    }

    public boolean logout() {
        try {
            User currentUser = AppUtils.getCurrentUser();
            if (currentUser != null) {
                logger.log(Level.INFO, "User {0} logging out", currentUser.getUsername());
                AppUtils.clearSession();
                logger.log(Level.INFO, "Logout successful for user: {0}", currentUser.getUsername());
                return true;
            } else {
                logger.log(Level.WARNING, "Logout attempted but no user was logged in");
                return false;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during logout", e);
            return false;
        }
    }

    public boolean isLoggedIn() {
        return AppUtils.getCurrentUser() != null;
    }

    public User getCurrentUser() {
        return AppUtils.getCurrentUser();
    }

    public String getCurrentUsername() {
        User currentUser = AppUtils.getCurrentUser();
        return currentUser != null ? currentUser.getUsername() : null;
    }

    public boolean isCurrentUserAdmin() {
        User currentUser = AppUtils.getCurrentUser();
        return currentUser != null && "admin".equals(currentUser.getRole());
    }

    public String getSessionInfo() {
        User currentUser = AppUtils.getCurrentUser();
        if (currentUser != null) {
            return "User: " + currentUser.getUsername() + " (Role: " + currentUser.getRole() + ")";
        }
        return "No user logged in";
    }

    public boolean validateCredentials(String username, String password) {
        try {
            return authService.validateLogin(username, password);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error validating credentials for user: " + username, e);
            return false;
        }
    }

    public AuthenticationService getAuthenticationService() {
        return authService;
    }

    public boolean isCredentialsFileAvailable() {
        return authService.isCredentialsFileAvailable();
    }

    public int getUserCount() {
        return authService.getUserCount();
    }
}
