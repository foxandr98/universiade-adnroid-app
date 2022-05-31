package net.foxandr.sport.universiade.commondto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CountriesEntity {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("iocName")
    private String iocName;

    @JsonProperty("countriesTEntities")
    private List<CountriesTEntity> countriesTEntities;



    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CountriesTEntity{

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

    public String getIocName() {
        return iocName;
    }

    public void setIocName(String iocName) {
        this.iocName = iocName;
    }

    public List<CountriesTEntity> getCountriesTEntities() {
        return countriesTEntities;
    }

    public void setCountriesTEntities(List<CountriesTEntity> countriesTEntities) {
        this.countriesTEntities = countriesTEntities;
    }
}
