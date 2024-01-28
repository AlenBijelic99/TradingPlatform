package ch.heigvd.application.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithms;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * This class is used to configure the security of the application.
 * It extends the VaadinWebSecurity class.
 * @author Bijelic Alen & Bogale Tegest
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

    // The secret is stored in /config/secrets/application.properties by default.
    // Never commit the secret into version control; each environment should have
    // its own secret.
    @Value("${ch.heigvd.application.auth.secret}")
    private String authSecret;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This method is used to configure the security of the application.
     * @param http The HttpSecurity object
     * @throws Exception Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/images/*.png")).permitAll());
        // Icons from the line-awesome addon
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(new AntPathRequestMatcher("/line-awesome/**/*.svg")).permitAll());
        // Autorize home and register page without being logged in
        http.authorizeHttpRequests(registry -> {
            registry.requestMatchers(new AntPathRequestMatcher("/")).permitAll();
            registry.requestMatchers(new AntPathRequestMatcher("/register")).permitAll();
        });

        super.configure(http);

        http.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        setLoginView(http, "/login");
        setStatelessAuthentication(http, new SecretKeySpec(Base64.getDecoder().decode(authSecret), JwsAlgorithms.HS256),
                "ch.heigvd.application");
    }

}
