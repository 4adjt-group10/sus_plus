package br.com.susunity.controller.dto.unity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record UnityInForm(
        @NotBlank String name,
        @NotBlank Integer numberOfToTalPatients,
        @NotNull AddressFormDTO address
) {
}
