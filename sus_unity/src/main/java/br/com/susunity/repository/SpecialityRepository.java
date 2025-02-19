package br.com.susunity.repository;

import br.com.susunity.model.SpecialityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpecialityRepository  extends JpaRepository<SpecialityModel, UUID> {
}
