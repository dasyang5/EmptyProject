package com.demo.config.auto;

import com.demo.bean.ConfigEnum;
import com.demo.config.constant.MenuType;
import com.demo.entity.*;
import com.demo.service.ConfigService;
import com.demo.utils.jdbc.JDBCUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Alex
 * @date 2021/9/23 14:26
 */
@Component
@Slf4j
public class InitSystemData implements ApplicationRunner {

    @Autowired
    private JDBCUtil jdbcUtil;

    @Autowired
    ConfigService configService;

    private Date date = new Date();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (configService.isNeedToInitData()) {
            log.info("-------------------------------------------");
            log.info("执行初始化数据");
            jdbcUtil.batchInsert(initConfig());
            jdbcUtil.batchInsert(initUser());
            jdbcUtil.batchInsert(initRole());
            jdbcUtil.batchInsert(initUserRole());
            jdbcUtil.batchInsert(initMenuResource());
            jdbcUtil.batchInsert(initRoleMenuResource());
            log.info("结束初始化数据");
            log.info("-------------------------------------------");
        }
    }

    private List initConfig() {
        List<Config> list = new ArrayList<>();
        list.add(new Config(ConfigEnum.INSERT_INIT_DATA_OFF));
        return list;
    }

    private List initUser() {
        List<User> list = new ArrayList<>();
        list.add(User.builder().userId("0").username("admin").password("$2a$10$OPFhxiPSjqHv5FSQy8IOfOeTBHuK8usRLHPXVUWkxGnRZpl6qG2cG").accountNonExpired(true).accountNonLocked(true).credentialsNonExpired(true).enabled(true).creTime(date).updTime(date).build());
        return list;
    }

    private List initRole() {
        List<Role> list = new ArrayList<>();
        list.add(Role.builder().roleId("1").roleName("管理员").roleAuthKey("admin").creTime(date).updTime(date).build());
//        list.add(Role.builder().roleId("2").roleName("机构用户").roleAuthKey("user").creTime(date).updTime(date).build());
        return list;
    }

    private List initUserRole() {
        List<UserRole> list = new ArrayList<>();
        list.add(UserRole.builder().userRoleId("0").userId("0").roleId("1").creTime(date).updTime(date).build());
        return list;
    }

    private List initMenuResource() {
        List<MenuResource> list = new ArrayList<>();

        list.add(MenuResource.builder().menuType(MenuType.MENU).menuResourceId("2").parentId(null).menuName("首页").menuNameEn("Home").menuPath("/system/home").menuOrder(1).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.API).menuResourceId("21").parentId("2").menuName("查询").menuNameEn("list").menuPath("/api/home/list").menuOrder(1).creTime(date).updTime(date).build());

        list.add(MenuResource.builder().menuType(MenuType.FOLDER).menuResourceId("1").parentId(null).menuName("系统管理").menuNameEn("System").menuPath("/system").menuOrder(5).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.MENU).menuResourceId("11").parentId("1").menuName("角色管理").menuNameEn("Roles").menuPath("/role").menuOrder(1).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.API).menuResourceId("111").parentId("11").menuName("查询").menuNameEn("list").menuPath("/api/role/list").menuOrder(1).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.API).menuResourceId("112").parentId("11").menuName("增加").menuNameEn("add").menuPath("/api/role/add").menuOrder(1).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.API).menuResourceId("113").parentId("11").menuName("删除").menuNameEn("delete").menuPath("/api/role/delete").menuOrder(1).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.API).menuResourceId("114").parentId("11").menuName("修改").menuNameEn("edit").menuPath("/api/role/edit").menuOrder(4).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.MENU).menuResourceId("12").parentId("1").menuName("权限管理").menuNameEn("Menus").menuPath("/menuResource").menuOrder(2).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.API).menuResourceId("121").parentId("12").menuName("查询").menuNameEn("list").menuPath("/api/menuResource/list").menuOrder(1).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.API).menuResourceId("122").parentId("12").menuName("增加").menuNameEn("add").menuPath("/api/menuResource/add").menuOrder(2).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.API).menuResourceId("123").parentId("12").menuName("删除").menuNameEn("delete").menuPath("/api/menuResource/delete").menuOrder(3).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.API).menuResourceId("124").parentId("12").menuName("修改").menuNameEn("edit").menuPath("/api/menuResource/edit").menuOrder(4).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.MENU).menuResourceId("13").parentId("1").menuName("机构管理").menuNameEn("Organizations").menuPath("/organ").menuOrder(3).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.API).menuResourceId("131").parentId("13").menuName("查询").menuNameEn("list").menuPath("/api/organ/list").menuOrder(1).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.API).menuResourceId("132").parentId("13").menuName("增加").menuNameEn("add").menuPath("/api/organ/add").menuOrder(2).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.API).menuResourceId("133").parentId("13").menuName("删除").menuNameEn("delete").menuPath("/api/organ/delete").menuOrder(3).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.API).menuResourceId("134").parentId("13").menuName("修改").menuNameEn("edit").menuPath("/api/organ/edit").menuOrder(4).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.MENU).menuResourceId("14").parentId("1").menuName("用户管理").menuNameEn("Users").menuPath("/user").menuOrder(4).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.API).menuResourceId("141").parentId("14").menuName("查询").menuNameEn("list").menuPath("/api/user/list").menuOrder(1).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.API).menuResourceId("142").parentId("14").menuName("增加").menuNameEn("add").menuPath("/api/user/add").menuOrder(2).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.API).menuResourceId("143").parentId("14").menuName("删除").menuNameEn("delete").menuPath("/api/user/delete").menuOrder(3).creTime(date).updTime(date).build());
        list.add(MenuResource.builder().menuType(MenuType.API).menuResourceId("144").parentId("14").menuName("修改").menuNameEn("edit").menuPath("/api/user/edit").menuOrder(4).creTime(date).updTime(date).build());

        return list;
    }

    private List initRoleMenuResource() {
        List<RoleMenuResource> list = new ArrayList<>();
        //Admin
        list.add(RoleMenuResource.builder().roleMenuResourceId("1").menuResourceId("21").roleId("1").creTime(date).updTime(date).build());

        list.add(RoleMenuResource.builder().roleMenuResourceId("2").menuResourceId("111").roleId("1").creTime(date).updTime(date).build());
        list.add(RoleMenuResource.builder().roleMenuResourceId("3").menuResourceId("112").roleId("1").creTime(date).updTime(date).build());
        list.add(RoleMenuResource.builder().roleMenuResourceId("4").menuResourceId("113").roleId("1").creTime(date).updTime(date).build());
        list.add(RoleMenuResource.builder().roleMenuResourceId("5").menuResourceId("114").roleId("1").creTime(date).updTime(date).build());

        list.add(RoleMenuResource.builder().roleMenuResourceId("6").menuResourceId("121").roleId("1").creTime(date).updTime(date).build());
        list.add(RoleMenuResource.builder().roleMenuResourceId("7").menuResourceId("122").roleId("1").creTime(date).updTime(date).build());
        list.add(RoleMenuResource.builder().roleMenuResourceId("8").menuResourceId("123").roleId("1").creTime(date).updTime(date).build());
        list.add(RoleMenuResource.builder().roleMenuResourceId("9").menuResourceId("124").roleId("1").creTime(date).updTime(date).build());

        list.add(RoleMenuResource.builder().roleMenuResourceId("10").menuResourceId("131").roleId("1").creTime(date).updTime(date).build());
        list.add(RoleMenuResource.builder().roleMenuResourceId("11").menuResourceId("132").roleId("1").creTime(date).updTime(date).build());
        list.add(RoleMenuResource.builder().roleMenuResourceId("12").menuResourceId("133").roleId("1").creTime(date).updTime(date).build());
        list.add(RoleMenuResource.builder().roleMenuResourceId("13").menuResourceId("134").roleId("1").creTime(date).updTime(date).build());

        list.add(RoleMenuResource.builder().roleMenuResourceId("14").menuResourceId("141").roleId("1").creTime(date).updTime(date).build());
        list.add(RoleMenuResource.builder().roleMenuResourceId("15").menuResourceId("142").roleId("1").creTime(date).updTime(date).build());
        list.add(RoleMenuResource.builder().roleMenuResourceId("16").menuResourceId("143").roleId("1").creTime(date).updTime(date).build());
        list.add(RoleMenuResource.builder().roleMenuResourceId("17").menuResourceId("144").roleId("1").creTime(date).updTime(date).build());

        return list;
    }

}
