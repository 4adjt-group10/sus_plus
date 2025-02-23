package br.com.suspatientrecord.queue.producer.dto;

import br.com.suspatientrecord.controller.dto.PatientRecordProfessionalForm;
import br.com.susunity.controller.dto.UnityProfessionalForm;

import java.io.Serializable;
import java.util.UUID;

public class PatientRecordProfessional implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID ProfessionalId;
    private UUID patientRecordId;

    public PatientRecordProfessional(UUID professionalId, UUID unityId) {
        ProfessionalId = professionalId;
        this.patientRecordId = unityId;
    }

    public PatientRecordProfessional() {
    }

    public PatientRecordProfessional(PatientRecordProfessionalForm unityProfessionalForm) {
        this.ProfessionalId = unityProfessionalForm.ProfessionalId();
        this.patientRecordId = unityProfessionalForm.patientRecordId();
    }

    public UUID getProfessionalId() {
        return ProfessionalId;
    }

    public void setProfessionalId(UUID professionalId) {
        ProfessionalId = professionalId;
    }

    public UUID getUnityId() {
        return patientRecordId;
    }

    public void setUnityId(UUID unityId) {
        this.patientRecordId = patientRecordId;
    }
}
