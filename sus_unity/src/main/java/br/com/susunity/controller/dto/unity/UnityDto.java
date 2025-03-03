package br.com.susunity.controller.dto.unity;

import br.com.susunity.controller.dto.professional.ProfessionalOut;
import br.com.susunity.model.Address;
import br.com.susunity.model.UnityModel;
import com.fasterxml.jackson.annotation.JsonInclude;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UnityDto(

        UUID id,
        String name,
        Address address,
        Integer numberOfPatients,
        Integer supportedPatients,
        BigDecimal occupancyPercentage,
        List<ProfessionalOut> professionals
) {
    public UnityDto(UnityModel unityModel,List<ProfessionalOut> professionals) {
        this(unityModel.getId(),
                unityModel.getName(),
                unityModel.getAddress(),
                unityModel.getNumberOfPatients(),
                unityModel.getSupportedPatients(),
                calculatePercentage(unityModel.getNumberOfPatients(),
                        unityModel.getSupportedPatients()),
                professionals);
    }

    public UnityDto(UnityModel unityModel) {
        this(unityModel.getId(),
                unityModel.getName(),
                unityModel.getAddress(),
                unityModel.getNumberOfPatients(),
                unityModel.getSupportedPatients(),
                calculatePercentage(unityModel.getNumberOfPatients(),
                        unityModel.getSupportedPatients()),
                new ArrayList<>());
    }


    private static BigDecimal calculatePercentage(Integer numberOfPatients, Integer numberOfToTalPatients) {
        if (numberOfToTalPatients == null || numberOfToTalPatients.equals(0)) {
            return new BigDecimal(0);
        }
        double valor = ((double) ((numberOfPatients != null) ? numberOfPatients : 0 ) / numberOfToTalPatients) * 100;
        BigDecimal bd = new BigDecimal(Double.toString(valor));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd ;
    }
}
