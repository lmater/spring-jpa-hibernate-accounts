package com.lmater.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("ADMIN","USER");
//		auth.inMemoryAuthentication().withUser("user").password("{noop}1234").roles("USER");

		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select login as principal,pass as credentials,active from user where login=?")
				.authoritiesByUsernameQuery("select user.login as login, role.role as role from users_roles\r\n" + 
						"inner join user on users_roles.user=user.id \r\n" + 
						"inner join role on role.id =users_roles.role\r\n" + 
						"where user.login=?")
				.passwordEncoder(new MessageDigestPasswordEncoder("MD5")).
				rolePrefix("ROLE_");

	
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.formLogin();// .loginPage("/login");

		http.csrf().disable().authorizeRequests().antMatchers("/", "/login").permitAll().antMatchers("/favicon.ico")
				.permitAll().antMatchers("/operations", "/consulterCompte").hasRole("USER")
				.antMatchers("/saveOperation").hasRole("ADMIN").anyRequest().authenticated()
				// we'll enable this in a later updates
				.and().exceptionHandling().accessDeniedPage("/403");

//		 http.csrf().disable()
//         .authorizeRequests()
//         .antMatchers("/","/login").permitAll()
// 		 .antMatchers("/operations", "/consulterCompte").hasRole("USER")
// 		 .antMatchers("/saveOperation").hasRole("ADMIN")
//         .anyRequest().authenticated()
//         .and()
//         .formLogin()
//         .loginPage("/login")
//         .usernameParameter("username")
//         .passwordParameter("password")
//         .defaultSuccessUrl("/",true)
//         .and()
//         .logout()
//         .logoutRequestMatcher(new AntPathRequestMatcher("/login"))
//         .logoutUrl("/login")
//         .logoutSuccessUrl("/login?logout")
//         .invalidateHttpSession(true);
	}
}
