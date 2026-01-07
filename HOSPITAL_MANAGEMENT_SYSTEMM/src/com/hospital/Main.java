// File: src/main/java/com/hospital/Main.java
package com.hospital;

import com.hospital.controllers.AdminDashboardController;
import com.hospital.controllers.DoctorDashboardController;
import com.hospital.controllers.HospitalDashboardController;
import com.hospital.controllers.NurseDashboardController;
import com.hospital.controllers.PatientDashboardController;
import com.hospital.controllers.PatientRegistrationController;
import com.hospital.services.DataService;
import com.hospital.services.SessionManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    private DataService dataService;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public Main() {
        setTitle("Hospital Management System");
        setSize(1400, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Initialize data service and load data
        dataService = new DataService();
        dataService.loadAllData();
        
        // Create main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Show login screen
        showLoginScreen();
        
        add(mainPanel);
        setVisible(true);
    }

    private void showLoginScreen() {
        JPanel loginPanel = new JPanel(null);
        loginPanel.setBackground(new Color(102, 126, 234));
        
        // Create center white card
        JPanel centerCard = new JPanel(null);
        centerCard.setBackground(Color.WHITE);
        centerCard.setBounds(475, 150, 450, 550);
        centerCard.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        // Title
        JLabel titleLabel = new JLabel("Hospital Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(102, 126, 234));
        titleLabel.setBounds(50, 30, 350, 30);
        centerCard.add(titleLabel);
        
        JLabel subtitleLabel = new JLabel("Please login to continue");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(102, 102, 102));
        subtitleLabel.setBounds(120, 65, 200, 20);
        centerCard.add(subtitleLabel);
        
        // Username
        JLabel usernameLabel = new JLabel("Username / Email");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        usernameLabel.setBounds(50, 120, 150, 20);
        centerCard.add(usernameLabel);
        
        JTextField usernameField = new JTextField();
        usernameField.setBounds(50, 145, 350, 40);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 224, 224)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        centerCard.add(usernameField);
        
        // Password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 13));
        passwordLabel.setBounds(50, 200, 150, 20);
        centerCard.add(passwordLabel);
        
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(50, 225, 350, 40);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 224, 224)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        centerCard.add(passwordField);
        
        // Role selection
        JLabel roleLabel = new JLabel("Login As");
        roleLabel.setFont(new Font("Arial", Font.BOLD, 13));
        roleLabel.setBounds(50, 280, 150, 20);
        centerCard.add(roleLabel);
        
        String[] roles = {"Doctor", "Nurse", "Patient", "Administrative Staff", "Hospital Admin"};
        JComboBox<String> roleCombo = new JComboBox<>(roles);
        roleCombo.setBounds(50, 305, 350, 40);
        roleCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        centerCard.add(roleCombo);
        
        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 370, 350, 45);
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(102, 126, 234));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        centerCard.add(loginButton);
        
        // Register button
        JButton registerButton = new JButton("New Patient? Register Here");
        registerButton.setBounds(50, 425, 350, 35);
        registerButton.setFont(new Font("Arial", Font.PLAIN, 13));
        registerButton.setForeground(new Color(102, 126, 234));
        registerButton.setBackground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        centerCard.add(registerButton);
        
        // Info label
        JLabel infoLabel = new JLabel("<html><center>Demo Login: Use any email from CSV<br>with password 'password123'</center></html>");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        infoLabel.setForeground(new Color(136, 136, 136));
        infoLabel.setBounds(50, 470, 350, 40);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerCard.add(infoLabel);
        
        // Add action listeners
        loginButton.addActionListener(e -> handleLogin(
            usernameField.getText(), 
            new String(passwordField.getPassword()),
            (String) roleCombo.getSelectedItem()
        ));
        
        registerButton.addActionListener(e -> showPatientRegistration());
        
        loginPanel.add(centerCard);
        
        mainPanel.removeAll();
        mainPanel.add(loginPanel, "login");
        cardLayout.show(mainPanel, "login");
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void handleLogin(String username, String password, String role) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password", 
                "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Accept "password123" or any password for demo purposes
        boolean passwordValid = password.equals("password123") || !password.isEmpty();
        
        boolean authenticated = false;
        String userId = null;

        switch (role) {
            case "Doctor":
                for (var clinician : dataService.getClinicians()) {
                    if (clinician.getEmail().equalsIgnoreCase(username) && passwordValid) {
                        authenticated = true;
                        userId = clinician.getClinicianId();
                        SessionManager.getInstance().login(userId, username, "DOCTOR");
                        break;
                    }
                }
                if (authenticated) {
                    new DoctorDashboardController(this, dataService).show();
                }
                break;
            case "Nurse":
                for (var staff : dataService.getStaff()) {
                    if (staff.getEmail().equalsIgnoreCase(username) && 
                        staff.getRole().equalsIgnoreCase("Nurse") && passwordValid) {
                        authenticated = true;
                        userId = staff.getStaffId();
                        SessionManager.getInstance().login(userId, username, "NURSE");
                        break;
                    }
                }
                if (authenticated) {
                    new NurseDashboardController(this, dataService).show();
                }
                break;
            case "Patient":
                for (var patient : dataService.getPatients()) {
                    if (patient.getEmail().equalsIgnoreCase(username) && passwordValid) {
                        authenticated = true;
                        userId = patient.getPatientId();
                        SessionManager.getInstance().login(userId, username, "PATIENT");
                        break;
                    }
                }
                if (authenticated) {
                    new PatientDashboardController(this, dataService).show();
                }
                break;
            case "Administrative Staff":
                for (var staff : dataService.getStaff()) {
                    if (staff.getEmail().equalsIgnoreCase(username) && 
                        (staff.getRole().equalsIgnoreCase("Administrative Staff") || 
                         staff.getRole().equalsIgnoreCase("Administrator")) && passwordValid) {
                        authenticated = true;
                        userId = staff.getStaffId();
                        SessionManager.getInstance().login(userId, username, "ADMIN");
                        break;
                    }
                }
                if (authenticated) {
                    new AdminDashboardController(this, dataService).show();
                }
                break;
            case "Hospital Admin":
                for (var staff : dataService.getStaff()) {
                    if (staff.getEmail().equalsIgnoreCase(username) && 
                        staff.getRole().equalsIgnoreCase("Hospital Administrator") && passwordValid) {
                        authenticated = true;
                        userId = staff.getStaffId();
                        SessionManager.getInstance().login(userId, username, "HOSPITAL_ADMIN");
                        break;
                    }
                }
                if (authenticated) {
                    new HospitalDashboardController(this, dataService).show();
                }
                break;
        }

        if (!authenticated) {
            JOptionPane.showMessageDialog(this, "Invalid credentials or role mismatch", 
                "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showPatientRegistration() {
        new PatientRegistrationController(this, dataService).show();
    }

    public void showLogin() {
        SessionManager.getInstance().logout();
        showLoginScreen();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new Main());
    }
}