package br.com.susunity.model;

import br.com.susunity.controller.dto.UnityInForm;
import jakarta.persistence.*;

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
    @Transient
    private Double porcent = calculatePercent();

    public UnityModel() {
    }

    public UnityModel(UUID id, String name, AddressModel address, Integer numberOfPatients, Integer numberOfTotalPatients) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.numberOfPatients = numberOfPatients;
        this.numberOfTotalPatients = numberOfTotalPatients;
    }

    private Double calculatePercent() {
        if(Objects.isNull(this.numberOfTotalPatients) ||  this.numberOfTotalPatients.equals(0)){
            return 0.0;
        }
        return ((double) ((this.numberOfPatients !=null) ? this.numberOfPatients : 0) / this.numberOfTotalPatients) * 100;
    }

    public UnityModel( String name, AddressModel address, Integer numberOfPatients, Integer numberOfTotalPatients) {
        this.name = name;
        this.address = address;
        this.numberOfPatients = numberOfPatients;
        this.numberOfTotalPatients = numberOfTotalPatients;

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

    public void setNumberOfPatients(Integer numberOfPatients) {
        this.numberOfPatients = numberOfPatients;
    }

    public Double getPorcent() {
        return porcent;
    }

    public void merge(UnityInForm unityInForm,AddressModel newAddress) {
        if(!this.name.equals(unityInForm.name())){
            this.name = unityInForm.name();
        }
        this.address = newAddress;
        this.numberOfTotalPatients = unityInForm.numberOfToTalPatients();
    }
}
