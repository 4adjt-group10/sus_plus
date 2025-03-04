package br.com.susunity.model;

import br.com.susunity.controller.dto.professional.ProfessionalAvailabilityFormDTO;
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
    private ProfessionalUnityModel professional;

    @Column(name = "unity_id")
    private UUID unityId;

    private LocalDateTime availableTime;

    public ProfessionalAvailabilityModel(ProfessionalUnityModel professional, UUID unityId, LocalDateTime availableTime) {
        this.id = UUID.randomUUID();
        this.professional = professional;
        this.availableTime = availableTime;
        this.unityId = unityId;
    }

    @Deprecated(since = "Only for use of frameworks")
    public ProfessionalAvailabilityModel() {
    }

    public UUID getId() {
        return id;
    }

    public ProfessionalUnityModel getProfessional() {
        return professional;
    }

    public LocalDateTime getAvailableTime() {
        return availableTime;
    }

    public String getProfessionalName() {
        return professional.getProfessionalName();
    }

    public UUID getUnityId() {
        return unityId;
    }

    public void merge(ProfessionalAvailabilityFormDTO formDTO) {
        this.availableTime = formDTO.availableTime();
    }
}
