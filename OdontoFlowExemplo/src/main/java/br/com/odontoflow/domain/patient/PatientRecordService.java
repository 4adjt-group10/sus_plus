package br.com.odontoflow.domain.patient;

import br.com.odontoflow.application.patient.PatientRecordDTO;
import br.com.odontoflow.application.patient.PatientRecordFormDTO;
import br.com.odontoflow.domain.professional.Professional;
import br.com.odontoflow.infrastructure.patient.PatientRecordRepository;
import br.com.odontoflow.domain.professional.ProfessionalService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientRecordService {

    private final PatientRecordRepository patientRecordRepository;
    private final PatientService patientService;
    private final ProfessionalService professionalService;

    public PatientRecordService(PatientRecordRepository patientRecordRepository,
                                PatientService patientService,
                                ProfessionalService professionalService) {
        this.patientRecordRepository = patientRecordRepository;
        this.patientService = patientService;
        this.professionalService = professionalService;
    }

    public PatientRecordDTO register(PatientRecordFormDTO formDTO){
        Patient patient = patientService.findPatientById(formDTO.patientId());
        Professional professional = professionalService.findProfessionalById(formDTO.professionalId());
        PatientRecord patientRecord = new PatientRecord(formDTO.description(), formDTO.date(), patient, professional);
        patientRecordRepository.save(patientRecord);
        return new PatientRecordDTO(patientRecord);
    }

    @Transactional
    public PatientRecordDTO update(UUID id, PatientRecordFormDTO formDTO){
        PatientRecord patientRecord = patientRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient record not found"));
        patientRecord.merge(formDTO);
        return new PatientRecordDTO(patientRecord);
    }

    public List<PatientRecordDTO> findByPatientId(UUID patientId){
        return patientRecordRepository.findByPatient_Id(patientId).stream().map(PatientRecordDTO::new).toList();
    }

    public List<PatientRecordDTO> findByProfessionalId(UUID professionalId){
        return patientRecordRepository.findByProfessional_Id(professionalId).stream().map(PatientRecordDTO::new).toList();
    }

    public PatientRecordDTO findById(UUID id){
        PatientRecord patientRecord = patientRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient record not found"));
        return new PatientRecordDTO(patientRecord);
    }

    public PatientRecord findLastByPatientId(UUID patientId){
        return patientRecordRepository.findFirstByPatient_IdOrderByDateDesc(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient record not found"));
    }
}
