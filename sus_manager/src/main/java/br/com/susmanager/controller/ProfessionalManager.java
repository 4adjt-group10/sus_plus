package br.com.susmanager.controller;

import br.com.susmanager.controller.dto.professional.ProfessionalAlterForm;
import br.com.susmanager.controller.dto.professional.ProfessionalCreateForm;
import br.com.susmanager.controller.dto.professional.ProfessionalManagerOut;
import br.com.susmanager.queue.producer.MessageProducer;
import br.com.susmanager.service.ProfessionalManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/manager/professional")
public class ProfessionalManager {

    @Autowired
    MessageProducer producer;

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
    public ResponseEntity<ProfessionalManagerOut> create(@RequestBody ProfessionalCreateForm form) {
        return ResponseEntity.status(CREATED).body(professionalManagerService.register(form));
    }

    @PutMapping("/alter/{professionalId}")
    public void alter(@PathVariable UUID professionalId, @RequestBody ProfessionalAlterForm form) {
        professionalManagerService.update(professionalId,form);
    }

    @PutMapping("/include-speciality/{professionalId}/{idSpeciality}")
    public void include(@PathVariable UUID professionalId, @PathVariable UUID idSpeciality) {
        professionalManagerService.includeSpeciality(professionalId,idSpeciality);
    }

    @PutMapping("/exclude-speciality/{professionalId}/{idSpeciality}")
    public void exclude(@PathVariable UUID professionalId, @PathVariable UUID idSpeciality) {
        professionalManagerService.excludSpeciality(professionalId,idSpeciality);
    }

    @DeleteMapping("/delete/{professionalId}")
    public void delete(@PathVariable UUID professionalId) {
        professionalManagerService.deleById(professionalId);
    }


}
