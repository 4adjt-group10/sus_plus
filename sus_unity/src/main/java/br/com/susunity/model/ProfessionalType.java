package br.com.susunity.model;

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