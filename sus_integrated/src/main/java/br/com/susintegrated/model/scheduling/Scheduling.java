package br.com.susintegrated.model.scheduling;

import br.com.susintegrated.model.patient.Patient;
import br.com.susintegrated.model.procedure.Procedure;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static br.com.susintegrated.model.scheduling.SchedulingStatus.*;
import static java.time.LocalDateTime.now;

@Entity
@Table(name = "Scheduling")
public class Scheduling {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "procedure_id")
    private Procedure procedure;
//    @ManyToOne
//    @JoinColumn(name = "professional_id")
//    private Professional professional;
    private LocalDateTime appointment;
    @Enumerated(EnumType.STRING)
    private SchedulingStatus status;

    @Deprecated(since = "Only for use of frameworks")
    public Scheduling() {
    }

    public Scheduling(Patient patient,
                      Procedure procedure,
//                      Professional professional,
                      LocalDateTime appointment,
                      SchedulingStatus status) {
        this.id = UUID.randomUUID();
        this.patient = patient;
        this.procedure = procedure;
//        this.professional = professional;
        this.appointment = appointment;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Procedure getProcedure() {
        return procedure;
    }

//    public Professional getProfessional() {
//        return professional;
//    }

    public LocalDateTime getAppointment() {
        return appointment;
    }

    public String getPatientName() {
        return patient.getName();
    }

    public String getProcedureName() {
        return procedure.getName();
    }

//    public String getProfessionalName() {
//        return professional.getName();
//    }

    public SchedulingStatus getStatus() {
        return status;
    }

    public UUID getPatientId() {
        return this.patient.getId();
    }

    public String getAppointmentFormated() {
        return appointment.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
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
        if (this.appointment.isAfter(now().plusHours(6))) {
            this.appointment = newAppointment;
        } else {
//            throw new RescheduleException("It is not possible to reschedule an appointment with less than 6 hours in advance.");
        }
    }

    public void merge(Procedure procedure, SchedulingStatus status) {
        this.procedure = procedure;
        this.status = status;
    }

    public void merge(Procedure procedure, LocalDateTime appointment, SchedulingStatus status) {
        reschedule(appointment);
        merge(procedure, status);
    }
}
