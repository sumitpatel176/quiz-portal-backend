package com.quiz.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.quiz.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Value("${app.frontend.url}")
	private String frontendUrl;

	@Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
	

	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf(csrf -> csrf.disable())
	            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
	            
	            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/api/v1/auth/register", "/api/v1/auth/login" ,"/api/v1/auth/refresh","/api/v1/auth/health").permitAll()
	                .requestMatchers("/api/user/me").hasAnyRole("USER","ADMIN")
	                .requestMatchers("/api/admin/**").hasRole("ADMIN")
	                
	                .requestMatchers("/api/quizzes/**").hasAnyRole("USER", "ADMIN")
	                .requestMatchers("/api/questions/**").hasAnyRole("USER", "ADMIN")
	                .requestMatchers("/api/results/**").hasAnyRole("USER", "ADMIN")
	                .requestMatchers("/api/v1/auth/logout").authenticated()
	                .anyRequest().authenticated()                  
	            );

	        // JWT Filter ko UsernamePasswordAuthenticationFilter ke pehle execution me add karna
	        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	        return http.build();
	    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontendUrl)); // React URL
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Saare endpoints par apply karo
        return source;
    }
}