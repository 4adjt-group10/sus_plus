package br.com.susintegrado.controller;

import br.com.susintegrado.controller.dto.procedure.ProcedureDTO;
import br.com.susintegrado.controller.dto.procedure.ProcedureFormDTO;
import br.com.susintegrado.service.ProcedureService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/procedure")
public class ProcedureController {

    private final ProcedureService procedureService;

    public ProcedureController(ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProcedureDTO> procedureRegister(@RequestBody @Valid ProcedureFormDTO procedureFormDTO){
        return ResponseEntity.status(CREATED).body(procedureService.createProcedure(procedureFormDTO));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProcedureDTO>> findAllProcedures(){
        return ResponseEntity.ok(procedureService.findAll());
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<ProcedureDTO> findProcedureForName(@PathVariable("name") String name){
        return ResponseEntity.ok(procedureService.findByName(name));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProcedureDTO> updateProcedure(@PathVariable("id") UUID id,
                                                        @RequestBody @Valid ProcedureFormDTO procedureFormDTO){
        return ResponseEntity.ok(procedureService.update(id, procedureFormDTO));
    }
}
