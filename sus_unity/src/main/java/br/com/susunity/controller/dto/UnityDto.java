package br.com.susunity.controller.dto;

import br.com.susunity.model.AddressModel;
import br.com.susunity.model.UnityModel;


import java.util.UUID;


public record UnityDto(

        UUID id,
        String name,
        AddressModel address,
        Integer numberOfPatients,
        Integer numberOfToTalPatients,
        Double porcent
) {
    public UnityDto(UnityModel unityModel) {
        this(unityModel.getId(),
                unityModel.getName(),
                unityModel.getAddress(),
                unityModel.getNumberOfPatients(),
                unityModel.getNumberOfTotalPatients(),
                unityModel.getPorcent());
    }
}
