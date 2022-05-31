package net.foxandr.sport.universiade.ui.home.games.participants.universities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.foxandr.sport.universiade.commondto.CountriesEntity;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UniversitiesEntity {


    @JsonProperty("id")
    private Long id;

    @JsonProperty("website")
    private String website;

    @JsonProperty("countriesEntity")
    private CountriesEntity countriesEntity;

    @JsonProperty("universitiesTEntities")
    private List<UniversitiesTEntity> universitiesTEntities;



    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UniversitiesTEntity{

        @JsonProperty("name")
        private String name;

        @JsonProperty("abbreviation")
        private String abbreviation;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAbbreviation() {
            return abbreviation;
        }

        public void setAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public CountriesEntity getCountriesEntity() {
        return countriesEntity;
    }

    public void setCountriesEntity(CountriesEntity countriesEntity) {
        this.countriesEntity = countriesEntity;
    }

    public List<UniversitiesTEntity> getUniversitiesTEntities() {
        return universitiesTEntities;
    }

    public void setUniversitiesTEntities(List<UniversitiesTEntity> universitiesTEntities) {
        this.universitiesTEntities = universitiesTEntities;
    }
}
