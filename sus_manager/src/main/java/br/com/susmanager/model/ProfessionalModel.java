package br.com.susmanager.model;

import br.com.susmanager.controller.dto.professional.ProfessionalCreateForm;
import br.com.susmanager.controller.dto.professional.ProfessionalType;
import jakarta.persistence.*;

import java.util.*;

@Table
@Entity(name = "Professional")
public class ProfessionalModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;
    private String unity;
    @Column(name = "document", nullable = false, unique = true)
    private String document;
    @OneToOne
    private AddressModel address;
    @Enumerated(EnumType.STRING)
    private ProfessionalType type;
    @ManyToMany
    @JoinTable(
            name = "Professional_Speciality",
            joinColumns = @JoinColumn(name = "professional_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private List<SpecialityModel> speciality;
    @OneToMany(mappedBy = "professional")
    private List<ProfessionalAvailabilityModel> availability;

    @Deprecated(since = "Only for framework use")
    public ProfessionalModel() {
    }

    public ProfessionalModel(UUID id,
                             String name,
                             String unity,
                             String document,
                             AddressModel address,
                             ProfessionalType type,
                             List<SpecialityModel> speciality,
                             List<ProfessionalAvailabilityModel> availability) {
        this.id = id;
        this.name = name;
        this.unity = unity;
        this.document = document;
        this.address = address;
        this.type = type;
        this.speciality = speciality;
        this.availability = availability;
    }

    public ProfessionalModel(ProfessionalCreateForm form, List<SpecialityModel> specialities) {
        this.name = form.name();
        this.unity = form.unity();
        this.document = form.document();
        this.address = new AddressModel(form.address());
        this.type = form.type();
        this.speciality = specialities;

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUnity() {
        return unity;
    }

    public String getDocument() {
        return document;
    }

    public AddressModel getAddress() {
        return address;
    }

    public ProfessionalType getType() {
        return type;
    }

    public List<SpecialityModel> getSpeciality() {
        return speciality;
    }

    public List<ProfessionalAvailabilityModel> getAvailability() {
        return availability;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public void merge(ProfessionalCreateForm professionalFormDTO, List<SpecialityModel> specialties) {
        this.name = professionalFormDTO.name();
        this.document = professionalFormDTO.document();
        this.type = professionalFormDTO.type();
        this.address.merge(professionalFormDTO.address());
        this.speciality = specialties;
    }
    public List<String> getSpecialityNames() {
        return Optional.ofNullable(speciality)
                .map(procedureList -> procedureList.stream().map(SpecialityModel::getName).toList())
                .orElse(List.of());
    }

    public void addSpeciality(SpecialityModel speciality) {
        this.speciality.add(speciality);
    }
}
