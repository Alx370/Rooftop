package Immobiliaris.Progetto_Rooftop.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity // Enable Spring Security's web security support
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource; // Injected CORS configuration

    public SecurityConfig(CorsConfigurationSource corsConfigurationSource) { // Constructor
        this.corsConfigurationSource = corsConfigurationSource; 
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource)) // Enable CORS
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll() // Allow all requests without authentication
            )
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
            .formLogin(form -> form.disable()) // Disable form login
            .httpBasic(basic -> basic.disable()); // Disable HTTP Basic authentication
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}