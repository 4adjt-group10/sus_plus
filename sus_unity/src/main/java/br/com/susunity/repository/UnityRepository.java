package br.com.susunity.repository;

import br.com.susunity.model.UnityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UnityRepository extends JpaRepository<UnityModel, UUID> {
    Optional<UnityModel> findByname(String name);
}
