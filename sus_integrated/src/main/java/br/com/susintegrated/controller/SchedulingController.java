//package br.com.susintegrado.controller;
//
//import br.com.susintegrado.controller.dto.scheduling.SchedulingDTO;
//import br.com.susintegrado.controller.dto.scheduling.SchedulingFormDTO;
//import br.com.susintegrado.controller.dto.scheduling.SchedulingUpdateDTO;
//import br.com.susintegrado.service.SchedulingService;
//import jakarta.validation.Valid;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.springframework.http.HttpStatus.CREATED;
//
//@RestController
//@RequestMapping("/scheduling")
//public class SchedulingController {
//
//    private final SchedulingService schedulingService;
//
//    public SchedulingController(SchedulingService schedulingService) {
//        this.schedulingService = schedulingService;
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<SchedulingDTO> schedulerRegister(@RequestBody @Valid SchedulingFormDTO schedulingFormDTO){
//        return ResponseEntity.status(CREATED).body(schedulingService.register(schedulingFormDTO));
//    }
//
//    @GetMapping("/list/patient/{id}")
//    public ResponseEntity<List<SchedulingDTO>> listByPatient(@PathVariable("id") UUID id,
//                                                             @RequestParam(value = "date", required = false) Optional<LocalDate> date) {
//        return ResponseEntity.ok(schedulingService.findAllByPatientId(id, date));
//    }
//
//    @GetMapping("/list/professional/{id}")
//    public ResponseEntity<List<SchedulingDTO>> listByProfessional(@PathVariable("id") UUID id,
//                                                                  @RequestParam(value = "date", required = false) Optional<LocalDate> date) {
//        return ResponseEntity.ok(schedulingService.findAllByProfessionalId(id, date));
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<SchedulingDTO> updateSchedule(@PathVariable("id") UUID id,
//                                                        @RequestBody @Valid SchedulingUpdateDTO updateDTO) {
//        return ResponseEntity.ok(schedulingService.update(id, updateDTO));
//    }
//
//    @PutMapping("/done/{id}")
//    public ResponseEntity<SchedulingDTO> done(@PathVariable("id") UUID id) {
//        return ResponseEntity.ok(schedulingService.done(id));
//    }
//
//}
