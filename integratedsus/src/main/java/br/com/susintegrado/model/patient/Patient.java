package br.com.susintegrado.model.patient;

import br.com.susintegrado.controller.dto.patient.PatientFormDTO;
import br.com.susintegrado.model.address.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @Column(name = "document", length = 11, nullable = false, unique = true)
    private String document;
    @OneToOne
    private Address address;
    @Column(name = "patientPhone", length = 14)
    private String phone;
    @Email
    private String email;
    private LocalDateTime createdAt;
    private String gender;
    private LocalDateTime birthdate;
    @Column(name = "sus_id", length = 15)
    private String susId;

    public Patient(PatientFormDTO patientFormDTO) {
        this.id = UUID.randomUUID();
        this.name = patientFormDTO.name();
        this.document = patientFormDTO.document();
        this.createdAt = LocalDateTime.now();
        this.phone = patientFormDTO.phone();
        this.email = patientFormDTO.email().orElse(null);
    }

    public Patient(String name, String document, String phone) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.document = document;
        this.phone = phone;
    }

    public String getSusId() {
        return susId;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public String getGender() {
        return gender;
    }

    @Deprecated(since = "Only for use of frameworks")
    public Patient() {

    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

    public String getPhone() {
        return phone;
    }

    public @Email String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean hasAddess() {
        return this.address != null;
    }

    public void merge(PatientFormDTO patientFormDTO) {
        this.name = patientFormDTO.name();
        this.document = patientFormDTO.document();
        this.phone = patientFormDTO.phone();
        this.email = patientFormDTO.email().orElse(null);
        this.address.merge(patientFormDTO.address());
    }
}
