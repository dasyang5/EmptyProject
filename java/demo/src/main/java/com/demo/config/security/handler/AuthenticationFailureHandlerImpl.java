package com.demo.config.security.handler;

import com.alibaba.fastjson.JSONArray;
import com.demo.config.constant.SecurityConst;
import com.demo.config.constant.SystemConst;
import com.demo.config.locale.MyI18n;
import com.demo.entity.User;
import com.demo.bean.RestResponse;
import com.demo.service.RoleMenuResourceService;
import com.demo.service.UserService;
import com.demo.utils.respone.ResponseUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author Alex
 * @date 2021/9/18 9:40
 */
@Service("authenticationFailHandler")
@Slf4j
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMenuResourceService roleMenuResourceService;

    @Autowired
    MyI18n myI18n;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");

        User user = userService.findByUsername(username);

        if (user != null) {
            if (e instanceof BadCredentialsException) {
                /**
                 * 记录密码错误次数
                 */
                user.setErrorPwdTime(user.getErrorPwdTime() == null ? 1 : (user.getErrorPwdTime() + 1));

                /**
                 * 密码错误n次，就锁定账号
                 */
                if (user.getErrorPwdTime() >= SystemConst.MAX_PWD_ERROR_TIME) {
                    user.setAccountNonLocked(false);
                }

                ResponseUtil.write(httpServletResponse, RestResponse.error(myI18n.get("user.login.wrong.hit1")));
            } else if (e instanceof LockedException) {
                //如果一小时后重试
                if ((new Date().getTime() - user.getLoginTime().getTime()) > SystemConst.ACCOUNT_LOCK_TIME) {
                    user.setAccountNonLocked(true);
                    if (new BCryptPasswordEncoder().matches(user.getPassword(), password)) {
                        user.setErrorPwdTime(0L);
                        user.setLoginTime(new Date());

                        JSONArray array = roleMenuResourceService.findUserResourceArray(user.getUserId());
                        user.setPassword(null);

                        String token = Jwts.builder()
                                .setSubject(username)
                                .setExpiration(new Date(System.currentTimeMillis() + SecurityConst.TOKEN_EXPIRED))
                                .setIssuedAt(new Date())
                                .signWith(SignatureAlgorithm.HS512, SecurityConst.TOKEN_SECRET_KEY)
                                .compact();

                        ResponseUtil.write(httpServletResponse, RestResponse.success()
                                .fluentPut("tokenType", "Bearer")
                                .fluentPut("token", token)
                                .fluentPut("user", user)
                                .fluentPut("auth", array)
                        );
                    } else {
                        user.setErrorPwdTime(3L);
                    }
                } else {
                    ResponseUtil.write(httpServletResponse, RestResponse.error(myI18n.get("user.login.wrong.hit2")));
                }
            } else if (e instanceof UsernameNotFoundException) {
                ResponseUtil.write(httpServletResponse, RestResponse.error(myI18n.get("user.login.wrong.hit1")));
            } else if (e instanceof DisabledException) {
                ResponseUtil.write(httpServletResponse, RestResponse.error(myI18n.get("user.login.wrong.hit3")));
            } else {
                ResponseUtil.write(httpServletResponse, RestResponse.error(e.getMessage()));
            }
            user.setLoginTime(new Date());
            userService.update(user);
        } else {
            ResponseUtil.write(httpServletResponse, RestResponse.error(myI18n.get("user.login.wrong.hit1")));
        }
    }

}
