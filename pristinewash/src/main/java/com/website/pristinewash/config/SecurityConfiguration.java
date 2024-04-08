package com.website.pristinewash.config;

import com.website.pristinewash.serviceImp.CustomUserDetailsService;
import com.website.pristinewash.utility.CustomAuthenticationProvider;
import com.website.pristinewash.utility.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(customAuthenticationProvider);
    }

    //        http
//                .csrf().disable() // Disable CSRF protection
//                .authorizeRequests()
//                .anyRequest().permitAll(); // Allow access to all requests
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/home/**").permitAll()
                .antMatchers("/sendotp/**").permitAll()
                .antMatchers("/login.html/**").permitAll()
                .antMatchers("/address/**").permitAll()
                .antMatchers("/category/**").permitAll()
                .antMatchers("/otp/**").permitAll()
                .antMatchers("/test/anyone").authenticated()
                .antMatchers("/schedule-call").authenticated()
                .antMatchers("/cart/**").authenticated()
                .antMatchers("/product_by_category/**").authenticated()
                .antMatchers("/user_cart/**").authenticated()
                .antMatchers("/test/admin").hasRole("ADMIN")
                .antMatchers("/test/user").hasRole("USER")
                .antMatchers("/test/useradmin").hasRole("USER")
                .antMatchers("/admin/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                    .loginPage("/login.html")
                    .permitAll()
                .and()
                    .logout()
                    .permitAll();
//                .and()
//                .httpBasic();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
//        return new CustomAuthenticationSuccessHandler();
//    }

}

