package com.hospital.controllers;

import com.hospital.models.*;
import com.hospital.services.*;
import com.hospital.utils.CSVWriter;
import com.hospital.Main;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class AdminDashboardController {
    private Main mainFrame;
    private DataService dataService;
    private JPanel mainPanel;
    private JPanel contentPanel;

    public AdminDashboardController(Main mainFrame, DataService dataService) {
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
        mainFrame.getMainPanel().add(mainPanel, "admin");
        mainFrame.getCardLayout().show(mainFrame.getMainPanel(), "admin");
        mainFrame.getMainPanel().revalidate();
        mainFrame.getMainPanel().repaint();
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(null);
        sidebar.setPreferredSize(new Dimension(250, 850));
        sidebar.setBackground(new Color(250, 112, 154));

        JLabel titleLabel = new JLabel("Admin Portal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(20, 30, 210, 30);
        sidebar.add(titleLabel);
        
        int yPos = 100;
        JButton dashboardBtn = createSidebarButton("📊 Dashboard", 20, yPos);
        dashboardBtn.addActionListener(e -> showDashboard());
        sidebar.add(dashboardBtn);
        
        yPos += 50;
        JButton appointmentsBtn = createSidebarButton("📅 Manage Appointments", 20, yPos);
        appointmentsBtn.addActionListener(e -> showManageAppointments());
        sidebar.add(appointmentsBtn);
        
        yPos += 50;
        JButton patientsBtn = createSidebarButton("👥 Assist Patients", 20, yPos);
        patientsBtn.addActionListener(e -> showAssistPatients());
        sidebar.add(patientsBtn);
        
        yPos += 50;
        JButton scheduleBtn = createSidebarButton("🗓️ View Schedule", 20, yPos);
        scheduleBtn.addActionListener(e -> showSchedule());
        sidebar.add(scheduleBtn);
        
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
        btn.setBackground(new Color(250, 112, 154));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(230, 95, 135));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(250, 112, 154));
            }
        });
        
        return btn;
    }

    private void showDashboard() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Administrative Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 500, 35);
        panel.add(titleLabel);
        
        // Statistics
        int xPos = 30;
        panel.add(createStatCard("Total Appointments", String.valueOf(dataService.getTotalAppointments()), 
            new Color(250, 112, 154), xPos, 100));
        
        xPos += 280;
        panel.add(createStatCard("Today's Appointments", String.valueOf(dataService.getTodayAppointments()), 
            new Color(102, 126, 234), xPos, 100));
        
        xPos += 280;
        panel.add(createStatCard("Total Patients", String.valueOf(dataService.getTotalPatients()), 
            new Color(17, 153, 142), xPos, 100));
        
        xPos += 280;
        panel.add(createStatCard("Total Doctors", String.valueOf(dataService.getTotalDoctors()), 
            new Color(240, 147, 251), xPos, 100));
        
        JScrollPane mainScroll = new JScrollPane(panel);
        mainScroll.setBorder(null);
        
        contentPanel.removeAll();
        contentPanel.add(mainScroll, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showManageAppointments() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Manage Appointments");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 400, 35);
        panel.add(titleLabel);
        
        String[] columns = {"ID", "Date", "Time", "Patient", "Doctor", "Type", "Status", "Action"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7; // Only Action column is editable
            }
        };
        
        for (Appointment appt : dataService.getAppointments()) {
            Patient p = dataService.getPatientById(appt.getPatientId());
            Clinician c = dataService.getClinicianById(appt.getClinicianId());
            model.addRow(new Object[]{
                appt.getAppointmentId(), appt.getAppointmentDate(), appt.getAppointmentTime(),
                p != null ? p.getFullName() : "Unknown",
                c != null ? c.getFullName() : "Unknown",
                appt.getAppointmentType(), appt.getStatus(), "View"
            });
        }
        
        JTable table = new JTable(model);
        styleTable(table);
        
        // Add button click handler for appointment actions
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                if (col == 7 && row >= 0) {
                    String appointmentId = (String) model.getValueAt(row, 0);
                    handleAppointmentAction(appointmentId, row, model);
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

    private void handleAppointmentAction(String appointmentId, int row, DefaultTableModel model) {
        String[] options = {"Schedule", "Cancel", "Delete", "Cancel"};
        int choice = JOptionPane.showOptionDialog(mainFrame, "Choose action for appointment " + appointmentId,
                "Appointment Management", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[3]);
        
        if (choice == 0) { // Schedule
            scheduleAppointment(appointmentId);
        } else if (choice == 1) { // Cancel
            cancelAppointment(appointmentId);
        } else if (choice == 2) { // Delete
            deleteAppointment(appointmentId, row, model);
        }
    }

    private void scheduleAppointment(String appointmentId) {
        Appointment appt = null;
        for (Appointment a : dataService.getAppointments()) {
            if (a.getAppointmentId().equals(appointmentId)) {
                appt = a;
                break;
            }
        }
        
        if (appt == null) return;
        
        JPanel panel = new JPanel(null);
        panel.setLayout(new java.awt.GridLayout(5, 2, 10, 10));
        
        JLabel dateLabel = new JLabel("Appointment Date:");
        JTextField dateField = new JTextField(appt.getAppointmentDate());
        
        JLabel timeLabel = new JLabel("Appointment Time:");
        JTextField timeField = new JTextField(appt.getAppointmentTime());
        
        JLabel typeLabel = new JLabel("Appointment Type:");
        JTextField typeField = new JTextField(appt.getAppointmentType());
        
        JLabel reasonLabel = new JLabel("Reason for Visit:");
        JTextField reasonField = new JTextField(appt.getReasonForVisit());
        
        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Scheduled", "Pending", "Confirmed"});
        statusCombo.setSelectedItem(appt.getStatus());
        
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(timeLabel);
        panel.add(timeField);
        panel.add(typeLabel);
        panel.add(typeField);
        panel.add(reasonLabel);
        panel.add(reasonField);
        panel.add(statusLabel);
        panel.add(statusCombo);
        
        int result = JOptionPane.showConfirmDialog(mainFrame, panel, "Schedule Appointment", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            appt.setAppointmentDate(dateField.getText());
            appt.setAppointmentTime(timeField.getText());
            appt.setAppointmentType(typeField.getText());
            appt.setReasonForVisit(reasonField.getText());
            appt.setStatus((String) statusCombo.getSelectedItem());
            
            CSVWriter.writeAppointments(dataService.getAppointments());
            JOptionPane.showMessageDialog(mainFrame, "Appointment scheduled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            showManageAppointments(); // Refresh the table
        }
    }

    private void cancelAppointment(String appointmentId) {
        Appointment appt = null;
        for (Appointment a : dataService.getAppointments()) {
            if (a.getAppointmentId().equals(appointmentId)) {
                appt = a;
                break;
            }
        }
        
        if (appt == null) return;
        
        int confirm = JOptionPane.showConfirmDialog(mainFrame, 
                "Are you sure you want to cancel appointment " + appointmentId + "?", 
                "Confirm Cancel", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            appt.setStatus("Cancelled");
            CSVWriter.writeAppointments(dataService.getAppointments());
            JOptionPane.showMessageDialog(mainFrame, "Appointment cancelled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            showManageAppointments(); // Refresh the table
        }
    }

    private void deleteAppointment(String appointmentId, int row, DefaultTableModel model) {
        int confirm = JOptionPane.showConfirmDialog(mainFrame, 
                "Are you sure you want to delete appointment " + appointmentId + "?", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            Appointment appt = null;
            for (Appointment a : dataService.getAppointments()) {
                if (a.getAppointmentId().equals(appointmentId)) {
                    appt = a;
                    break;
                }
            }
            
            if (appt != null) {
                dataService.getAppointments().remove(appt);
                CSVWriter.writeAppointments(dataService.getAppointments());
                JOptionPane.showMessageDialog(mainFrame, "Appointment deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                showManageAppointments(); // Refresh the table
            }
        }
    }

    private void showAssistPatients() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Assist Patients");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 400, 35);
        panel.add(titleLabel);
        
        String[] columns = {"Patient ID", "Name", "Phone", "Email", "Insurance"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (Patient p : dataService.getPatients()) {
            model.addRow(new Object[]{
                p.getPatientId(), p.getFullName(), p.getPhoneNumber(),
                p.getEmail(), p.getInsuranceProvider()
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

    private void showSchedule() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Hospital Schedule");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 400, 35);
        panel.add(titleLabel);
        
        Map<String, java.util.List<Appointment>> appointmentsByDate = new HashMap<>();
        for (Appointment appt : dataService.getAppointments()) {
            appointmentsByDate.computeIfAbsent(appt.getAppointmentDate(), k -> new ArrayList<>()).add(appt);
        }
        
        int yPos = 90;
        for (Map.Entry<String, java.util.List<Appointment>> entry : appointmentsByDate.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()).limit(7).collect(Collectors.toList())) {
            JPanel datePanel = new JPanel(null);
            datePanel.setBackground(Color.WHITE);
            datePanel.setBounds(30, yPos, 1080, 80);
            datePanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
            
            JLabel dateLabel = new JLabel(entry.getKey());
            dateLabel.setFont(new Font("Arial", Font.BOLD, 18));
            dateLabel.setForeground(new Color(102, 126, 234));
            dateLabel.setBounds(20, 20, 400, 25);
            datePanel.add(dateLabel);
            
            JLabel countLabel = new JLabel(entry.getValue().size() + " appointments");
            countLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            countLabel.setForeground(new Color(102, 102, 102));
            countLabel.setBounds(20, 45, 400, 20);
            datePanel.add(countLabel);
            
            panel.add(datePanel);
            yPos += 90;
        }
        
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

    private void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(250, 112, 154, 50));
        table.setSelectionForeground(Color.BLACK);
        
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(250, 250, 250));
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
    }
}