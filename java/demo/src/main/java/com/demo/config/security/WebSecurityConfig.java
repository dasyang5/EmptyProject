package com.demo.config.security;

import com.demo.config.security.handler.LogoutSuccessHandlerImpl;
import com.demo.config.filter.JwtAuthenticationFilter;
import com.demo.service.UserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

/**
 * @author Alex
 * @date 2021/9/18 9:28
 */
@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailService userDetailService;

    @Autowired
    @Qualifier("authenticationSuccessHandler")
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    @Qualifier("authenticationFailHandler")
    private AuthenticationFailureHandler failHandler;

    @Autowired
    @Qualifier("authenticationEntryPointImpl")
    private AuthenticationEntryPoint entryPoint;

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // ??????????????????OPTIONS????????????????????????????????????????????????????????????OPTIONS??????????????????
        httpSecurity.cors().and().csrf().disable().authorizeRequests().
                //????????????????????????PreFlight??????
                        requestMatchers(CorsUtils::isPreFlightRequest).permitAll();


        httpSecurity
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "**/favicon.ico", "/index").permitAll()
                /**
                 * ??????????????????
                 */
                .antMatchers("/online/**").permitAll()
                /**
                 * ????????????????????????
                 */
                .anyRequest().authenticated()
                /**
                 * ????????????
                 */
                .and().formLogin().loginProcessingUrl("/login").successHandler(successHandler).failureHandler(failHandler)
                .permitAll()
                /**
                 * ????????????
                 */
                .and().logout().logoutUrl("/logout").permitAll().logoutSuccessHandler(new LogoutSuccessHandlerImpl())
                /**
                 * ???????????????
                 */
                .and().exceptionHandling().authenticationEntryPoint(entryPoint);

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
