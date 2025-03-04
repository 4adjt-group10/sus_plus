package br.com.susunity.controller;

import br.com.susunity.controller.dto.professional.ProfessionalAvailabilityDTO;
import br.com.susunity.controller.dto.professional.ProfessionalAvailabilityFormDTO;
import br.com.susunity.service.ProfessionalAvailabilityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/professional-availability")
public class ProfessionalAvailabilityController {

    private final ProfessionalAvailabilityService availabilityService;

    public ProfessionalAvailabilityController(ProfessionalAvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProfessionalAvailabilityDTO> addRegister(@RequestBody @Valid ProfessionalAvailabilityFormDTO professionalAvailabilityFormDTO){
        return ResponseEntity.status(CREATED).body(availabilityService.registerAvailability(professionalAvailabilityFormDTO));
    }

    @GetMapping("/list-all")
    public ResponseEntity<List<ProfessionalAvailabilityDTO>> listAllAvailabilities() {
        return ResponseEntity.ok(availabilityService.listAllAvailabilities());
    }

    @GetMapping("/professional/{professionalId}")
    public ResponseEntity<List<ProfessionalAvailabilityDTO>> listAvailabilities(@PathVariable("professionalId") UUID id) {
        return ResponseEntity.ok(availabilityService.listAvailabilitiesByProfessionalId(id));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<ProfessionalAvailabilityDTO>> listAvailabilitiesByDate(@PathVariable("date") LocalDate date,
                                                                                      @RequestParam(value = "unityId") UUID unityId) {
        return ResponseEntity.ok(availabilityService.listAvailabilitiesByDate(date, unityId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProfessionalAvailabilityDTO> updateAvailability(@PathVariable("id") UUID id,
                                                                          @RequestBody @Valid ProfessionalAvailabilityFormDTO professionalAvailabilityFormDTO) {
        return ResponseEntity.ok(availabilityService.updateAvailability(id, professionalAvailabilityFormDTO));
    }
}
