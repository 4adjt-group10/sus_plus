package br.com.suspatientrecord.queue.producer.dto;

import br.com.suspatientrecord.controller.dto.PatientRecordFormDTO;
import br.com.suspatientrecord.model.PatientRecordModel;

import java.io.Serializable;
import java.util.UUID;

public class RecordToUnity implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID professionalId;
    private UUID unityId;
    private UUID especialityId;

    public RecordToUnity() {
    }


    public RecordToUnity(PatientRecordModel patientRecordModel) {
        this.professionalId = patientRecordModel.getProfessionId();
        this.unityId = patientRecordModel.getUnityId();
        this.especialityId = patientRecordModel.getEspecialityId();
    }

    public UUID getProfessionalId() {
        return professionalId;
    }

    public UUID getUnityId() {
        return unityId;
    }

    public UUID getEspecialityId() {
        return especialityId;
    }
}
