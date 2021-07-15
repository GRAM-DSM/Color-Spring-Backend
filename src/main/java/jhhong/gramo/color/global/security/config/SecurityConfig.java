package jhhong.gramo.color.global.security.config;

import jhhong.gramo.color.domain.user.entity.UserRepository;
import jhhong.gramo.color.global.security.authentication.CustomAuthenticationEntryPoint;
import jhhong.gramo.color.global.security.jwt.JwtAuthenticationManager;
import jhhong.gramo.color.global.security.jwt.JwtTokenExtractor;
import jhhong.gramo.color.global.security.jwt.JwtTokenProvider;
import jhhong.gramo.color.global.security.jwt.JwtVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public record SecurityConfig(JwtTokenProvider jwtTokenProvider,
                             UserRepository userRepository) {

    @Bean
    protected SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .cors().disable()
                .addFilterAt(getAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .authorizeExchange()
                .pathMatchers(HttpMethod.POST, "/user").permitAll()
                .pathMatchers(HttpMethod.POST, "/auth").permitAll()
                .anyExchange().authenticated()
                .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private AuthenticationWebFilter getAuthenticationFilter() {
        var authManager = new JwtAuthenticationManager(userRepository);
        var bearerConverter = new JwtTokenExtractor(new JwtVerifier(jwtTokenProvider));
        var filter = new AuthenticationWebFilter(authManager);

        filter.setServerAuthenticationConverter(bearerConverter);
        filter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.anyExchange());

        return filter;
    }
}
