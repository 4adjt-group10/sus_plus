package br.com.suspatientrecord.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;

import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;

public class PatientRecordModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Patient_Record_Professional",
            joinColumns = @JoinColumn(name = "patient_record_id"),
            inverseJoinColumns = @JoinColumn(name = "professional_id"))
    private List<ProfissionalPatientRecordModel> professional;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Patient_Record_Unity",
            joinColumns = @JoinColumn(name = "patient_record_id"),
            inverseJoinColumns = @JoinColumn(name = "unity_id"))
    private List<UnityPatientRecordModel> unity;

    @OneToOne
    private AddressModel address;

    @Column(name = "patient_record")
    private String patientRecord;

    public PatientRecordModel() {}

    public PatientRecordModel(UUID id, List<ProfissionalPatientRecordModel> professional, List<UnityPatientRecordModel> unity, AddressModel address, String patientRecord) {
        this.id = id;
        this.professional = professional;
        this.unity = unity;
        this.address = address;
        this.patientRecord = patientRecord;
    }

    public PatientRecordModel(List<ProfissionalPatientRecordModel> professional, List<UnityPatientRecordModel> unity, AddressModel address, String patientRecord) {
        this.professional = professional;
        this.unity = unity;
        this.address = address;
        this.patientRecord = patientRecord;
    }


}