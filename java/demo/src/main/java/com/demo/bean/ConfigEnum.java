package com.demo.bean;

/**
 * @author Alex
 * @date 2021/9/23 14:29
 */
public enum  ConfigEnum {

    INSERT_INIT_DATA_ON("insert_init_data", "1", "自动插入数据 开"),
    INSERT_INIT_DATA_OFF("insert_init_data", "0", "自动插入数据 关")
    ;

    private String key;

    private String value;

    private String desc;

    ConfigEnum(String key, String value, String desc) {
        this.key = key;
        this.value = value;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
