//package br.com.suspatientrecord.controller;
//
//import br.com.suspatientrecord.controller.dto.PatientRecordFormDTO;
//import br.com.suspatientrecord.controller.dto.PatientRecordOutDTO;
//import br.com.suspatientrecord.model.PatientRecordModel;
//import br.com.suspatientrecord.service.PatientRecordService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/patient-records")
//public class PatientRecordController {
//
//    @Autowired
//    private PatientRecordService patientRecordService;
//
//    @PostMapping
//    public ResponseEntity<PatientRecordOutDTO> createPatientRecord(@RequestBody PatientRecordFormDTO patientRecord) {
//        PatientRecordModel createdPatientRecord = patientRecordService.createPatientRecord(patientRecord);
//        return new ResponseEntity<>(createdPatientRecord, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<PatientRecordOutDTO> getPatientRecordById(@PathVariable UUID id) {
//        PatientRecordModel patientRecord = patientRecordService.getPatientRecordById(id);
//        if (patientRecord != null) {
//            return new ResponseEntity<>(patientRecord, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping("/unity/{unityId}/patient/{patientId}")
//    public ResponseEntity<PatientRecordOutDTO> getPatientRecordByUnityIdAndPatientId(@PathVariable UUID unityId, @PathVariable UUID patientId) {
//        PatientRecordModel patientRecord = patientRecordService.getPatientRecordByUnityIdAndPatientId(unityId, patientId);
//        if (patientRecord != null) {
//            return new ResponseEntity<>(patientRecord, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping("/professional/{professionalId}")
//    public ResponseEntity<List<PatientRecordOutDTO>> getAllPatientRecordByProfessionalId(@PathVariable UUID professionalId) {
//        List<PatientRecordModel> patientRecord = patientRecordService.getAllPatientRecordByProfessionalId(professionalId);
//        if (patientRecord != null) {
//            return new ResponseEntity<>(patientRecord, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping
//    public ResponseEntity<List<PatientRecordOutDTO>> getAllPatientRecords() {
//        List<PatientRecordModel> patientRecords = patientRecordService.getAllPatientRecords();
//        return new ResponseEntity<>(patientRecords, HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<PatientRecordModel> updatePatientRecord(@PathVariable UUID id, @RequestBody PatientRecordModel patientRecord) {
//        PatientRecordModel updatedPatientRecord = patientRecordService.updatePatientRecord(id, patientRecord);
//        if (updatedPatientRecord != null) {
//            return new ResponseEntity<>(updatedPatientRecord, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletePatientRecord(@PathVariable UUID id) {
//        boolean isDeleted = patientRecordService.deletePatientRecord(id);
//        if (isDeleted) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//}
