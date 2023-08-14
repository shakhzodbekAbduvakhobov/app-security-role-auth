package uz.center.appsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("Director").password(passwordEncoder().encode("123")).roles("DIRECTOR").authorities("READ_ALL_PRODUCT", "READ_ONE_PRODUCT", "ADD_PRODUCT", "EDIT_PRODUCT", "DELETE_PRODUCT")
                .and()
                .withUser("Director2").password(passwordEncoder().encode("123")).roles("DIRECTOR").authorities("READ_ALL_PRODUCT", "READ_ONE_PRODUCT", "ADD_PRODUCT")
                .and()
                .withUser("User").password(passwordEncoder().encode("123")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers(HttpMethod.DELETE, "/api/product/*").hasAuthority("DELETE_PRODUCT")
//                .antMatchers("/api/product/**").hasAnyAuthority("READ_ALL_PRODUCT", "READ_ONE_PRODUCT", "ADD_PRODUCT", "EDIT_PRODUCT", "DELETE_PRODUCT")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
