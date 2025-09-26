package com.logistica.logistica_api.infra.security


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfigurations(
    private val securityFilter: SecurityFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        println("INICIANDO CONFIGURATIONS!!!")
        return http
            .csrf { csrf -> csrf.disable() }
            .sessionManagement { sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { req ->
                req.requestMatchers("/login").permitAll()
                req.requestMatchers("/v3/api-docs/**", "/context-path/swagger-ui.html", "/context-path/swagger-ui/**").permitAll()
                req.anyRequest().authenticated()
            }
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }


    @Bean
    fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager {
        return configuration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}