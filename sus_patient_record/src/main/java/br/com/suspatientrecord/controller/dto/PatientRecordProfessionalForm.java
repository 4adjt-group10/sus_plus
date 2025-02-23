package br.com.suspatientrecord.controller.dto;

import java.util.UUID;

public record PatientRecordProfessionalForm(UUID ProfessionalId, UUID patientRecordId) {
}