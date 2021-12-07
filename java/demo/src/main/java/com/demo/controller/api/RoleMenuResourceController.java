package com.demo.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.demo.entity.User;
import com.demo.bean.RestResponse;
import com.demo.controller.BaseController;
import com.demo.service.RoleMenuResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alex
 * @date 2021/9/24 15:17
 */
@RestController
@RequestMapping("roleMenuResource")
public class RoleMenuResourceController extends BaseController {

    @Autowired
    private RoleMenuResourceService roleMenuResourceService;

    @GetMapping("getUserMenuResource")
    public RestResponse getUserMenuResource() {

        User user = getCurrentUser();

        JSONArray array = roleMenuResourceService.findUserResourceArray(user.getUserId());

        return RestResponse.success().fluentPut("rows", array);
    }

}
