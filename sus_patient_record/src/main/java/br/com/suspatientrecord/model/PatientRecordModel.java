package br.com.suspatientrecord.model;

import br.com.suspatientrecord.controller.dto.PatientRecordFormDTO;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;

import java.util.UUID;
import jakarta.persistence.*;
@Entity
@Table(name = "PATIENT_RECORD")
public class PatientRecordModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private UUID patientId;
    private UUID professionId;
    private UUID unityId;
    private UUID specialityId;
    private String specialityName;
    private String description;
    private String patientName;
    private String professionName;
    private String unityName;

    public PatientRecordModel() {
    }

    public PatientRecordModel(UUID patientId, UUID professionId, UUID unityId, UUID specialityId, String specialityName, String description, String patientName, String professionName, String unityName) {
        this.patientId = patientId;
        this.professionId = professionId;
        this.unityId = unityId;
        this.specialityId = specialityId;
        this.specialityName = specialityName;
        this.description = description;
        this.patientName = patientName;
        this.professionName = professionName;
        this.unityName = unityName;
    }

    public PatientRecordModel(PatientRecordFormDTO patientRecord) {
        this.patientId = patientRecord.patientID();
        this.professionId = patientRecord.professionalID();
        this.unityId = patientRecord.unityID();
        this.description = patientRecord.observation();
        this.specialityId = patientRecord.especialityId();

    }

    public UUID getId() {
        return id;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public UUID getProfessionId() {
        return professionId;
    }

    public UUID getUnityId() {
        return unityId;
    }

    public String getDescription() {
        return description;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getProfessionName() {
        return professionName;
    }

    public String getUnityName() {
        return unityName;
    }

    public UUID getSpecialityId() {
        return specialityId;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String especialityName) {
        this.specialityName = especialityName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public void setUnityName(String unityName) {
        this.unityName = unityName;
    }
}