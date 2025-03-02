package br.com.suspatientrecord.controller;

import br.com.suspatientrecord.controller.dto.PatientRecordFormDTO;
import br.com.suspatientrecord.controller.dto.PatientRecordOutDTO;
import br.com.suspatientrecord.service.PatientRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patient-records")
public class PatientRecordController {


    private final PatientRecordService patientRecordService;

    public PatientRecordController(PatientRecordService patientRecordService) {
        this.patientRecordService = patientRecordService;
    }

    @PostMapping
    public ResponseEntity<PatientRecordOutDTO> createPatientRecord(@RequestBody PatientRecordFormDTO patientRecord) {
        return ResponseEntity.ok(patientRecordService.createPatientRecord(patientRecord));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientRecordOutDTO> getPatientRecordById(@PathVariable UUID id) {
        return ResponseEntity.ok(patientRecordService.getPatientRecordById(id));
    }

    @GetMapping("/unity/{unityId}/patient/{patientId}")
    public ResponseEntity<List<PatientRecordOutDTO>> getPatientRecordByUnityIdAndPatientId(@PathVariable UUID unityId, @PathVariable UUID patientId) {
        return ResponseEntity.ok(patientRecordService.getPatientRecordByUnityIdAndPatientId(unityId, patientId));

    }

    @GetMapping("/professional/{professionalId}")
    public ResponseEntity<List<PatientRecordOutDTO>> getAllPatientRecordByProfessionalId(@PathVariable UUID professionalId) {
        return ResponseEntity.ok(patientRecordService.getAllPatientRecordByProfessionalId(professionalId));
    }

}
