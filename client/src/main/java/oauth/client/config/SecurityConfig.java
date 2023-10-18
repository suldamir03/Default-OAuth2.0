package oauth.client.config;

import static org.springframework.security.config.Customizer.withDefaults;

import lombok.RequiredArgsConstructor;
import oauth.client.handlers.CustomOAuthFailureHandler;
import oauth.client.handlers.CustomOAuthSuccessHandler;
import oauth.client.service.OAuthUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuthUserService oAuthUserService;
    private final CustomOAuthSuccessHandler customOAuthSuccessHandler;
    private final CustomOAuthFailureHandler customOAuthFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth ->
                        auth
                                .antMatchers("/").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(login ->
                        login
                                .userInfoEndpoint().userService(oAuthUserService)
                                .and()
                                .successHandler(customOAuthSuccessHandler)
                                .failureHandler(customOAuthFailureHandler)

                )
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.IF_REQUIRED))
                .formLogin(withDefaults())
                .build();
    }
}
