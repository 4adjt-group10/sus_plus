package br.com.susunity.queue.consumer.dto.manager;

import br.com.susunity.model.ProfessionalType;

import java.util.List;
import java.util.UUID;

public class Professional {
    private UUID profissionalId;
    private String profissionalName;
    private ProfessionalType type;
    private List<Speciality> speciality;
    private boolean professionalValidated;
    private UUID unityId;

    public Professional(UUID profissionalId,
                        String profissionalName,
                        ProfessionalType type,
                        List<Speciality> speciality,
                        Boolean professionalValidated,
                        UUID unityId) {
        this.profissionalId = profissionalId;
        this.profissionalName = profissionalName;
        this.type = type;
        this.speciality = speciality;
        this.professionalValidated = professionalValidated;
        this.unityId = unityId;
    }



    public Professional() {
    }


    public Professional(UUID idUnity) {
        professionalValidated = false;
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

    public boolean isValidProfessional() {
        return professionalValidated;
    }

    public UUID getUnityId() {
        return unityId;
    }
}
