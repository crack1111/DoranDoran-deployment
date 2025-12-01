package kr.ac.kopo.dorandoran.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletResponse;
import kr.ac.kopo.dorandoran.CustomLoginSuccessHandler;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	CustomLoginSuccessHandler loginHandler;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
		return http
				.authorizeHttpRequests(req -> req
						.requestMatchers("/", "/login", "/error", "/signup", "/ws/**", "/css/**", "/js/**", "/img/**", "/api/**",
								"/resourcefile/**", "/upload/**", "/house/list/**", "/house/detail/**", "/search/**",
								"/reviewupload/**", "/house/reviewlist/**", "/header/**", "/house/address/**",
								"/house/search/**")
						.permitAll() // 여기가 아무나 볼수있는곳
						.requestMatchers("/member/**").hasRole("ADMIN").requestMatchers("/admin/**").hasRole("ADMIN")
						.anyRequest().authenticated())

				.formLogin(login -> login.loginPage("/login").successHandler(loginHandler).permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true))
				.exceptionHandling(
						exception -> exception.authenticationEntryPoint((request, response, authException) -> {
							String ajaxHeader = request.getHeader("X-Requested-With");
							if ("XMLHttpRequest".equals(ajaxHeader)) {
								// AJAX 요청이면 401 상태 코드 응답
								response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
							} else {
								// 일반 요청이면 로그인 페이지로 리다이렉트
								response.sendRedirect("/login");
							}
						}).accessDeniedHandler((request, response, accessDeniedException) -> {
							response.sendRedirect("/"); // 권한이 없는 사람이 직접 들어가려고 할 때 index로 리다이렉트
						}))
				.userDetailsService(userDetailsService).csrf(csrf -> csrf.disable()).build();
	}

	@Bean
	UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		return new UserDetailsServiceImpl(passwordEncoder);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
