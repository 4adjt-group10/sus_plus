package br.com.sus_scheduling.controller.dto.scheduling;

import br.com.sus_scheduling.model.Scheduling;
import br.com.sus_scheduling.model.SchedulingStatus;

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
