package com.hospital.models;

public class Patient {
    private String patientId;
    private String firstName;
    private String lastName;
    private String gender;
    private String dateOfBirth;
    private String phoneNumber;
    private String email;
    private String address;
    private String bloodGroup;
    private String emergencyContact;
    private String registrationDate;
    private String insuranceProvider;

    public Patient(String patientId, String firstName, String lastName, String gender, String dateOfBirth,
                   String phoneNumber, String email, String address, String bloodGroup, String emergencyContact,
                   String registrationDate, String insuranceProvider) {
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
    public String getFullName() { return firstName + " " + lastName; }
    public String getGender() { return gender; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getBloodGroup() { return bloodGroup; }
    public String getEmergencyContact() { return emergencyContact; }
    public String getRegistrationDate() { return registrationDate; }
    public String getInsuranceProvider() { return insuranceProvider; }
}
