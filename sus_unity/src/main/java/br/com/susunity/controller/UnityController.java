package br.com.susunity.controller;

import br.com.susunity.controller.dto.UnityInForm;
import br.com.susunity.controller.dto.UnityDto;
import br.com.susunity.controller.dto.UnityProfessionalForm;
import br.com.susunity.queue.producer.MessageProducer;
import br.com.susunity.service.UnityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/unity")
public class UnityController {
    @Autowired
    private MessageProducer producer;

    private final UnityService unityService;

    public UnityController(UnityService unityService) {
        this.unityService = unityService;
    }


    @PostMapping("/create")
    public ResponseEntity<UnityDto> createUnity(@RequestBody UnityInForm unityInForm) {
        return ResponseEntity.ok(unityService.create(unityInForm));
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<UnityDto>> allUnity() {
        return ResponseEntity.ok(unityService.findAll());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UnityDto> findUnity(@PathVariable UUID id) {

        return ResponseEntity.ok(unityService.findById(id));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<UnityDto> alterUnity(@PathVariable UUID id, @RequestBody UnityInForm unityInForm) {

        return ResponseEntity.ok(unityService.update(id,unityInForm));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUnity(@PathVariable UUID id) {
        unityService.delete(id);
        return ResponseEntity.ok("unidade Deletada");
    }
    @PutMapping("/update/{id}/{quantity}/in")
    public ResponseEntity<UnityDto> alterInUnity(@PathVariable UUID id, @PathVariable Integer quantity) {

        return ResponseEntity.ok(unityService.updateInQuantity(id,quantity));
    }

    @PutMapping("/update/{id}/{quantity}/out")
    public ResponseEntity<UnityDto> alterOutUnity(@PathVariable UUID id, @PathVariable Integer quantity) {

        return ResponseEntity.ok(unityService.updateOutQuantity(id,quantity));
    }

    @PostMapping("/include/professional")
    public ResponseEntity includeProfessional(@RequestBody UnityProfessionalForm unityProfessionalForm) {
        unityService.includeProfessional(unityProfessionalForm);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/exclude/professional")
    public ResponseEntity excludeProfessional(@RequestBody UnityProfessionalForm unityProfessionalForm) {
        unityService.excludeProfessional(unityProfessionalForm);
        return ResponseEntity.ok().build();
    }
}
