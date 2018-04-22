package com.talsist.domain.user;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class PersistentLogins {

    @NotNull
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_persistent_logins"))
    private User user;

    @Id
    private String series;

    @NotNull
    private String token;

    @NotNull
    private LocalDate lastUsed;

    public PersistentLogins() {
    }

    public PersistentLogins(User user, String series, String token, LocalDate lastUsed) {
        this.user = user;
        this.series = series;
        this.token = token;
        this.lastUsed = lastUsed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDate getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(LocalDate lastUsed) {
        this.lastUsed = lastUsed;
    }

    public void update(String newSeries, String newToken, LocalDate newLastUsed) {
        this.series = newSeries;
        this.token = newToken;
        this.lastUsed = newLastUsed;
    }

}
