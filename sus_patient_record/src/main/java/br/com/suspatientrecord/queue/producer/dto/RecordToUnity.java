package br.com.suspatientrecord.queue.producer.dto;

import br.com.suspatientrecord.model.PatientRecordModel;

import java.io.Serializable;
import java.util.UUID;

public class RecordToUnity implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID patientRecordId;
    private UUID professionalId;
    private UUID unityId;
    private UUID specialityId;

    public RecordToUnity() {
    }


    public RecordToUnity(UUID patientRecordId, UUID professionalId, UUID unityId, UUID specialityId) {
        this.patientRecordId = patientRecordId;
        this.professionalId = professionalId;
        this.unityId = unityId;
        this.specialityId = specialityId;
    }

    public RecordToUnity(PatientRecordModel patientRecordModel) {
        this.patientRecordId = patientRecordModel.getId();
        this.professionalId = patientRecordModel.getProfessionId();
        this.unityId = patientRecordModel.getUnityId();
        this.specialityId = patientRecordModel.getSpecialityId();
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
