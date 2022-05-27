package net.foxandr.sport.universiade.ui.home.games.events.genderdisciplines.disciplines;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class DisciplinesEntity {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("disciplineCode")
    private String disciplineCode;

    @JsonProperty("disciplinesTEntities")
    private List<DisciplinesTEntity> disciplinesTEntities;

    @JsonProperty("scoreType")
    private String scoreType;

    @JsonProperty("isIndividual")
    private Boolean isIndividual;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DisciplinesTEntity{

        @JsonProperty("name")
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisciplineCode() {
        return disciplineCode;
    }

    public void setDisciplineCode(String disciplineCode) {
        this.disciplineCode = disciplineCode;
    }

    public List<DisciplinesTEntity> getDisciplinesTEntities() {
        return disciplinesTEntities;
    }

    public void setDisciplinesTEntities(List<DisciplinesTEntity> disciplinesTEntities) {
        this.disciplinesTEntities = disciplinesTEntities;
    }

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    public Boolean getIsIndividual() {
        return isIndividual;
    }

    public void setIsIndividual(Boolean isIndividual) {
        this.isIndividual = isIndividual;
    }
}
