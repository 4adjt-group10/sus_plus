package br.com.susmanager.controller;


import br.com.susmanager.controller.dto.speciality.SpecialityDTO;
import br.com.susmanager.controller.dto.speciality.SpecialityForm;
import br.com.susmanager.service.SpecialityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/speciality")
public class SpecialityController {

    @Autowired
    private SpecialityService specialityService;


    @PostMapping("/create")
    public ResponseEntity<SpecialityDTO> procedureRegister(@RequestBody @Valid SpecialityForm specialityForm){
        return ResponseEntity.status(CREATED).body(specialityService.createProcedure(specialityForm));
    }

    @GetMapping("/list")
    public ResponseEntity<List<SpecialityDTO>> findAllProcedures(){
        return ResponseEntity.ok(specialityService.findAll());
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<SpecialityDTO> findProcedureForName(@PathVariable("name") String name){
        return ResponseEntity.ok(specialityService.findByName(name));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SpecialityDTO> updateProcedure(@PathVariable("id") UUID id,
                                                        @RequestBody @Valid SpecialityForm specialityForm){
        return ResponseEntity.ok(specialityService.update(id, specialityForm));
    }
}
