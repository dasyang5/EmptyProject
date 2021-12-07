package com.demo.config.security.handler;

import com.alibaba.fastjson.JSONArray;
import com.demo.entity.User;
import com.demo.bean.RestResponse;
import com.demo.bean.UserDetail;
import com.demo.config.security.jwt.JwtTokenProvider;
import com.demo.service.RoleMenuResourceService;
import com.demo.service.UserService;
import com.demo.utils.respone.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author Alex
 * @date 2021/9/18 9:38
 */
@Service("authenticationSuccessHandler")
@Slf4j
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMenuResourceService roleMenuResourceService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        String username = ((UserDetail)authentication.getPrincipal()).getUsername();

        User user = userService.findByUsername(username);

        if (user != null) {

            user.setErrorPwdTime(0L);
            user.setLoginTime(new Date());

            userService.update(user);

            JSONArray array = roleMenuResourceService.findUserResourceArray(user.getUserId());

            user.setPassword(null);

            ResponseUtil.write(httpServletResponse, RestResponse.success()
                    .fluentPut("tokenType", "Bearer")
                    .fluentPut("token", JwtTokenProvider.createJwtToken(authentication))
                    .fluentPut("user", user)
                    .fluentPut("auth", array)
            );
        } else {
            ResponseUtil.write(httpServletResponse, RestResponse.error("用户不存在"));
        }
    }

}
