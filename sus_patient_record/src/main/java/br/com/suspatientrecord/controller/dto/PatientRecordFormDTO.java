package br.com.suspatientrecord.controller.dto;

import java.util.UUID;

public record PatientRecordFormDTO(UUID patientID, UUID professionalID, UUID unityID, UUID especialityId, String observation) {
}
