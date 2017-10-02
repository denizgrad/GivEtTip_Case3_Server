package cloud.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

import cloud.service.security.CustomBasicAuthenticationEntryPoint;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter   {
	public static String REALM="CLOUD";

	@Autowired
	Environment env;
	
	@PostConstruct
	public void postConstruct() {
		System.out.println("SecurityConfig is up and running");
	}
//	@Autowired
//	UserDetailsService authenticationService;
	
//    @Autowired //In case of jpa authentication
//    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//    	auth.userDetailsService(authenticationService);
//    }
     
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser(env.getProperty("app.uname"))
			.password(env.getProperty("app.pass"))
			.roles("ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
 
	  http.csrf().disable()
	  	.authorizeRequests()
	  	.antMatchers("/main/login/**").hasRole("ADMIN")
	  	.antMatchers("/main/users/**").hasRole("ADMIN")
	  	.antMatchers("/main/records/**").hasRole("ADMIN")
		.and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint());
	  http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
 	}
	
	@Bean
	public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
		return new CustomBasicAuthenticationEntryPoint();
	}
	
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
