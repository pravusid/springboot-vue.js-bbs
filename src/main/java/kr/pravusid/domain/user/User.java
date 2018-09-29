package kr.pravusid.domain.user;

import kr.pravusid.domain.BaseEntity;
import kr.pravusid.domain.UserVerifiable;
import kr.pravusid.domain.user.persistentlogins.PersistentLogins;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User extends BaseEntity implements UserVerifiable, UserDetails {

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
    private Set<Authority> authorities;

    public User(Long id) {
        super(id);
    }

    public User(String username, String password, String name, String email) {
        this();
        this.username = username;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.name = name;
        this.email = email;
    }

    private User() {
        authorities = new HashSet<>();
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

    @Override
    public boolean verifyUser(String username) {
        return this.username.equals(username);
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
