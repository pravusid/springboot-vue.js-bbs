package com.talsist.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String userId;

    private String password;
    private String name;
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void update(User newUser) {
        this.password = newUser.password;
        this.name = newUser.name;
        this.email = newUser.email;
    }

    public boolean verifyId(Long reqId) {
        return reqId.equals(getId());
    }

    public boolean verifyPassword(User reqUser) {
        return reqUser.password.equals(password);
    }

    @Override
    public String toString() {
        return super.toString() + userId + name + email;
    }

}
