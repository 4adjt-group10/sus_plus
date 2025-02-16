package br.com.susunity.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "Speciality")
public class SpecialityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(unique = true)
    private String name;
    @ManyToMany(mappedBy = "speciality")
    private List<ProfissionalUnityModel> professionals;

    public SpecialityModel() {
    }

    public SpecialityModel(UUID id, String name, List<ProfissionalUnityModel> professionals) {
        this.id = id;
        this.name = name;
        this.professionals = professionals;
    }

    public SpecialityModel(String name, List<ProfissionalUnityModel> professionals) {
        this.name = name;
        this.professionals = professionals;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ProfissionalUnityModel> getProfessionals() {
        return professionals;
    }
}
