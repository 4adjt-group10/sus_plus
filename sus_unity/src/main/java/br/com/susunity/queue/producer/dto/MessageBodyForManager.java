package br.com.susunity.queue.producer.dto;

import br.com.susunity.controller.dto.professional.UnityProfessionalForm;

import java.io.Serializable;
import java.util.UUID;

public class MessageBodyForManager implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID professionalId;
    private UUID unityId;

    public MessageBodyForManager(UUID professionalId, UUID unityId) {
        this.professionalId = professionalId;
        this.unityId = unityId;
    }

    public MessageBodyForManager() {
    }

    public MessageBodyForManager(UnityProfessionalForm unityProfessionalForm) {
        this.professionalId = unityProfessionalForm.ProfessionalId();
        this.unityId = unityProfessionalForm.unityId();
    }

    public UUID getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(UUID professionalId) {
        this.professionalId = professionalId;
    }

    public UUID getUnityId() {
        return unityId;
    }

    public void setUnityId(UUID unityId) {
        this.unityId = unityId;
    }
}
