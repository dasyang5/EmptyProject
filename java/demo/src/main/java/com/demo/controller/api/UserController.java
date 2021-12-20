package com.demo.controller.api;

import com.demo.config.constant.SystemConst;
import com.demo.controller.BaseController;
import com.demo.entity.User;
import com.demo.entity.UserRole;
import com.demo.bean.RestResponse;
import com.demo.bean.TableData;
import com.demo.service.RoleService;
import com.demo.service.UserRoleService;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author Alex
 * @date 2021/9/18 9:56
 */
@RestController
@RequestMapping("api/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @GetMapping("list")
    public RestResponse list(User user) {

        user.setOrganId(getCurrentOrganId());

        TableData<User> tableData = userService.findUserList(user, getPageBean());

        return RestResponse.success(tableData);

    }

    @PostMapping("add")
    public RestResponse add(User user, String roleId) {

        if (userService.findByUsername(user.getUsername()) != null) {
            return RestResponse.error(myI18n.get("user.add.hit.1"));
        }

        user.setPassword(new BCryptPasswordEncoder().encode(SystemConst.DEFAULT_PASSWORD));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setCreTime(new Date());
        user.setUpdTime(new Date());

        user = userService.save(user);

        UserRole userRole = UserRole.builder().roleId(roleId).userId(user.getUserId()).creTime(new Date()).updTime(new Date()).build();

        userRoleService.save(userRole);

        return RestResponse.success();
    }

    @GetMapping("get")
    public RestResponse get(String username) {
        User user = userService.findByUsername(username);
        return RestResponse.success().fluentPut("user", user);
    }

    @GetMapping("getProfileData")
    public RestResponse getProfileData() {

        User user = userService.get(getCurrentUser().getUserId());

        return RestResponse.success().fluentPut("user", user).fluentPut("organ", user.getOrgan());
    }

    @DeleteMapping("delete")
    public RestResponse delete(String userId) {

        userService.delete(userId);

        userRoleService.deleteByUserId(userId);

        return RestResponse.success();
    }

    @PostMapping("changePassword")
    public RestResponse changePassword(String oldPass, String pass) {

        User user = userService.get(getCurrentUser().getUserId());
        if (new BCryptPasswordEncoder().matches(oldPass, user.getPassword())) {

            user.setPassword(new BCryptPasswordEncoder().encode(pass));
            userService.update(user);

            return RestResponse.success();
        } else {
            return RestResponse.error(myI18n.get("user.wrong.password"));
        }
    }

    @PostMapping("reset")
    public RestResponse reset(String userId) {
        User user = userService.get(userId);

        user.setErrorPwdTime(0L);
        user.setPassword(new BCryptPasswordEncoder().encode(SystemConst.DEFAULT_PASSWORD));
        user.setAccountNonLocked(true);

        userService.update(user);

        return RestResponse.success();
    }

    @PostMapping("disable")
    public RestResponse disable(String userId) {
        User user = userService.get(userId);

        user.setEnabled(false);

        userService.update(user);

        return RestResponse.success();
    }

    @PostMapping("enable")
    public RestResponse enable(String userId) {
        User user = userService.get(userId);

        user.setEnabled(true);
        userService.update(user);

        return RestResponse.success();
    }

    @PostMapping("updateTheme")
    public RestResponse updateTheme(String theme) {

        userService.customerUpdate()
                .set("theme", theme)
                .where("userId", getCurrentUserId())
                .update();
        return success();

    }

}
