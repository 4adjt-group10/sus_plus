package br.com.susmanager.controller.dto.professional;

import br.com.susmanager.model.ProfessionalModel;

import java.util.List;
import java.util.UUID;

public record ProfessionalManagerOut(UUID id, String name, String document, ProfessionalType type, List<String> procedures) {

    public ProfessionalManagerOut(ProfessionalModel professional) {
        this(professional.getId(),
                professional.getName(),
                professional.getDocument(),
                professional.getType(),
                professional.getSpecialityNames());
    }
}
