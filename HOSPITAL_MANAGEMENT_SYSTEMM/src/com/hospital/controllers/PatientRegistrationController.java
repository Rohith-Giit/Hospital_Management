package com.hospital.controllers;

import com.hospital.models.Patient;
import com.hospital.services.DataService;
import com.hospital.Main;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class PatientRegistrationController {
    private Main mainFrame;
    private DataService dataService;

    public PatientRegistrationController(Main mainFrame, DataService dataService) {
        this.mainFrame = mainFrame;
        this.dataService = dataService;
    }

    public void show() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Patient Registration");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(102, 126, 234));
        titleLabel.setBounds(400, 30, 400, 40);
        panel.add(titleLabel);
        
        JLabel subtitleLabel = new JLabel("Register as a new patient");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(102, 102, 102));
        subtitleLabel.setBounds(430, 75, 300, 25);
        panel.add(subtitleLabel);
        
        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(300, 120, 800, 650);
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        
        int yPos = 30;
        
        JTextField firstNameField = createFormField(formPanel, "First Name:*", yPos, 30);
        JTextField lastNameField = createFormField(formPanel, "Last Name:*", yPos, 420);
        
        yPos += 70;
        JLabel genderLabel = new JLabel("Gender:*");
        genderLabel.setFont(new Font("Arial", Font.BOLD, 14));
        genderLabel.setBounds(30, yPos, 150, 25);
        formPanel.add(genderLabel);
        
        String[] genders = {"Male", "Female", "Other"};
        JComboBox<String> genderCombo = new JComboBox<>(genders);
        genderCombo.setBounds(30, yPos + 30, 350, 35);
        formPanel.add(genderCombo);
        
        JLabel dobLabel = new JLabel("Date of Birth:*");
        dobLabel.setFont(new Font("Arial", Font.BOLD, 14));
        dobLabel.setBounds(420, yPos, 150, 25);
        formPanel.add(dobLabel);
        
        JTextField dobField = new JTextField("YYYY-MM-DD");
        dobField.setBounds(420, yPos + 30, 350, 35);
        formPanel.add(dobField);
        
        yPos += 70;
        JTextField phoneField = createFormField(formPanel, "Phone Number:*", yPos, 30);
        JTextField emailField = createFormField(formPanel, "Email:*", yPos, 420);
        
        yPos += 70;
        JLabel addressLabel = new JLabel("Address:*");
        addressLabel.setFont(new Font("Arial", Font.BOLD, 14));
        addressLabel.setBounds(30, yPos, 150, 25);
        formPanel.add(addressLabel);
        
        JTextArea addressArea = new JTextArea();
        addressArea.setBounds(30, yPos + 30, 740, 60);
        addressArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        formPanel.add(addressArea);
        
        yPos += 100;
        JLabel bloodLabel = new JLabel("Blood Group:*");
        bloodLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bloodLabel.setBounds(30, yPos, 150, 25);
        formPanel.add(bloodLabel);
        
        String[] bloodGroups = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        JComboBox<String> bloodCombo = new JComboBox<>(bloodGroups);
        bloodCombo.setBounds(30, yPos + 30, 350, 35);
        formPanel.add(bloodCombo);
        
        JTextField emergencyField = createFormField(formPanel, "Emergency Contact:*", yPos, 420);
        
        yPos += 70;
        JTextField insuranceField = createFormField(formPanel, "Insurance Provider:", yPos, 30);
        
        yPos += 70;
        JButton registerButton = new JButton("Register Patient");
        registerButton.setBounds(30, yPos, 350, 45);
        registerButton.setFont(new Font("Arial", Font.BOLD, 15));
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(17, 153, 142));
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton backButton = new JButton("Back to Login");
        backButton.setBounds(420, yPos, 350, 45);
        backButton.setFont(new Font("Arial", Font.BOLD, 15));
        backButton.setForeground(Color.BLACK);
        backButton.setBackground(new Color(200, 200, 200));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        registerButton.addActionListener(e -> {
            if (!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty() && 
                !phoneField.getText().isEmpty() && !emailField.getText().isEmpty() &&
                !addressArea.getText().isEmpty() && !dobField.getText().isEmpty()) {
                
                String patientId = "PAT" + String.format("%03d", dataService.getPatients().size() + 1);
                
                Patient newPatient = new Patient(
                    patientId,
                    firstNameField.getText(),
                    lastNameField.getText(),
                    (String)genderCombo.getSelectedItem(),
                    dobField.getText(),
                    phoneField.getText(),
                    emailField.getText(),
                    addressArea.getText(),
                    (String)bloodCombo.getSelectedItem(),
                    emergencyField.getText(),
                    LocalDate.now().toString(),
                    insuranceField.getText().isEmpty() ? "None" : insuranceField.getText()
                );
                
                dataService.addPatient(newPatient);
                
                JOptionPane.showMessageDialog(mainFrame, 
                    "Patient registered successfully!\nYour Patient ID is: " + patientId, 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                mainFrame.showLogin();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Please fill all required fields marked with *", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        backButton.addActionListener(e -> mainFrame.showLogin());
        
        formPanel.add(registerButton);
        formPanel.add(backButton);
        panel.add(formPanel);
        
        JScrollPane mainScroll = new JScrollPane(panel);
        mainScroll.setBorder(null);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        
        mainFrame.getMainPanel().removeAll();
        mainFrame.getMainPanel().add(mainScroll, "registration");
        mainFrame.getCardLayout().show(mainFrame.getMainPanel(), "registration");
        mainFrame.getMainPanel().revalidate();
        mainFrame.getMainPanel().repaint();
    }

    private JTextField createFormField(JPanel panel, String label, int yPos, int xPos) {
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("Arial", Font.BOLD, 14));
        fieldLabel.setBounds(xPos, yPos, 200, 25);
        panel.add(fieldLabel);
        
        JTextField field = new JTextField();
        field.setBounds(xPos, yPos + 30, 350, 35);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panel.add(field);
        
        return field;
    }
}