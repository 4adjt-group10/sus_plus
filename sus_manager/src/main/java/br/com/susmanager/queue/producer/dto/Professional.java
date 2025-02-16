package br.com.susmanager.queue.producer.dto;



import br.com.susmanager.controller.dto.professional.ProfessionalType;
import br.com.susmanager.model.ProfessionalModel;
import br.com.susmanager.model.SpecialityModel;

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

    public Professional(ProfessionalModel professionalModel, UUID idUnity) {
        this.profissionalId = professionalModel.getId();
        this.profissionalName = professionalModel.getName();
        this.type = professionalModel.getType();
        this.speciality = getEspeciality(professionalModel.getSpeciality());
        isProfessional = Boolean.TRUE;
        unityId = idUnity;
    }

    public Professional(UUID idUnity) {
        isProfessional = Boolean.FALSE;
        unityId = idUnity;
    }

    private List<Speciality> getEspeciality(List<SpecialityModel> speciality) {
        List<Speciality> especialityList = new ArrayList<>();
        for (SpecialityModel specialityModel : speciality) {
            Speciality speciality1 = new Speciality(specialityModel);
            especialityList.add(speciality1);
        }
        return especialityList;
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
