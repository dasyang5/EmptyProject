package com.demo.repository;

import com.demo.entity.Config;
import com.demo.config.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alex
 * @date 2021/9/23 14:33
 */
@Repository
public interface ConfigRepository extends BaseRepository<Config, String> {
}
