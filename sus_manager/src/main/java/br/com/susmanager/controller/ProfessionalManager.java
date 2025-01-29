package br.com.susmanager.controller;

import br.com.susmanager.controller.dto.professional.ProfessionalCreateForm;
import br.com.susmanager.controller.dto.professional.ProfessionalManagerOut;
import br.com.susmanager.service.ProfessionalManagerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/manager/professional")
public class ProfessionalManager {


    private final ProfessionalManagerService professionalManagerService;

    public ProfessionalManager(ProfessionalManagerService professionalManagerService) {
        this.professionalManagerService = professionalManagerService;
    }

    @GetMapping("/findall")
    public ResponseEntity<List<ProfessionalManagerOut>> findAll() {
        return ResponseEntity.ok(professionalManagerService.listAllProfessionals());
    }

    @GetMapping("/find/{professionalId}")
    public ResponseEntity<ProfessionalManagerOut> find(@PathVariable UUID professionalId) {
        return ResponseEntity.ok(professionalManagerService.findById(professionalId));
    }

    @PostMapping("/create")
    public ResponseEntity<ProfessionalManagerOut> create(@RequestBody @Valid ProfessionalCreateForm form) {
        return ResponseEntity.status(CREATED).body(professionalManagerService.register(form));
    }

    @PutMapping("/alter/{professionalId}")
    public void alter(@PathVariable UUID professionalId, @RequestBody ProfessionalCreateForm form) {
        professionalManagerService.update(professionalId,form);
    }

    @DeleteMapping("/delete/{professionalId}")
    public void delete(@PathVariable UUID professionalId) {
        professionalManagerService.deleById(professionalId);
    }

}
