package net.foxandr.sport.universiade.ui.home.games.participants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.foxandr.sport.universiade.ui.home.games.participants.universities.UniversitiesEntity;

import org.threeten.bp.LocalDate;

import java.time.Instant;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ParticipantsEntity {


    @JsonProperty("id")
    private Long id;

    @JsonProperty("height")
    private Short height;

    @JsonProperty("weight")
    private Short weight;

    @JsonProperty("universitiesEntity")
    private UniversitiesEntity universitiesEntity;

    @JsonProperty("athletesEntity")
    private AthletesEntity athletesEntity;


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AthletesEntity {

        @JsonProperty("id")
        private Long id;

        @JsonProperty("sex")
        private String sex;

        @JsonProperty("birthdayDate")
        private String birthdayDate;

        @JsonProperty("athletesTEntities")
        List<AthletesTEntity> athletesTEntities;


        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class AthletesTEntity {

            @JsonProperty("firstName")
            private String firstName;

            @JsonProperty("lastName")
            private String lastName;

            @JsonProperty("middleName")
            private String middleName;

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public String getMiddleName() {
                return middleName;
            }

            public void setMiddleName(String middleName) {
                this.middleName = middleName;
            }
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthdayDate() {
            return birthdayDate;
        }

        public void setBirthdayDate(String birthdayDate) {
            this.birthdayDate = birthdayDate;
        }

        public List<AthletesTEntity> getAthletesTEntities() {
            return athletesTEntities;
        }

        public void setAthletesTEntities(List<AthletesTEntity> athletesTEntities) {
            this.athletesTEntities = athletesTEntities;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getHeight() {
        return height;
    }

    public void setHeight(Short height) {
        this.height = height;
    }

    public Short getWeight() {
        return weight;
    }

    public void setWeight(Short weight) {
        this.weight = weight;
    }

    public UniversitiesEntity getUniversitiesEntity() {
        return universitiesEntity;
    }

    public void setUniversitiesEntity(UniversitiesEntity universitiesEntity) {
        this.universitiesEntity = universitiesEntity;
    }

    public AthletesEntity getAthletesEntity() {
        return athletesEntity;
    }

    public void setAthletesEntity(AthletesEntity athletesEntity) {
        this.athletesEntity = athletesEntity;
    }
}
