package com.demo.repository;

import com.demo.entity.MenuResource;
import com.demo.config.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alex
 * @date 2021/9/24 13:54
 */
@Repository
public interface MenuResourceRepository extends BaseRepository<MenuResource, String> {

    List<MenuResource> findMenuResourcesByParentIdOrderByMenuOrder(String parentId);

    List<MenuResource> findMenuResourcesByParentIdIsNullOrderByMenuOrder();

}
