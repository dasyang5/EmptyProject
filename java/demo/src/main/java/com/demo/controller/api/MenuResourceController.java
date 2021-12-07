package com.demo.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.demo.entity.MenuResource;
import com.demo.bean.RestResponse;
import com.demo.bean.TableData;
import com.demo.controller.BaseController;
import com.demo.service.MenuResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author Alex
 * @date 2021/9/26 14:12
 */
@RestController
@RequestMapping("api/menuResource")
public class MenuResourceController extends BaseController {

    @Autowired
    private MenuResourceService menuResourceService;

    @GetMapping("list")
    public RestResponse list(MenuResource menuResource) {

        TableData<MenuResource> tableData = menuResourceService.findTableData(menuResource, new String[]{"creTime desc"}, getPageBean());

        return RestResponse.success(tableData);
    }

    @GetMapping("findParentList")
    public RestResponse findParentList() {

        List<MenuResource> list = menuResourceService.findParentList();

        return RestResponse.success().fluentPut("rows", list);
    }

    @PostMapping("add")
    public RestResponse add(MenuResource menuResource) {


        menuResource.setMenuOrder(100);
        menuResource.setCreTime(new Date());
        menuResource.setUpdTime(new Date());

        menuResourceService.save(menuResource);

        return RestResponse.success();
    }

    @GetMapping("getTreeData")
    public RestResponse getTreeData() {

        JSONArray resources = menuResourceService.findTreeData();

        return RestResponse.success().fluentPut("resources", resources);
    }

    @PostMapping("updateMenuResourceOrder")
    public RestResponse updateMenuResourceOrder(String parentId, String draggingNodeId, String dropNodeId, String type) {

        menuResourceService.updateMenuResourceOrder(parentId, draggingNodeId, dropNodeId, type);

        return RestResponse.success();
    }

}
