package br.com.suspatientrecord.queue.producer.dto;

import br.com.suspatientrecord.controller.dto.PatientRecordFormDTO;

import java.io.Serializable;
import java.util.UUID;

public class RecordToIntegrated implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID patientId;
    private UUID patienteRecordId;

    public RecordToIntegrated() {
    }

    public RecordToIntegrated(UUID patientId, UUID patienteRecordId) {
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
