package br.com.susunity.controller.dto;

import br.com.susunity.model.AddressModel;
import br.com.susunity.model.UnityModel;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;


public record UnityDto(

        UUID id,
        String name,
        AddressModel address,
        Integer numberOfPatients,
        Integer numberOfTotalPatients,
        BigDecimal porcent
) {
    public UnityDto(UnityModel unityModel) {
        this(unityModel.getId(),
                unityModel.getName(),
                unityModel.getAddress(),
                unityModel.getNumberOfPatients(),
                unityModel.getNumberOfTotalPatients(),
                calculatePercent(unityModel.getNumberOfPatients(),
                        unityModel.getNumberOfTotalPatients()));
    }
    private static BigDecimal calculatePercent(Integer numberOfPatients, Integer numberOfToTalPatients) {
        if (numberOfToTalPatients == null || numberOfToTalPatients.equals(0)) {
            return new BigDecimal(0);
        }
        double valor = ((double) ((numberOfPatients != null) ? numberOfPatients : 0 ) / numberOfToTalPatients) * 100;
        BigDecimal bd = new BigDecimal(Double.toString(valor));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd ;
    }
}
