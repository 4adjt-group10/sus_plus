package br.com.odontoflow.application.patient;

import br.com.odontoflow.domain.patient.PatientRecord;
import br.com.odontoflow.application.professional.ProfessionalDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record PatientRecordDTO(
        UUID id,
        String description,
        LocalDateTime date,
        PatientDTO patient,
        ProfessionalDTO professional
) {

    public PatientRecordDTO(PatientRecord patientRecord) {
        this(patientRecord.getId(),
                patientRecord.getDescription(),
                patientRecord.getDate(),
                new PatientDTO(patientRecord.getPatient()),
                new ProfessionalDTO(patientRecord.getProfessional()));
    }
}
