package br.com.suspatientrecord.repository;

import br.com.suspatientrecord.model.PatientRecordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRecordRepository extends JpaRepository<PatientRecordModel, UUID> {

    Optional<PatientRecordModel> getPatientRecordByUnityIdAndPatientId(UUID unityId, UUID patientId);

    Optional<List<PatientRecordModel>> getAllPatientRecordByProfessionalId(UUID professionalId);
}
