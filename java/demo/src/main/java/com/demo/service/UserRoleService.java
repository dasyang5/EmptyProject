package com.demo.service;

import com.demo.entity.UserRole;
import com.demo.config.jpa.BaseService;

import java.util.List;

/**
 * @author Alex
 * @date 2021/9/23 10:56
 */
public interface UserRoleService extends BaseService<UserRole, String> {

    List<UserRole> findUserRolesByUserId(String userId);

    void deleteByUserId(String userId);

}
