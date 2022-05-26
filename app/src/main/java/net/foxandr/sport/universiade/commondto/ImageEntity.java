package net.foxandr.sport.universiade.commondto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageEntity {
    @JsonProperty("uuid")
    private String uuid;

}
