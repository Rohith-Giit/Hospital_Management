package com.example.hospital;

import java.time.LocalDate;

// Model class representing a patient
// Stores demographic and contact information
public class Patient {

    // Unique identifier for the patient
    private Long id;

    // Patient's full name
    private String name;

    // Patient's date of birth
    private LocalDate dateOfBirth;

    // Contact phone number or email
    private String contactInformation;

    // Default constructor
    public Patient() {
    }

    // Parameterized constructor
    public Patient(Long id, String name, LocalDate dateOfBirth, String contactInformation) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.contactInformation = contactInformation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }
}