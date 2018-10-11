package kr.pravusid.domain.user.persistentlogins;

import kr.pravusid.domain.user.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class PersistentLogins {

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_persistent_logins"))
    private User user;

    @Id
    private String series;

    private String token;

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
