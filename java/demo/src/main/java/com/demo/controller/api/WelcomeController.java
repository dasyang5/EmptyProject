package com.demo.controller.api;

import com.demo.controller.BaseController;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alex
 * @date 2021/12/10 9:04
 */
@RequestMapping("/api/welcome")
@RestController
public class WelcomeController extends BaseController {

    @Autowired
    UserService userService;


}
