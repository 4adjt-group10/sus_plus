package br.com.susunity.repository;

import br.com.susunity.model.ProfessionalUnityModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfessionalRepository  extends JpaRepository<ProfessionalUnityModel, UUID> {

    Optional<ProfessionalUnityModel> findByProfessionalId(UUID profissionalId);

    Optional<ProfessionalUnityModel> findByProfessionalIdAndUnity_id(UUID profissionalId, UUID uuid);
}
