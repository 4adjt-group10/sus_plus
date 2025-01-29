package br.com.susmanager.repository;

import br.com.susmanager.model.SpecialityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;
@Repository
public interface SpecialityRepository extends JpaRepository<SpecialityModel, UUID> {
    Optional<SpecialityModel> findByName(String name);

    @Query(value = """
            SELECT * FROM Speciality p 
                JOIN Professional_Speciality pp ON p.id = pp.speciality_id
            WHERE p.id = :id 
            AND pp.professional_id = :professionalId
    """, nativeQuery = true)
    Optional<SpecialityModel> findByIdAndProfessionalId(UUID id, UUID professionalId);
}
