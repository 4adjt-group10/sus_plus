package br.com.susunity.model;

import br.com.susunity.controller.dto.unity.UnityInForm;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "UNITY")
public class UnityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(unique = true,name = "name", nullable = false)
    private String name;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Address address;

    private Integer numberOfPatients;

    private Integer supportedPatients;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Unity_Professional",
            joinColumns = @JoinColumn(name = "unity_id"),
            inverseJoinColumns = @JoinColumn(name = "professional_id"))
    private List<ProfessionalUnityModel> professional;

    @Deprecated(since = "Only for use of frameworks")
    public UnityModel() {
    }

    public UnityModel(UUID id,
                      String name,
                      Address address,
                      Integer numberOfPatients,
                      Integer supportedPatients,
                      List<ProfessionalUnityModel> professional) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.numberOfPatients = numberOfPatients;
        this.supportedPatients = supportedPatients;
        this.professional = professional;
    }

    public UnityModel(UnityInForm unityInForm) {
        this.name = unityInForm.name();
        this.address = new Address(unityInForm.address());
        this.supportedPatients = (unityInForm.supportedPatients() != null) ? unityInForm.supportedPatients() : 0;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Integer getNumberOfPatients() {
        return numberOfPatients;
    }

    public Integer getSupportedPatients() {
        return supportedPatients;
    }

    public List<ProfessionalUnityModel> getProfessional() {
        return professional;
    }

    public void inPatient(Integer numberOfPatients) {
        this.numberOfPatients = this.numberOfPatients == null ? numberOfPatients : this.numberOfPatients + numberOfPatients;
    }

    public void outPatient(Integer numberOfPatients) {
        if(Objects.nonNull(this.numberOfPatients) && this.numberOfPatients > numberOfPatients) {
            this.numberOfPatients = this.numberOfPatients - numberOfPatients;
        }
    }

    public void merge(UnityInForm unityInForm) {
        if(!this.name.equals(unityInForm.name())){
            this.name = unityInForm.name();
        }
        this.address.merge(unityInForm.address());
        this.supportedPatients = unityInForm.supportedPatients();
    }

    public void setProfessional(ProfessionalUnityModel messageBody) {
        if(Objects.isNull(professional) || professional.isEmpty()) {
            professional = new ArrayList<>();
            professional.add(messageBody);
        } else if(!validateProfessional(messageBody.getProfessionalId())) {
            professional.add(messageBody);
        }
    }

    public boolean validateProfessional(UUID professionalId) {
        return this.professional
                .stream()
                .anyMatch(p -> p.getProfessionalId().equals(professionalId));
    }

    public void remove(ProfessionalUnityModel professionalUnityModel) {
        professional.remove(professionalUnityModel);
    }
}
