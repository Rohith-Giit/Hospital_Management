// File: src/main/java/com/hospital/utils/CSVReader.java
package com.hospital.utils;

import com.hospital.models.*;
import java.io.*;
import java.util.*;

public class CSVReader {

    public static List<Patient> readPatients(String filePath) {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                CSVReader.class.getClassLoader().getResourceAsStream(filePath)))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                // CSV columns: patient_id(0), first_name(1), last_name(2), date_of_birth(3), 
                // nhs_number(4), gender(5), phone_number(6), email(7), address(8), 
                // postcode(9), emergency_contact_name(10), emergency_contact_phone(11), registration_date(12), gp_surgery_id(13)
                if (values.length >= 12) {
                    patients.add(new Patient(
                        cleanValue(values[0]),      // patient_id
                        cleanValue(values[1]),      // first_name
                        cleanValue(values[2]),      // last_name
                        cleanValue(values[5]),      // gender
                        cleanValue(values[3]),      // date_of_birth
                        cleanValue(values[6]),      // phone_number
                        cleanValue(values[7]),      // email
                        cleanValue(values[8]),      // address
                        cleanValue(values[9]),      // postcode (blood_group)
                        cleanValue(values[10]),     // emergency_contact_name
                        values.length > 12 ? cleanValue(values[12]) : "", // registration_date
                        values.length > 13 ? cleanValue(values[13]) : ""  // gp_surgery_id (insurance_provider)
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading patients: " + e.getMessage());
        }
        return patients;
    }

    public static List<Clinician> readClinicians(String filePath) {
        List<Clinician> clinicians = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                CSVReader.class.getClassLoader().getResourceAsStream(filePath)))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (values.length >= 12) {
                    clinicians.add(new Clinician(
                        cleanValue(values[0]), cleanValue(values[1]), cleanValue(values[2]),
                        cleanValue(values[3]), cleanValue(values[4]), cleanValue(values[5]),
                        cleanValue(values[6]), cleanValue(values[7]), 
                        parseIntSafe(values[8]), cleanValue(values[9]), 
                        cleanValue(values[10]), cleanValue(values[11])
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading clinicians: " + e.getMessage());
        }
        return clinicians;
    }

    public static List<Appointment> readAppointments(String filePath) {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                CSVReader.class.getClassLoader().getResourceAsStream(filePath)))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (values.length >= 13) {
                    appointments.add(new Appointment(
                        cleanValue(values[0]), cleanValue(values[1]), cleanValue(values[2]),
                        cleanValue(values[3]), cleanValue(values[4]), cleanValue(values[5]),
                        parseIntSafe(values[6]), cleanValue(values[7]), cleanValue(values[8]),
                        cleanValue(values[9]), cleanValue(values[10]), cleanValue(values[11]),
                        cleanValue(values[12])
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading appointments: " + e.getMessage());
        }
        return appointments;
    }

    public static List<Prescription> readPrescriptions(String filePath) {
        List<Prescription> prescriptions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                CSVReader.class.getClassLoader().getResourceAsStream(filePath)))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (values.length >= 10) {
                    prescriptions.add(new Prescription(
                        cleanValue(values[0]), cleanValue(values[1]), cleanValue(values[2]),
                        cleanValue(values[3]), cleanValue(values[4]), cleanValue(values[5]),
                        parseIntSafe(values[6]), cleanValue(values[7]), cleanValue(values[8]),
                        cleanValue(values[9])
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading prescriptions: " + e.getMessage());
        }
        return prescriptions;
    }

    public static List<Facility> readFacilities(String filePath) {
        List<Facility> facilities = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                CSVReader.class.getClassLoader().getResourceAsStream(filePath)))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                // CSV columns: facility_id(0), facility_name(1), facility_type(2), address(3), 
                // postcode(4), phone_number(5), email(6), opening_hours(7), manager_name(8), capacity(9), specialities_offered(10)
                if (values.length >= 10) {
                    facilities.add(new Facility(
                        cleanValue(values[0]),      // facility_id
                        cleanValue(values[1]),      // facility_name
                        cleanValue(values[2]),      // facility_type
                        cleanValue(values[3]),      // address
                        "",                         // city (empty, not in CSV)
                        "",                         // state (empty, not in CSV)
                        cleanValue(values[4]),      // postcode
                        cleanValue(values[5]),      // phone_number
                        cleanValue(values[6]),      // email
                        parseIntSafe(values[9]),    // capacity
                        "Active",                   // status (default)
                        ""                          // createdDate (default)
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading facilities: " + e.getMessage());
        }
        return facilities;
    }

    public static List<Staff> readStaff(String filePath) {
        List<Staff> staffList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                CSVReader.class.getClassLoader().getResourceAsStream(filePath)))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (values.length >= 12) {
                    staffList.add(new Staff(
                        cleanValue(values[0]), cleanValue(values[1]), cleanValue(values[2]),
                        cleanValue(values[3]), cleanValue(values[4]), cleanValue(values[5]),
                        cleanValue(values[6]), cleanValue(values[7]), cleanValue(values[8]),
                        cleanValue(values[9]), cleanValue(values[10]), cleanValue(values[11])
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading staff: " + e.getMessage());
        }
        return staffList;
    }

    public static List<Referral> readReferrals(String filePath) {
        List<Referral> referrals = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                CSVReader.class.getClassLoader().getResourceAsStream(filePath)))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (values.length >= 9) {
                    referrals.add(new Referral(
                        cleanValue(values[0]), cleanValue(values[1]), cleanValue(values[2]),
                        cleanValue(values[3]), cleanValue(values[4]), cleanValue(values[5]),
                        cleanValue(values[6]), cleanValue(values[7]), cleanValue(values[8])
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading referrals: " + e.getMessage());
        }
        return referrals;
    }

    private static String cleanValue(String value) {
        if (value == null) return "";
        return value.trim().replace("\"", "");
    }

    private static int parseIntSafe(String value) {
        try {
            return Integer.parseInt(cleanValue(value));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}