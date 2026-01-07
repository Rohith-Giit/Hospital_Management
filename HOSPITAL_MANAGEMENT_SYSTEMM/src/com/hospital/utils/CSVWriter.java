package com.hospital.utils;

import com.hospital.models.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CSVWriter {
    private static final String CSV_DIR = "src";

    public static void writePatients(List<Patient> patients) {
        String filePath = CSV_DIR + "/patients.csv";
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write header
            writer.write("patient_id,first_name,last_name,date_of_birth,nhs_number,gender,phone_number,email,address,postcode,emergency_contact_name,emergency_contact_phone,registration_date,gp_surgery_id\n");
            
            // Write data rows
            for (Patient p : patients) {
                writer.write(String.format("%s,%s,%s,%s,,,%s,%s,%s,,,%s,%s,S001\n",
                    p.getPatientId(),
                    p.getFirstName(),
                    p.getLastName(),
                    p.getDateOfBirth(),
                    p.getPhoneNumber(),
                    p.getEmail(),
                    p.getAddress(),
                    p.getEmergencyContact(),
                    p.getRegistrationDate()
                ));
            }
            System.out.println("Patients data saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing patients: " + e.getMessage());
        }
    }

    public static void writeClinicians(List<Clinician> clinicians) {
        String filePath = CSV_DIR + "/clinicians.csv";
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write header
            writer.write("clinician_id,first_name,last_name,title,speciality,gmc_number,phone_number,email,workplace_id,workplace_type,employment_status,start_date\n");
            
            // Write data rows
            for (Clinician c : clinicians) {
                writer.write(String.format("%s,%s,%s,%s,%s,,%s,%s,%d,%s,%s,%s\n",
                    c.getClinicianId(),
                    c.getFirstName(),
                    c.getLastName(),
                    c.getTitle(),
                    c.getSpeciality(),
                    c.getPhoneNumber(),
                    c.getEmail(),
                    c.getWorkplaceId(),
                    c.getWorkplaceType(),
                    c.getEmploymentStatus(),
                    c.getStartDate()
                ));
            }
            System.out.println("Clinicians data saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing clinicians: " + e.getMessage());
        }
    }

    public static void writeAppointments(List<Appointment> appointments) {
        String filePath = CSV_DIR + "/appointments.csv";
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write header
            writer.write("appointment_id,patient_id,clinician_id,facility_id,appointment_date,appointment_time,duration_minutes,appointment_type,status,reason_for_visit,notes,created_date,last_modified\n");
            
            // Write data rows
            for (Appointment a : appointments) {
                writer.write(String.format("%s,%s,%s,,%s,%s,30,%s,%s,%s,,,%s\n",
                    a.getAppointmentId(),
                    a.getPatientId(),
                    a.getClinicianId(),
                    a.getAppointmentDate(),
                    a.getAppointmentTime(),
                    a.getAppointmentType(),
                    a.getStatus(),
                    a.getReasonForVisit(),
                    a.getCreatedDate()
                ));
            }
            System.out.println("Appointments data saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing appointments: " + e.getMessage());
        }
    }

    public static void writePrescriptions(List<Prescription> prescriptions) {
        String filePath = CSV_DIR + "/prescriptions.csv";
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write header
            writer.write("prescription_id,patient_id,clinician_id,appointment_id,prescription_date,medication_name,dosage,frequency,duration_days,quantity,instructions,pharmacy_name,status,issue_date,collection_date\n");
            
            // Write data rows
            for (Prescription p : prescriptions) {
                writer.write(String.format("%s,%s,%s,,%s,%s,%s,%s,30,,,,,%s,\n",
                    p.getPrescriptionId(),
                    p.getPatientId(),
                    p.getClinicianId(),
                    p.getPrescriptionDate(),
                    p.getDrugName(),
                    p.getDosage(),
                    p.getFrequency(),
                    p.getIssueDate()
                ));
            }
            System.out.println("Prescriptions data saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing prescriptions: " + e.getMessage());
        }
    }

    public static void writeFacilities(List<Facility> facilities) {
        String filePath = CSV_DIR + "/facilities.csv";
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write header
            writer.write("facility_id,facility_name,facility_type,address,postcode,phone_number,email,opening_hours,manager_name,capacity,specialities_offered\n");
            
            // Write data rows
            for (Facility f : facilities) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s,%s,,,,%s\n",
                    f.getFacilityId(),
                    f.getFacilityName(),
                    f.getFacilityType(),
                    f.getAddress(),
                    f.getPostalCode(),
                    f.getPhoneNumber(),
                    f.getEmail(),
                    f.getCapacity()
                ));
            }
            System.out.println("Facilities data saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing facilities: " + e.getMessage());
        }
    }

    public static void writeReferrals(List<Referral> referrals) {
        String filePath = CSV_DIR + "/referrals.csv";
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write header
            writer.write("referral_id,patient_id,referring_clinician_id,referred_to_clinician_id,referring_facility_id,referred_to_facility_id,referral_date,urgency_level,referral_reason,clinical_summary,requested_investigations,status,appointment_id,notes,created_date,last_updated\n");
            
            // Write data rows
            for (Referral r : referrals) {
                writer.write(String.format("%s,%s,%s,,%s,,%s,%s,%s,,,,%s,,,%s\n",
                    r.getReferralId(),
                    r.getPatientId(),
                    r.getReferringClinicianId(),
                    r.getReferringFacilityId(),
                    r.getReferralDate(),
                    r.getUrgencyLevel(),
                    r.getReferralReason(),
                    r.getStatus(),
                    r.getCreatedDate()
                ));
            }
            System.out.println("Referrals data saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing referrals: " + e.getMessage());
        }
    }
}
