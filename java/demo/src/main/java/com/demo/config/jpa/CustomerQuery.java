package com.demo.config.jpa;

import com.demo.bean.PageBean;
import com.demo.bean.TableData;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alex
 * @date 2021/12/8 13:34
 */
public class CustomerQuery<T, ID extends Serializable> {

    private BaseRepository<T, ID> baseRepository;

    private PageBean pageBean;

    private T t;

    private StringBuilder paramsHql = new StringBuilder();

    private StringBuilder orderHql = new StringBuilder();

    private Map<String, FuzzyConfig> likeFields = new HashMap<>();

    private Map<String, Object> params = new HashMap<>();

    CustomerQuery(BaseRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    public List<T> list() {
        return baseRepository.findByHql(" from " + paramsHql.toString() + orderHql.toString(), params, pageBean);
    }

    public T get() {
        List<T> list = list();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public long count() {
        return baseRepository.countByHql("select count(1) from " + paramsHql.toString(), params);
    }

    public TableData<T> tableData() {
        TableData<T> tableData = new TableData<>();
        tableData.setCount(count());
        tableData.setData(list());
        return tableData;
    }

    public CustomerQuery<T, ID> pageBean(PageBean pageBean) {
        this.pageBean = pageBean;
        return this;
    }

    public CustomerQuery<T, ID> orderBy(String orderStr) {
        this.orderHql.append(orderStr);
        return this;
    }

    public CustomerQuery<T, ID> entity(T t) {
        this.t = t;
        return this;
    }

    public CustomerQuery<T, ID> param(String key, Object value) {
        paramsHql.append(key).append(" = :").append(key);
        params.put(key, value);
        return this;
    }

    public CustomerQuery<T, ID> isNull(String key) {
        paramsHql.append(key).append(" is null ");
        return this;
    }

    public CustomerQuery<T, ID> notNull(String key) {
        paramsHql.append(key).append(" not null ");
        return this;
    }

    public CustomerQuery<T, ID> in(String key, Object[] values) {
        return this;
    }

    public CustomerQuery<T, ID> notIn(String key, Object[] values) {
        return this;
    }

    public CustomerQuery<T, ID> like(String key, FuzzyConfig fuzzyConfig) {
        likeFields.put(key, fuzzyConfig);
        return this;
    }

    public CustomerQuery<T, ID> like(String key, String value) {
        paramsHql.append(key).append(" like :").append(key);
        params.put(key, value);
        return this;
    }

    @SneakyThrows
    private void generateHql() {

        Class clazz = t.getClass();

        paramsHql.append(clazz.getSimpleName());

        for (Field f : clazz.getDeclaredFields()) {
            if (f.getAnnotation(Column.class) != null) {
                f.setAccessible(true);
                Object value = f.get(t);
                if (null != value && StringUtils.hasLength(value.toString())) {
                    if (params.isEmpty()) {
                        paramsHql.append(" where ");
                    } else {
                        paramsHql.append(" and ");
                    }
                    if (likeFields.containsKey(f.getName())) {
                        FuzzyConfig fuzzyConfig = likeFields.get(f.getName());
                        paramsHql.append(f.getName()).append(" like ").append(":").append(f.getName());
                        params.put(f.getName(), fuzzyConfig.getPrefix() + f.get(t) + fuzzyConfig.getSuffix());
                    } else {
                        paramsHql.append(f.getName()).append(" = ").append(":").append(f.getName());
                        params.put(f.getName(), f.get(t));
                    }
                }
            }
        }
    }

}
