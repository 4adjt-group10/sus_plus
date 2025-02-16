package br.com.susunity.queue.consumer.dto;

import br.com.susunity.model.ProfessionalType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Professional {
    private UUID profissionalId;
    private String profissionalName;
    private ProfessionalType type;
    private List<Speciality> speciality;
    private Boolean isProfessional;
    private UUID unityId;

    public Professional( UUID profissionalId, String profissionalName, ProfessionalType type, List<Speciality> speciality, Boolean isProfessional, UUID unityId) {
        this.profissionalId = profissionalId;
        this.profissionalName = profissionalName;
        this.type = type;
        this.speciality = speciality;
        this.isProfessional = isProfessional;
        this.unityId = unityId;
    }

    public Professional() {
    }


    public Professional(UUID idUnity) {
        isProfessional = Boolean.FALSE;
        unityId = idUnity;
    }


    public UUID getProfissionalId() {
        return profissionalId;
    }

    public String getProfissionalName() {
        return profissionalName;
    }

    public ProfessionalType getType() {
        return type;
    }

    public List<Speciality> getSpeciality() {
        return speciality;
    }

    public Boolean getProfessional() {
        return isProfessional;
    }

    public UUID getUnityId() {
        return unityId;
    }
}
