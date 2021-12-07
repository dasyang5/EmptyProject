package com.demo.repository;

import com.demo.entity.UserRole;
import com.demo.config.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alex
 * @date 2021/9/23 10:53
 */
@Repository
public interface UserRoleRepository extends BaseRepository<UserRole, String> {

    List<UserRole> findUserRolesByUserId(String userId);

}
