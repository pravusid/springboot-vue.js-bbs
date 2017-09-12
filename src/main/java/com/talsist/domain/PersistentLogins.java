package com.talsist.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class PersistentLogins {

    @NotNull
    @OneToOne
    @JoinColumn(foreignKey=@ForeignKey(name = "fk_persistent_logins"))
    private User user;
    
    @Id
    private String series;
    
    @NotNull
    private String token;
    
    @NotNull
    private Date lastUsed;

    public PersistentLogins() {}

    public PersistentLogins(User user, String series, String token, Date lastUsed) {
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

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }
    
    public void update(String newSeries, String newToken, Date newLastUsed) {
        this.series = newSeries;
        this.token = newToken;
        this.lastUsed = newLastUsed;
    }

}
