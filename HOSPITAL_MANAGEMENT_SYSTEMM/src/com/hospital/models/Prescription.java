package com.hospital.models;

public class Prescription {
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

    public String getPrescriptionId() { return prescriptionId; }
    public String getPatientId() { return patientId; }
    public String getClinicianId() { return clinicianId; }
    public String getDrugName() { return drugName; }
    public String getDosage() { return dosage; }
    public String getFrequency() { return frequency; }
    public int getDurationDays() { return durationDays; }
    public String getInstructions() { return instructions; }
    public String getPrescribedDate() { return prescribedDate; }
    public String getPrescriptionDate() { return prescribedDate; }
    public String getIssueDate() { return prescribedDate; }
    public String getStatus() { return status; }
    
    public void setStatus(String status) { this.status = status; }
}
