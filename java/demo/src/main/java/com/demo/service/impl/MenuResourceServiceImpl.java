package com.demo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.entity.MenuResource;
import com.demo.repository.MenuResourceRepository;
import com.demo.service.MenuResourceService;
import com.demo.config.jpa.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Alex
 * @date 2021/9/24 13:50
 */
@Service
public class MenuResourceServiceImpl extends BaseServiceImpl<MenuResource, String> implements MenuResourceService {

    @Autowired
    private MenuResourceRepository menuResourceRepository;

    @Override
    public JSONArray findTreeData() {

        JSONArray array = new JSONArray();
        List<MenuResource> menuResourceList;

        menuResourceList = menuResourceRepository.findByHql("from MenuResource");

        List<MenuResource> list = new ArrayList<>();

        for (MenuResource mr : menuResourceList) {
            if (!StringUtils.hasLength(mr.getParentId())) {
                list.add(mr);
            }
        }

        list.stream().sorted(getComparator()).forEach(a -> obj2Json(array, menuResourceList, a));

        return array;
    }

    @Override
    public void updateMenuResourceOrder(String parentId, String draggingNodeId, String dropNodeId, String type) {

        List<MenuResource> menuResourceList = null;

        if (StringUtils.hasLength(parentId)) {
            menuResourceList = menuResourceRepository.findMenuResourcesByParentIdOrderByMenuOrder(parentId);
        } else {
            menuResourceList = menuResourceRepository.findMenuResourcesByParentIdIsNullOrderByMenuOrder();
        }

        MenuResource[] menuResources = new MenuResource[menuResourceList.size()];

        int newCount = 0;

        int currentCount = 0;

        MenuResource newMenuResource = null;

        for (MenuResource mr : menuResourceList) {
            if (mr.getMenuResourceId().equals(draggingNodeId)) {
                newMenuResource = mr;
            } else if (mr.getMenuResourceId().equals(dropNodeId)) {
                if (type.equals("before")) {
                    newCount = currentCount++;

                    menuResources[currentCount++] = mr;
                } else {
                    menuResources[currentCount++] = mr;

                    newCount = currentCount++;
                }
            } else {
                menuResources[currentCount++] = mr;
            }
        }

        menuResources[newCount] = newMenuResource;

        for (int i = 0; i < menuResources.length; i++) {
            MenuResource mr = menuResources[i];
            mr.setMenuOrder(i);
            menuResourceRepository.save(mr);
        }
    }

    @Override
    public List<MenuResource> findParentList() {

        String hql = "from MenuResource where menuType != 'API'";

        return menuResourceRepository.findByHql(hql);
    }

    private void obj2Json(JSONArray result, List<MenuResource> menuResourceList, MenuResource mr) {

        JSONObject node = new JSONObject();
        node.put("id", mr.getMenuResourceId());
        node.put("parentId", mr.getParentId());
        node.put("order", mr.getMenuOrder());
        node.put("type", mr.getMenuType());
        node.put("name", mr.getMenuName());
        node.put("nameEn", mr.getMenuNameEn());
        node.put("path", mr.getMenuPath());
        node.put("child", initChildNode(mr, menuResourceList));
        result.add(node);
    }

    private Comparator<MenuResource> getComparator() {
        return ((a, b) -> {
            if (a.getMenuOrder() > b.getMenuOrder()) {
                return 1;
            } else if (a.getMenuOrder().equals(b.getMenuOrder())) {
                return 0;
            } else {
                return -1;
            }
        });
    }

    private JSONArray initChildNode(MenuResource menuResource, List<MenuResource> menuResourceList) {

        JSONArray jsonArray = new JSONArray();

        List<MenuResource> list = new ArrayList<>();

        for (MenuResource mr : menuResourceList) {
            if (StringUtils.hasLength(mr.getParentId()) && mr.getParentId().equals(menuResource.getMenuResourceId())) {
                list.add(mr);
            }
        }

        list.stream().sorted(getComparator()).forEach(a -> obj2Json(jsonArray, menuResourceList, a));

        return jsonArray;
    }
}
