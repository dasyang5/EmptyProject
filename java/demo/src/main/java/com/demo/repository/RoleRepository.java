package com.demo.repository;

import com.demo.entity.Role;
import com.demo.config.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alex
 * @date 2021/9/23 10:52
 */
@Repository
public interface RoleRepository extends BaseRepository<Role, String> {

}
