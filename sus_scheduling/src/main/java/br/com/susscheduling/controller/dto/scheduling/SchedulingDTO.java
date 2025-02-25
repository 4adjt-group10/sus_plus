package br.com.susscheduling.controller.dto.scheduling;

import br.com.susscheduling.model.scheduling.Scheduling;
import br.com.susscheduling.model.scheduling.SchedulingStatus;

import java.util.UUID;

public record SchedulingDTO(
        UUID id,
        String appointment,
        SchedulingStatus status
) {

    public SchedulingDTO(Scheduling scheduling) {
        this(scheduling.getId(),
                scheduling.getAppointment().toString(),
                scheduling.getStatus());
    }

}
