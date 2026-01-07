// File: src/main/java/com/hospital/controllers/HospitalDashboardController.java
package com.hospital.controllers;

import com.hospital.models.*;
import com.hospital.services.*;
import com.hospital.utils.CSVWriter;
import com.hospital.Main;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;

public class HospitalDashboardController {
    private Main mainFrame;
    private DataService dataService;
    private JPanel mainPanel;
    private JPanel contentPanel;

    public HospitalDashboardController(Main mainFrame, DataService dataService) {
        this.mainFrame = mainFrame;
        this.dataService = dataService;
    }

    public void show() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createSidebar(), BorderLayout.WEST);
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(245, 247, 250));
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        showDashboard();
        
        mainFrame.getMainPanel().removeAll();
        mainFrame.getMainPanel().add(mainPanel, "hospital");
        mainFrame.getCardLayout().show(mainFrame.getMainPanel(), "hospital");
        mainFrame.getMainPanel().revalidate();
        mainFrame.getMainPanel().repaint();
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(null);
        sidebar.setPreferredSize(new Dimension(250, 850));
        sidebar.setBackground(new Color(79, 172, 254));

        JLabel titleLabel = new JLabel("Hospital Admin");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(20, 30, 210, 30);
        sidebar.add(titleLabel);
        
        int yPos = 100;
        JButton dashboardBtn = createSidebarButton("📊 Dashboard", 20, yPos);
        dashboardBtn.addActionListener(e -> showDashboard());
        sidebar.add(dashboardBtn);
        
        yPos += 50;
        JButton facilitiesBtn = createSidebarButton("🏥 Facilities", 20, yPos);
        facilitiesBtn.addActionListener(e -> showFacilities());
        sidebar.add(facilitiesBtn);
        
        yPos += 50;
        JButton doctorsBtn = createSidebarButton("👨‍⚕️ Doctors", 20, yPos);
        doctorsBtn.addActionListener(e -> showDoctors());
        sidebar.add(doctorsBtn);
        
        yPos += 50;
        JButton addDoctorBtn = createSidebarButton("➕ Add Doctor", 20, yPos);
        addDoctorBtn.addActionListener(e -> showAddDoctor());
        sidebar.add(addDoctorBtn);
        
        yPos += 50;
        JButton staffBtn = createSidebarButton("👥 Staff", 20, yPos);
        staffBtn.addActionListener(e -> showStaff());
        sidebar.add(staffBtn);
        
        yPos += 50;
        JButton reportsBtn = createSidebarButton("📈 Reports", 20, yPos);
        reportsBtn.addActionListener(e -> showReports());
        sidebar.add(reportsBtn);
        
        JButton logoutBtn = createSidebarButton("🚪 Logout", 20, 750);
        logoutBtn.addActionListener(e -> mainFrame.showLogin());
        sidebar.add(logoutBtn);
        
        return sidebar;
    }

    private JButton createSidebarButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 210, 40);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(79, 172, 254));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(60, 150, 230));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(79, 172, 254));
            }
        });
        
        return btn;
    }

    private void showDashboard() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Hospital Management Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 600, 35);
        panel.add(titleLabel);
        
        JLabel subtitleLabel = new JLabel("Central Healthcare Facility Management System");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(102, 102, 102));
        subtitleLabel.setBounds(30, 70, 600, 25);
        panel.add(subtitleLabel);
        
        // Statistics
        int xPos = 30;
        panel.add(createStatCard("Total Facilities", String.valueOf(dataService.getFacilities().size()), 
            new Color(79, 172, 254), xPos, 120));
        
        xPos += 280;
        panel.add(createStatCard("Total Doctors", String.valueOf(dataService.getClinicians().size()), 
            new Color(102, 126, 234), xPos, 120));
        
        xPos += 280;
        panel.add(createStatCard("Total Staff", String.valueOf(dataService.getStaff().size()), 
            new Color(17, 153, 142), xPos, 120));
        
        xPos += 280;
        panel.add(createStatCard("Total Patients", String.valueOf(dataService.getPatients().size()), 
            new Color(240, 147, 251), xPos, 120));
        
        // Hospital Details
        JLabel detailsLabel = new JLabel("Hospital Information");
        detailsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        detailsLabel.setBounds(30, 270, 400, 30);
        panel.add(detailsLabel);
        
        if (!dataService.getFacilities().isEmpty()) {
            Facility mainFacility = dataService.getFacilities().get(0);
            
            JPanel infoPanel = new JPanel(null);
            infoPanel.setBackground(Color.WHITE);
            infoPanel.setBounds(30, 310, 1080, 350);
            infoPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
            
            int yPos = 30;
            addInfoField(infoPanel, "Facility Name:", mainFacility.getFacilityName(), 30, yPos);
            yPos += 45;
            addInfoField(infoPanel, "Type:", mainFacility.getFacilityType(), 30, yPos);
            yPos += 45;
            addInfoField(infoPanel, "Address:", mainFacility.getAddress() + ", " + 
                mainFacility.getCity() + ", " + mainFacility.getState(), 30, yPos);
            yPos += 45;
            addInfoField(infoPanel, "Phone:", mainFacility.getPhoneNumber(), 30, yPos);
            yPos += 45;
            addInfoField(infoPanel, "Email:", mainFacility.getEmail(), 30, yPos);
            yPos += 45;
            addInfoField(infoPanel, "Capacity:", mainFacility.getCapacity() + " beds", 30, yPos);
            yPos += 45;
            addInfoField(infoPanel, "Status:", mainFacility.getStatus(), 30, yPos);
            
            panel.add(infoPanel);
        }
        
        JScrollPane mainScroll = new JScrollPane(panel);
        mainScroll.setBorder(null);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        
        contentPanel.removeAll();
        contentPanel.add(mainScroll, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showFacilities() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Hospital Facilities");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 400, 35);
        panel.add(titleLabel);
        
        String[] columns = {"Facility ID", "Name", "Type", "Phone", "Capacity", "Status", "Action"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only Action column is editable
            }
        };
        
        for (Facility f : dataService.getFacilities()) {
            model.addRow(new Object[]{
                f.getFacilityId(), f.getFacilityName(), f.getFacilityType(),
                f.getPhoneNumber(), f.getCapacity(), f.getStatus(), "Edit | Delete"
            });
        }
        
        JTable table = new JTable(model);
        styleTable(table);
        
        // Add button click handler for Edit and Delete
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                if (col == 6 && row >= 0) {
                    String facilityId = (String) model.getValueAt(row, 0);
                    handleFacilityAction(facilityId, row, model);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 90, 1080, 600);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        panel.add(scrollPane);
        
        JScrollPane mainScroll = new JScrollPane(panel);
        mainScroll.setBorder(null);
        
        contentPanel.removeAll();
        contentPanel.add(mainScroll, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void handleFacilityAction(String facilityId, int row, DefaultTableModel model) {
        String[] options = {"Edit", "Delete", "Cancel"};
        int choice = JOptionPane.showOptionDialog(mainFrame, "Choose action for facility " + facilityId,
                "Facility Management", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[2]);
        
        if (choice == 0) { // Edit
            editFacility(facilityId);
        } else if (choice == 1) { // Delete
            deleteFacility(facilityId, row, model);
        }
    }

    private void editFacility(String facilityId) {
        Facility facility = dataService.getFacilityById(facilityId);
        if (facility == null) return;
        
        JPanel panel = new JPanel(null);
        panel.setLayout(new java.awt.GridLayout(5, 2, 10, 10));
        
        JLabel nameLabel = new JLabel("Facility Name:");
        JTextField nameField = new JTextField(facility.getFacilityName());
        
        JLabel typeLabel = new JLabel("Type:");
        JTextField typeField = new JTextField(facility.getFacilityType());
        
        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField(facility.getPhoneNumber());
        
        JLabel capacityLabel = new JLabel("Capacity:");
        JTextField capacityField = new JTextField(String.valueOf(facility.getCapacity()));
        
        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
        statusCombo.setSelectedItem(facility.getStatus());
        
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(typeLabel);
        panel.add(typeField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(capacityLabel);
        panel.add(capacityField);
        panel.add(statusLabel);
        panel.add(statusCombo);
        
        int result = JOptionPane.showConfirmDialog(mainFrame, panel, "Edit Facility", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            facility.setFacilityName(nameField.getText());
            facility.setFacilityType(typeField.getText());
            facility.setPhoneNumber(phoneField.getText());
            facility.setCapacity(Integer.parseInt(capacityField.getText()));
            facility.setStatus((String) statusCombo.getSelectedItem());
            
            CSVWriter.writeFacilities(dataService.getFacilities());
            JOptionPane.showMessageDialog(mainFrame, "Facility updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            showFacilities(); // Refresh the table
        }
    }

    private void deleteFacility(String facilityId, int row, DefaultTableModel model) {
        int confirm = JOptionPane.showConfirmDialog(mainFrame, 
                "Are you sure you want to delete facility " + facilityId + "?", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            Facility facility = dataService.getFacilityById(facilityId);
            if (facility != null) {
                dataService.getFacilities().remove(facility);
                CSVWriter.writeFacilities(dataService.getFacilities());
                JOptionPane.showMessageDialog(mainFrame, "Facility deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                showFacilities(); // Refresh the table
            }
        }
    }

    private void showDoctors() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Doctors Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 400, 35);
        panel.add(titleLabel);
        
        String[] columns = {"Doctor ID", "Name", "Specialization", "Experience", "Phone", "Email", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (Clinician c : dataService.getClinicians()) {
            model.addRow(new Object[]{
                c.getClinicianId(), c.getFullName(), c.getSpeciality(),
                c.getYearsOfExperience() + " years", c.getPhoneNumber(),
                c.getEmail(), c.getAvailabilityStatus()
            });
        }
        
        JTable table = new JTable(model);
        styleTable(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 90, 1080, 600);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        panel.add(scrollPane);
        
        JScrollPane mainScroll = new JScrollPane(panel);
        mainScroll.setBorder(null);
        
        contentPanel.removeAll();
        contentPanel.add(mainScroll, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showAddDoctor() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Add New Doctor");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 500, 35);
        panel.add(titleLabel);
        
        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(30, 90, 800, 650);
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        
        int yPos = 30;
        
        JTextField firstNameField = createFormField(formPanel, "First Name:*", yPos);
        yPos += 70;
        JTextField lastNameField = createFormField(formPanel, "Last Name:*", yPos);
        yPos += 70;
        
        JLabel specLabel = new JLabel("Specialization:*");
        specLabel.setFont(new Font("Arial", Font.BOLD, 14));
        specLabel.setBounds(30, yPos, 200, 25);
        formPanel.add(specLabel);
        
        String[] specializations = {"Cardiology", "Pediatrics", "Orthopedics", "Neurology", 
            "General Practice", "Dermatology", "Psychiatry", "Radiology", "Surgery", 
            "Oncology", "Anesthesiology", "Emergency Medicine"};
        JComboBox<String> specCombo = new JComboBox<>(specializations);
        specCombo.setBounds(30, yPos + 30, 740, 35);
        formPanel.add(specCombo);
        
        yPos += 70;
        JTextField phoneField = createFormField(formPanel, "Phone Number:", yPos);
        yPos += 70;
        JTextField emailField = createFormField(formPanel, "Email Address:*", yPos);
        yPos += 70;
        
        JLabel facilityLabel = new JLabel("Facility:");
        facilityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        facilityLabel.setBounds(30, yPos, 200, 25);
        formPanel.add(facilityLabel);
        
        JComboBox<String> facilityCombo = new JComboBox<>();
        facilityCombo.setBounds(30, yPos + 30, 740, 35);
        for (Facility f : dataService.getFacilities()) {
            facilityCombo.addItem(f.getFacilityId() + " - " + f.getFacilityName());
        }
        formPanel.add(facilityCombo);
        
        yPos += 70;
        JTextField experienceField = createFormField(formPanel, "Years of Experience:", yPos);
        yPos += 70;
        
        JLabel employmentLabel = new JLabel("Employment Type:");
        employmentLabel.setFont(new Font("Arial", Font.BOLD, 14));
        employmentLabel.setBounds(30, yPos, 200, 25);
        formPanel.add(employmentLabel);
        
        String[] employmentTypes = {"Full-time", "Part-time", "Contract", "On-call"};
        JComboBox<String> employmentCombo = new JComboBox<>(employmentTypes);
        employmentCombo.setBounds(30, yPos + 30, 740, 35);
        formPanel.add(employmentCombo);
        
        yPos += 70;
        JButton addButton = new JButton("Add Doctor");
        addButton.setBounds(30, yPos, 740, 45);
        addButton.setFont(new Font("Arial", Font.BOLD, 15));
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(new Color(79, 172, 254));
        addButton.setFocusPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        addButton.addActionListener(e -> {
            if (!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty() && 
                !emailField.getText().isEmpty()) {
                
                String clinicianId = "CLI" + String.format("%03d", dataService.getClinicians().size() + 1);
                String facilityId = facilityCombo.getSelectedItem() != null ? 
                    ((String)facilityCombo.getSelectedItem()).split(" - ")[0] : "FAC001";
                
                Clinician newClinician = new Clinician(
                    clinicianId,
                    firstNameField.getText(),
                    lastNameField.getText(),
                    (String)specCombo.getSelectedItem(),
                    phoneField.getText(),
                    emailField.getText(),
                    facilityId,
                    "Available",
                    Integer.parseInt(experienceField.getText().isEmpty() ? "0" : experienceField.getText()),
                    (String)employmentCombo.getSelectedItem(),
                    LocalDate.now().toString(),
                    LocalDate.now().toString()
                );
                
                dataService.addClinician(newClinician);
                
                JOptionPane.showMessageDialog(mainFrame, "Doctor added successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                showDoctors();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Please fill all required fields", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        formPanel.add(addButton);
        panel.add(formPanel);
        
        JScrollPane mainScroll = new JScrollPane(panel);
        mainScroll.setBorder(null);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        
        contentPanel.removeAll();
        contentPanel.add(mainScroll, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JTextField createFormField(JPanel panel, String label, int yPos) {
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("Arial", Font.BOLD, 14));
        fieldLabel.setBounds(30, yPos, 200, 25);
        panel.add(fieldLabel);
        
        JTextField field = new JTextField();
        field.setBounds(30, yPos + 30, 740, 35);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panel.add(field);
        
        return field;
    }

    private void showStaff() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Staff Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 400, 35);
        panel.add(titleLabel);
        
        String[] columns = {"Staff ID", "Name", "Role", "Department", "Phone", "Email", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (Staff s : dataService.getStaff()) {
            model.addRow(new Object[]{
                s.getStaffId(), s.getFullName(), s.getRole(),
                s.getDepartment(), s.getPhoneNumber(),
                s.getEmail(), s.getEmploymentStatus()
            });
        }
        
        JTable table = new JTable(model);
        styleTable(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 90, 1080, 600);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        panel.add(scrollPane);
        
        JScrollPane mainScroll = new JScrollPane(panel);
        mainScroll.setBorder(null);
        
        contentPanel.removeAll();
        contentPanel.add(mainScroll, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showReports() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Hospital Reports & Analytics");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 500, 35);
        panel.add(titleLabel);
        
        JPanel statsPanel = new JPanel(null);
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBounds(30, 90, 1080, 500);
        statsPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        
        JLabel statsTitle = new JLabel("Overall Statistics");
        statsTitle.setFont(new Font("Arial", Font.BOLD, 18));
        statsTitle.setBounds(30, 30, 400, 30);
        statsPanel.add(statsTitle);
        
        int yPos = 80;
        addStatField(statsPanel, "Total Appointments:", String.valueOf(dataService.getAppointments().size()), 30, yPos);
        yPos += 45;
        addStatField(statsPanel, "Scheduled Appointments:", String.valueOf(dataService.getAppointments().stream()
            .filter(a -> a.getStatus().equals("Scheduled")).count()), 30, yPos);
        yPos += 45;
        addStatField(statsPanel, "Completed Appointments:", String.valueOf(dataService.getAppointments().stream()
            .filter(a -> a.getStatus().equals("Completed")).count()), 30, yPos);
        yPos += 45;
        addStatField(statsPanel, "Total Prescriptions:", String.valueOf(dataService.getPrescriptions().size()), 30, yPos);
        yPos += 45;
        addStatField(statsPanel, "Active Prescriptions:", String.valueOf(dataService.getPrescriptions().stream()
            .filter(p -> p.getStatus().equals("Active")).count()), 30, yPos);
        yPos += 45;
        addStatField(statsPanel, "Total Referrals:", String.valueOf(dataService.getReferrals().size()), 30, yPos);
        
        panel.add(statsPanel);
        
        JScrollPane mainScroll = new JScrollPane(panel);
        mainScroll.setBorder(null);
        
        contentPanel.removeAll();
        contentPanel.add(mainScroll, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void addStatField(JPanel panel, String label, String value, int x, int y) {
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Arial", Font.BOLD, 14));
        lblLabel.setForeground(new Color(102, 102, 102));
        lblLabel.setBounds(x, y, 300, 25);
        panel.add(lblLabel);
        
        JLabel valLabel = new JLabel(value);
        valLabel.setFont(new Font("Arial", Font.BOLD, 16));
        valLabel.setForeground(new Color(79, 172, 254));
        valLabel.setBounds(x + 320, y, 200, 25);
        panel.add(valLabel);
    }

    private void addInfoField(JPanel panel, String label, String value, int x, int y) {
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Arial", Font.BOLD, 14));
        lblLabel.setForeground(new Color(102, 102, 102));
        lblLabel.setBounds(x, y, 200, 25);
        panel.add(lblLabel);
        
        JLabel valLabel = new JLabel(value);
        valLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        valLabel.setBounds(x + 220, y, 800, 25);
        panel.add(valLabel);
    }

    private JPanel createStatCard(String title, String value, Color color, int x, int y) {
        JPanel card = new JPanel(null);
        card.setBounds(x, y, 260, 120);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        titleLabel.setForeground(new Color(102, 102, 102));
        titleLabel.setBounds(20, 20, 220, 20);
        card.add(titleLabel);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 32));
        valueLabel.setForeground(color);
        valueLabel.setBounds(20, 50, 220, 40);
        card.add(valueLabel);
        
        return card;
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(79, 172, 254, 50));
        table.setSelectionForeground(Color.BLACK);
        
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(250, 250, 250));
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
    }
}

