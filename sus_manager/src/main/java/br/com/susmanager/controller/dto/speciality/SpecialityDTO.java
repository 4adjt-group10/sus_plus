package br.com.susmanager.controller.dto.speciality;



import br.com.susmanager.model.SpecialityModel;

import java.util.List;
import java.util.UUID;

public record SpecialityDTO(UUID id, String name, List<String> professionals) {

    public SpecialityDTO(SpecialityModel procedure) {
        this(procedure.getId(), procedure.getName(), procedure.getProfessionalsNames());
    }
}
