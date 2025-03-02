package br.com.susintegrated.model;

import br.com.susintegrated.controller.dto.patient.PatientFormDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Address address;

    @Column(name = "patientPhone", length = 14)
    private String phone;

    @Email
    private String email;

    private LocalDateTime createdAt;

    public Patient(PatientFormDTO patientFormDTO) {
        this.name = patientFormDTO.name();
        this.document = patientFormDTO.document();
        this.createdAt = LocalDateTime.now();
        this.phone = patientFormDTO.phone();
        this.email = patientFormDTO.email().orElse(null);
        this.address = new Address(patientFormDTO.address());
    }

    public Patient(String name, String document, String phone) {
        this.name = name;
        this.document = document;
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public void merge(PatientFormDTO patientFormDTO) {
        this.name = patientFormDTO.name();
        this.document = patientFormDTO.document();
        this.phone = patientFormDTO.phone();
        this.email = patientFormDTO.email().orElse(null);
        this.address.merge(patientFormDTO.address());
    }
}
