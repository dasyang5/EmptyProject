package com.demo.service.impl;

import com.demo.entity.UserRole;
import com.demo.repository.UserRoleRepository;
import com.demo.service.UserRoleService;
import com.demo.config.jpa.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alex
 * @date 2021/9/23 10:57
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole, String> implements UserRoleService {

    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> findUserRolesByUserId(String userId) {
        return userRoleRepository.findUserRolesByUserId(userId);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteByUserId(String userId) {

        String hql = "delete from UserRole where userId = :userId";
        Map<String, Object> params = new HashMap<>();

        params.put("userId", userId);

        userRoleRepository.executeUpdate(hql, params);
    }

}
