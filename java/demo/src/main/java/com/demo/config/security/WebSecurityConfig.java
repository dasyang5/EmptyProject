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
        // 当出现跨域的OPTIONS请求时，发现被拦截，加入下面设置可实现对OPTIONS请求的放行。
        httpSecurity.cors().and().csrf().disable().authorizeRequests().
                //处理跨域请求中的PreFlight请求
                        requestMatchers(CorsUtils::isPreFlightRequest).permitAll();


        httpSecurity
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "**/favicon.ico", "/index").permitAll()
                /**
                 * 交易请求验签
                 */
                .antMatchers("/online/**").permitAll()
                /**
                 * 所有请求要求授权
                 */
                .anyRequest().authenticated()
                /**
                 * 登录配置
                 */
                .and().formLogin().loginProcessingUrl("/login").successHandler(successHandler).failureHandler(failHandler)
                .permitAll()
                /**
                 * 登出配置
                 */
                .and().logout().logoutUrl("/logout").permitAll().logoutSuccessHandler(new LogoutSuccessHandlerImpl())
                /**
                 * 未授权处理
                 */
                .and().exceptionHandling().authenticationEntryPoint(entryPoint);

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
