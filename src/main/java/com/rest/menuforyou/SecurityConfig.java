package com.rest.menuforyou;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Inject
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/menus/{\\d+}/ingredients").permitAll()
				.antMatchers(HttpMethod.GET, "/ingredients/{\\d+}").permitAll()
				.antMatchers(HttpMethod.GET, "/menus/{\\d+}/typedishes").permitAll()
				.antMatchers(HttpMethod.GET, "/typedishes/{\\d+}").permitAll()
				.antMatchers(HttpMethod.GET, "/menus/{\\d+}/dishes").permitAll()
				.antMatchers(HttpMethod.GET, "/menus/{\\d+}/parameters").permitAll()
				.antMatchers(HttpMethod.GET, "/dishes/{\\d+}").permitAll()
				.antMatchers(HttpMethod.GET, "/restaurants").permitAll()
				.antMatchers(HttpMethod.POST, "/users").permitAll()
				.antMatchers(HttpMethod.POST, "/dishes/{\\d+}/feedbacks").permitAll()
				.antMatchers(HttpMethod.GET, "/menus/{\\d+}/feedbacks").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/menus/{\\d+}/ingredients").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/menus/{\\d+}/typedishes").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/menus/{\\d+}/dishes").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/menus/{\\d+}/parameters").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/menus/{\\d+}/parameters/forcereload").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/uploadRestaurantImage").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/uploadDishImage").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/ingredients").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/typedishes").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/dishes").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/restaurants").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/ingredients").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/typedishes").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/dishes").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/restaurants").hasRole("ADMIN")
				.anyRequest().authenticated()
				.and()
				.httpBasic()
				.realmName("Menu For You")
				.and()
				.csrf()
				.disable();
	}

}
