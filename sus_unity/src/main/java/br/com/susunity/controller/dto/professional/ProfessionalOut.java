package br.com.susunity.controller.dto.professional;


import br.com.susunity.model.ProfessionalType;
import br.com.susunity.model.ProfessionalUnityModel;

import java.util.List;
import java.util.UUID;

public record ProfessionalOut(UUID professionalUnityId, String name, ProfessionalType type, List<String> Speciality) {

    public ProfessionalOut(ProfessionalUnityModel professional, List<String> Speciality) {
        this(professional.getId(),
                professional.getProfessionalName(),
                professional.getType(),
                Speciality);
    }

}
