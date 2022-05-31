package net.foxandr.sport.universiade.commondto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VenuesEntity implements Serializable {

    @JsonProperty("longitude")
    private BigDecimal longitude;

    @JsonProperty("latitude")
    private BigDecimal latitude;

    @JsonProperty("venuesTEntities")
    private List<VenuesTEntity> venuesTEntities;


    @JsonIgnoreProperties(ignoreUnknown = true)
    static class VenuesTEntity implements Serializable {

        @JsonProperty("name")
        private String venueName;

        @JsonProperty("address")
        private String address;

        public String getVenueName() {
            return venueName;
        }

        public void setVenueName(String venueName) {
            this.venueName = venueName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }


    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public List<VenuesTEntity> getVenuesTEntities() {
        return venuesTEntities;
    }

    public void setVenuesTEntities(List<VenuesTEntity> venuesTEntities) {
        this.venuesTEntities = venuesTEntities;
    }
}


