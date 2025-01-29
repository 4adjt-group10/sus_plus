package br.com.susmanager.controller.dto.speciality;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record SpecialityForm(
        @NotBlank String name,
        @Nullable List<UUID> professionalsIds
) {

}
