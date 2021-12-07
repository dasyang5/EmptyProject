package com.demo.service;

import com.alibaba.fastjson.JSONArray;
import com.demo.config.jpa.BaseService;
import com.demo.entity.RoleMenuResource;
import com.demo.entity.User;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Alex
 * @date 2021/9/24 13:53
 */
public interface RoleMenuResourceService extends BaseService<RoleMenuResource, String> {

    /**
     * 查询用户所有的权限   用来在前端生成菜单
     * @param userId
     * @return
     */
    JSONArray findUserResourceArray(String userId);

    /**
     * 查询角色所有的权限   用来生成权限树
     * @param roleId
     * @return
     */
    JSONArray findRoleResourceArray(String roleId, User currentUser);

    void updateRoleResource(List<LinkedHashMap> allMaps, List<LinkedHashMap> hafMaps, String roleId);

    List<RoleMenuResource> findUserAllApi(String userId);

}
