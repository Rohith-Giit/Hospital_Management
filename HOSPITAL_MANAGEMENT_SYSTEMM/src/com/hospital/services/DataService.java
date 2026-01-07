package com.hospital.services;

import com.hospital.models.*;
import com.hospital.utils.CSVReader;
import com.hospital.utils.CSVWriter;
import java.util.*;
import java.util.stream.Collectors;

public class DataService {
    private List<Patient> patients = new ArrayList<>();
    private List<Clinician> clinicians = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();
    private List<Prescription> prescriptions = new ArrayList<>();
    private List<Facility> facilities = new ArrayList<>();
    private List<Staff> staff = new ArrayList<>();
    private List<Referral> referrals = new ArrayList<>();

    public void loadAllData() {
        patients = CSVReader.readPatients("patients.csv");
        clinicians = CSVReader.readClinicians("clinicians.csv");
        appointments = CSVReader.readAppointments("appointments.csv");
        prescriptions = CSVReader.readPrescriptions("prescriptions.csv");
        facilities = CSVReader.readFacilities("facilities.csv");
        staff = CSVReader.readStaff("staff.csv");
        referrals = CSVReader.readReferrals("referrals.csv");
        
        System.out.println("Loaded: " + patients.size() + " patients");
        System.out.println("Loaded: " + clinicians.size() + " clinicians");
        System.out.println("Loaded: " + appointments.size() + " appointments");
        System.out.println("Loaded: " + prescriptions.size() + " prescriptions");
        System.out.println("Loaded: " + facilities.size() + " facilities");
        System.out.println("Loaded: " + staff.size() + " staff");
        System.out.println("Loaded: " + referrals.size() + " referrals");
    }

    // Getters
    public List<Patient> getPatients() { return patients; }
    public List<Clinician> getClinicians() { return clinicians; }
    public List<Appointment> getAppointments() { return appointments; }
    public List<Prescription> getPrescriptions() { return prescriptions; }
    public List<Facility> getFacilities() { return facilities; }
    public List<Staff> getStaff() { return staff; }
    public List<Referral> getReferrals() { return referrals; }

    // Find by ID methods
    public Patient getPatientById(String id) {
        return patients.stream().filter(p -> p.getPatientId().equals(id)).findFirst().orElse(null);
    }

    public Clinician getClinicianById(String id) {
        return clinicians.stream().filter(c -> c.getClinicianId().equals(id)).findFirst().orElse(null);
    }

    public Facility getFacilityById(String id) {
        return facilities.stream().filter(f -> f.getFacilityId().equals(id)).findFirst().orElse(null);
    }

    public Staff getStaffById(String id) {
        return staff.stream().filter(s -> s.getStaffId().equals(id)).findFirst().orElse(null);
    }

    // Get appointments for specific entities
    public List<Appointment> getAppointmentsByClinicianId(String clinicianId) {
        return appointments.stream()
                .filter(a -> a.getClinicianId().equals(clinicianId))
                .collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsByPatientId(String patientId) {
        return appointments.stream()
                .filter(a -> a.getPatientId().equals(patientId))
                .collect(Collectors.toList());
    }

    // Get prescriptions for specific entities
    public List<Prescription> getPrescriptionsByPatientId(String patientId) {
        return prescriptions.stream()
                .filter(p -> p.getPatientId().equals(patientId))
                .collect(Collectors.toList());
    }

    public List<Prescription> getPrescriptionsByClinicianId(String clinicianId) {
        return prescriptions.stream()
                .filter(p -> p.getClinicianId().equals(clinicianId))
                .collect(Collectors.toList());
    }

    // Get referrals
    public List<Referral> getReferralsByPatientId(String patientId) {
        return referrals.stream()
                .filter(r -> r.getPatientId().equals(patientId))
                .collect(Collectors.toList());
    }

    // Add new records
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        CSVWriter.writeAppointments(appointments);
    }

    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
        CSVWriter.writePrescriptions(prescriptions);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
        CSVWriter.writePatients(patients);
    }

    public void addClinician(Clinician clinician) {
        clinicians.add(clinician);
        CSVWriter.writeClinicians(clinicians);
    }

    public void addReferral(Referral referral) {
        referrals.add(referral);
        CSVWriter.writeReferrals(referrals);
    }

    // Statistics methods
    public int getTotalPatients() {
        return patients.size();
    }

    public int getTotalDoctors() {
        return clinicians.size();
    }

    public int getTotalAppointments() {
        return appointments.size();
    }

    public long getTodayAppointments() {
        // Would filter by today's date in real implementation
        return appointments.stream()
                .filter(a -> a.getStatus().equals("Scheduled"))
                .count();
    }
}