package net.foxandr.sport.universiade.ui.login.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class LoggedInUserDTO {

    @JsonProperty("username")
    private String username;

    private String password;

    @JsonProperty("authorities")
    private List<Authority> authorities;


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Authority{
        @JsonProperty("authority")
        private String authority;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
}