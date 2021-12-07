package com.demo.repository;

import com.demo.entity.Organ;
import com.demo.config.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alex
 * @date 2021/9/26 13:33
 */
@Repository
public interface OrganRepository extends BaseRepository<Organ, String> {

}
