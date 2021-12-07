package com.demo.service.impl;

import com.demo.config.jpa.BaseServiceImpl;
import com.demo.repository.RoleRepository;
import com.demo.bean.PageBean;
import com.demo.entity.Role;
import com.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alex
 * @date 2021/9/23 10:57
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, String> implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> findAdminRoles(Role role, PageBean pageBean) {

        String hql = "from Role where organId is null ";

        Map<String, Object> params = new HashMap<>();

        if (role != null) {
            if (StringUtils.hasLength(role.getRoleName())) {
                hql += "and roleName = :roleName";
                params.put("roleName", role.getRoleName());
            }
        }

        return roleRepository.findByHql(hql, params, pageBean);
    }

    @Override
    public long countAdminRoles(Role role) {

        String hql = "select count(1) from Role where organId is null ";

        Map<String, Object> params = new HashMap<>();

        if (role != null) {
            if (StringUtils.hasLength(role.getRoleName())) {
                hql += "and roleName = :roleName";
                params.put("roleName", role.getRoleName());
            }
        }

        return roleRepository.countByHql(hql, params);
    }

}
