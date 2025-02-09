package br.com.susintegrado.controller.dto.procedure;

import br.com.susintegrado.model.procedure.Procedure;

import java.math.BigDecimal;
import java.util.UUID;

public record ProcedureDTO(UUID id, String name, BigDecimal price) {

    public ProcedureDTO(Procedure procedure) {
        this(procedure.getId(), procedure.getName(), procedure.getPrice());
    }
}
