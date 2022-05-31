package net.foxandr.sport.universiade.ui.home.games.events.stages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StagesEntity implements Serializable {

    @JsonProperty("stageCode")
    private String stageCode;

    @JsonProperty("stagesTEntities")
    private List<StagesTEntity> stagesTEntities;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StagesTEntity implements Serializable  {

        @JsonProperty("name")
        private String stageName;

        public String getStageName() {
            return stageName;
        }

        public void setStageName(String stageName) {
            this.stageName = stageName;
        }
    }

    public String getStageCode() {
        return stageCode;
    }

    public void setStageCode(String stageCode) {
        this.stageCode = stageCode;
    }

    public List<StagesTEntity> getStagesTEntities() {
        return stagesTEntities;
    }

    public void setStagesTEntities(List<StagesTEntity> stagesTEntities) {
        this.stagesTEntities = stagesTEntities;
    }
}
