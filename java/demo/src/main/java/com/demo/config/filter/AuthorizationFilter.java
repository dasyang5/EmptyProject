package com.demo.config.filter;

import com.demo.config.constant.SystemConst;
import com.demo.config.security.jwt.JwtTokenProvider;
import com.demo.entity.RoleMenuResource;
import com.demo.entity.User;
import com.demo.service.RoleMenuResourceService;
import com.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Alex
 * @date 2021/9/24 15:20
 */
@Slf4j
@WebFilter(filterName = "AuthorizationFilter", urlPatterns = "/*")
public class AuthorizationFilter implements Filter {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMenuResourceService roleMenuResourceService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String token = JwtTokenProvider.getJwtFromRequest((HttpServletRequest) servletRequest);

        if (StringUtils.hasLength(token)) {
            String username = JwtTokenProvider.getUsernameFromJwt(token);

            User user = userService.findByUsername(username);

            HttpSession session = ((HttpServletRequest) servletRequest).getSession();

            session.setAttribute(SystemConst.CURRENT_USER, user);

            session.setAttribute(SystemConst.CURRENT_ORGAN_ID, user.getOrganId());

            String api = ((HttpServletRequest) servletRequest).getRequestURI();

            filterChain.doFilter(servletRequest, servletResponse);

/*            if (hasAuth(api, user.getUserId())) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ResponseUtil.write((HttpServletResponse) servletResponse, RestResponse.error("Unauthorized Path: " + api)
                        .fluentPut("code", "403"));
            }*/
        }
    }

    private boolean hasAuth(String api, String userId) {

        List<RoleMenuResource> roleMenuResourceList = roleMenuResourceService.findUserAllApi(userId);

        for (RoleMenuResource rmr : roleMenuResourceList) {
            if (rmr.getMenuResource().getMenuPath().equals(api)) {
                return true;
            }
        }

        return false;
    }
}
