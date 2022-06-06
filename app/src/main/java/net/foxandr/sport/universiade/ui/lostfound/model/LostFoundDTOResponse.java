package net.foxandr.sport.universiade.ui.lostfound.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.foxandr.sport.universiade.commondto.ImageEntity;


@JsonIgnoreProperties(ignoreUnknown = true)
public class LostFoundDTOResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("itemDescription")
    private String itemDescription;

    @JsonProperty("lostItemArea")
    private String lostItemArea;

    @JsonProperty("cityName")
    private String cityName;

    @JsonProperty("createdOn")
    private String createdOn;

    @JsonProperty("imagesEntity")
    private ImageEntity imagesEntity;

    @JsonProperty("lostFoundRequestsEntity")
    private LostFoundRequestsEntity lostFoundRequestsEntity;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LostFoundRequestsEntity{
        @JsonProperty("contactName")
        private String contactName;

        @JsonProperty("contactToNotify")
        private String contactToNotify;

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

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public ImageEntity getImagesEntity() {
        return imagesEntity;
    }

    public void setImagesEntity(ImageEntity imagesEntity) {
        this.imagesEntity = imagesEntity;
    }

    public LostFoundRequestsEntity getLostFoundRequestsEntity() {
        return lostFoundRequestsEntity;
    }

    public void setLostFoundRequestsEntity(LostFoundRequestsEntity lostFoundRequestsEntity) {
        this.lostFoundRequestsEntity = lostFoundRequestsEntity;
    }
}
