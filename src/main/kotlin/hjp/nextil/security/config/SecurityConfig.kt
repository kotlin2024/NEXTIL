package hjp.nextil.security.config

import hjp.nextil.domain.member.repository.MemberRepository
import hjp.nextil.domain.member.service.OAuth2Service
import hjp.nextil.security.jwt.JwtAuthenticationFilter
import hjp.nextil.security.jwt.JwtTokenManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        return http
            .csrf { it.disable() }
            .cors{}
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-resources/**",
                    "/swagger-ui.html",
                    "swagger-ui/index.html#",
                    "/oauth/**",
                    "/api/auth/**",
                    ).permitAll()
                it.anyRequest().authenticated()
            }
//            .oauth2Login {
//                it.loginPage("/login")
//                    .defaultSuccessUrl("/", true)
//                    .userInfoEndpoint { userInfo ->
//                        userInfo.userService(oAuth2Service)
//                    }
//            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

}