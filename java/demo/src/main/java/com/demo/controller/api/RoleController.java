package com.demo.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.entity.User;
import com.demo.entity.UserRole;
import com.demo.bean.RestResponse;
import com.demo.bean.TableData;
import com.demo.config.constant.MenuType;
import com.demo.controller.BaseController;
import com.demo.entity.Role;
import com.demo.service.RoleMenuResourceService;
import com.demo.service.RoleService;
import com.demo.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Alex
 * @date 2021/9/26 15:30
 */
@RestController
@RequestMapping("api/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleMenuResourceService roleMenuResourceService;

    @GetMapping("list")
    public RestResponse list(Role role) {

        TableData<Role> tableData = null;

        User user = getCurrentUser();

        if (user.getUsername().equals("admin")) {
            tableData = new TableData<Role>();
            tableData.setData(roleService.findAdminRoles(role, getPageBean()));
            tableData.setCount(roleService.countAdminRoles(role));
        } else {
            role.setOrganId(getCurrentOrganId());

            tableData = roleService.findTableData(role, new String[]{"creTime desc"}, getPageBean());
        }

        return RestResponse.success(tableData);
    }

    @GetMapping("get")
    public RestResponse get(String roleId) {

        Role role = roleService.get(roleId);

        JSONArray resources = roleMenuResourceService.findRoleResourceArray(roleId, getCurrentUser());

        JSONArray checkedKey = new JSONArray();

        getCheckedKey(resources, checkedKey);

        return RestResponse.success().fluentPut("role", role).fluentPut("resources", resources).fluentPut("checkedKey", checkedKey);

    }

    @GetMapping("findRoleList")
    public RestResponse findRoleList() {

        List<Role> roleList;

        if (getCurrentUser().getUsername().equals("admin")) {
            roleList = roleService.findAdminRoles(null, null);
            roleList.removeIf(role -> role.getRoleName().equals("admin"));
        } else {
            Role role = new Role();
            role.setOrganId(getCurrentOrganId());
            roleList = roleService.find(role, null);
        }
        return RestResponse.success().fluentPut("rows", roleList);
    }

    private void getCheckedKey(JSONArray array, JSONArray result) {
        for (Object obj : array) {
            JSONObject object = (JSONObject) obj;
            if (object.getBoolean("hasAuth")&&object.getString("type").equals(MenuType.API)) {
                result.add(object.getString("id"));
            }
            JSONArray child = object.getJSONArray("child");
            if (child!=null && child.size() > 0) {
                getCheckedKey(child, result);
            }
        }
    }

    @PostMapping("add")
    public RestResponse add(Role role) {

        role.setRoleAuthKey(role.getRoleName());
        role.setOrganId(getCurrentOrganId());
        role.setCreTime(new Date());
        role.setUpdTime(new Date());

        roleService.save(role);

        return RestResponse.success();
    }

    @PostMapping("edit")
    public RestResponse edit(@RequestBody(required = false) Map paramMap) {

        String roleId = (String) paramMap.get("roleId");

        String roleName = (String) paramMap.get("roleName");

        String roleAuthKey = (String) paramMap.get("roleAuthKey");

        List<LinkedHashMap> auths = (List<LinkedHashMap>) paramMap.get("auths");
        List<LinkedHashMap> halfAuths = (List<LinkedHashMap>) paramMap.get("halfAuths");

        Role role = roleService.get(roleId);

        role.setRoleName(roleName);

        roleService.update(role);

        roleMenuResourceService.updateRoleResource(auths, halfAuths, roleId);

        return RestResponse.success();
    }

    @DeleteMapping("delete")
    public RestResponse delete(String roleId) {

        if (userRoleService.count(UserRole.builder().roleId(roleId).build()) > 0) {
            return RestResponse.error(myI18n.get("role.remove.hit.msg.1"));
        }

        roleService.delete(roleId);

        return RestResponse.success();
    }

}
