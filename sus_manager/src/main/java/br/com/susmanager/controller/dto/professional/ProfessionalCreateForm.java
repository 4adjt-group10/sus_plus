package br.com.susmanager.controller.dto.professional;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProfessionalCreateForm(
        @NotBlank String name,
        @NotBlank String document,
        @NotBlank String unity,
        @NotNull AddressFormDTO address,
        @NotNull ProfessionalType type,
        @Nullable List<UUID> specialityIds,
        @Nullable List<LocalDateTime> availabilities
) {
}
