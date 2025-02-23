package br.com.suspatientrecord.service;

import br.com.suspatientrecord.controller.dto.PatientRecordFormDTO;
import br.com.suspatientrecord.model.PatientRecordModel;
import br.com.suspatientrecord.queue.consumer.dto.MessageBodyByIntegrated;
import br.com.suspatientrecord.queue.consumer.dto.MessageBodyByUnity;
import br.com.suspatientrecord.queue.producer.MessageProducer;
import br.com.suspatientrecord.queue.producer.dto.RecordToIntegrated;
import br.com.suspatientrecord.queue.producer.dto.RecordToUnity;
import br.com.suspatientrecord.repository.PatientRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class PatientRecordService {


    private final PatientRecordRepository patientRecordRepository;
    private final MessageProducer messageProducer;
    private final Logger logger = Logger.getLogger(PatientRecordService.class.getName());

    public PatientRecordService(PatientRecordRepository patientRecordRepository, MessageProducer messageProducer) {
        this.patientRecordRepository = patientRecordRepository;
        this.messageProducer = messageProducer;
    }

    public PatientRecordModel createPatientRecord(PatientRecordFormDTO patientRecord) {
        PatientRecordModel patientRecordModel = patientRecordRepository.save(new PatientRecordModel(patientRecord));
        messageProducer.sendToIntegrated(new RecordToIntegrated(patientRecordModel.getPatientId(), patientRecordModel.getId()));
        return patientRecordRepository.save(patientRecordModel);
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
            patientRecordModel.setEspecialityName(message.getSpecilityName());
            patientRecordModel.setProfessionName(message.getProfessionalName());
            patientRecordRepository.save(patientRecordModel);
        }else{
            logger.warning("Unity , professional or speciality not valid, deleting record");
            patientRecordRepository.deleteById(message.getPatientRecordId());
        }
    }
}
