package br.com.suspatientrecord.service;

import br.com.suspatientrecord.controller.dto.PatientRecordFormDTO;
import br.com.suspatientrecord.controller.dto.PatientRecordOutDTO;
import br.com.suspatientrecord.model.PatientRecordModel;
import br.com.suspatientrecord.queue.consumer.dto.MessageBodyByIntegrated;
import br.com.suspatientrecord.queue.consumer.dto.MessageBodyByUnity;
import br.com.suspatientrecord.queue.producer.MessageProducer;
import br.com.suspatientrecord.queue.producer.dto.RecordToIntegrated;
import br.com.suspatientrecord.queue.producer.dto.RecordToUnity;
import br.com.suspatientrecord.repository.PatientRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PatientRecordService {


    private final PatientRecordRepository patientRecordRepository;
    private final MessageProducer messageProducer;
    private final Logger logger = Logger.getLogger(PatientRecordService.class.getName());

    public PatientRecordService(PatientRecordRepository patientRecordRepository, MessageProducer messageProducer) {
        this.patientRecordRepository = patientRecordRepository;
        this.messageProducer = messageProducer;
    }

    public PatientRecordOutDTO createPatientRecord(PatientRecordFormDTO patientRecord) {
        PatientRecordModel patientRecordModel = patientRecordRepository.save(new PatientRecordModel(patientRecord));
        messageProducer.sendToIntegrated(new RecordToIntegrated(patientRecordModel.getPatientId(), patientRecordModel.getId()));
        return new PatientRecordOutDTO(patientRecordModel);
    }



    public void updatePatientRecoedByIntegrated(MessageBodyByIntegrated message) {

        if(message.isValidated()){
            PatientRecordModel patientRecordModel = patientRecordRepository.findById(message.getPatientRecordId()).
                    orElseThrow(EntityNotFoundException::new);
            patientRecordModel.setPatientName(message.getPatientName());
            patientRecordRepository.save(patientRecordModel);
            messageProducer.sendToUnity(new RecordToUnity(patientRecordModel));
        }else{
            logger.warning("Patient record not validated, deleting record");
            patientRecordRepository.deleteById(message.getPatientRecordId());
        }

    }

    public void updatePatientRecoedByUnity(MessageBodyByUnity message) {
        if(message.isValidated()){
            PatientRecordModel patientRecordModel = patientRecordRepository.findById(message.getPatientRecordId()).
                    orElseThrow(EntityNotFoundException::new);
            patientRecordModel.setUnityName(message.getUnityName());
            patientRecordModel.setSpecialityName(message.getSpecilityName());
            patientRecordModel.setProfessionName(message.getProfessionalName());
            patientRecordRepository.save(patientRecordModel);
        }else{
            logger.warning("Unity , professional or speciality not valid, deleting record");
            patientRecordRepository.deleteById(message.getPatientRecordId());
        }
    }

    public PatientRecordOutDTO getPatientRecordById(UUID id) {
        PatientRecordModel patientRecordModel = patientRecordRepository.findById(id).
                orElseThrow(EntityNotFoundException::new);
        return new PatientRecordOutDTO(patientRecordModel);

    }

    public List<PatientRecordOutDTO> getPatientRecordByUnityIdAndPatientId(UUID unityId, UUID patientId) {
        List<PatientRecordModel> patientRecords = patientRecordRepository.findAllByUnityIdAndPatientId(unityId, patientId);
        return patientRecords.stream()
                .map(PatientRecordOutDTO::new)
                .collect(Collectors.toList());
    }

    public List<PatientRecordOutDTO> getAllPatientRecordByProfessionalId(UUID professionalId) {
        List<PatientRecordModel> patientRecords = patientRecordRepository.findAllByProfessionId(professionalId);
        return patientRecords.stream()
                .map(PatientRecordOutDTO::new)
                .collect(Collectors.toList());
    }
}
