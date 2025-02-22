package br.com.susscheduling.controller.dto.scheduling;

import br.com.susscheduling.model.scheduling.Scheduling;
import br.com.susscheduling.model.scheduling.SchedulingStatus;

import java.util.UUID;

public record SchedulingDTO(
        UUID id,
        String patientName,
        String professionalName,
        String specialityName,
        String unityName,
        String appointment,
        SchedulingStatus status
) {

    public SchedulingDTO(Scheduling scheduling) {
        this(scheduling.getId(),
                scheduling.getPatientName(),
                scheduling.getProfessionalName(),
                scheduling.getSpecialityName(),
                scheduling.getUnityName(),
                scheduling.getAppointment().toString(),
                scheduling.getStatus());
    }

}
