package com.demo.controller.api;

import com.demo.controller.BaseController;
import com.demo.entity.User;
import com.demo.bean.RestResponse;
import com.demo.bean.TableData;
import com.demo.entity.Organ;
import com.demo.service.OrganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Alex
 * @date 2021/9/26 13:35
 */
@RestController
@RequestMapping("api/organ")
public class OrganController extends BaseController {

    @Autowired
    private OrganService organService;

    @GetMapping("list")
    public RestResponse list(Organ organ) {

        TableData<Organ> tableData = organService.findTableData(organ, new String[]{"creTime desc"}, getPageBean());

        return RestResponse.success(tableData);
    }

    @GetMapping("get")
    public RestResponse get(String organId) {

        Organ organ = organService.get(organId);

        return RestResponse.success().fluentPut("organ", organ);
    }

    @GetMapping("findOrganList")
    public RestResponse findOrganList() {

        List<Organ> organList;

        User user = getCurrentUser();

        if (user.getUsername().equals("admin")) {
            organList = organService.findAll();
        } else {
            Organ organ = organService.get(getCurrentOrganId());
            organList = new ArrayList<>();
            organList.add(organ);
        }
        return RestResponse.success().fluentPut("rows", organList);
    }

    @PostMapping("add")
    public RestResponse add(Organ organ) {

        organ.setCreTime(new Date());
        organ.setUpdTime(new Date());

        organService.save(organ);

        return RestResponse.success();
    }

    @PostMapping("edit")
    public RestResponse edit(Organ organ) {

        Organ obj = organService.get(organ.getOrganId());

        obj.setOrganName(organ.getOrganName());
        obj.setUpdTime(new Date());

        organService.update(obj);

        return RestResponse.success();
    }

    @DeleteMapping("delete")
    public RestResponse delete(String organId) {

        Organ organ = organService.get(organId);

        organService.delete(organ);

        return RestResponse.success();
    }

}
