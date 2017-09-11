package com.talsist.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final static String REMEMBER_ME_KEY = "REMKEY";
    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/").permitAll()
                .antMatchers("/mypage/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
            .formLogin()
                .loginPage("/login").failureUrl("/login?error").permitAll()
                .usernameParameter("userId")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("IDPRAVUS_AUTH")
                .and()
            .exceptionHandling()
                .accessDeniedPage("/denied")
                .and()
            .rememberMe()
                .key(REMEMBER_ME_KEY)
                .rememberMeParameter("REMEMBER_ME")
                .rememberMeServices(tokenBasedRememberMeServices());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenBasedRememberMeServices tokenBasedRememberMeServices() {
        TokenBasedRememberMeServices tokenBasedRememberMeServices =
                new TokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailsService);
        tokenBasedRememberMeServices.setCookieName("IDPRAVUS_AUTH");
        tokenBasedRememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 30);
        return tokenBasedRememberMeServices;
    }

}
