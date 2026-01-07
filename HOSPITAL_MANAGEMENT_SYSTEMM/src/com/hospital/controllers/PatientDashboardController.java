// File: src/main/java/com/hospital/controllers/PatientDashboardController.java
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

public class PatientDashboardController {
    private Main mainFrame;
    private DataService dataService;
    private String currentPatientId;
    private JPanel mainPanel;
    private JPanel contentPanel;

    public PatientDashboardController(Main mainFrame, DataService dataService) {
        this.mainFrame = mainFrame;
        this.dataService = dataService;
        this.currentPatientId = SessionManager.getInstance().getCurrentUserId();
    }

    public void show() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createSidebar(), BorderLayout.WEST);
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(245, 247, 250));
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        showDashboard();
        
        mainFrame.getMainPanel().removeAll();
        mainFrame.getMainPanel().add(mainPanel, "patient");
        mainFrame.getCardLayout().show(mainFrame.getMainPanel(), "patient");
        mainFrame.getMainPanel().revalidate();
        mainFrame.getMainPanel().repaint();
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(null);
        sidebar.setPreferredSize(new Dimension(250, 850));
        sidebar.setBackground(new Color(17, 153, 142));

        Patient patient = dataService.getPatientById(currentPatientId);
        
        JLabel titleLabel = new JLabel("Patient Portal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(20, 30, 210, 30);
        sidebar.add(titleLabel);
        
        JLabel nameLabel = new JLabel(patient != null ? patient.getFullName() : "Patient");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(new Color(224, 224, 224));
        nameLabel.setBounds(20, 65, 210, 25);
        sidebar.add(nameLabel);
        
        JLabel idLabel = new JLabel("ID: " + (patient != null ? patient.getPatientId() : ""));
        idLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        idLabel.setForeground(new Color(208, 208, 208));
        idLabel.setBounds(20, 90, 210, 20);
        sidebar.add(idLabel);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(20, 120, 210, 2);
        separator.setForeground(new Color(255, 255, 255, 50));
        sidebar.add(separator);
        
        int yPos = 140;
        JButton dashboardBtn = createSidebarButton("📊 Dashboard", 20, yPos, new Color(17, 153, 142));
        dashboardBtn.addActionListener(e -> showDashboard());
        sidebar.add(dashboardBtn);
        
        yPos += 50;
        JButton appointmentsBtn = createSidebarButton("📅 My Appointments", 20, yPos, new Color(17, 153, 142));
        appointmentsBtn.addActionListener(e -> showAppointments());
        sidebar.add(appointmentsBtn);
        
        yPos += 50;
        JButton bookBtn = createSidebarButton("➕ Book Appointment", 20, yPos, new Color(17, 153, 142));
        bookBtn.addActionListener(e -> showBookAppointment());
        sidebar.add(bookBtn);
        
        yPos += 50;
        JButton prescriptionsBtn = createSidebarButton("💊 Prescriptions", 20, yPos, new Color(17, 153, 142));
        prescriptionsBtn.addActionListener(e -> showPrescriptions());
        sidebar.add(prescriptionsBtn);
        
        yPos += 50;
        JButton reportsBtn = createSidebarButton("📋 Medical Reports", 20, yPos, new Color(17, 153, 142));
        reportsBtn.addActionListener(e -> showReports());
        sidebar.add(reportsBtn);
        
        yPos += 50;
        JButton profileBtn = createSidebarButton("👤 My Profile", 20, yPos, new Color(17, 153, 142));
        profileBtn.addActionListener(e -> showProfile());
        sidebar.add(profileBtn);
        
        JButton logoutBtn = createSidebarButton("🚪 Logout", 20, 750, new Color(17, 153, 142));
        logoutBtn.addActionListener(e -> mainFrame.showLogin());
        sidebar.add(logoutBtn);
        
        return sidebar;
    }

    private JButton createSidebarButton(String text, int x, int y, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 210, 40);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(14, 128, 119));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });
        
        return btn;
    }

    private void showDashboard() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Patient Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 400, 35);
        panel.add(titleLabel);
        
        Patient patient = dataService.getPatientById(currentPatientId);
        JLabel welcomeLabel = new JLabel("Welcome back, " + (patient != null ? patient.getFirstName() : "Patient") + "!");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeLabel.setForeground(new Color(102, 102, 102));
        welcomeLabel.setBounds(30, 70, 400, 25);
        panel.add(welcomeLabel);
        
        // Statistics Cards
        List<Appointment> myAppointments = dataService.getAppointmentsByPatientId(currentPatientId);
        List<Prescription> myPrescriptions = dataService.getPrescriptionsByPatientId(currentPatientId);
        
        long upcomingAppts = myAppointments.stream()
                .filter(a -> a.getStatus().equals("Scheduled"))
                .count();
        
        int xPos = 30;
        panel.add(createStatCard("Upcoming Appointments", String.valueOf(upcomingAppts), 
            new Color(17, 153, 142), xPos, 120));
        
        xPos += 280;
        panel.add(createStatCard("Total Appointments", String.valueOf(myAppointments.size()), 
            new Color(102, 126, 234), xPos, 120));
        
        xPos += 280;
        panel.add(createStatCard("Active Prescriptions", String.valueOf(myPrescriptions.stream()
            .filter(p -> p.getStatus().equals("Active")).count()), 
            new Color(240, 147, 251), xPos, 120));
        
        xPos += 280;
        panel.add(createStatCard("Blood Group", patient != null ? patient.getBloodGroup() : "N/A", 
            new Color(250, 112, 154), xPos, 120));
        
        // Upcoming Appointments Table
        JLabel apptLabel = new JLabel("Upcoming Appointments");
        apptLabel.setFont(new Font("Arial", Font.BOLD, 20));
        apptLabel.setBounds(30, 270, 400, 30);
        panel.add(apptLabel);
        
        String[] columns = {"Date", "Time", "Doctor", "Type", "Reason", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (Appointment appt : myAppointments.stream()
                .filter(a -> a.getStatus().equals("Scheduled"))
                .limit(5).collect(Collectors.toList())) {
            Clinician c = dataService.getClinicianById(appt.getClinicianId());
            model.addRow(new Object[]{
                appt.getAppointmentDate(), appt.getAppointmentTime(),
                c != null ? c.getFullName() : "Unknown",
                appt.getAppointmentType(), appt.getReasonForVisit(), appt.getStatus()
            });
        }
        
        JTable table = new JTable(model);
        styleTable(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 310, 1080, 350);
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

    private void showAppointments() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("My Appointments");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 400, 35);
        panel.add(titleLabel);
        
        String[] columns = {"ID", "Date", "Time", "Doctor", "Type", "Reason", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (Appointment appt : dataService.getAppointmentsByPatientId(currentPatientId)) {
            Clinician c = dataService.getClinicianById(appt.getClinicianId());
            model.addRow(new Object[]{
                appt.getAppointmentId(), appt.getAppointmentDate(), appt.getAppointmentTime(),
                c != null ? c.getFullName() : "Unknown",
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

    private void showBookAppointment() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Book New Appointment");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 500, 35);
        panel.add(titleLabel);
        
        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(30, 90, 700, 500);
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        
        int yPos = 30;
        
        JLabel doctorLabel = new JLabel("Select Doctor:");
        doctorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        doctorLabel.setBounds(30, yPos, 150, 25);
        formPanel.add(doctorLabel);
        
        JComboBox<String> doctorCombo = new JComboBox<>();
        doctorCombo.setBounds(30, yPos + 30, 640, 35);
        for (Clinician c : dataService.getClinicians()) {
            doctorCombo.addItem(c.getClinicianId() + " - " + c.getFullName() + " (" + c.getSpeciality() + ")");
        }
        formPanel.add(doctorCombo);
        
        yPos += 90;
        JLabel dateLabel = new JLabel("Appointment Date:");
        dateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        dateLabel.setBounds(30, yPos, 150, 25);
        formPanel.add(dateLabel);
        
        JTextField dateField = new JTextField("YYYY-MM-DD");
        dateField.setBounds(30, yPos + 30, 300, 35);
        formPanel.add(dateField);
        
        JLabel timeLabel = new JLabel("Time:");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timeLabel.setBounds(370, yPos, 150, 25);
        formPanel.add(timeLabel);
        
        String[] times = {"09:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00", "17:00"};
        JComboBox<String> timeCombo = new JComboBox<>(times);
        timeCombo.setBounds(370, yPos + 30, 300, 35);
        formPanel.add(timeCombo);
        
        yPos += 90;
        JLabel typeLabel = new JLabel("Appointment Type:");
        typeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        typeLabel.setBounds(30, yPos, 150, 25);
        formPanel.add(typeLabel);
        
        String[] types = {"Consultation", "Follow-up", "Emergency", "Routine Check-up"};
        JComboBox<String> typeCombo = new JComboBox<>(types);
        typeCombo.setBounds(30, yPos + 30, 640, 35);
        formPanel.add(typeCombo);
        
        yPos += 90;
        JLabel reasonLabel = new JLabel("Reason for Visit:");
        reasonLabel.setFont(new Font("Arial", Font.BOLD, 14));
        reasonLabel.setBounds(30, yPos, 150, 25);
        formPanel.add(reasonLabel);
        
        JTextArea reasonArea = new JTextArea();
        reasonArea.setBounds(30, yPos + 30, 640, 80);
        reasonArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        formPanel.add(reasonArea);
        
        yPos += 130;
        JButton bookBtn = new JButton("Book Appointment");
        bookBtn.setBounds(30, yPos, 640, 45);
        bookBtn.setFont(new Font("Arial", Font.BOLD, 15));
        bookBtn.setForeground(Color.WHITE);
        bookBtn.setBackground(new Color(17, 153, 142));
        bookBtn.setFocusPainted(false);
        bookBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        bookBtn.addActionListener(e -> {
            if (doctorCombo.getSelectedItem() != null && !dateField.getText().isEmpty()) {
                String clinicianId = ((String)doctorCombo.getSelectedItem()).split(" - ")[0];
                String appointmentId = "APT" + System.currentTimeMillis();
                
                Appointment newAppointment = new Appointment(
                    appointmentId, currentPatientId, clinicianId, "FAC001",
                    dateField.getText(), (String)timeCombo.getSelectedItem(), 30,
                    typeCombo.getSelectedItem() != null ? (String)typeCombo.getSelectedItem() : "Consultation",
                    "Scheduled", reasonArea.getText(), "",
                    LocalDate.now().toString(), LocalDate.now().toString()
                );
                
                dataService.addAppointment(newAppointment);
                
                JOptionPane.showMessageDialog(mainFrame, "Appointment booked successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                showAppointments();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Please fill all required fields", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        formPanel.add(bookBtn);
        panel.add(formPanel);
        
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
        
        JLabel titleLabel = new JLabel("My Prescriptions");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 400, 35);
        panel.add(titleLabel);
        
        String[] columns = {"Date", "Prescribed By", "Drug", "Dosage", "Frequency", "Duration", "Instructions", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (Prescription presc : dataService.getPrescriptionsByPatientId(currentPatientId)) {
            Clinician c = dataService.getClinicianById(presc.getClinicianId());
            model.addRow(new Object[]{
                presc.getPrescribedDate(),
                c != null ? c.getFullName() : "Unknown",
                presc.getDrugName(), presc.getDosage(), presc.getFrequency(),
                presc.getDurationDays() + " days", presc.getInstructions(), presc.getStatus()
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
        
        JLabel titleLabel = new JLabel("Medical Reports");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 400, 35);
        panel.add(titleLabel);
        
        JLabel infoLabel = new JLabel("Your medical reports and test results");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoLabel.setForeground(new Color(102, 102, 102));
        infoLabel.setBounds(30, 70, 400, 25);
        panel.add(infoLabel);
        
        List<Appointment> completedAppts = dataService.getAppointmentsByPatientId(currentPatientId).stream()
                .filter(a -> a.getStatus().equals("Completed"))
                .collect(Collectors.toList());
        
        int yPos = 120;
        for (Appointment appt : completedAppts) {
            JPanel reportPanel = new JPanel(null);
            reportPanel.setBackground(Color.WHITE);
            reportPanel.setBounds(30, yPos, 1080, 100);
            reportPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
            
            Clinician doctor = dataService.getClinicianById(appt.getClinicianId());
            JLabel apptLabel = new JLabel("Consultation with " + (doctor != null ? doctor.getFullName() : "Doctor"));
            apptLabel.setFont(new Font("Arial", Font.BOLD, 14));
            apptLabel.setBounds(20, 15, 400, 25);
            reportPanel.add(apptLabel);
            
            JLabel dateLabel = new JLabel("Date: " + appt.getAppointmentDate());
            dateLabel.setFont(new Font("Arial", Font.PLAIN, 13));
            dateLabel.setBounds(20, 40, 400, 20);
            reportPanel.add(dateLabel);
            
            JLabel notesLabel = new JLabel("Notes: " + (appt.getNotes().isEmpty() ? "No notes available" : appt.getNotes()));
            notesLabel.setFont(new Font("Arial", Font.PLAIN, 13));
            notesLabel.setBounds(20, 60, 1000, 20);
            reportPanel.add(notesLabel);
            
            panel.add(reportPanel);
            yPos += 110;
        }
        
        JScrollPane mainScroll = new JScrollPane(panel);
        mainScroll.setBorder(null);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        
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
        
        Patient patient = dataService.getPatientById(currentPatientId);
        
        if (patient != null) {
            JPanel profilePanel = new JPanel(null);
            profilePanel.setBackground(Color.WHITE);
            profilePanel.setBounds(30, 90, 800, 600);
            profilePanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
            
            int yPos = 30;
            addProfileField(profilePanel, "Patient ID:", patient.getPatientId(), 30, yPos);
            yPos += 50;
            addProfileField(profilePanel, "Full Name:", patient.getFullName(), 30, yPos);
            yPos += 50;
            addProfileField(profilePanel, "Gender:", patient.getGender(), 30, yPos);
            yPos += 50;
            addProfileField(profilePanel, "Date of Birth:", patient.getDateOfBirth(), 30, yPos);
            yPos += 50;
            addProfileField(profilePanel, "Blood Group:", patient.getBloodGroup(), 30, yPos);
            yPos += 50;
            addProfileField(profilePanel, "Phone Number:", patient.getPhoneNumber(), 30, yPos);
            yPos += 50;
            addProfileField(profilePanel, "Email:", patient.getEmail(), 30, yPos);
            yPos += 50;
            addProfileField(profilePanel, "Address:", patient.getAddress(), 30, yPos);
            yPos += 50;
            addProfileField(profilePanel, "Emergency Contact:", patient.getEmergencyContact(), 30, yPos);
            yPos += 50;
            addProfileField(profilePanel, "Insurance Provider:", patient.getInsuranceProvider(), 30, yPos);
            yPos += 50;
            addProfileField(profilePanel, "Registration Date:", patient.getRegistrationDate(), 30, yPos);
            
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
        table.setSelectionBackground(new Color(17, 153, 142, 50));
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