package br.com.susunity.queue.consumer.dto;

import java.util.UUID;

public class Speciality {
    private UUID id;
    private String name;

    public Speciality(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Speciality() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
