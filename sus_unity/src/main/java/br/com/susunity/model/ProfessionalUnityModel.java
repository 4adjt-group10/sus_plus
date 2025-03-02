package br.com.susunity.model;

import br.com.susunity.queue.consumer.dto.manager.Professional;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name ="Professional")
public class ProfessionalUnityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private UUID professionalId;

    private String professionalName;

    @Enumerated(EnumType.STRING)
    private ProfessionalType type;

    @ManyToMany(mappedBy = "professional",fetch = FetchType.EAGER)
    private List<UnityModel> unity;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Professional_Speciality",
            joinColumns = @JoinColumn(name = "professional_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private List<SpecialityModel> speciality;

    @OneToMany(mappedBy = "professional",fetch = FetchType.EAGER)
    private List<ProfessionalAvailabilityModel> availability;

    public ProfessionalUnityModel() {
    }

    public ProfessionalUnityModel(UUID id,
                                  UUID professionalId,
                                  String professionalName,
                                  ProfessionalType type,
                                  List<UnityModel> unity,
                                  List<SpecialityModel> speciality,
                                  List<ProfessionalAvailabilityModel> availability) {
        this.id = id;
        this.professionalId = professionalId;
        this.professionalName = professionalName;
        this.type = type;
        this.unity = unity;
        this.speciality = speciality;
        this.availability = availability;
    }

    public ProfessionalUnityModel(UUID professionalId,
                                  String professionalName,
                                  ProfessionalType type,
                                  List<UnityModel> unity,
                                  List<SpecialityModel> speciality,
                                  List<ProfessionalAvailabilityModel> availability) {
        this.professionalId = professionalId;
        this.professionalName = professionalName;
        this.type = type;
        this.unity = unity;
        this.speciality = speciality;
        this.availability = availability;
    }

    public ProfessionalUnityModel(Professional messageBody, UnityModel unityIn, List<SpecialityModel> specialityModels) {
        this.professionalId = messageBody.getProfissionalId();
        this.professionalName = messageBody.getProfissionalName();
        this.type = messageBody.getType();
        if(Objects.isNull(this.unity)) {
            this.unity = new ArrayList<>();
            unity.add(unityIn);
        }else{
            this.unity.add(unityIn);
        }
    }

    public ProfessionalUnityModel(Professional messageBody) {
        this.professionalId = messageBody.getProfissionalId();
        this.professionalName = messageBody.getProfissionalName();
        this.type = messageBody.getType();
    }


    public UUID getId() {
        return id;
    }

    public UUID getProfessionalId() {
        return professionalId;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public ProfessionalType getType() {
        return type;
    }

    public List<UnityModel> getUnity() {
        return unity;
    }

    public List<SpecialityModel> getSpeciality() {
        return speciality;
    }

    public List<ProfessionalAvailabilityModel> getAvailability() {
        return availability;
    }

    public void setSpeciality(List<SpecialityModel> speciality) {
        this.speciality = speciality;
    }

    public boolean validateAppointment(LocalDateTime appointment) {
        return this.availability.stream().anyMatch(a -> a.getAvailableTime().equals(appointment));
    }
}
