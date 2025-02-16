package br.com.susunity.model;

import br.com.susunity.queue.consumer.dto.Professional;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name ="PROFISSIONAL")
public class ProfissionalUnityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private UUID profissionalId;
    private String profissionalName;
    @Enumerated(EnumType.STRING)
    private ProfessionalType type;
    @ManyToMany
    private List<UnityModel> unityModel;
    @ManyToMany
    @JoinTable(
            name = "Professional_Speciality",
            joinColumns = @JoinColumn(name = "professional_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private List<SpecialityModel> speciality;

    public ProfissionalUnityModel() {
    }

    public ProfissionalUnityModel(UUID id, UUID profissionalId, String profissionalName, ProfessionalType type, List<UnityModel> unityModel, List<SpecialityModel> speciality) {
        this.id = id;
        this.profissionalId = profissionalId;
        this.profissionalName = profissionalName;
        this.type = type;
        this.unityModel = unityModel;
        this.speciality = speciality;
    }

    public ProfissionalUnityModel(UUID profissionalId, String profissionalName, ProfessionalType type, List<UnityModel> unityModel, List<SpecialityModel> speciality) {
        this.profissionalId = profissionalId;
        this.profissionalName = profissionalName;
        this.type = type;
        this.unityModel = unityModel;
        this.speciality = speciality;
    }



    public ProfissionalUnityModel(Professional messageBody, UnityModel unity) {
        this.profissionalId = messageBody.getProfissionalId();
        this.profissionalName = messageBody.getProfissionalName();
        this.type = messageBody.getType();
        if(Objects.isNull(this.unityModel)) {
            this.unityModel = new ArrayList<>();
            unityModel.add(unity);
        }else{
            this.unityModel.add(unity);
        }


    }

    public UUID getId() {
        return id;
    }

    public UUID getProfissionalId() {
        return profissionalId;
    }

    public String getProfissionalName() {
        return profissionalName;
    }

    public ProfessionalType getType() {
        return type;
    }

    public List<UnityModel> getUnityModel() {
        return unityModel;
    }

    public List<SpecialityModel> getSpeciality() {
        return speciality;
    }
}
