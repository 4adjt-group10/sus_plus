package br.com.susintegrated.controller.dto.patient;

import br.com.susintegrated.controller.dto.address.AddressFormDTO;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public record PatientFormDTO(
        @NotBlank String name,
        @NotBlank String document,
        @NotBlank String phone,
        Optional<String> email,
        AddressFormDTO address
) {

}
