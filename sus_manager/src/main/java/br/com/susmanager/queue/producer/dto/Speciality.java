package br.com.susmanager.queue.producer.dto;

import br.com.susmanager.model.SpecialityModel;

import java.util.UUID;

public class Speciality {
    private UUID id;
    private String name;

    public Speciality(SpecialityModel specialityModel) {
        this.id = specialityModel.getId();
        this.name = specialityModel.getName();
    }
}
