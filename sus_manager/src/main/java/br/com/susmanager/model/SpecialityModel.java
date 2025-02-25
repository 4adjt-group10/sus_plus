package br.com.susmanager.model;

import br.com.susmanager.controller.dto.speciality.SpecialityForm;
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
    @ManyToMany(mappedBy = "speciality",fetch = FetchType.EAGER)
    private List<ProfessionalModel> professionals;


    public SpecialityModel(SpecialityForm procedureFormDTO, List<ProfessionalModel> professionals) {
        this.id = UUID.randomUUID();
        this.name = procedureFormDTO.name();
        this.professionals = professionals;
    }

    @Deprecated(since = "Only for use of frameworks")
    public SpecialityModel() {

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public List<ProfessionalModel> getProfessionals() {
        return professionals;
    }

    public List<String> getProfessionalsNames() {
        return Optional.ofNullable(professionals)
                .map(professionalList -> professionalList.stream().map(ProfessionalModel::getName).toList())
                .orElse(List.of());
    }

    public void addProfessional(ProfessionalModel professional) {
        professionals.add(professional);
    }

    public void merge(SpecialityForm procedureFormDTO, List<ProfessionalModel> professionals) {
        this.name = procedureFormDTO.name();
        updateProfessional(professionals);
    }

    private void updateProfessional(List<ProfessionalModel> professionals) {
        this.professionals.forEach(p -> {
            professionals.stream()
                    .filter(pm -> pm.getId().equals(p.getId()))
                    .findFirst()
                    .ifPresent(professionals::remove);
        });
        this.professionals.addAll(professionals);
    }
}
