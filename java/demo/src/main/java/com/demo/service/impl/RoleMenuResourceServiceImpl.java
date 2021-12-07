package com.demo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.config.constant.MenuType;
import com.demo.entity.RoleMenuResource;
import com.demo.entity.User;
import com.demo.entity.UserRole;
import com.demo.repository.RoleMenuResourceRepository;
import com.demo.repository.UserRoleRepository;
import com.demo.utils.jdbc.JDBCUtil;
import com.demo.service.MenuResourceService;
import com.demo.service.RoleMenuResourceService;
import com.demo.config.jpa.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Alex
 * @date 2021/9/24 13:53
 */
@Service
public class RoleMenuResourceServiceImpl extends BaseServiceImpl<RoleMenuResource, String> implements RoleMenuResourceService {

    @Autowired
    private RoleMenuResourceRepository roleMenuResourceRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private MenuResourceService menuResourceService;

    @Autowired
    private JDBCUtil jdbcUtil;

    @Override
    public JSONArray findUserResourceArray(String userId) {

        JSONArray result = menuResourceService.findTreeData();

        List<UserRole> userRoleList = userRoleRepository.findUserRolesByUserId(userId);

        for (UserRole userRole : userRoleList) {
            findByRoleId(userRole.getRoleId(), result);
        }

        return result;
    }

    @Override
    public JSONArray findRoleResourceArray(String roleId, User currentUser) {

        JSONArray array = menuResourceService.findTreeData();

        findByRoleId(roleId, array);

        if (!currentUser.getUsername().equals("admin")) {
            List<UserRole> userRoleList = userRoleRepository.findUserRolesByUserId(currentUser.getUserId());

            List<RoleMenuResource> allAuthorities = new ArrayList<>();

            for (UserRole userRole : userRoleList) {
                List<RoleMenuResource> roleMenuResourceList = roleMenuResourceRepository.findRoleMenuResourcesByRoleId(userRole.getRoleId());

                allAuthorities.addAll(roleMenuResourceList);
            }


            Iterator iterator = array.iterator();

            while (iterator.hasNext()) {
                JSONObject object = (JSONObject) iterator.next();
                if (!hasApi(object, allAuthorities)) {
                    iterator.remove();
                }
            }

        }

        return array;
    }

    private boolean hasApi(JSONObject object, List<RoleMenuResource> allAuthorities) {
        if (object.getString("type").equals(MenuType.MENU)) {
            //如果是menu，那么判断是否有有权限的api
            JSONArray children = object.getJSONArray("child");
            for (Object child : children) {
                JSONObject api = (JSONObject) child;
                for (RoleMenuResource roleMenuResource : allAuthorities) {
                    if (roleMenuResource.getMenuResourceId().equals(api.getString("id"))) {
                        return true;
                    }
                }
            }
            return false;
        } else if (object.getString("type").equals(MenuType.FOLDER)) {
            //FOLDER下面只能是Folder或者API
            JSONArray children = object.getJSONArray("child");
            Iterator<Object> iterator = children.iterator();
            boolean hasAuth = false;
            while (iterator.hasNext()) {
                JSONObject c = (JSONObject) iterator.next();
                boolean hasApi = hasApi(c, allAuthorities);
                hasAuth = hasAuth | hasApi;
                if (!hasApi) {
                    iterator.remove();
                }
            }
            return hasAuth;
        } else {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void updateRoleResource(List<LinkedHashMap> allMaps, List<LinkedHashMap> hafMaps, String roleId) {

        Date date = new Date();

        Map<String, Object> params = new HashMap<>();
        params.put("roleId", roleId);
        roleMenuResourceRepository.executeUpdate("delete from RoleMenuResource where roleId = :roleId", params);

        List<LinkedHashMap> all = new ArrayList<>();
        all.addAll(allMaps);
        all.addAll(hafMaps);
        List<RoleMenuResource> list = new ArrayList<>();
        for (LinkedHashMap linkedHashMap : all) {
            String type = (String) linkedHashMap.get("type");
            if (type.equals(MenuType.API)) {
                list.add(
                        RoleMenuResource.builder()
                                .roleMenuResourceId(UUID.randomUUID().toString().replace("-", ""))
                                .menuResourceId((String) linkedHashMap.get("id"))
                                .roleId(roleId)
                                .creTime(date)
                                .updTime(date)
                                .build()
                );
            }
        }

        jdbcUtil.batchInsert(list);

    }

    @Override
    public List<RoleMenuResource> findUserAllApi(String userId) {

        List<UserRole> userRoleList = userRoleRepository.findUserRolesByUserId(userId);

        List<RoleMenuResource> result = new ArrayList<>();

        for (UserRole userRole : userRoleList) {
            List<RoleMenuResource> list = roleMenuResourceRepository.findRoleMenuResourcesByRoleId(userRole.getRoleId());
            result.addAll(list);
        }

        return result;
    }

    private void findByRoleId(String roleId, JSONArray array) {

        List<RoleMenuResource> roleMenuResourceList = roleMenuResourceRepository.findRoleMenuResourcesByRoleId(roleId);

        setAuth(array, roleMenuResourceList);

    }

    private boolean setAuth(JSONArray array, List<RoleMenuResource> roleMenuResourceList) {

        boolean childHasAuth = false;

        for (Object arrayObj : array) {
            JSONObject obj = (JSONObject) arrayObj;

            String id = obj.getString("id");

            boolean hasAuth = hasAuth(roleMenuResourceList, id);
            boolean hasChildAuth = false;

            JSONArray child = obj.getJSONArray("child");

            if (child != null && child.size() > 0) {
                hasChildAuth = setAuth(child, roleMenuResourceList);
            }
            if (hasAuth || hasChildAuth) {
                childHasAuth = true;
                obj.put("hasAuth", true);
            } else {
                obj.put("hasAuth", false);
            }

        }

        return childHasAuth;

    }

    private boolean hasAuth(List<RoleMenuResource> roleMenuResourceList, String id) {

        for (RoleMenuResource rmr : roleMenuResourceList) {
            if (rmr.getMenuResourceId().equals(id)) {
                return true;
            }
        }

        return false;

    }



}
