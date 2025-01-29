package br.com.susmanager.repository;

import br.com.susmanager.model.ProfessionalAvailabilityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ProfessionalAvailabilityRepository extends JpaRepository<ProfessionalAvailabilityModel, UUID> {
    List<ProfessionalAvailabilityModel> findByProfessionalId(UUID professionalId);

    @Query("SELECT pa FROM ProfessionalAvailabilityModel pa WHERE CAST(pa.availableTime AS date) = :date")
    List<ProfessionalAvailabilityModel> findByAvailableByDate(@Param("date") LocalDate date);

    /*
     * Retorna as disponibilidades de um profissional em um determinado dia da semana.
     * Considere Domingo = 1 e sábado = 7.
     * */
    @Query("SELECT pa FROM ProfessionalAvailabilityModel pa WHERE FUNCTION('DAY_OF_WEEK', pa.availableTime) = :dayOfWeek")
    List<ProfessionalAvailabilityModel> findByAvailableTimeByDayOfWeek(@Param("dayOfWeek") Integer dayOfWeek);

    /*
     * Retorna as disponibilidades de um profissional em um determinado horário do dia.
     * Considere 0 a 23 horas.
     * */
    @Query("SELECT pa FROM ProfessionalAvailabilityModel pa WHERE FUNCTION('HOUR', pa.availableTime) = :hour ORDER BY pa.availableTime ASC")
    List<ProfessionalAvailabilityModel> findByAvailableByHour(@Param("hour") Integer hour);

    List<ProfessionalAvailabilityModel> findAllByAvailableTimeBefore(LocalDateTime now);

    Optional<ProfessionalAvailabilityModel> findByProfessionalIdAndAvailableTime(UUID professionalId, LocalDateTime date);

    Collection<ProfessionalAvailabilityModel> findAllByProfessional_Speciality_id(UUID id);
}
