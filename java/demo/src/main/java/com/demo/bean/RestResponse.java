package com.demo.bean;

import java.util.HashMap;
import java.util.List;

/**
 * @author Alex
 * @date 11/11/2020 9:36 AM
 */
public class RestResponse extends HashMap<String, Object> {

    private static final String OPERATE_SUCCESSFUL_HIT = "Operate Successfully";

    public static RestResponse success() {
        return success(OPERATE_SUCCESSFUL_HIT);
    }

    public static RestResponse success(String msg) {
        return getInstance(true, msg);
    }

    public static RestResponse success(TableData tableData) {
        return success(OPERATE_SUCCESSFUL_HIT).tableData(tableData);
    }

    public static RestResponse error(String msg) {
        return getInstance(false, msg);
    }

    private static RestResponse getInstance(boolean isSuccess, String msg) {
        RestResponse restResponse = new RestResponse();
        restResponse.put("success", isSuccess);
        restResponse.put("msg", msg);
        return restResponse;
    }

    //私有化默认构造方法
    private RestResponse() {

    }

    public RestResponse fluentPut(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public RestResponse tableData(TableData tableData) {
        this.put("rows", tableData.getData());
        this.put("total", tableData.getCount());
        return this;
    }

    public RestResponse rows(List list) {
        this.put("rows", list);
        return this;
    }

}
