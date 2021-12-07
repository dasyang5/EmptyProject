package com.demo.service.impl;

import com.demo.config.jpa.BaseServiceImpl;
import com.demo.repository.OrganRepository;
import com.demo.entity.Organ;
import com.demo.service.OrganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Alex
 * @date 2021/9/26 13:34
 */
@Service
public class OrganServiceImpl extends BaseServiceImpl<Organ, String> implements OrganService {

    @Autowired
    private OrganRepository organRepository;

}
