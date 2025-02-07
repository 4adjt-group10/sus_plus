package br.com.susintegrado.controller.dto.patient;

import br.com.susintegrado.controller.dto.address.AddressFormDTO;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.Optional;

public record PatientFormDTO(
        @NotBlank String name,
        @NotBlank String document,
        @NotBlank String phone,
        Optional<String> email,
        AddressFormDTO address,
        @NotBlank String gender,
        @NotBlank LocalDateTime birthdate,
        @NotBlank String susId
) {

}
