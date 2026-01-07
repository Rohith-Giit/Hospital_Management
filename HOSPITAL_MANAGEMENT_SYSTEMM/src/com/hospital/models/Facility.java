package com.hospital.models;

public class Facility {
    private String facilityId;
    private String facilityName;
    private String facilityType;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String phoneNumber;
    private String email;
    private int capacity;
    private String status;
    private String createdDate;

    public Facility(String facilityId, String facilityName, String facilityType, String address,
                    String city, String state, String postalCode, String phoneNumber, String email,
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

    public String getFacilityId() { return facilityId; }
    public String getFacilityName() { return facilityName; }
    public String getFacilityType() { return facilityType; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getPostalCode() { return postalCode; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public int getCapacity() { return capacity; }
    public String getStatus() { return status; }
    public String getCreatedDate() { return createdDate; }
    
    // Setters for update functionality
    public void setFacilityName(String facilityName) { this.facilityName = facilityName; }
    public void setFacilityType(String facilityType) { this.facilityType = facilityType; }
    public void setAddress(String address) { this.address = address; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmail(String email) { this.email = email; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setStatus(String status) { this.status = status; }
}
