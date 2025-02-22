package br.com.susintegrated.model.patient;

import br.com.susintegrated.controller.dto.patient.PatientRecordDTO;
import br.com.susintegrated.controller.dto.patient.PatientRecordFormDTO;
import br.com.susintegrated.repository.PatientRecordRepository;
import br.com.susintegrated.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientRecordService {

    private final PatientRecordRepository patientRecordRepository;
    private final PatientService patientService;
    //private final ProfessionalService professionalService;

    public PatientRecordService(PatientRecordRepository patientRecordRepository,
                                PatientService patientService) {
        this.patientRecordRepository = patientRecordRepository;
        this.patientService = patientService;
        //this.professionalService = professionalService;
    }

    public PatientRecordDTO register(PatientRecordFormDTO formDTO){
        Patient patient = patientService.findPatientById(formDTO.patientId());
        //Professional professional = professionalService.findProfessionalById(formDTO.professionalId());
        PatientRecord patientRecord = new PatientRecord(formDTO.description(), formDTO.date(), patient);
        patientRecordRepository.save(patientRecord);
        return new PatientRecordDTO(patientRecord);
    }

//    @Transactional
//    public PatientRecordDTO update(UUID id, PatientRecordFormDTO formDTO){
//        PatientRecord patientRecord = patientRecordRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Patient record not found"));
//        patientRecord.merge(formDTO);
//        return new PatientRecordDTO(patientRecord);
//    }

    public List<PatientRecordDTO> findByPatientId(UUID patientId){
        return patientRecordRepository.findByPatient_Id(patientId).stream().map(PatientRecordDTO::new).toList();
    }

//    public List<PatientRecordDTO> findByProfessionalId(UUID professionalId){
//        return patientRecordRepository.findByProfessional_Id(professionalId).stream().map(PatientRecordDTO::new).toList();
//    }

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
