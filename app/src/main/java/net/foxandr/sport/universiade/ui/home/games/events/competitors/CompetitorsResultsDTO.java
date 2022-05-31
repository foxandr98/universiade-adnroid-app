package net.foxandr.sport.universiade.ui.home.games.events.competitors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.foxandr.sport.universiade.ui.home.games.participants.ParticipantsEntity;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CompetitorsResultsDTO {

    @JsonProperty("partNumber")
    private Integer partNumber;

    @JsonProperty("laneNumber")
    private Integer laneNumber;

    @JsonProperty("resultTypesEntity")
    private ResultTypesEntity resultTypesEntity;

    @JsonProperty("participantsEntity")
    private ParticipantsEntity participantsEntity;


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResultTypesEntity {

        @JsonProperty("id")
        private Long id;

        @JsonProperty("isWin")
        private Boolean isWin;

        @JsonProperty("resultTypesTEntities")
        private List<ResultTypesTEntity> resultTypesTEntities;


        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ResultTypesTEntity {

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

        public Boolean getIsWin() {
            return isWin;
        }

        public void setIsWin(Boolean isWin) {
            this.isWin = isWin;
        }

        public List<ResultTypesTEntity> getResultTypesTEntities() {
            return resultTypesTEntities;
        }

        public void setResultTypesTEntities(List<ResultTypesTEntity> resultTypesTEntities) {
            this.resultTypesTEntities = resultTypesTEntities;
        }
    }


    public Integer getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(Integer partNumber) {
        this.partNumber = partNumber;
    }

    public Integer getLaneNumber() {
        return laneNumber;
    }

    public void setLaneNumber(Integer laneNumber) {
        this.laneNumber = laneNumber;
    }

    public ResultTypesEntity getResultTypesEntity() {
        return resultTypesEntity;
    }

    public void setResultTypesEntity(ResultTypesEntity resultTypesEntity) {
        this.resultTypesEntity = resultTypesEntity;
    }

    public ParticipantsEntity getParticipantsEntity() {
        return participantsEntity;
    }

    public void setParticipantsEntity(ParticipantsEntity participantsEntity) {
        this.participantsEntity = participantsEntity;
    }
}
