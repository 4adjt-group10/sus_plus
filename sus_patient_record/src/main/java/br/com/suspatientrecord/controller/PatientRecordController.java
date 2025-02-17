package br.com.suspatientrecord.controller;

import br.com.suspatientrecord.model.PatientRecord;
import br.com.suspatientrecord.service.PatientRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patient-records")
public class PatientRecordController {

    @Autowired
    private PatientRecordService patientRecordService;

    @PostMapping
    public ResponseEntity<PatientRecord> createPatientRecord(@RequestBody PatientRecord patientRecord) {
        PatientRecord createdPatientRecord = patientRecordService.createPatientRecord(patientRecord);
        return new ResponseEntity<>(createdPatientRecord, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientRecord> getPatientRecordById(@PathVariable UUID id) {
        PatientRecord patientRecord = patientRecordService.getPatientRecordById(id);
        if (patientRecord != null) {
            return new ResponseEntity<>(patientRecord, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<PatientRecord>> getAllPatientRecords() {
        List<PatientRecord> patientRecords = patientRecordService.getAllPatientRecords();
        return new ResponseEntity<>(patientRecords, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientRecord> updatePatientRecord(@PathVariable UUID id, @RequestBody PatientRecord patientRecord) {
        PatientRecord updatedPatientRecord = patientRecordService.updatePatientRecord(id, patientRecord);
        if (updatedPatientRecord != null) {
            return new ResponseEntity<>(updatedPatientRecord, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientRecord(@PathVariable UUID id) {
        boolean isDeleted = patientRecordService.deletePatientRecord(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
