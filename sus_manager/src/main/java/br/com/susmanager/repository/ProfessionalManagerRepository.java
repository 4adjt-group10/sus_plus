package br.com.susmanager.repository;

import br.com.susmanager.model.ProfessionalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ProfessionalManagerRepository extends JpaRepository<ProfessionalModel, UUID> {

    Optional<ProfessionalModel> findByDocument(String document);

}
