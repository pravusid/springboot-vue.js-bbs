package com.talsist.dto;

import com.talsist.domain.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserDto extends BaseDto {

    private String username;
    private String password;
    private String name;
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public User toEntity(PasswordEncoder encoder) {
        return new User(username, encoder.encode(password), name, email);
    }

}
