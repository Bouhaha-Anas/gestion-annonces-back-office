package com.epi.pfa.utilities;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	DataSource dataSource;
	
	@Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.jdbcAuthentication()
				.usersByUsernameQuery("select login, mot_de_passe, enabled from comptes where login=?")
				.authoritiesByUsernameQuery("select login, role from comptes where login=?")
				.dataSource(dataSource);	
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http
			.authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/resultatRecherche","/profil","/modificationProfil", "/resultatRechercheClients", "/resultatRechercheEntrepreneurs", "/resultatRechercheCategories").authenticated()
			.antMatchers("/nouvelAdministrateur", "/administrateurs", "/modificationAdministrateur/{id}", "desactiverAdministrateur/{id}", "activerAdministrateur/{id}", "/resultatRechercheAdministrateurs").hasAuthority("SUPERADMINISTRATEUR")
			.antMatchers("/home", "/categories", "/nouvelleCategorie", "/modificationCategorie/{id}", "/entrepreneurs", "/modificationEntrepreneur/{id}", "desactiverEntrepreneur/{id}", "activerEntrepreneur/{id}", "/clients", "/modificationClient/{id}", "desactiverClient/{id}", "activerClient/{id}", "/offres", "desactiverOffre/{id}" ).hasAnyAuthority("SUPERADMINISTRATEUR","ADMINISTRATEUR")
			.anyRequest()
				.authenticated()
			.and()
			.csrf()
				.disable().formLogin()
			.loginPage("/login")
				.failureHandler(authenticationFailureHandler)
				.defaultSuccessUrl("/home")
				.usernameParameter("login")
				.passwordParameter("motDePasse")
			.and()
			.logout()
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")
			.and()
			.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler);	
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() 
	{
	    return new HttpSessionEventPublisher();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception 
	{
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/fonts/**", "/assets/**", "/webarch/**", "/cdn-cgi/**");
	}
	
}
