package br.com.suspatientrecord.repository;

import br.com.suspatientrecord.model.PatientRecordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PatientRecordRepository extends JpaRepository<PatientRecordModel, UUID> {

    List<PatientRecordModel> findAllByUnityIdAndPatientId(UUID unityId, UUID patientId);

    List<PatientRecordModel> findAllByProfessionId(UUID professionalId);
}
