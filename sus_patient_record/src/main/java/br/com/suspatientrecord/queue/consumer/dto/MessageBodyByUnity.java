package br.com.suspatientrecord.queue.consumer.dto;

import java.util.UUID;

public class MessageBodyByUnity {
    private UUID patientRecordId;
    private String professionalName;
    private String unityName;
    private String specilityName;
    private boolean validated;

    public MessageBodyByUnity() {
    }

    public MessageBodyByUnity(UUID patientRecordId, String professionalName, String unityName, String specilityName, boolean validated) {
        this.patientRecordId = patientRecordId;
        this.professionalName = professionalName;
        this.unityName = unityName;
        this.specilityName = specilityName;
        this.validated = validated;
    }

    public UUID getPatientRecordId() {
        return patientRecordId;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public String getUnityName() {
        return unityName;
    }

    public String getSpecilityName() {
        return specilityName;
    }

    public boolean isValidated() {
        return validated;
    }
}
