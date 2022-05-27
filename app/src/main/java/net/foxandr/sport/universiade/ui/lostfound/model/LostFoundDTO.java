package net.foxandr.sport.universiade.ui.lostfound.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LostFoundDTO {

    @JsonProperty("itemDescription")
    private String itemDescription;

    @JsonProperty("lostItemArea")
    private String lostItemArea;

    @JsonProperty("cityName")
    private String cityName;

    @JsonProperty("cityName")
    private Boolean isRequest;

    @JsonProperty("contactName")
    private String contactName;

    @JsonProperty("contactToNotify")
    private String contactToNotify;


    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getLostItemArea() {
        return lostItemArea;
    }

    public void setLostItemArea(String lostItemArea) {
        this.lostItemArea = lostItemArea;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Boolean getRequest() {
        return isRequest;
    }

    public void setRequest(Boolean request) {
        isRequest = request;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactToNotify() {
        return contactToNotify;
    }

    public void setContactToNotify(String contactToNotify) {
        this.contactToNotify = contactToNotify;
    }
}
