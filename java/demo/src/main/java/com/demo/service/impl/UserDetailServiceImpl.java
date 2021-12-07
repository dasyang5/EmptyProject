package com.demo.service.impl;

import com.demo.bean.UserDetail;
import com.demo.entity.User;
import com.demo.entity.UserRole;
import com.demo.repository.UserRepository;
import com.demo.repository.UserRoleRepository;
import com.demo.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex
 * @date 2021/9/18 9:34
 */
@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("Not found username:" + s);
        }
        UserDetail userDetail = new UserDetail();
        userDetail.setUser(user);

        List<String> authorities = new ArrayList<>();
        for (UserRole userRole : userRoleRepository.findUserRolesByUserId(user.getUserId())) {
            authorities.add(userRole.getRole().getRoleAuthKey());
        }

        userDetail.setRoles(authorities);

        return userDetail;
    }
}
