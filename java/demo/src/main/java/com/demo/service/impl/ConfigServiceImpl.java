package com.demo.service.impl;

import com.demo.entity.Config;
import com.demo.repository.ConfigRepository;
import com.demo.bean.ConfigEnum;
import com.demo.service.ConfigService;
import com.demo.config.jpa.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Alex
 * @date 2021/9/23 14:34
 */
@Service
public class ConfigServiceImpl extends BaseServiceImpl<Config, String> implements ConfigService {

    @Autowired
    private ConfigRepository configRepository;

    @Override
    public boolean isNeedToInitData() {

        Optional<Config> configOption = configRepository.findById(ConfigEnum.INSERT_INIT_DATA_ON.getKey());

        return configOption.map(config -> config.getConfigValue().equals(ConfigEnum.INSERT_INIT_DATA_ON.getValue())).orElse(true);

    }

}
