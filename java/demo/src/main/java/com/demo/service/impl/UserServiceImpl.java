package com.demo.service.impl;

import com.demo.entity.User;
import com.demo.repository.UserRepository;
import com.demo.bean.PageBean;
import com.demo.bean.TableData;
import com.demo.service.UserService;
import com.demo.config.jpa.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alex
 * @date 2021/9/18 11:03
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public TableData<User> findUserList(User user, PageBean pageBean) {

        StringBuilder hql = new StringBuilder(" from User where username != 'admin' ");
        Map<String, Object> params = new HashMap<>();


        if (user != null) {
            if (StringUtils.hasLength(user.getUserId())) {
                hql.append(" and userId = :userId ");
                params.put("userId", user.getUserId());
            }
            if (StringUtils.hasLength(user.getUsername())) {
                hql.append(" and username = :username ");
                params.put("username", user.getUsername());
            }
        }

        hql.append(" order by creTime desc ");

        TableData<User> tableData = new TableData<>();
        tableData.setCount(count(user) - 1);
        tableData.setData(userRepository.findByHql(hql.toString(), params, pageBean));

        return tableData;
    }
}
