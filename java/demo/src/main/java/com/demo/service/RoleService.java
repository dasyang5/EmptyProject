package com.demo.service;

import com.demo.config.jpa.BaseService;
import com.demo.bean.PageBean;
import com.demo.entity.Role;

import java.util.List;

/**
 * @author Alex
 * @date 2021/9/23 10:56
 */
public interface RoleService extends BaseService<Role, String> {

    List<Role> findAdminRoles(Role role, PageBean pageBean);

    long countAdminRoles(Role role);
}
