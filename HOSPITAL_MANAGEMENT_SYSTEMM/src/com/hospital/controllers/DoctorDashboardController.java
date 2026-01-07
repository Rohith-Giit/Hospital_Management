// File: src/main/java/com/hospital/controllers/DoctorDashboardController.java
package com.hospital.controllers;

import com.hospital.models.*;
import com.hospital.services.*;
import com.hospital.Main;
import com.hospital.Main;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorDashboardController {
    private Main mainFrame;
    private DataService dataService;
    private String currentClinicianId;
    private JPanel mainPanel;
    private JPanel contentPanel;

    public DoctorDashboardController(Main mainFrame, DataService dataService) {
        this.mainFrame = mainFrame;
        this.dataService = dataService;
        this.currentClinicianId = SessionManager.getInstance().getCurrentUserId();
    }

    public void show() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createSidebar(), BorderLayout.WEST);
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(245, 247, 250));
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        showDashboard();
        
        mainFrame.getMainPanel().removeAll();
        mainFrame.getMainPanel().add(mainPanel, "doctor");
        mainFrame.getCardLayout().show(mainFrame.getMainPanel(), "doctor");
        mainFrame.getMainPanel().revalidate();
        mainFrame.getMainPanel().repaint();
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(null);
        sidebar.setPreferredSize(new Dimension(250, 850));
        sidebar.setBackground(new Color(102, 126, 234));

        Clinician doctor = dataService.getClinicianById(currentClinicianId);
        
        JLabel titleLabel = new JLabel("Doctor Portal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(20, 30, 210, 30);
        sidebar.add(titleLabel);
        
        JLabel nameLabel = new JLabel(doctor != null ? doctor.getFullName() : "Doctor");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(new Color(224, 224, 224));
        nameLabel.setBounds(20, 65, 210, 25);
        sidebar.add(nameLabel);
        
        JLabel specLabel = new JLabel(doctor != null ? doctor.getSpeciality() : "");
        specLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        specLabel.setForeground(new Color(208, 208, 208));
        specLabel.setBounds(20, 90, 210, 20);
        sidebar.add(specLabel);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(20, 120, 210, 2);
        separator.setForeground(new Color(255, 255, 255, 50));
        sidebar.add(separator);
        
        int yPos = 140;
        JButton dashboardBtn = createSidebarButton("📊 Dashboard", 20, yPos);
        dashboardBtn.addActionListener(e -> showDashboard());
        sidebar.add(dashboardBtn);
        
        yPos += 50;
        JButton patientsBtn = createSidebarButton("👥 My Patients", 20, yPos);
        patientsBtn.addActionListener(e -> showPatients());
        sidebar.add(patientsBtn);
        
        yPos += 50;
        JButton appointmentsBtn = createSidebarButton("📅 Appointments", 20, yPos);
        appointmentsBtn.addActionListener(e -> showAppointments());
        sidebar.add(appointmentsBtn);
        
        yPos += 50;
        JButton prescriptionsBtn = createSidebarButton("💊 Prescriptions", 20, yPos);
        prescriptionsBtn.addActionListener(e -> showPrescriptions());
        sidebar.add(prescriptionsBtn);
        
        yPos += 50;
        JButton examineBtn = createSidebarButton("🔍 Examine Patient", 20, yPos);
        examineBtn.addActionListener(e -> showExaminePatient());
        sidebar.add(examineBtn);
        
        yPos += 50;
        JButton profileBtn = createSidebarButton("👤 My Profile", 20, yPos);
        profileBtn.addActionListener(e -> showProfile());
        sidebar.add(profileBtn);
        
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
        btn.setBackground(new Color(102, 126, 234));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(85, 104, 195));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(102, 126, 234));
            }
        });
        
        return btn;
    }

    private void showDashboard() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Doctor Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 400, 35);
        panel.add(titleLabel);
        
        // Statistics Cards
        List<Appointment> myAppointments = dataService.getAppointmentsByClinicianId(currentClinicianId);
        List<Prescription> myPrescriptions = dataService.getPrescriptionsByClinicianId(currentClinicianId);
        
        long todayAppts = myAppointments.stream()
                .filter(a -> a.getStatus().equals("Scheduled"))
                .count();
        
        int xPos = 30;
        panel.add(createStatCard("Today's Appointments", String.valueOf(todayAppts), 
            new Color(102, 126, 234), xPos, 100));
        
        xPos += 280;
        panel.add(createStatCard("Total Patients", String.valueOf(myAppointments.size()), 
            new Color(17, 153, 142), xPos, 100));
        
        xPos += 280;
        panel.add(createStatCard("Prescriptions Given", String.valueOf(myPrescriptions.size()), 
            new Color(240, 147, 251), xPos, 100));
        
        xPos += 280;
        panel.add(createStatCard("Pending", String.valueOf(myAppointments.stream()
            .filter(a -> a.getStatus().equals("Scheduled")).count()), 
            new Color(250, 112, 154), xPos, 100));
        
        // Today's Appointments Table
        JLabel apptLabel = new JLabel("Today's Appointments");
        apptLabel.setFont(new Font("Arial", Font.BOLD, 20));
        apptLabel.setBounds(30, 250, 400, 30);
        panel.add(apptLabel);
        
        String[] columns = {"Time", "Patient", "Reason", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (Appointment appt : myAppointments.stream()
                .filter(a -> a.getStatus().equals("Scheduled"))
                .limit(10).collect(Collectors.toList())) {
            Patient p = dataService.getPatientById(appt.getPatientId());
            model.addRow(new Object[]{
                appt.getAppointmentTime(),
                p != null ? p.getFullName() : "Unknown",
                appt.getReasonForVisit(),
                appt.getStatus()
            });
        }
        
        JTable table = new JTable(model);
        styleTable(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 290, 1080, 400);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        panel.add(scrollPane);
        
        JScrollPane mainScroll = new JScrollPane(panel);
        mainScroll.setBorder(null);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        
        contentPanel.removeAll();
        contentPanel.add(mainScroll, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
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

    private void showPatients() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("My Patients");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 400, 35);
        panel.add(titleLabel);
        
        String[] columns = {"Patient ID", "Name", "Gender", "Blood Group", "Phone", "Email"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        Set<String> patientIds = new HashSet<>();
        for (Appointment appt : dataService.getAppointmentsByClinicianId(currentClinicianId)) {
            patientIds.add(appt.getPatientId());
        }
        
        for (String patientId : patientIds) {
            Patient p = dataService.getPatientById(patientId);
            if (p != null) {
                model.addRow(new Object[]{
                    p.getPatientId(), p.getFullName(), p.getGender(),
                    p.getBloodGroup(), p.getPhoneNumber(), p.getEmail()
                });
            }
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

    private void showAppointments() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("My Appointments");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 400, 35);
        panel.add(titleLabel);
        
        String[] columns = {"Date", "Time", "Patient", "Type", "Reason", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (Appointment appt : dataService.getAppointmentsByClinicianId(currentClinicianId)) {
            Patient p = dataService.getPatientById(appt.getPatientId());
            model.addRow(new Object[]{
                appt.getAppointmentDate(), appt.getAppointmentTime(),
                p != null ? p.getFullName() : "Unknown",
                appt.getAppointmentType(), appt.getReasonForVisit(), appt.getStatus()
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

    private void showPrescriptions() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Prescriptions Given");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 400, 35);
        panel.add(titleLabel);
        
        String[] columns = {"Date", "Patient", "Drug", "Dosage", "Frequency", "Duration", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (Prescription presc : dataService.getPrescriptionsByClinicianId(currentClinicianId)) {
            Patient p = dataService.getPatientById(presc.getPatientId());
            model.addRow(new Object[]{
                presc.getPrescribedDate(),
                p != null ? p.getFullName() : "Unknown",
                presc.getDrugName(), presc.getDosage(), presc.getFrequency(),
                presc.getDurationDays() + " days", presc.getStatus()
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

    private void showExaminePatient() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Examine Patient & Prescribe");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 500, 35);
        panel.add(titleLabel);
        
        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(30, 90, 800, 600);
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        
        int yPos = 30;
        
        JLabel patientLabel = new JLabel("Select Patient:");
        patientLabel.setFont(new Font("Arial", Font.BOLD, 14));
        patientLabel.setBounds(30, yPos, 150, 25);
        formPanel.add(patientLabel);
        
        JComboBox<String> patientCombo = new JComboBox<>();
        patientCombo.setBounds(30, yPos + 30, 740, 35);
        
        Set<String> patientIds = new HashSet<>();
        for (Appointment appt : dataService.getAppointmentsByClinicianId(currentClinicianId)) {
            patientIds.add(appt.getPatientId());
        }
        for (String patientId : patientIds) {
            Patient p = dataService.getPatientById(patientId);
            if (p != null) patientCombo.addItem(p.getPatientId() + " - " + p.getFullName());
        }
        formPanel.add(patientCombo);
        
        yPos += 90;
        JLabel diagnosisLabel = new JLabel("Diagnosis & Notes:");
        diagnosisLabel.setFont(new Font("Arial", Font.BOLD, 14));
        diagnosisLabel.setBounds(30, yPos, 200, 25);
        formPanel.add(diagnosisLabel);
        
        JTextArea diagnosisArea = new JTextArea();
        diagnosisArea.setBounds(30, yPos + 30, 740, 80);
        diagnosisArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        formPanel.add(diagnosisArea);
        
        yPos += 130;
        JLabel drugLabel = new JLabel("Drug Name:");
        drugLabel.setFont(new Font("Arial", Font.BOLD, 14));
        drugLabel.setBounds(30, yPos, 150, 25);
        formPanel.add(drugLabel);
        
        JTextField drugField = new JTextField();
        drugField.setBounds(30, yPos + 30, 350, 35);
        formPanel.add(drugField);
        
        JLabel dosageLabel = new JLabel("Dosage:");
        dosageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        dosageLabel.setBounds(420, yPos, 150, 25);
        formPanel.add(dosageLabel);
        
        JTextField dosageField = new JTextField();
        dosageField.setBounds(420, yPos + 30, 350, 35);
        formPanel.add(dosageField);
        
        yPos += 90;
        JLabel frequencyLabel = new JLabel("Frequency:");
        frequencyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        frequencyLabel.setBounds(30, yPos, 150, 25);
        formPanel.add(frequencyLabel);
        
        JTextField frequencyField = new JTextField();
        frequencyField.setBounds(30, yPos + 30, 350, 35);
        formPanel.add(frequencyField);
        
        JLabel durationLabel = new JLabel("Duration (days):");
        durationLabel.setFont(new Font("Arial", Font.BOLD, 14));
        durationLabel.setBounds(420, yPos, 150, 25);
        formPanel.add(durationLabel);
        
        JTextField durationField = new JTextField();
        durationField.setBounds(420, yPos + 30, 350, 35);
        formPanel.add(durationField);
        
        yPos += 90;
        JLabel instructionsLabel = new JLabel("Instructions:");
        instructionsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        instructionsLabel.setBounds(30, yPos, 150, 25);
        formPanel.add(instructionsLabel);
        
        JTextArea instructionsArea = new JTextArea();
        instructionsArea.setBounds(30, yPos + 30, 740, 60);
        instructionsArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        formPanel.add(instructionsArea);
        
        yPos += 110;
        JButton prescribeBtn = new JButton("Prescribe Medication");
        prescribeBtn.setBounds(30, yPos, 740, 45);
        prescribeBtn.setFont(new Font("Arial", Font.BOLD, 15));
        prescribeBtn.setForeground(Color.WHITE);
        prescribeBtn.setBackground(new Color(102, 126, 234));
        prescribeBtn.setFocusPainted(false);
        prescribeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        prescribeBtn.addActionListener(e -> {
            if (patientCombo.getSelectedItem() != null && !drugField.getText().isEmpty()) {
                String patientId = ((String)patientCombo.getSelectedItem()).split(" - ")[0];
                String prescriptionId = "PRE" + System.currentTimeMillis();
                
                Prescription newPrescription = new Prescription(
                    prescriptionId, patientId, currentClinicianId,
                    drugField.getText(), dosageField.getText(), frequencyField.getText(),
                    Integer.parseInt(durationField.getText().isEmpty() ? "0" : durationField.getText()),
                    instructionsArea.getText(), LocalDate.now().toString(), "Active"
                );
                
                dataService.addPrescription(newPrescription);
                
                JOptionPane.showMessageDialog(mainFrame, "Prescription added successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                
                drugField.setText("");
                dosageField.setText("");
                frequencyField.setText("");
                durationField.setText("");
                instructionsArea.setText("");
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Please select patient and enter drug name", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        formPanel.add(prescribeBtn);
        panel.add(formPanel);
        
        JScrollPane mainScroll = new JScrollPane(panel);
        mainScroll.setBorder(null);
        
        contentPanel.removeAll();
        contentPanel.add(mainScroll, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showProfile() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("My Profile");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 400, 35);
        panel.add(titleLabel);
        
        Clinician doctor = dataService.getClinicianById(currentClinicianId);
        
        if (doctor != null) {
            JPanel profilePanel = new JPanel(null);
            profilePanel.setBackground(Color.WHITE);
            profilePanel.setBounds(30, 90, 800, 500);
            profilePanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
            
            int yPos = 30;
            addProfileField(profilePanel, "Clinician ID:", doctor.getClinicianId(), 30, yPos);
            yPos += 50;
            addProfileField(profilePanel, "Full Name:", doctor.getFullName(), 30, yPos);
            yPos += 50;
            addProfileField(profilePanel, "Specialization:", doctor.getSpeciality(), 30, yPos);
            yPos += 50;
            addProfileField(profilePanel, "Phone Number:", doctor.getPhoneNumber(), 30, yPos);
            yPos += 50;
            addProfileField(profilePanel, "Email:", doctor.getEmail(), 30, yPos);
            yPos += 50;
            addProfileField(profilePanel, "Years of Experience:", doctor.getYearsOfExperience() + " years", 30, yPos);
            yPos += 50;
            addProfileField(profilePanel, "Employment Type:", doctor.getEmploymentType(), 30, yPos);
            yPos += 50;
            addProfileField(profilePanel, "Status:", doctor.getAvailabilityStatus(), 30, yPos);
            
            panel.add(profilePanel);
        }
        
        JScrollPane mainScroll = new JScrollPane(panel);
        mainScroll.setBorder(null);
        
        contentPanel.removeAll();
        contentPanel.add(mainScroll, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void addProfileField(JPanel panel, String label, String value, int x, int y) {
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Arial", Font.BOLD, 14));
        lblLabel.setForeground(new Color(102, 102, 102));
        lblLabel.setBounds(x, y, 200, 25);
        panel.add(lblLabel);
        
        JLabel valLabel = new JLabel(value);
        valLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        valLabel.setBounds(x + 220, y, 500, 25);
        panel.add(valLabel);
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(102, 126, 234, 50));
        table.setSelectionForeground(Color.BLACK);
        
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(250, 250, 250));
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
}