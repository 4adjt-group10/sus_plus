package br.com.susmanager.controller.dto.professional;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ProfessionalAlterForm(
        @NotBlank String name,
        @NotBlank String document,
        @NotNull AddressFormDTO address,
        @NotNull ProfessionalType type
) {
}
