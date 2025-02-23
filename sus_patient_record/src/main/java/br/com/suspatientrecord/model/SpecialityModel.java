package br.com.suspatientrecord.model;

import br.com.susunity.queue.consumer.dto.Speciality;
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
    @OneToOne
    private ProfissionalPatientRecordModel professional;

    public SpecialityModel() {
    }

    public SpecialityModel(UUID id, String name, ProfissionalPatientRecordModel professional) {
        this.id = id;
        this.name = name;
        this.professional = professional;
    }

    public SpecialityModel(String name, ProfissionalPatientRecordModel professional) {
        this.name = name;
        this.professional = professional;
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

    public ProfissionalPatientRecordModel getProfessionals() {
        return professional;
    }
}
