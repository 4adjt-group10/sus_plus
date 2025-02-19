package br.com.susunity.controller.dto;


import br.com.susunity.model.ProfessionalType;
import br.com.susunity.model.ProfissionalUnityModel;

import java.util.List;
import java.util.UUID;

public record ProfessionalOut(UUID id, String name, ProfessionalType type, List<String> Speciality) {

    public ProfessionalOut(ProfissionalUnityModel professional, List<String> Speciality) {
        this(professional.getId(),
                professional.getProfissionalName(),
                professional.getType(),
                Speciality);
    }

}
