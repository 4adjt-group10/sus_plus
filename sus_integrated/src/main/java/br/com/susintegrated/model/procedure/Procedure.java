package br.com.susintegrated.model.procedure;

import br.com.susintegrated.controller.dto.procedure.ProcedureFormDTO;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "Procedure")
public class Procedure {

    @Id
    private UUID id;
    @Column(unique = true)
    private String name;
    private BigDecimal price;
//    @ManyToMany(mappedBy = "procedures")
//    private List<Professional> professionals;

    public Procedure(ProcedureFormDTO procedureFormDTO) {
        this.id = UUID.randomUUID();
        this.name = procedureFormDTO.name();
        this.price = procedureFormDTO.price();
//        this.professionals = professionals;
    }

    @Deprecated(since = "Only for use of frameworks")
    public Procedure() {

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

//    public List<Professional> getProfessionals() {
//        return professionals;
//    }

//    public List<String> getProfessionalsNames() {
//        return Optional.ofNullable(professionals)
//                .map(professionalList -> professionalList.stream().map(Professional::getName).toList())
//                .orElse(List.of());
//    }

//    public void addProfessional(Professional professional) {
//        professionals.add(professional);
//    }
//
//    public void merge(ProcedureFormDTO procedureFormDTO, List<Professional> professionals) {
//        this.name = procedureFormDTO.name();
//        this.price = procedureFormDTO.price();
//        this.professionals = professionals;
//    }
}
