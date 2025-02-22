package br.com.susmanager.model;

import br.com.susmanager.controller.dto.professional.ProfessionalAvailabilityFormDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ProfessionalAvailability")
public class ProfessionalAvailabilityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professional_id")
    private ProfessionalModel professional;

    private LocalDateTime availableTime;

    public ProfessionalAvailabilityModel(ProfessionalModel professional, LocalDateTime availableTime) {
        this.id = UUID.randomUUID();
        this.professional = professional;
        this.availableTime = availableTime;
    }

    @Deprecated(since = "Only for use of frameworks")
    public ProfessionalAvailabilityModel() {
    }

    public UUID getId() {
        return id;
    }

    public ProfessionalModel getProfessional() {
        return professional;
    }

    public LocalDateTime getAvailableTime() {
        return availableTime;
    }

    public String getProfessionalName() {
        return professional.getName();
    }

    public void merge(ProfessionalAvailabilityFormDTO formDTO) {
        this.availableTime = formDTO.availableTime();
    }
}
