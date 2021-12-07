package com.demo.repository;

import com.demo.entity.RoleMenuResource;
import com.demo.config.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alex
 * @date 2021/9/24 13:54
 */
@Repository
public interface RoleMenuResourceRepository extends BaseRepository<RoleMenuResource, String> {

    List<RoleMenuResource> findRoleMenuResourcesByRoleId(String roleId);

}
