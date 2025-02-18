package br.com.susunity.repository;

import br.com.susunity.model.ProfissionalUnityModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfessionalRepository  extends JpaRepository<ProfissionalUnityModel, UUID> {
    Optional<ProfissionalUnityModel> findByProfissionalId(UUID profissionalId);
}
