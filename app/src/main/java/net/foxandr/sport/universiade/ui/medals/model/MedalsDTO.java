package net.foxandr.sport.universiade.ui.medals.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MedalsDTO {

    @JsonProperty("rank")
    private Long rank;

    @JsonProperty("iocName")
    private String iocName;

    @JsonProperty("name")
    private String countryName;

    @JsonProperty("goldCount")
    private Integer goldCount;

    @JsonProperty("silverCount")
    private Integer silverCount;

    @JsonProperty("bronzeCount")
    private Integer bronzeCount;

    @JsonProperty("total")
    private Integer total;


    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public String getIocName() {
        return iocName;
    }

    public void setIocName(String iocName) {
        this.iocName = iocName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getGoldCount() {
        return goldCount;
    }

    public void setGoldCount(Integer goldCount) {
        this.goldCount = goldCount;
    }

    public Integer getSilverCount() {
        return silverCount;
    }

    public void setSilverCount(Integer silverCount) {
        this.silverCount = silverCount;
    }

    public Integer getBronzeCount() {
        return bronzeCount;
    }

    public void setBronzeCount(Integer bronzeCount) {
        this.bronzeCount = bronzeCount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
