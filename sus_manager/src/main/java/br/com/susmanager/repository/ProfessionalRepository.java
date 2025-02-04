package br.com.susmanager.repository;

import br.com.odontoflow.domain.professional.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, UUID> {

    Optional<Professional> findByDocument(String document);
}
