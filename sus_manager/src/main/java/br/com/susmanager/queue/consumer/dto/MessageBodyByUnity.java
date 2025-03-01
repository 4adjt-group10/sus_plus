package br.com.susmanager.queue.consumer.dto;


import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class MessageBodyByUnity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private UUID ProfessionalId;
    private UUID unityId;

    public MessageBodyByUnity(UUID professionalId, UUID unityId) {
        ProfessionalId = professionalId;
        this.unityId = unityId;
    }

    public MessageBodyByUnity() {
    }

    public UUID getProfessionalId() {
        return ProfessionalId;
    }

    public void setProfessionalId(UUID professionalId) {
        ProfessionalId = professionalId;
    }

    public UUID getUnityId() {
        return unityId;
    }

    public void setUnityId(UUID unityId) {
        this.unityId = unityId;
    }

    @Override
    public String toString() {
        return "UnityProfessional{" +
                "ProfessionalId=" + ProfessionalId +
                ", unityId=" + unityId +
                '}';
    }
}
