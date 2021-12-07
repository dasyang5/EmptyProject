package com.demo.service;

import com.demo.config.jpa.BaseService;
import com.demo.entity.Config;

/**
 * @author Alex
 * @date 2021/9/23 14:34
 */
public interface ConfigService extends BaseService<Config, String> {

    boolean isNeedToInitData();

}
