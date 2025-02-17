package br.com.suspatientrecord.service;

import br.com.suspatientrecord.model.PatientRecord;
import br.com.suspatientrecord.repository.PatientRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PatientRecordService {

    @Autowired
    private PatientRecordRepository patientRecordRepository;

    public PatientRecord createPatientRecord(PatientRecord patientRecord) {
        return patientRecordRepository.save(patientRecord);
    }

    public PatientRecord getPatientRecordById(UUID id) {
        Optional<PatientRecord> patientRecord = patientRecordRepository.findById(id);
        return patientRecord.orElse(null);
    }

    public List<PatientRecord> getAllPatientRecords() {
        return patientRecordRepository.findAll();
    }

    public PatientRecord updatePatientRecord(UUID id, PatientRecord patientRecord) {
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
}
