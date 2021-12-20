package com.demo.config.jpa;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alex
 * @date 2021/12/14 10:48
 */
public class CustomerDelete<T, ID extends Serializable> {

    private BaseRepository<T, ID> baseRepository;

    private Class clazz;

    private StringBuilder whereHql = new StringBuilder();

    private Map<String, Object> params = new HashMap<>();

    CustomerDelete(BaseRepository<T, ID> baseRepository, Class clazz) {
        this.baseRepository = baseRepository;
        this.clazz = clazz;
    }

    public CustomerDelete<T, ID> where(String key, Object value) {

        this.whereHql.append(" , ").append(key).append(" = ").append(":").append(key);
        params.put(key, value);
        return this;
    }
    public void update() {
        baseRepository.executeHql(" delete from " + clazz.getSimpleName() + whereHql.toString().replaceFirst("and", " where"), params);
    }

}
