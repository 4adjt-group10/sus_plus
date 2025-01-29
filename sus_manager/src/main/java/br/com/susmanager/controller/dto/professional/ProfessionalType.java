package br.com.susmanager.controller.dto.professional;

public enum ProfessionalType {

    DOCTOR("Medico"),
    NURSE("Enfermeira");

    private final String description;

    ProfessionalType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}