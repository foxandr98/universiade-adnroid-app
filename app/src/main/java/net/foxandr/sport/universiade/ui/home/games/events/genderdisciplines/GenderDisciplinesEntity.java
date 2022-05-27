package net.foxandr.sport.universiade.ui.home.games.events.genderdisciplines;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.foxandr.sport.universiade.ui.home.games.events.genderdisciplines.disciplines.DisciplinesEntity;


@JsonIgnoreProperties(ignoreUnknown = true)
public class GenderDisciplinesEntity {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("disciplinesEntity")
    private DisciplinesEntity disciplinesEntity;

    @JsonProperty("genderType")
    private String genderType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DisciplinesEntity getDisciplinesEntity() {
        return disciplinesEntity;
    }

    public void setDisciplinesEntity(DisciplinesEntity disciplinesEntity) {
        this.disciplinesEntity = disciplinesEntity;
    }

    public String getGenderType() {
        return genderType;
    }

    public void setGenderType(String genderType) {
        this.genderType = genderType;
    }
}
