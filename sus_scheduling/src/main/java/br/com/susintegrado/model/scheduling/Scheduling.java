package br.com.susintegrado.model.scheduling;

import br.com.susintegrado.controller.dto.scheduling.SchedulingFormDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.susintegrado.model.scheduling.SchedulingStatus.*;


@Entity
@Table(name = "Scheduling")
public class Scheduling {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "patient_id")
    private UUID patientId;
    @Column(name = "patient_name")
    private String patientName;
    @Column(name = "speciality_id")
    private UUID specialityId;
    @Column(name = "speciality_name")
    private String specialityName;
    @Column(name = "professional_id")
    private UUID professionalId;
    @Column(name = "professional_name")
    private String professionalName;
    @Column(name = "unity_id")
    private UUID unityId;
    @Column(name = "unity_name")
    private String unityName;
    private LocalDateTime appointment;
    @Enumerated(EnumType.STRING)
    private SchedulingStatus status;

    @Deprecated(since = "Only for use of frameworks")
    public Scheduling() {
    }

    public Scheduling(SchedulingFormDTO formDTO) {
        this.patientId = formDTO.patientId();
        this.specialityId = formDTO.specialityId();
        this.unityId = formDTO.unityId();
        this.appointment = formDTO.appointment();
        this.status = UNDER_ANALYSIS;
    }

    public Scheduling(UUID patientId,
                      String patientName,
                      UUID specialityId,
                      String specialityName,
                      UUID professionalId,
                      String professionalName,
                      UUID unityId,
                      String unityName,
                      LocalDateTime appointment,
                      SchedulingStatus status) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.specialityId = specialityId;
        this.specialityName = specialityName;
        this.professionalId = professionalId;
        this.professionalName = professionalName;
        this.unityId = unityId;
        this.unityName = unityName;
        this.appointment = appointment;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public UUID getSpecialityId() {
        return specialityId;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public UUID getProfessionalId() {
        return professionalId;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public UUID getUnityId() {
        return unityId;
    }

    public String getUnityName() {
        return unityName;
    }

    public LocalDateTime getAppointment() {
        return appointment;
    }

    public SchedulingStatus getStatus() {
        return status;
    }

    public boolean hasStatus(SchedulingStatus status) {
        return this.status.equals(status);
    }

    public void cancel() {
        this.status = CANCELED;
    }

    public void done() {
        this.status = DONE;
    }

    public void late() {
        this.status = LATE;
    }

    private void reschedule(LocalDateTime newAppointment) {
        this.appointment = newAppointment;
    }
}
