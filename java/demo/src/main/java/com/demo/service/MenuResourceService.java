package com.demo.service;

import com.alibaba.fastjson.JSONArray;
import com.demo.config.jpa.BaseService;
import com.demo.entity.MenuResource;

import java.util.List;

/**
 * @author Alex
 * @date 2021/9/24 13:49
 */
public interface MenuResourceService extends BaseService<MenuResource, String> {

    JSONArray findTreeData();

    void updateMenuResourceOrder(String parentId, String draggingNodeId, String dropNodeId, String type);

    List<MenuResource> findParentList();

}
