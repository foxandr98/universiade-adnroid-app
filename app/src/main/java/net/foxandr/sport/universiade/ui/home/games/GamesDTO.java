package net.foxandr.sport.universiade.ui.home.games;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GamesDTO {

    @JsonProperty("id")
    private Long gameId;

    @JsonProperty("gameYear")
    private Short gameYear;

    @JsonProperty("isSummer")
    private boolean isSummer;

    @JsonProperty("codeName")
    private String codeName;

    @JsonProperty("countryName")
    private String countryName;

    @JsonProperty("gameName")
    private String gameName;


    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Short getGameYear() {
        return gameYear;
    }

    public void setGameYear(Short gameYear) {
        this.gameYear = gameYear;
    }

    public boolean getIsSummer() {
        return isSummer;
    }

    public void setIsSummer(boolean summer) {
        isSummer = summer;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
