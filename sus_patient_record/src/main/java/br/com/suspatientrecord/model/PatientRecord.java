package br.com.suspatientrecord.model;

import java.util.UUID;

public class PatientRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "patient_id")
    private UUID patientId;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "professional_id")
    private UUID professionalId;

    @Column(name = "professional_name")
    private String professionalName;

    @Column(name = "unity_id")
    private UUID unityId;

    @Column(name = "unity_name")
    private String unityName;

    @Column(name = "patient_record")
    private String patientRecord;

    public PatientRecord(UUID id, UUID patientId, UUID professionalId, String professionalName, UUID unityId, String unityName, String patientRecord) {
        this.id = id;
        this.patientId = patientId;
        this.professionalId = professionalId;
        this.professionalName = professionalName;
        this.unityId = unityId;
        this.unityName = unityName;
        this.patientRecord = patientRecord;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public UUID getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(UUID professionalId) {
        this.professionalId = professionalId;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }

    public UUID getUnityId() {
        return unityId;
    }

    public void setUnityId(UUID unityId) {
        this.unityId = unityId;
    }

    public String getUnityName() {
        return unityName;
    }

    public void setUnityName(String unityName) {
        this.unityName = unityName;
    }

    public String getPatientRecord() {
        return patientRecord;
    }

    public void setPatientRecord(String patientRecord) {
        this.patientRecord = patientRecord;
    }

    public String getPatientName() {  return patientName;   }

    public void setPatientName(String patientName) { this.patientName = patientName;  }
}