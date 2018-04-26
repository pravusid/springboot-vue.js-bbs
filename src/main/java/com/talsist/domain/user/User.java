package com.talsist.domain.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.talsist.domain.BaseEntity;

@Entity
public class User extends BaseEntity implements UserDetails {

    @OneToOne(mappedBy = "user")
    private PersistentLogins persistentLogins;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private List<Authority> authorities;

    public User(String username, String password, String name, String email) {
        this();
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    private User() {
        authorities = new ArrayList<>();
        authorities.add(Authority.USER);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void update(User reqUser) {
        password = (reqUser.password.isEmpty()) ? password : reqUser.password;
        name = (reqUser.name.isEmpty()) ? name : reqUser.name;
        email = (reqUser.email.isEmpty()) ? email : reqUser.email;
    }

    public boolean verifyId(Long reqId) {
        return reqId.equals(getId());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
