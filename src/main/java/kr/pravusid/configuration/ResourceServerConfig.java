package kr.pravusid.configuration;

import kr.pravusid.domain.user.Authority;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${resource.id:spring-boot-application}")
    private String resourceId;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .cors()
                .and()
            .requestMatchers()
                .antMatchers("/api/**")
                .and()
            .authorizeRequests()
                .antMatchers("/api/**/board/**").permitAll()
                .antMatchers("/api/**/user/signup").permitAll()
                .antMatchers("/api/**").hasAuthority(Authority.USER.getAuthority())
                .and()
            .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.resourceId(resourceId);
    }

}
