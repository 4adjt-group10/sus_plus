package br.com.sus_scheduling.repository;

import br.com.sus_scheduling.model.SchedulingMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SchedulingMetadataRepository extends MongoRepository<SchedulingMetadata, String> {
    SchedulingMetadata findBySchedulingId(UUID schedulingId);
} 