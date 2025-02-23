package br.com.suspatientrecord.queue.consumer.dto;

import java.util.UUID;

public class MessageBodyByUnity {
    private UUID patientRecordId;
    private String professionalName;
    private String unityName;
    private String specilityName;
    private boolean isValidated;

    public MessageBodyByUnity() {
    }

    public MessageBodyByUnity(UUID patientRecordId, String professionalName, String unityName, String specilityName, boolean isValidated) {
        this.patientRecordId = patientRecordId;
        this.professionalName = professionalName;
        this.unityName = unityName;
        this.specilityName = specilityName;
        this.isValidated = isValidated;
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
        return isValidated;
    }
}
