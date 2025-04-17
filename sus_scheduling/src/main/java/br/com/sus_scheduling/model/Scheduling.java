package br.com.sus_scheduling.model;

import br.com.sus_scheduling.controller.dto.scheduling.SchedulingFormDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.sus_scheduling.model.SchedulingStatus.*;


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
    
    // Campos de auditoria
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;
    
    @Column(name = "updated_by")
    private String updatedBy;
    
    @Version
    private Long version;

    @Deprecated(since = "Only for use of frameworks")
    public Scheduling() {
    }

    public Scheduling(SchedulingFormDTO formDTO, String createdBy) {
        this.patientId = formDTO.patientId();
        this.specialityId = formDTO.specialityId();
        this.unityId = formDTO.unityId();
        this.professionalId = formDTO.professionalId();
        this.appointment = formDTO.appointment();
        this.status = UNDER_ANALYSIS;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.createdBy = createdBy;
        this.updatedBy = createdBy;
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
                      SchedulingStatus status,
                      String createdBy) {
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
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.createdBy = createdBy;
        this.updatedBy = createdBy;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Long getVersion() {
        return version;
    }

    public boolean hasStatus(SchedulingStatus status) {
        return this.status.equals(status);
    }

    public void cancel(String updatedBy) {
        this.status = CANCELED;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = updatedBy;
    }

    public void done(String updatedBy) {
        this.status = DONE;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = updatedBy;
    }

    public void late(String updatedBy) {
        this.status = LATE;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = updatedBy;
    }

    public void approve(String updatedBy) {
        this.status = SCHEDULED;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = updatedBy;
    }
}
