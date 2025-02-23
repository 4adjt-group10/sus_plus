package br.com.susunity.queue.consumer.dto.patientrecord;

import java.util.UUID;

public class MessageBodyByPatientRecord {
    private UUID patientRecordId;
    private UUID professionalId;
    private UUID unityId;
    private UUID specialityId;

    public MessageBodyByPatientRecord() {
    }

    public MessageBodyByPatientRecord(UUID patientRecordId, UUID professionalId, UUID unityId, UUID specialityId) {
        this.patientRecordId = patientRecordId;
        this.professionalId = professionalId;
        this.unityId = unityId;
        this.specialityId = specialityId;
    }

    public UUID getPatientRecordId() {
        return patientRecordId;
    }

    public UUID getProfessionalId() {
        return professionalId;
    }

    public UUID getUnityId() {
        return unityId;
    }

    public UUID getSpecialityId() {
        return specialityId;
    }
}
