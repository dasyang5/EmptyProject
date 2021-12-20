package com.demo.config.jpa;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alex
 * @date 2021/12/14 10:48
 */
public class CustomerUpdate<T, ID extends Serializable> {

    private BaseRepository<T, ID> baseRepository;

    private Class clazz;

    private StringBuilder setHql = new StringBuilder();

    private StringBuilder whereHql = new StringBuilder();

    private Map<String, Object> params = new HashMap<>();

    CustomerUpdate(BaseRepository<T, ID> baseRepository, Class clazz) {
        this.baseRepository = baseRepository;
        this.clazz = clazz;
    }

    public CustomerUpdate<T, ID> where(String key, Object value) {

        this.whereHql.append(" and ").append(key).append(" = ").append(":").append(key);
        params.put(key, value);
        return this;
    }

    public CustomerUpdate<T, ID> set(String key, Object value) {
        this.setHql.append(" , ").append(key).append(" = ").append(":").append(key);
        params.put(key, value);
        return this;
    }

    public void update() {
        baseRepository.executeUpdate(" update " + clazz.getSimpleName() + setHql.toString().replaceFirst(",", "set") + whereHql.toString().replaceFirst("and", " where"), params);
    }

}
