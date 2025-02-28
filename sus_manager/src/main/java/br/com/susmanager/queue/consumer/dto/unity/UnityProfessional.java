package br.com.susmanager.queue.consumer.dto.unity;


import java.io.Serializable;
import java.util.UUID;

public class UnityProfessional implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID ProfessionalId;
    private UUID unityId;

    public UnityProfessional(UUID professionalId, UUID unityId) {
        ProfessionalId = professionalId;
        this.unityId = unityId;
    }

    public UnityProfessional() {
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
