package br.com.suspatientrecord.model;

import br.com.susunity.queue.consumer.dto.Professional;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name ="Professional")
public class ProfissionalPatientRecordModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private UUID profissionalId;

    private String profissionalName;

    @Enumerated(EnumType.STRING)
    private ProfessionalType type;

    @ManyToMany(mappedBy = "professional",fetch = FetchType.EAGER)
    private List<UnityPatientRecordModel> unity;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Professional_Speciality",
            joinColumns = @JoinColumn(name = "professional_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private List<SpecialityModel> speciality;

    public ProfissionalPatientRecordModel() {
    }

    public ProfissionalPatientRecordModel(UUID id, UUID profissionalId, String profissionalName, ProfessionalType type, List<UnityPatientRecordModel> unity, List<SpecialityModel> speciality) {
        this.id = id;
        this.profissionalId = profissionalId;
        this.profissionalName = profissionalName;
        this.type = type;
        this.unity = unity;
        this.speciality = speciality;
    }

    public ProfissionalPatientRecordModel(UUID profissionalId, String profissionalName, ProfessionalType type, List<UnityPatientRecordModel> unity, List<SpecialityModel> speciality) {
        this.profissionalId = profissionalId;
        this.profissionalName = profissionalName;
        this.type = type;
        this.unity = unity;
        this.speciality = speciality;
    }



    public ProfissionalPatientRecordModel(Professional messageBody, UnityPatientRecordModel unityIn, List<SpecialityModel> specialityModels) {
        this.profissionalId = messageBody.getProfissionalId();
        this.profissionalName = messageBody.getProfissionalName();
        this.type = messageBody.getType();
        if(Objects.isNull(this.unity)) {
            this.unity = new ArrayList<>();
            unity.add(unityIn);
        }else{
            this.unity.add(unityIn);
        }
    }

    public ProfissionalPatientRecordModel(Professional messageBody) {
        this.profissionalId = messageBody.getProfissionalId();
        this.profissionalName = messageBody.getProfissionalName();
        this.type = messageBody.getType();

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

    public List<UnityPatientRecordModel> getUnity() {
        return unity;
    }

    public List<SpecialityModel> getSpeciality() {
        return speciality;
    }

    public void setSpeciality(List<SpecialityModel> speciality) {
        this.speciality = speciality;
    }
}
