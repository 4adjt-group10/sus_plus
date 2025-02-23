package br.com.suspatientrecord.queue.consumer.dto;

import java.util.UUID;

public class MessageBodyByIntegrated {
    private UUID patientRecordId;
    private String patientName;
    private boolean isValidated;

    public MessageBodyByIntegrated(UUID patientRecordId, String patientName, boolean isValidated) {
        this.patientRecordId = patientRecordId;
        this.patientName = patientName;
        this.isValidated = isValidated;
    }

    public UUID getPatientRecordId() {
        return patientRecordId;
    }

    public String getPatientName() {
        return patientName;
    }

    public boolean isValidated() {
        return isValidated;
    }
}
