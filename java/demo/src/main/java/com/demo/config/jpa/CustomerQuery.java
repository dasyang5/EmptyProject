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

    private Class clazz;

    private StringBuilder paramsHql = new StringBuilder();

    private StringBuilder orderHql = new StringBuilder();

    private Map<String, Object> params = new HashMap<>();

    CustomerQuery(BaseRepository<T, ID> baseRepository, Class clazz) {
        this.baseRepository = baseRepository;
        this.clazz = clazz;
    }

    public List<T> list() {
        String hql = " from " + clazz.getSimpleName() + paramsHql.toString().replaceFirst("and", "where") +
                (orderHql.length() > 0 ? " order by " + orderHql.toString() : "");
        return baseRepository.findByHql(hql, params, pageBean);
    }

    public T get() {
        List<T> list = list();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public long count() {
        return baseRepository.countByHql("select count(1) from " + clazz.getSimpleName() + paramsHql.toString().replaceFirst("and", "where"), params);
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
        this.orderHql.append(" ").append(orderStr).append(" ");
        return this;
    }

    public CustomerQuery<T, ID> entity(T t) {
        return this.entity(t, null);
    }

    @SneakyThrows
    public CustomerQuery<T, ID> entity(T t, Map<String, FuzzyConfig> likeFields) {
        Class clazz = t.getClass();

        paramsHql.append(clazz.getSimpleName());

        for (Field f : clazz.getDeclaredFields()) {
            if (f.getAnnotation(Column.class) != null) {
                f.setAccessible(true);
                Object value = f.get(t);
                if (null != value && StringUtils.hasLength(value.toString())) {
                    paramsHql.append(" and ");
                    if (likeFields != null && likeFields.containsKey(f.getName())) {
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
        return this;
    }

    public CustomerQuery<T, ID> equal(String key, Object value) {
        paramsHql.append(" and ").append(key).append(" = :").append(key).append(" ");
        params.put(key, value);
        return this;
    }

    public CustomerQuery<T, ID> isNull(String key) {
        paramsHql.append(" and ").append(key).append(" is null ");
        return this;
    }

    public CustomerQuery<T, ID> isNotNull(String key) {
        paramsHql.append(" and ").append(key).append(" is not null ");
        return this;
    }

    public CustomerQuery<T, ID> like(String key, String value) {
        paramsHql.append(" and ").append(key).append(" like :").append(key).append(" ");
        params.put(key, value);
        return this;
    }

}
