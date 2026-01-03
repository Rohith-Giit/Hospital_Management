public class Models {

    // Appointment Model
    public static class Appointment {
        private String appointmentId;
        private String patientId;
        private String clinicianId;
        private String facilityId;
        private String appointmentDate;
        private String appointmentTime;
        private int durationMinutes;
        private String appointmentType;
        private String status;
        private String reasonForVisit;
        private String notes;
        private String createdDate;
        private String lastModified;

        public Appointment(String appointmentId, String patientId, String clinicianId, String facilityId,
                         String appointmentDate, String appointmentTime, int durationMinutes, String appointmentType,
                         String status, String reasonForVisit, String notes, String createdDate, String lastModified) {
            this.appointmentId = appointmentId;
            this.patientId = patientId;
            this.clinicianId = clinicianId;
            this.facilityId = facilityId;
            this.appointmentDate = appointmentDate;
            this.appointmentTime = appointmentTime;
            this.durationMinutes = durationMinutes;
            this.appointmentType = appointmentType;
            this.status = status;
            this.reasonForVisit = reasonForVisit;
            this.notes = notes;
            this.createdDate = createdDate;
            this.lastModified = lastModified;
        }

        // Getters
        public String getAppointmentId() { return appointmentId; }
        public String getPatientId() { return patientId; }
        public String getClinicianId() { return clinicianId; }
        public String getFacilityId() { return facilityId; }
        public String getAppointmentDate() { return appointmentDate; }
        public String getAppointmentTime() { return appointmentTime; }
        public int getDurationMinutes() { return durationMinutes; }
        public String getAppointmentType() { return appointmentType; }
        public String getStatus() { return status; }
        public String getReasonForVisit() { return reasonForVisit; }
        public String getNotes() { return notes; }
        public String getCreatedDate() { return createdDate; }
        public String getLastModified() { return lastModified; }

        // Setters
        public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }
        public void setPatientId(String patientId) { this.patientId = patientId; }
        public void setClinicianId(String clinicianId) { this.clinicianId = clinicianId; }
        public void setFacilityId(String facilityId) { this.facilityId = facilityId; }
        public void setAppointmentDate(String appointmentDate) { this.appointmentDate = appointmentDate; }
        public void setAppointmentTime(String appointmentTime) { this.appointmentTime = appointmentTime; }
        public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
        public void setAppointmentType(String appointmentType) { this.appointmentType = appointmentType; }
        public void setStatus(String status) { this.status = status; }
        public void setReasonForVisit(String reasonForVisit) { this.reasonForVisit = reasonForVisit; }
        public void setNotes(String notes) { this.notes = notes; }
        public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
        public void setLastModified(String lastModified) { this.lastModified = lastModified; }
    }

    // Clinician Model
    public static class Clinician {
        private String clinicianId;
        private String firstName;
        private String lastName;
        private String specialization;
        private long phoneNumber;
        private String email;
        private String facilityId;
        private String availabilityStatus;
        private int yearsOfExperience;
        private String employmentType;
        private String createdDate;
        private String lastUpdated;

        public Clinician(String clinicianId, String firstName, String lastName, String specialization,
                        long phoneNumber, String email, String facilityId, String availabilityStatus,
                        int yearsOfExperience, String employmentType, String createdDate, String lastUpdated) {
            this.clinicianId = clinicianId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.specialization = specialization;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.facilityId = facilityId;
            this.availabilityStatus = availabilityStatus;
            this.yearsOfExperience = yearsOfExperience;
            this.employmentType = employmentType;
            this.createdDate = createdDate;
            this.lastUpdated = lastUpdated;
        }

        // Getters
        public String getClinicianId() { return clinicianId; }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getSpecialization() { return specialization; }
        public long getPhoneNumber() { return phoneNumber; }
        public String getEmail() { return email; }
        public String getFacilityId() { return facilityId; }
        public String getAvailabilityStatus() { return availabilityStatus; }
        public int getYearsOfExperience() { return yearsOfExperience; }
        public String getEmploymentType() { return employmentType; }
        public String getCreatedDate() { return createdDate; }
        public String getLastUpdated() { return lastUpdated; }

        // Setters
        public void setClinicianId(String clinicianId) { this.clinicianId = clinicianId; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public void setSpecialization(String specialization) { this.specialization = specialization; }
        public void setPhoneNumber(long phoneNumber) { this.phoneNumber = phoneNumber; }
        public void setEmail(String email) { this.email = email; }
        public void setFacilityId(String facilityId) { this.facilityId = facilityId; }
        public void setAvailabilityStatus(String availabilityStatus) { this.availabilityStatus = availabilityStatus; }
        public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }
        public void setEmploymentType(String employmentType) { this.employmentType = employmentType; }
        public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
        public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }
    }

    // Facility Model
    public static class Facility {
        private String facilityId;
        private String facilityName;
        private String facilityType;
        private String address;
        private String city;
        private String state;
        private int postalCode;
        private long phoneNumber;
        private String email;
        private int capacity;
        private String status;
        private String createdDate;

        public Facility(String facilityId, String facilityName, String facilityType, String address,
                       String city, String state, int postalCode, long phoneNumber, String email,
                       int capacity, String status, String createdDate) {
            this.facilityId = facilityId;
            this.facilityName = facilityName;
            this.facilityType = facilityType;
            this.address = address;
            this.city = city;
            this.state = state;
            this.postalCode = postalCode;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.capacity = capacity;
            this.status = status;
            this.createdDate = createdDate;
        }

        // Getters
        public String getFacilityId() { return facilityId; }
        public String getFacilityName() { return facilityName; }
        public String getFacilityType() { return facilityType; }
        public String getAddress() { return address; }
        public String getCity() { return city; }
        public String getState() { return state; }
        public int getPostalCode() { return postalCode; }
        public long getPhoneNumber() { return phoneNumber; }
        public String getEmail() { return email; }
        public int getCapacity() { return capacity; }
        public String getStatus() { return status; }
        public String getCreatedDate() { return createdDate; }

        // Setters
        public void setFacilityId(String facilityId) { this.facilityId = facilityId; }
        public void setFacilityName(String facilityName) { this.facilityName = facilityName; }
        public void setFacilityType(String facilityType) { this.facilityType = facilityType; }
        public void setAddress(String address) { this.address = address; }
        public void setCity(String city) { this.city = city; }
        public void setState(String state) { this.state = state; }
        public void setPostalCode(int postalCode) { this.postalCode = postalCode; }
        public void setPhoneNumber(long phoneNumber) { this.phoneNumber = phoneNumber; }
        public void setEmail(String email) { this.email = email; }
        public void setCapacity(int capacity) { this.capacity = capacity; }
        public void setStatus(String status) { this.status = status; }
        public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
    }

    // Patient Model
    public static class Patient {
        private String patientId;
        private String firstName;
        private String lastName;
        private String gender;
        private String dateOfBirth;
        private long phoneNumber;
        private String email;
        private String address;
        private String bloodGroup;
        private long emergencyContact;
        private String registrationDate;
        private String insuranceProvider;

        public Patient(String patientId, String firstName, String lastName, String gender,
                      String dateOfBirth, long phoneNumber, String email, String address,
                      String bloodGroup, long emergencyContact, String registrationDate, String insuranceProvider) {
            this.patientId = patientId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.gender = gender;
            this.dateOfBirth = dateOfBirth;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.address = address;
            this.bloodGroup = bloodGroup;
            this.emergencyContact = emergencyContact;
            this.registrationDate = registrationDate;
            this.insuranceProvider = insuranceProvider;
        }

        // Getters
        public String getPatientId() { return patientId; }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getGender() { return gender; }
        public String getDateOfBirth() { return dateOfBirth; }
        public long getPhoneNumber() { return phoneNumber; }
        public String getEmail() { return email; }
        public String getAddress() { return address; }
        public String getBloodGroup() { return bloodGroup; }
        public long getEmergencyContact() { return emergencyContact; }
        public String getRegistrationDate() { return registrationDate; }
        public String getInsuranceProvider() { return insuranceProvider; }

        // Setters
        public void setPatientId(String patientId) { this.patientId = patientId; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public void setGender(String gender) { this.gender = gender; }
        public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
        public void setPhoneNumber(long phoneNumber) { this.phoneNumber = phoneNumber; }
        public void setEmail(String email) { this.email = email; }
        public void setAddress(String address) { this.address = address; }
        public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
        public void setEmergencyContact(long emergencyContact) { this.emergencyContact = emergencyContact; }
        public void setRegistrationDate(String registrationDate) { this.registrationDate = registrationDate; }
        public void setInsuranceProvider(String insuranceProvider) { this.insuranceProvider = insuranceProvider; }
    }

    // Prescription Model
    public static class Prescription {
        private String prescriptionId;
        private String patientId;
        private String clinicianId;
        private String drugName;
        private String dosage;
        private String frequency;
        private int durationDays;
        private String instructions;
        private String prescribedDate;
        private String status;

        public Prescription(String prescriptionId, String patientId, String clinicianId, String drugName,
                          String dosage, String frequency, int durationDays, String instructions,
                          String prescribedDate, String status) {
            this.prescriptionId = prescriptionId;
            this.patientId = patientId;
            this.clinicianId = clinicianId;
            this.drugName = drugName;
            this.dosage = dosage;
            this.frequency = frequency;
            this.durationDays = durationDays;
            this.instructions = instructions;
            this.prescribedDate = prescribedDate;
            this.status = status;
        }

        // Getters
        public String getPrescriptionId() { return prescriptionId; }
        public String getPatientId() { return patientId; }
        public String getClinicianId() { return clinicianId; }
        public String getDrugName() { return drugName; }
        public String getDosage() { return dosage; }
        public String getFrequency() { return frequency; }
        public int getDurationDays() { return durationDays; }
        public String getInstructions() { return instructions; }
        public String getPrescribedDate() { return prescribedDate; }
        public String getStatus() { return status; }

        // Setters
        public void setPrescriptionId(String prescriptionId) { this.prescriptionId = prescriptionId; }
        public void setPatientId(String patientId) { this.patientId = patientId; }
        public void setClinicianId(String clinicianId) { this.clinicianId = clinicianId; }
        public void setDrugName(String drugName) { this.drugName = drugName; }
        public void setDosage(String dosage) { this.dosage = dosage; }
        public void setFrequency(String frequency) { this.frequency = frequency; }
        public void setDurationDays(int durationDays) { this.durationDays = durationDays; }
        public void setInstructions(String instructions) { this.instructions = instructions; }
        public void setPrescribedDate(String prescribedDate) { this.prescribedDate = prescribedDate; }
        public void setStatus(String status) { this.status = status; }
    }

    // Referral Model
    public static class Referral {
        private String referralId;
        private String patientId;
        private String referringClinician;
        private String referredToSpecialist;
        private String referralReason;
        private String referralDate;
        private String priorityLevel;
        private String status;
        private String notes;

        public Referral(String referralId, String patientId, String referringClinician,
                       String referredToSpecialist, String referralReason, String referralDate,
                       String priorityLevel, String status, String notes) {
            this.referralId = referralId;
            this.patientId = patientId;
            this.referringClinician = referringClinician;
            this.referredToSpecialist = referredToSpecialist;
            this.referralReason = referralReason;
            this.referralDate = referralDate;
            this.priorityLevel = priorityLevel;
            this.status = status;
            this.notes = notes;
        }

        // Getters
        public String getReferralId() { return referralId; }
        public String getPatientId() { return patientId; }
        public String getReferringClinician() { return referringClinician; }
        public String getReferredToSpecialist() { return referredToSpecialist; }
        public String getReferralReason() { return referralReason; }
        public String getReferralDate() { return referralDate; }
        public String getPriorityLevel() { return priorityLevel; }
        public String getStatus() { return status; }
        public String getNotes() { return notes; }

        // Setters
        public void setReferralId(String referralId) { this.referralId = referralId; }
        public void setPatientId(String patientId) { this.patientId = patientId; }
        public void setReferringClinician(String referringClinician) { this.referringClinician = referringClinician; }
        public void setReferredToSpecialist(String referredToSpecialist) { this.referredToSpecialist = referredToSpecialist; }
        public void setReferralReason(String referralReason) { this.referralReason = referralReason; }
        public void setReferralDate(String referralDate) { this.referralDate = referralDate; }
        public void setPriorityLevel(String priorityLevel) { this.priorityLevel = priorityLevel; }
        public void setStatus(String status) { this.status = status; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    // Staff Model
    public static class Staff {
        private String staffId;
        private String firstName;
        private String lastName;
        private String role;
        private String department;
        private String facilityId;
        private long phoneNumber;
        private String email;
        private String employmentStatus;
        private String startDate;
        private String lineManager;
        private String accessLevel;

        public Staff(String staffId, String firstName, String lastName, String role,
                    String department, String facilityId, long phoneNumber, String email,
                    String employmentStatus, String startDate, String lineManager, String accessLevel) {
            this.staffId = staffId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.role = role;
            this.department = department;
            this.facilityId = facilityId;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.employmentStatus = employmentStatus;
            this.startDate = startDate;
            this.lineManager = lineManager;
            this.accessLevel = accessLevel;
        }

        // Getters
        public String getStaffId() { return staffId; }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getRole() { return role; }
        public String getDepartment() { return department; }
        public String getFacilityId() { return facilityId; }
        public long getPhoneNumber() { return phoneNumber; }
        public String getEmail() { return email; }
        public String getEmploymentStatus() { return employmentStatus; }
        public String getStartDate() { return startDate; }
        public String getLineManager() { return lineManager; }
        public String getAccessLevel() { return accessLevel; }

        // Setters
        public void setStaffId(String staffId) { this.staffId = staffId; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public void setRole(String role) { this.role = role; }
        public void setDepartment(String department) { this.department = department; }
        public void setFacilityId(String facilityId) { this.facilityId = facilityId; }
        public void setPhoneNumber(long phoneNumber) { this.phoneNumber = phoneNumber; }
        public void setEmail(String email) { this.email = email; }
        public void setEmploymentStatus(String employmentStatus) { this.employmentStatus = employmentStatus; }
        public void setStartDate(String startDate) { this.startDate = startDate; }
        public void setLineManager(String lineManager) { this.lineManager = lineManager; }
        public void setAccessLevel(String accessLevel) { this.accessLevel = accessLevel; }
    }
}