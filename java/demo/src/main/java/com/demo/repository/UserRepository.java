package com.demo.repository;

import com.demo.entity.User;
import com.demo.config.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alex
 * @date 2021/9/18 10:44
 */
@Repository
public interface UserRepository extends BaseRepository<User, String> {

    User getUserByUsername(String username);

}
