package br.com.suspatientrecord.controller.dto;

import br.com.suspatientrecord.model.PatientRecordModel;

import java.util.UUID;

public record PatientRecordOutDTO(
    UUID id,
    String patientName,
    String professionalName,
    String specialityName,
    String unityName,
    String patientRecord) {


    public PatientRecordOutDTO(PatientRecordModel patientRecordModel) {
        this(patientRecordModel.getId(),
                patientRecordModel.getPatientName(),
                patientRecordModel.getProfessionName(),
                patientRecordModel.getSpecialityName(),
                patientRecordModel.getUnityName(),
                patientRecordModel.getDescription());
    }
}


