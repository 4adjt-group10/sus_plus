package br.com.susmanager.queue.producer.dto;



import br.com.susmanager.controller.dto.professional.ProfessionalType;
import br.com.susmanager.model.ProfessionalModel;
import br.com.susmanager.model.SpecialityModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MessageBodyForUnity implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID profissionalId;
    private String profissionalName;
    private ProfessionalType type;
    private List<SpecialityDto> specialityDto;
    private boolean isProfessional;
    private UUID unityId;

    public MessageBodyForUnity(UUID profissionalId, String profissionalName, ProfessionalType type, List<SpecialityDto> specialityDto, boolean isProfessional, UUID unityId) {
        this.profissionalId = profissionalId;
        this.profissionalName = profissionalName;
        this.type = type;
        this.specialityDto = specialityDto;
        this.isProfessional = isProfessional;
        this.unityId = unityId;
    }

    public MessageBodyForUnity() {
    }

    public MessageBodyForUnity(ProfessionalModel professionalModel, UUID idUnity) {
        this.profissionalId = professionalModel.getId();
        this.profissionalName = professionalModel.getName();
        this.type = professionalModel.getType();
        this.specialityDto = getEspeciality(professionalModel.getSpeciality());
        isProfessional = true;
        unityId = idUnity;
    }

    public MessageBodyForUnity(UUID idUnity) {
        isProfessional = false;
        unityId = idUnity;
    }

    private List<SpecialityDto> getEspeciality(List<SpecialityModel> speciality) {
        List<SpecialityDto> especialityList = new ArrayList<>();
        for (SpecialityModel specialityModel : speciality) {
            SpecialityDto specialityDto = new SpecialityDto(specialityModel);
            especialityList.add(specialityDto);
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

    public List<SpecialityDto> getSpeciality() {
        return specialityDto;
    }

    public Boolean getProfessional() {
        return isProfessional;
    }

    public UUID getUnityId() {
        return unityId;
    }
}
