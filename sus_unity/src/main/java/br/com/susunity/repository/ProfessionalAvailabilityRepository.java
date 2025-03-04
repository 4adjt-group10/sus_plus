package br.com.susunity.repository;

import br.com.susunity.model.ProfessionalAvailabilityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessionalAvailabilityRepository extends JpaRepository<ProfessionalAvailabilityModel, UUID> {

    List<ProfessionalAvailabilityModel> findByProfessionalId(UUID professionalId);

    @Query("SELECT pa FROM ProfessionalAvailabilityModel pa WHERE CAST(pa.availableTime AS date) = :date AND pa.unityId = :unityId")
    List<ProfessionalAvailabilityModel> findByAvailableByDateAndUnityId(@Param("date") LocalDate date, @Param("unityId") UUID unityId);

    List<ProfessionalAvailabilityModel> findAllByAvailableTimeBefore(LocalDateTime now);

    Optional<ProfessionalAvailabilityModel> findByProfessionalIdAndAvailableTime(UUID professionalId, LocalDateTime date);

    List<ProfessionalAvailabilityModel> findAllByProfessional_Speciality_id(UUID specialityId);

    void deleteByProfessionalIdAndAvailableTimeAndUnityId(UUID professionalId, LocalDateTime availableTime, UUID unityId);

    default void deleteByProfessionalAndUnity(UUID professionalId, LocalDateTime availableTime, UUID unityId) {
        deleteByProfessionalIdAndAvailableTimeAndUnityId(professionalId, availableTime, unityId);
    }
}