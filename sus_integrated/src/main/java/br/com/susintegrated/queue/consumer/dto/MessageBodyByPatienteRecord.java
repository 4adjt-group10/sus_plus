package br.com.susintegrated.queue.consumer.dto;

import java.util.UUID;

public class MessageBodyByPatienteRecord {
    private UUID patientId;
    private UUID patienteRecordId;

    public MessageBodyByPatienteRecord() {
    }

    public MessageBodyByPatienteRecord(UUID patientId, UUID patienteRecordId) {
        this.patientId = patientId;
        this.patienteRecordId = patienteRecordId;
    }


    public UUID getPatientId() {
        return patientId;
    }

    public UUID getPatienteRecordId() {
        return patienteRecordId;
    }
}
