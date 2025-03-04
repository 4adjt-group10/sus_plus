package br.com.susunity.queue.consumer.dto.manager;

import br.com.susunity.model.ProfessionalType;

import java.util.List;
import java.util.UUID;

public record MessageBodyByManager(UUID professionalId,
        String professionalName,
        ProfessionalType type,
        List<Speciality> speciality,
        boolean professionalValidated,
        UUID unityId) {
}
