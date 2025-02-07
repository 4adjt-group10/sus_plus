package br.com.susintegrado.controller.dto.patient;

import br.com.susintegrado.model.patient.Patient;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record PatientDTO(UUID id, String name, String document, String phone, String gender,
                         LocalDateTime birthdate,
                         String susId) {

    public PatientDTO(Patient patient) {
        this(patient.getId(), patient.getName(), patient.getDocument(), patient.getPhone(), patient.getGender(),
                patient.getBirthdate(), patient.getSusId());
    }
}
