package com.examples.configuracion;

import com.examples.seguridad.JwtFilter;
import com.examples.services.UsuarioService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UsuarioService usuarioService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
        	.cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
            	    .requestMatchers("/auth/**").permitAll()
            	    .requestMatchers(
            	        "/apiRoles/**", "/apiUsuarios/**",
            	        "/apiEstadisticas/**"
            	    ).hasAuthority("Administrador")
            	    .requestMatchers("/apiAlbaranes/**", "/apiFacturas/**").hasAnyAuthority("Administrador", "Administrativa")
            	    .requestMatchers("/apiStock/**", "/apiPedidos/**", "/apiProveedores/**", "/apiBares/**", "/apiProductos/**").hasAnyAuthority(
            	        "Administrador", "Encargado_Camareros", "Encargado_Cocineros", "Camarero", "Cocinero"
            	    )
            	    .anyRequest().authenticated()
            	)
            .userDetailsService(usuarioService)
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:8100", "http://192.168.1.137:8100"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

