package com.talsist.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.talsist.domain.Authority;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final static String REMEMBER_ME_KEY = "KEY";
    
    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService customUserDetailsService) {
        this.userDetailsService = customUserDetailsService;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/board/**").permitAll()
                .antMatchers("/mypage/**").hasAnyAuthority(Authority.USER.getAuthority(), Authority.ADMIN.getAuthority())
                .antMatchers("/admin/**").hasAuthority(Authority.ADMIN.getAuthority())
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
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
            .exceptionHandling()
                .accessDeniedPage("/denied")
                .and()
            .rememberMe()
                .key(REMEMBER_ME_KEY)
                .rememberMeServices(persistentTokenBasedRememberMeServices());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices(){
        PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices =
                new PersistentTokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailsService, persistentTokenRepository());
        persistentTokenBasedRememberMeServices.setParameter("REMEMBER_ME");
        persistentTokenBasedRememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 30);
        return persistentTokenBasedRememberMeServices;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        return new PersistentTokenRepositoryImpl();
    }

}
