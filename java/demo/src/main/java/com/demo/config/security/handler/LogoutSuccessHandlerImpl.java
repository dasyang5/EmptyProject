package com.demo.config.security.handler;

import com.demo.bean.RestResponse;
import com.demo.config.security.jwt.JwtTokenProvider;
import com.demo.utils.respone.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alex
 * @date 2021/9/18 9:41
 */
@Slf4j
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        String token = JwtTokenProvider.getJwtFromRequest(httpServletRequest);

        if (StringUtils.hasLength(token)) {
//            log.info("用户[{}]于[{}]注销成功!", JwtTokenProvider.getUsernameFromJwt(token, SecurityConst.TOKEN_SECRET_KEY), new Date());
        }

       /* if (authentication != null) {
            log.info("用户[{}]于[{}]注销成功!", ((UserDetail) authentication.getPrincipal()).getUsername(), new Date());
        }*/

        ResponseUtil.write(httpServletResponse, RestResponse.success("登出成功"));
    }
}
