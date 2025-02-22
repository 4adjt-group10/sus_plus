package br.com.susunity.model;

import br.com.susunity.queue.consumer.dto.manager.Speciality;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Speciality")
public class SpecialityModel {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(unique = true)
    private String name;
    @ManyToMany(mappedBy = "speciality",fetch = FetchType.EAGER)
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

    public SpecialityModel(Speciality speciality) {
        this.id = speciality.getId();
        this.name = speciality.getName();
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
