package br.com.suspatientrecord.service;

import br.com.suspatientrecord.model.PatientRecordModel;
import br.com.suspatientrecord.repository.PatientRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PatientRecordService {

    @Autowired
    private PatientRecordRepository patientRecordRepository;

    public PatientRecordModel createPatientRecord(PatientRecordModel patientRecord) {
        return patientRecordRepository.save(patientRecord);
    }

    public PatientRecordModel getPatientRecordById(UUID id) {
        Optional<PatientRecordModel> patientRecord = patientRecordRepository.findById(id);
        return patientRecord.orElse(null);
    }

    public List<PatientRecordModel> getAllPatientRecords() {
        return patientRecordRepository.findAll();
    }

    public PatientRecordModel updatePatientRecord(UUID id, PatientRecordModel patientRecord) {
        if (patientRecordRepository.existsById(id)) {
            patientRecord.setId(id);
            return patientRecordRepository.save(patientRecord);
        }
        return null;
    }

    public boolean deletePatientRecord(UUID id) {
        if (patientRecordRepository.existsById(id)) {
            patientRecordRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public PatientRecordModel getPatientRecordByUnityIdAndPatientId(UUID unityId, UUID patientId) {
        Optional<PatientRecordModel> patientRecord = patientRecordRepository.getPatientRecordByUnityIdAndPatientId(unityId, patientId);
        return patientRecord.orElse(null);
    }

    public List<PatientRecordModel> getAllPatientRecordByProfessionalId(UUID professionalId) {
        Optional<List<PatientRecordModel>> patientRecord = patientRecordRepository.getAllPatientRecordByProfessionalId(professionalId);
        return patientRecord.orElse(null);
    }
}
