package br.com.susmanager.queue.producer.dto;

import br.com.susmanager.model.SpecialityModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name"
})
public class SpecialityDto implements Serializable {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;

    public SpecialityDto(SpecialityModel specialityModel) {
        this.id = specialityModel.getId();
        this.name = specialityModel.getName();
    }

    // Getters and Setters
    @JsonProperty("id")
    public UUID getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(UUID id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }
}
