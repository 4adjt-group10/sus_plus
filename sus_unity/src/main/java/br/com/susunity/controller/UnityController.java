package br.com.susunity.controller;

import br.com.susunity.controller.dto.UnityInForm;
import br.com.susunity.controller.dto.UnityDto;
import br.com.susunity.service.UnityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/unity")
public class UnityController {

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
    @PutMapping("/update/{id}/{quantity}")
    public ResponseEntity<UnityDto> alterUnity(@PathVariable UUID id, @PathVariable Integer quantity) {

        return ResponseEntity.ok(unityService.updateQuantity(id,quantity));
    }
}
