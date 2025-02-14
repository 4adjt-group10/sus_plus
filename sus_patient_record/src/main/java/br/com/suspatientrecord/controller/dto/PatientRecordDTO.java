package br.com.suspatientrecord.controller.dto;

import java.util.UUID;

public record PatientRecordDTO(
    UUID id,
    String patientName,
    String professionalName,
    String specialityName,
    String unityName,
    String patientRecord) {

    public PatientRecordDTO(UUID id, String patientName, String professionalName, String specialityName, String unityName, String patientRecord) {
            this.id = id;
            this.patientName = patientName;
            this.professionalName = professionalName;
            this.specialityName = specialityName;
            this.unityName = unityName;
            this.patientRecord = patientRecord;
        }

}


