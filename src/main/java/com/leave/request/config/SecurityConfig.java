/**
 * 
 */
package com.leave.request.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.leave.request.constants.UserRoleEnum;
import com.leave.request.service.CustomUserDetailsService;
import com.leave.request.util.SecurityUtil;

/**
 * @author Eraine
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/favicon.ico", "/css/**", "/js/**", "/fonts/**", "/webjars/**", "/json/all/**").permitAll()
				.antMatchers("/register").permitAll()
//				.antMatchers("/manage", "/manage/**").access("hasAuthority('TEAM_LEAD') or hasAuthority('MANAGER')") // we can do this but I prefer to handle it in controller 				
				.anyRequest().fullyAuthenticated()
			.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/home")
				.permitAll()
			.and()
				.logout()
				.permitAll();
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

}
