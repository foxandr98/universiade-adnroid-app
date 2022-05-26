package net.foxandr.sport.universiade.ui.news.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.foxandr.sport.universiade.commondto.ImageEntity;

import org.threeten.bp.Instant;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsDTO {

    @JsonProperty("createdOn")
    private Instant createdOn;

    @JsonProperty("newsTEntities")
    private List<NewsTEntity> newsTEntities;

    @JsonProperty("imagesEntity")
    private ImageEntity imagesEntity;

    public static class NewsTEntity{
        @JsonProperty("title")
        private String title;

        @JsonProperty("text")
        private String text;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public List<NewsTEntity> getNewsTEntities() {
        return newsTEntities;
    }

    public void setNewsTEntities(List<NewsTEntity> newsTEntities) {
        this.newsTEntities = newsTEntities;
    }

    public ImageEntity getImagesEntity() {
        return imagesEntity;
    }

    public void setImagesEntity(ImageEntity imagesEntity) {
        this.imagesEntity = imagesEntity;
    }
}


