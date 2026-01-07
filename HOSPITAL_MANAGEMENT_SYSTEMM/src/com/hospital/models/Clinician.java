package com.hospital.models;

import java.time.LocalDate;
import java.time.Year;

public class Clinician {
    private String clinicianId;
    private String firstName;
    private String lastName;
    private String title;
    private String speciality;
    private String gmcNumber;
    private String phoneNumber;
    private String email;
    private int workplaceId;
    private String workplaceType;
    private String employmentStatus;
    private String startDate;
    private String availabilityStatus;

    public Clinician(String clinicianId, String firstName, String lastName, String title, 
                    String speciality, String gmcNumber, String phoneNumber, String email,
                    int workplaceId, String workplaceType, String employmentStatus, String startDate) {
        this.clinicianId = clinicianId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.speciality = speciality;
        this.gmcNumber = gmcNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.workplaceId = workplaceId;
        this.workplaceType = workplaceType;
        this.employmentStatus = employmentStatus;
        this.startDate = startDate;
        this.availabilityStatus = "Available";
    }

    // Getters
    public String getClinicianId() { return clinicianId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getTitle() { return title; }
    public String getSpeciality() { return speciality; }
    public String getGmcNumber() { return gmcNumber; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public int getWorkplaceId() { return workplaceId; }
    public String getWorkplaceType() { return workplaceType; }
    public String getEmploymentStatus() { return employmentStatus; }
    public String getStartDate() { return startDate; }
    public String getAvailabilityStatus() { return availabilityStatus; }
    
    public void setAvailabilityStatus(String status) { this.availabilityStatus = status; }
    
    // Calculated methods
    public int getYearsOfExperience() {
        try {
            if (startDate == null || startDate.isEmpty()) {
                return 0;
            }
            int startYear = Integer.parseInt(startDate.substring(0, 4));
            int currentYear = Year.now().getValue();
            return currentYear - startYear;
        } catch (Exception e) {
            return 0;
        }
    }
    
    public String getEmploymentType() {
        return employmentStatus != null ? employmentStatus : "Full-time";
    }
}
