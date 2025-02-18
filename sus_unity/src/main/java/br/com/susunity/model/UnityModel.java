package br.com.susunity.model;

import br.com.susunity.controller.dto.UnityInForm;
import jakarta.persistence.*;

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
    @OneToOne
    private AddressModel address;
    private Integer numberOfPatients;
    private Integer numberOfTotalPatients;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Unity_Professional",
            joinColumns = @JoinColumn(name = "unity_id"),
            inverseJoinColumns = @JoinColumn(name = "professional_id"))
    private List<ProfissionalUnityModel> professional;


    public UnityModel() {
    }

    public UnityModel(UUID id, String name, AddressModel address, Integer numberOfPatients, Integer numberOfTotalPatients, List<ProfissionalUnityModel> professional) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.numberOfPatients = numberOfPatients;
        this.numberOfTotalPatients = numberOfTotalPatients;
        this.professional = professional;
    }

    public UnityModel(String name, AddressModel address, Integer numberOfPatients, Integer numberOfTotalPatients, List<ProfissionalUnityModel> professional) {
        this.name = name;
        this.address = address;
        this.numberOfPatients = numberOfPatients;
        this.numberOfTotalPatients = numberOfTotalPatients;
        this.professional = professional;
    }

    public UnityModel(UnityInForm unityInForm, AddressModel newAddress) {
        this.name = unityInForm.name();
        this.address = newAddress;
        this.numberOfTotalPatients = (unityInForm.numberOfToTalPatients() != null) ? unityInForm.numberOfToTalPatients() : 0;
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

    public List<ProfissionalUnityModel> getProfessional() {
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


    public void merge(UnityInForm unityInForm,AddressModel newAddress) {
        if(!this.name.equals(unityInForm.name())){
            this.name = unityInForm.name();
        }
        this.address = newAddress;
        this.numberOfTotalPatients = unityInForm.numberOfToTalPatients();
    }

    public void setProfessional(ProfissionalUnityModel messageBody) {
        if(Objects.isNull(professional) || professional.isEmpty()) {
            professional = new ArrayList<>();
            professional.add(messageBody);
        }
    }


}
