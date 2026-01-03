import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private List<Models.Appointment> appointments;
    private List<Models.Clinician> clinicians;
    private List<Models.Facility> facilities;
    private List<Models.Patient> patients;
    private List<Models.Prescription> prescriptions;
    private List<Models.Referral> referrals;
    private List<Models.Staff> staff;
    
    private static final String BASE_PATH = "C:\\Users\\user\\Documents\\NetBeansProjects\\HospitalManagementSystem\\src\\";

    public DataManager() {
        appointments = new ArrayList<>();
        clinicians = new ArrayList<>();
        facilities = new ArrayList<>();
        patients = new ArrayList<>();
        prescriptions = new ArrayList<>();
        referrals = new ArrayList<>();
        staff = new ArrayList<>();
        
        loadAllData();
    }

    private void loadAllData() {
        loadAppointments();
        loadClinicians();
        loadFacilities();
        loadPatients();
        loadPrescriptions();
        loadReferrals();
        loadStaff();
    }

    private void loadAppointments() {
        String file = BASE_PATH + "appointments.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (values.length >= 13) {
                    appointments.add(new Models.Appointment(
                        cleanValue(values[0]),  // appointment_id
                        cleanValue(values[1]),  // patient_id
                        cleanValue(values[2]),  // clinician_id
                        cleanValue(values[3]),  // facility_id
                        cleanValue(values[4]),  // appointment_date
                        cleanValue(values[5]),  // appointment_time
                        parseIntSafe(values[6]), // duration_minutes
                        cleanValue(values[7]),  // appointment_type
                        cleanValue(values[8]),  // status
                        cleanValue(values[9]),  // reason_for_visit
                        cleanValue(values[10]), // notes
                        cleanValue(values[11]), // created_date
                        cleanValue(values[12])  // last_modified
                    ));
                }
            }
            System.out.println("Loaded " + appointments.size() + " appointments");
        } catch (IOException e) {
            System.err.println("Error loading appointments: " + e.getMessage());
        }
    }

    private void loadClinicians() {
        String file = BASE_PATH + "clinicians.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (values.length >= 12) {
                    clinicians.add(new Models.Clinician(
                        cleanValue(values[0]),  // clinician_id
                        cleanValue(values[1]),  // first_name
                        cleanValue(values[2]),  // last_name
                        cleanValue(values[3]),  // specialization
                        parseLongSafe(values[4]), // phone_number
                        cleanValue(values[5]),  // email
                        cleanValue(values[6]),  // facility_id
                        cleanValue(values[7]),  // availability_status
                        parseIntSafe(values[8]), // years_of_experience
                        cleanValue(values[9]),  // employment_type
                        cleanValue(values[10]), // created_date
                        cleanValue(values[11])  // last_updated
                    ));
                }
            }
            System.out.println("Loaded " + clinicians.size() + " clinicians");
        } catch (IOException e) {
            System.err.println("Error loading clinicians: " + e.getMessage());
        }
    }

    private void loadFacilities() {
        String file = BASE_PATH + "facilities.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (values.length >= 12) {
                    facilities.add(new Models.Facility(
                        cleanValue(values[0]),  // facility_id
                        cleanValue(values[1]),  // facility_name
                        cleanValue(values[2]),  // facility_type
                        cleanValue(values[3]),  // address
                        cleanValue(values[4]),  // city
                        cleanValue(values[5]),  // state
                        parseIntSafe(values[6]), // postal_code
                        parseLongSafe(values[7]), // phone_number
                        cleanValue(values[8]),  // email
                        parseIntSafe(values[9]), // capacity
                        cleanValue(values[10]), // status
                        cleanValue(values[11])  // created_date
                    ));
                }
            }
            System.out.println("Loaded " + facilities.size() + " facilities");
        } catch (IOException e) {
            System.err.println("Error loading facilities: " + e.getMessage());
        }
    }

    private void loadPatients() {
        String file = BASE_PATH + "patients.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (values.length >= 12) {
                    patients.add(new Models.Patient(
                        cleanValue(values[0]),  // patient_id
                        cleanValue(values[1]),  // first_name
                        cleanValue(values[2]),  // last_name
                        cleanValue(values[3]),  // gender
                        cleanValue(values[4]),  // date_of_birth
                        parseLongSafe(values[5]), // phone_number
                        cleanValue(values[6]),  // email
                        cleanValue(values[7]),  // address
                        cleanValue(values[8]),  // blood_group
                        parseLongSafe(values[9]), // emergency_contact
                        cleanValue(values[10]), // registration_date
                        cleanValue(values[11])  // insurance_provider
                    ));
                }
            }
            System.out.println("Loaded " + patients.size() + " patients");
        } catch (IOException e) {
            System.err.println("Error loading patients: " + e.getMessage());
        }
    }

    private void loadPrescriptions() {
        String file = BASE_PATH + "prescriptions.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (values.length >= 10) {
                    prescriptions.add(new Models.Prescription(
                        cleanValue(values[0]),  // prescription_id
                        cleanValue(values[1]),  // patient_id
                        cleanValue(values[2]),  // clinician_id
                        cleanValue(values[3]),  // drug_name
                        cleanValue(values[4]),  // dosage
                        cleanValue(values[5]),  // frequency
                        parseIntSafe(values[6]), // duration_days
                        cleanValue(values[7]),  // instructions
                        cleanValue(values[8]),  // prescribed_date
                        cleanValue(values[9])   // status
                    ));
                }
            }
            System.out.println("Loaded " + prescriptions.size() + " prescriptions");
        } catch (IOException e) {
            System.err.println("Error loading prescriptions: " + e.getMessage());
        }
    }

    private void loadReferrals() {
        String file = BASE_PATH + "referrals.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (values.length >= 9) {
                    referrals.add(new Models.Referral(
                        cleanValue(values[0]),  // referral_id
                        cleanValue(values[1]),  // patient_id
                        cleanValue(values[2]),  // referring_clinician
                        cleanValue(values[3]),  // referred_to_specialist
                        cleanValue(values[4]),  // referral_reason
                        cleanValue(values[5]),  // referral_date
                        cleanValue(values[6]),  // priority_level
                        cleanValue(values[7]),  // status
                        cleanValue(values[8])   // notes
                    ));
                }
            }
            System.out.println("Loaded " + referrals.size() + " referrals");
        } catch (IOException e) {
            System.err.println("Error loading referrals: " + e.getMessage());
        }
    }

    private void loadStaff() {
        String file = BASE_PATH + "staff.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (values.length >= 12) {
                    staff.add(new Models.Staff(
                        cleanValue(values[0]),  // staff_id
                        cleanValue(values[1]),  // first_name
                        cleanValue(values[2]),  // last_name
                        cleanValue(values[3]),  // role
                        cleanValue(values[4]),  // department
                        cleanValue(values[5]),  // facility_id
                        parseLongSafe(values[6]), // phone_number
                        cleanValue(values[7]),  // email
                        cleanValue(values[8]),  // employment_status
                        cleanValue(values[9]),  // start_date
                        cleanValue(values[10]), // line_manager
                        cleanValue(values[11])  // access_level
                    ));
                }
            }
            System.out.println("Loaded " + staff.size() + " staff members");
        } catch (IOException e) {
            System.err.println("Error loading staff: " + e.getMessage());
        }
    }

    private String cleanValue(String value) {
        if (value == null) return "";
        value = value.trim();
        if (value.startsWith("\"") && value.endsWith("\"")) {
            value = value.substring(1, value.length() - 1);
        }
        return value.replace("\"\"", "\"");
    }

    private int parseIntSafe(String value) {
        try {
            String cleaned = cleanValue(value);
            return cleaned.isEmpty() ? 0 : Integer.parseInt(cleaned);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private long parseLongSafe(String value) {
        try {
            String cleaned = cleanValue(value);
            return cleaned.isEmpty() ? 0L : Long.parseLong(cleaned);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    // Getters
    public List<Models.Appointment> getAppointments() {
        return appointments;
    }

    public List<Models.Clinician> getClinicians() {
        return clinicians;
    }

    public List<Models.Facility> getFacilities() {
        return facilities;
    }

    public List<Models.Patient> getPatients() {
        return patients;
    }

    public List<Models.Prescription> getPrescriptions() {
        return prescriptions;
    }

    public List<Models.Referral> getReferrals() {
        return referrals;
    }

    public List<Models.Staff> getStaff() {
        return staff;
    }

    // Methods to add new data (for runtime operations)
    public void addAppointment(Models.Appointment appointment) {
        appointments.add(appointment);
        System.out.println("Added new appointment: " + appointment.getAppointmentId());
    }

    public void addPatient(Models.Patient patient) {
        patients.add(patient);
        System.out.println("Added new patient: " + patient.getPatientId());
    }

    public void addPrescription(Models.Prescription prescription) {
        prescriptions.add(prescription);
        System.out.println("Added new prescription: " + prescription.getPrescriptionId());
    }

    public void addClinician(Models.Clinician clinician) {
        clinicians.add(clinician);
        System.out.println("Added new clinician: " + clinician.getClinicianId());
    }

    public void addStaff(Models.Staff staffMember) {
        staff.add(staffMember);
        System.out.println("Added new staff member: " + staffMember.getStaffId());
    }

    public void addFacility(Models.Facility facility) {
        facilities.add(facility);
        System.out.println("Added new facility: " + facility.getFacilityId());
    }

    public void addReferral(Models.Referral referral) {
        referrals.add(referral);
        System.out.println("Added new referral: " + referral.getReferralId());
    }
}