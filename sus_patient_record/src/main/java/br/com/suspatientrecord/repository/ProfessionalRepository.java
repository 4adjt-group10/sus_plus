package br.com.suspatientrecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfessionalRepository  extends JpaRepository<ProfissionalUnityModel, UUID> {
    Optional<ProfissionalUnityModel> findByProfissionalId(UUID profissionalId);

    Optional<ProfissionalUnityModel> findByProfissionalIdAndUnityId(UUID profissionalId, UUID uuid);
}
