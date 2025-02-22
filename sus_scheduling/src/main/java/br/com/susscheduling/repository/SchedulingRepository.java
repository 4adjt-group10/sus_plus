package br.com.susscheduling.repository;

import br.com.susscheduling.model.scheduling.Scheduling;
import br.com.susscheduling.model.scheduling.SchedulingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface SchedulingRepository extends JpaRepository<Scheduling, UUID> {

    List<Scheduling> findAllByPatientId(UUID patientId);

    List<Scheduling> findAllByProfessionalId(UUID id);

    @Query(value = "SELECT * FROM Scheduling s WHERE CAST(s.appointment AS DATE) = :date", nativeQuery = true)
    List<Scheduling> findAllByAppointmentsToDay(@Param("date") LocalDate date);

    List<Scheduling> findAllByStatus(SchedulingStatus status);

    List<Scheduling> findAllByAppointmentBetweenAndStatusIn(LocalDateTime now, LocalDateTime next24Hours, List<SchedulingStatus> statusList);

    @Query(value = "SELECT * FROM Scheduling s WHERE s.patient_id =:id and CAST(s.appointment AS DATE) = :date", nativeQuery = true)
    List<Scheduling> findAllByPatientIdAndDate(UUID id, LocalDate date);

    @Query(value = "SELECT * FROM Scheduling s WHERE s.professional_id =:id and CAST(s.appointment AS DATE) = :date", nativeQuery = true)
    List<Scheduling> findAllByProfessionalIdAndDate(UUID id, LocalDate date);
}
