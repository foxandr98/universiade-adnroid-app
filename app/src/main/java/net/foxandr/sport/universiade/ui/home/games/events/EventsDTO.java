package net.foxandr.sport.universiade.ui.home.games.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.foxandr.sport.universiade.commondto.VenuesEntity;
import net.foxandr.sport.universiade.ui.home.games.events.genderdisciplines.GenderDisciplinesEntity;
import net.foxandr.sport.universiade.ui.home.games.events.stages.StagesEntity;

import org.threeten.bp.Instant;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventsDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("stagesEntity")
    private StagesEntity stagesEntity;

    @JsonProperty("genderDisciplinesEntity")
    private GenderDisciplinesEntity genderDisciplinesEntity;

    @JsonProperty("venuesEntity")
    private VenuesEntity venuesEntity;

    @JsonProperty("utcEventTime")
    private String eventTime;

    @JsonProperty("finished")
    private Boolean isFinished;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StagesEntity getStagesEntity() {
        return stagesEntity;
    }

    public void setStagesEntity(StagesEntity stagesEntity) {
        this.stagesEntity = stagesEntity;
    }

    public GenderDisciplinesEntity getGenderDisciplinesEntity() {
        return genderDisciplinesEntity;
    }

    public void setGenderDisciplinesEntity(GenderDisciplinesEntity genderDisciplinesEntity) {
        this.genderDisciplinesEntity = genderDisciplinesEntity;
    }

    public VenuesEntity getVenuesEntity() {
        return venuesEntity;
    }

    public void setVenuesEntity(VenuesEntity venuesEntity) {
        this.venuesEntity = venuesEntity;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }
}
