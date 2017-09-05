package com.talsist.domain;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 입력값이 없어도 증가된 값을 자동으로 넣어줌
    private Long id;

    @Column(nullable = false)
    private String userId;

    private String password;
    private String name;
    private String email;

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
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

    @Override
    public String toString() {
        return userId + password + name + email;
    }
}
