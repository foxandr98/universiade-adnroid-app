package net.foxandr.sport.universiade.ui.users.volunteers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.foxandr.sport.universiade.commondto.VenuesEntity;


@JsonIgnoreProperties(ignoreUnknown = true)
public class VolunteerScheduleDTO {

    @JsonProperty("venuesEntity")
    private VenuesEntity venuesEntity;

    @JsonProperty("utcHelpTime")
    private String utcHelpTime;

    @JsonProperty("extraInfo")
    private String extraInfo;

    public VenuesEntity getVenuesEntity() {
        return venuesEntity;
    }

    public void setVenuesEntity(VenuesEntity venuesEntity) {
        this.venuesEntity = venuesEntity;
    }

    public String getUtcHelpTime() {
        return utcHelpTime;
    }

    public void setUtcHelpTime(String utcHelpTime) {
        this.utcHelpTime = utcHelpTime;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}
