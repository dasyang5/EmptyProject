package com.demo.config.filter;

import com.demo.config.security.jwt.JwtTokenProvider;
import com.demo.service.UserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alex
 * @date 2021/9/18 14:08
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = JwtTokenProvider.getJwtFromRequest(httpServletRequest);

        if (httpServletRequest.getRequestURI().startsWith("/online")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }

        if (token != null && JwtTokenProvider.validateToken(token)) {
            String username = JwtTokenProvider.getUsernameFromJwt(token);

            UserDetails userDetails = userDetailService.loadUserByUsername(username);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } else {
            log.error(httpServletRequest.getParameter("username") + " :Token is null");
        }
        super.doFilter(httpServletRequest, httpServletResponse, filterChain);

    }

    /*@Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/online", request.getServletPath());
    }*/
}
