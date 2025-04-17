package br.com.sus_scheduling.repository;

import br.com.sus_scheduling.model.SchedulingHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SchedulingHistoryRepository extends MongoRepository<SchedulingHistory, String> {
    List<SchedulingHistory> findBySchedulingId(UUID schedulingId);
    List<SchedulingHistory> findByPatientId(UUID patientId);
    List<SchedulingHistory> findByStatusAndAppointmentBetween(
            br.com.sus_scheduling.model.SchedulingStatus status,
            java.time.LocalDateTime start,
            java.time.LocalDateTime end
    );
} 