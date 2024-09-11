package backend.keumbangresource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import backend.keumbangresource.filter.JWTFilter;
import backend.keumbangresource.grpc.GrpcTokenClient;
import backend.keumbangresource.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	private final JWTUtil jwtUtil;
	private final GrpcTokenClient grpcTokenClient;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// CSRF disable
		http.csrf((csrf) -> csrf.disable());
		
		// formLogin disable
		http.formLogin((formLogin) -> formLogin.disable());
		
		// HTTP basic 인증 disable
		http.httpBasic((httpBasic) -> httpBasic.disable());
		
		// JWTFilter 등록
		http.addFilterBefore(
				new JWTFilter(jwtUtil, grpcTokenClient),
				UsernamePasswordAuthenticationFilter.class);
		
		// 세션 설정
		http.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		return http.build();
	}
}
