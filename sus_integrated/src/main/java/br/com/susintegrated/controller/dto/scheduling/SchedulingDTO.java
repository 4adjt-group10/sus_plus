package br.com.susintegrated.controller.dto.scheduling;

import br.com.susintegrated.model.scheduling.Scheduling;
import br.com.susintegrated.model.scheduling.SchedulingStatus;

import java.util.UUID;

public record SchedulingDTO(
        UUID id,
        String patientName,
        String procedureName,
        String appointment,
        SchedulingStatus status
) {

    public SchedulingDTO(Scheduling scheduling) {
        this(scheduling.getId(),
                scheduling.getPatientName(),
                scheduling.getProcedureName(),
                scheduling.getAppointmentFormated(),
                scheduling.getStatus());
    }

}
