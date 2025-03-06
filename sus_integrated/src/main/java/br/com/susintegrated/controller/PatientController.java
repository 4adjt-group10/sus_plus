package br.com.susintegrated.controller;

import br.com.susintegrated.controller.dto.patient.PatientDTO;
import br.com.susintegrated.controller.dto.patient.PatientFormDTO;
import br.com.susintegrated.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/create")
    public ResponseEntity<PatientDTO> register(@RequestBody @Valid PatientFormDTO patientFormDTO) {
        return ResponseEntity.status(CREATED).body(patientService.register(patientFormDTO));
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<PatientDTO> search(@PathVariable("id") UUID patientId) {
        return ResponseEntity.ok(new PatientDTO(patientService.findPatientById(patientId)));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<PatientDTO>> listAll(@Nullable @PageableDefault(size = 20, sort = "name", direction = ASC) Pageable pageable) {
        return ResponseEntity.ok(patientService.listAll(pageable));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PatientDTO> update(@PathVariable("id") UUID id, @RequestBody @Valid PatientFormDTO patientFormDTO) {
        return ResponseEntity.ok(patientService.update(id, patientFormDTO));
    }
}
