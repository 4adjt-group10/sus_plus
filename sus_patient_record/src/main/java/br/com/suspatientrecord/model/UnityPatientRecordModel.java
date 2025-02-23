package br.com.suspatientrecord.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "UNITY")
public class UnityPatientRecordModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(unique = true,name = "name", nullable = false)
    private String name;

    @OneToOne
    private AddressModel address;

    private Integer numberOfPatients;

    private Integer numberOfTotalPatients;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Unity_Professional",
            joinColumns = @JoinColumn(name = "unity_id"),
            inverseJoinColumns = @JoinColumn(name = "professional_id"))
    private List<ProfissionalPatientRecordModel> professional;

    public UnityPatientRecordModel() {
    }

    public UnityPatientRecordModel(UUID id, String name, AddressModel address, Integer numberOfPatients, Integer numberOfTotalPatients, List<ProfissionalPatientRecordModel> professional) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.numberOfPatients = numberOfPatients;
        this.numberOfTotalPatients = numberOfTotalPatients;
        this.professional = professional;
    }

    public UnityPatientRecordModel(String name, AddressModel address, Integer numberOfPatients, Integer numberOfTotalPatients, List<ProfissionalPatientRecordModel> professional) {
        this.name = name;
        this.address = address;
        this.numberOfPatients = numberOfPatients;
        this.numberOfTotalPatients = numberOfTotalPatients;
        this.professional = professional;
    }


    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AddressModel getAddress() {
        return address;
    }

    public Integer getNumberOfPatients() {
        return numberOfPatients;
    }

    public Integer getNumberOfTotalPatients() {
        return numberOfTotalPatients;
    }

    public List<ProfissionalPatientRecordModel> getProfessional() {
        return professional;
    }

    public void inPatiente(Integer numberOfPatients) {
        this.numberOfPatients = this.numberOfPatients + numberOfPatients;
    }

    public void outPatiente(Integer numberOfPatients) {
        if(Objects.nonNull(this.numberOfPatients) && this.numberOfPatients > numberOfPatients) {
            this.numberOfPatients = this.numberOfPatients - numberOfPatients;
        }
    }

    public void remove(ProfissionalPatientRecordModel profissionalUnityModel) {
        professional.remove(profissionalUnityModel);
    }
}
