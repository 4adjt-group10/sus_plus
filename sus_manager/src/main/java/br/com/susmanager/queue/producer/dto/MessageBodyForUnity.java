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

    private UUID professionalId;
    private String professionalName;
    private ProfessionalType type;
    private List<SpecialityDto> speciality;
    private boolean professionalValidated;
    private UUID unityId;

    public MessageBodyForUnity(UUID professionalId,
                               String professionalName,
                               ProfessionalType type,
                               List<SpecialityDto> speciality,
                               boolean professionalValidated,
                               UUID unityId) {
        this.professionalId = professionalId;
        this.professionalName = professionalName;
        this.type = type;
        this.speciality = speciality;
        this.professionalValidated = professionalValidated;
        this.unityId = unityId;
    }

    public MessageBodyForUnity() {
    }

    public MessageBodyForUnity(ProfessionalModel professionalModel, UUID idUnity) {
        this.professionalId = professionalModel.getId();
        this.professionalName = professionalModel.getName();
        this.type = professionalModel.getType();
        this.speciality = getEspeciality(professionalModel.getSpeciality());
        professionalValidated = true;
        unityId = idUnity;
    }

    public MessageBodyForUnity(UUID idUnity) {
        professionalValidated = false;
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

    public UUID getProfessionalId() {
        return professionalId;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public ProfessionalType getType() {
        return type;
    }

    public List<SpecialityDto> getSpeciality() {
        return speciality;
    }

    public Boolean getProfessionalValidated() {
        return professionalValidated;
    }

    public UUID getUnityId() {
        return unityId;
    }
}
