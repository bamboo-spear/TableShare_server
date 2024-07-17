package bamboospear.tableshare_server.config

import bamboospear.tableshare_server.filter.TokenFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(val tokenFilter: TokenFilter) {
    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        return http
            .formLogin { it.disable() }
            .csrf { it.disable() }
            .cors { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/login", "/sign-up").permitAll()
                    .requestMatchers("/test").permitAll()
                    .requestMatchers("/swagger-ui.html", "/api-docs/**", "/swagger-ui/**").permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}