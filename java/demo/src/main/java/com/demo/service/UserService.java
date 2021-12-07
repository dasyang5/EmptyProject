package com.demo.service;

import com.demo.config.jpa.BaseService;
import com.demo.entity.User;
import com.demo.bean.PageBean;
import com.demo.bean.TableData;

/**
 * @author Alex
 * @date 2021/9/18 11:03
 */
public interface UserService extends BaseService<User, String> {
    User findByUsername(String username);

    TableData<User> findUserList(User user, PageBean pageBean);
}
