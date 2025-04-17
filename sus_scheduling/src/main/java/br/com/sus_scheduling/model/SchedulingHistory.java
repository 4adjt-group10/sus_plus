package br.com.sus_scheduling.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "scheduling_history")
public class SchedulingHistory {
    @Id
    private String id;
    private UUID schedulingId;
    private UUID patientId;
    private String patientName;
    private UUID specialityId;
    private String specialityName;
    private UUID professionalId;
    private String professionalName;
    private UUID unityId;
    private String unityName;
    private LocalDateTime appointment;
    private SchedulingStatus status;
    private LocalDateTime statusChangeDate;
    private String changeReason;
    private String changedBy;

    public SchedulingHistory() {
    }

    public SchedulingHistory(Scheduling scheduling, String changeReason, String changedBy) {
        this.schedulingId = scheduling.getId();
        this.patientId = scheduling.getPatientId();
        this.patientName = scheduling.getPatientName();
        this.specialityId = scheduling.getSpecialityId();
        this.specialityName = scheduling.getSpecialityName();
        this.professionalId = scheduling.getProfessionalId();
        this.professionalName = scheduling.getProfessionalName();
        this.unityId = scheduling.getUnityId();
        this.unityName = scheduling.getUnityName();
        this.appointment = scheduling.getAppointment();
        this.status = scheduling.getStatus();
        this.statusChangeDate = LocalDateTime.now();
        this.changeReason = changeReason;
        this.changedBy = changedBy;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(UUID schedulingId) {
        this.schedulingId = schedulingId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public UUID getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(UUID specialityId) {
        this.specialityId = specialityId;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public UUID getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(UUID professionalId) {
        this.professionalId = professionalId;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }

    public UUID getUnityId() {
        return unityId;
    }

    public void setUnityId(UUID unityId) {
        this.unityId = unityId;
    }

    public String getUnityName() {
        return unityName;
    }

    public void setUnityName(String unityName) {
        this.unityName = unityName;
    }

    public LocalDateTime getAppointment() {
        return appointment;
    }

    public void setAppointment(LocalDateTime appointment) {
        this.appointment = appointment;
    }

    public SchedulingStatus getStatus() {
        return status;
    }

    public void setStatus(SchedulingStatus status) {
        this.status = status;
    }

    public LocalDateTime getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(LocalDateTime statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }
} 