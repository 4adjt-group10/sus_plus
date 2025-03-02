package br.com.susintegrated.controller.dto.patient;

import br.com.susintegrated.model.Patient;

import java.util.UUID;

public record PatientDTO(UUID id, String name, String document, String phone) {

    public PatientDTO(Patient patient) {
        this(patient.getId(), patient.getName(), patient.getDocument(), patient.getPhone());
    }
}
