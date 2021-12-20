package com.demo.controller;

import com.demo.bean.PageBean;
import com.demo.bean.RestResponse;
import com.demo.config.constant.SystemConst;
import com.demo.config.locale.MyI18n;
import com.demo.config.properties.ApplicationYML;
import com.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Alex
 * @date 2021/9/24 15:37
 */
public class BaseController {

    @Autowired
    public HttpSession httpSession;

    @Autowired
    public MyI18n myI18n;

    @Autowired
    public ApplicationYML applicationYML;

    @Autowired
    public HttpServletRequest httpServletRequest;

    public User getCurrentUser() {
        return (User) httpSession.getAttribute(SystemConst.CURRENT_USER);
    }

    public String getCurrentUserId() {
        return getCurrentUser().getUserId();
    }

    public String getCurrentOrganId() {
        return (String) httpSession.getAttribute(SystemConst.CURRENT_ORGAN_ID);
    }

    public RestResponse error(String msg) {
        return RestResponse.error(msg);
    }

    public RestResponse success() {
        return RestResponse.success();
    }

    public PageBean getPageBean() {

        PageBean pageBean = new PageBean();

        try {
            int pageSize = Integer.parseInt(httpServletRequest.getParameter(SystemConst.PAGE_SIZE));
            pageBean.setPageSize(pageSize);
        } catch (Exception e) {
            pageBean.setPageSize(10);
        }

        try {
            int page = Integer.parseInt(httpServletRequest.getParameter(SystemConst.CURRENT_PAGE));
            pageBean.setPage(page);
        } catch (Exception e) {
            pageBean.setPage(1);
        }

        return pageBean;
    }

}
