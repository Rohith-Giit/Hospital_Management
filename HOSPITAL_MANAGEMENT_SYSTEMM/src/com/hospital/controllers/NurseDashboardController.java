package com.hospital.controllers;

import com.hospital.models.*;
import com.hospital.services.*;
import com.hospital.Main;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class NurseDashboardController {
    private Main mainFrame;
    private DataService dataService;
    private JPanel mainPanel;
    private JPanel contentPanel;

    public NurseDashboardController(Main mainFrame, DataService dataService) {
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
        mainFrame.getMainPanel().add(mainPanel, "nurse");
        mainFrame.getCardLayout().show(mainFrame.getMainPanel(), "nurse");
        mainFrame.getMainPanel().revalidate();
        mainFrame.getMainPanel().repaint();
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(null);
        sidebar.setPreferredSize(new Dimension(250, 850));
        sidebar.setBackground(new Color(240, 147, 251));

        JLabel titleLabel = new JLabel("Nurse Portal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(20, 30, 210, 30);
        sidebar.add(titleLabel);
        
        int yPos = 100;
        JButton dashboardBtn = createSidebarButton("📊 Dashboard", 20, yPos);
        dashboardBtn.addActionListener(e -> showDashboard());
        sidebar.add(dashboardBtn);
        
        yPos += 50;
        JButton patientsBtn = createSidebarButton("👥 Monitor Patients", 20, yPos);
        patientsBtn.addActionListener(e -> showMonitorPatients());
        sidebar.add(patientsBtn);
        
        yPos += 50;
        JButton updateBtn = createSidebarButton("📝 Update Report", 20, yPos);
        updateBtn.addActionListener(e -> showUpdateReport());
        sidebar.add(updateBtn);
        
        yPos += 50;
        JButton drugsBtn = createSidebarButton("💊 Administer Drugs", 20, yPos);
        drugsBtn.addActionListener(e -> showAdministerDrugs());
        sidebar.add(drugsBtn);
        
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
        btn.setBackground(new Color(240, 147, 251));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(220, 130, 235));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(240, 147, 251));
            }
        });
        
        return btn;
    }

    private void showDashboard() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Nurse Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 400, 35);
        panel.add(titleLabel);
        
        // Statistics
        int xPos = 30;
        panel.add(createStatCard("Total Patients", String.valueOf(dataService.getPatients().size()), 
            new Color(240, 147, 251), xPos, 100));
        
        xPos += 360;
        panel.add(createStatCard("Active Prescriptions", String.valueOf(dataService.getPrescriptions().stream()
            .filter(p -> p.getStatus().equals("Active")).count()), 
            new Color(102, 126, 234), xPos, 100));
        
        xPos += 360;
        panel.add(createStatCard("Today's Appointments", String.valueOf(dataService.getTodayAppointments()), 
            new Color(17, 153, 142), xPos, 100));
        
        // Patient List
        JLabel patientLabel = new JLabel("Patient Monitoring");
        patientLabel.setFont(new Font("Arial", Font.BOLD, 20));
        patientLabel.setBounds(30, 250, 400, 30);
        panel.add(patientLabel);
        
        String[] columns = {"Patient ID", "Name", "Blood Group", "Phone"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (Patient p : dataService.getPatients().stream().limit(10).collect(Collectors.toList())) {
            model.addRow(new Object[]{
                p.getPatientId(), p.getFullName(), p.getBloodGroup(), p.getPhoneNumber()
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
        
        contentPanel.removeAll();
        contentPanel.add(mainScroll, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showMonitorPatients() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Monitor Patients");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 400, 35);
        panel.add(titleLabel);
        
        String[] columns = {"Patient ID", "Name", "Blood Group", "Phone", "Address"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (Patient p : dataService.getPatients()) {
            model.addRow(new Object[]{
                p.getPatientId(), p.getFullName(), p.getBloodGroup(), 
                p.getPhoneNumber(), p.getAddress()
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

    private void showUpdateReport() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Update Patient Report");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 500, 35);
        panel.add(titleLabel);
        
        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(30, 90, 700, 450);
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        
        int yPos = 30;
        
        JLabel patientLabel = new JLabel("Select Patient:");
        patientLabel.setFont(new Font("Arial", Font.BOLD, 14));
        patientLabel.setBounds(30, yPos, 150, 25);
        formPanel.add(patientLabel);
        
        JComboBox<String> patientCombo = new JComboBox<>();
        patientCombo.setBounds(30, yPos + 30, 640, 35);
        for (Patient p : dataService.getPatients()) {
            patientCombo.addItem(p.getPatientId() + " - " + p.getFullName());
        }
        formPanel.add(patientCombo);
        
        yPos += 90;
        JLabel vitalLabel = new JLabel("Vital Signs:");
        vitalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        vitalLabel.setBounds(30, yPos, 200, 25);
        formPanel.add(vitalLabel);
        
        JTextArea vitalArea = new JTextArea();
        vitalArea.setBounds(30, yPos + 30, 640, 100);
        vitalArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        formPanel.add(vitalArea);
        
        yPos += 150;
        JLabel obsLabel = new JLabel("Observations:");
        obsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        obsLabel.setBounds(30, yPos, 200, 25);
        formPanel.add(obsLabel);
        
        JTextArea obsArea = new JTextArea();
        obsArea.setBounds(30, yPos + 30, 640, 100);
        obsArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        formPanel.add(obsArea);
        
        yPos += 150;
        JButton updateBtn = new JButton("Update Report");
        updateBtn.setBounds(30, yPos, 640, 45);
        updateBtn.setFont(new Font("Arial", Font.BOLD, 15));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setBackground(new Color(240, 147, 251));
        updateBtn.setFocusPainted(false);
        updateBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        updateBtn.addActionListener(e -> {
            if (patientCombo.getSelectedItem() != null) {
                JOptionPane.showMessageDialog(mainFrame, "Patient report updated successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                vitalArea.setText("");
                obsArea.setText("");
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Please select a patient", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        formPanel.add(updateBtn);
        panel.add(formPanel);
        
        JScrollPane mainScroll = new JScrollPane(panel);
        mainScroll.setBorder(null);
        
        contentPanel.removeAll();
        contentPanel.add(mainScroll, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showAdministerDrugs() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Administer Drugs");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 400, 35);
        panel.add(titleLabel);
        
        String[] columns = {"Patient", "Drug", "Dosage", "Frequency", "Instructions", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (Prescription p : dataService.getPrescriptions().stream()
                .filter(pr -> pr.getStatus().equals("Active")).collect(Collectors.toList())) {
            Patient patient = dataService.getPatientById(p.getPatientId());
            model.addRow(new Object[]{
                patient != null ? patient.getFullName() : "Unknown",
                p.getDrugName(), p.getDosage(), p.getFrequency(),
                p.getInstructions(), p.getStatus()
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

    private JPanel createStatCard(String title, String value, Color color, int x, int y) {
        JPanel card = new JPanel(null);
        card.setBounds(x, y, 340, 120);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        titleLabel.setForeground(new Color(102, 102, 102));
        titleLabel.setBounds(20, 20, 300, 20);
        card.add(titleLabel);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 32));
        valueLabel.setForeground(color);
        valueLabel.setBounds(20, 50, 300, 40);
        card.add(valueLabel);
        
        return card;
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(240, 147, 251, 50));
        table.setSelectionForeground(Color.BLACK);
        
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(250, 250, 250));
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
    }
}