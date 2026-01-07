package com.hospital.models;

public class Referral {
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

    public String getReferralId() { return referralId; }
    public String getPatientId() { return patientId; }
    public String getReferringClinician() { return referringClinician; }
    public String getReferringClinicianId() { return referringClinician; }
    public String getReferredToSpecialist() { return referredToSpecialist; }
    public String getReferralReason() { return referralReason; }
    public String getReferralDate() { return referralDate; }
    public String getPriorityLevel() { return priorityLevel; }
    public String getUrgencyLevel() { return priorityLevel; }
    public String getStatus() { return status; }
    public String getNotes() { return notes; }
    public String getCreatedDate() { return referralDate; }
    public String getReferringFacilityId() { return ""; }
    
    public void setStatus(String status) { this.status = status; }
}