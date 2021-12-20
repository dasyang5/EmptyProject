package com.demo.config.jpa;

import com.demo.bean.PageBean;
import com.demo.bean.TableData;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author Alex
 * @date 5/10/2021 4:19 PM
 */
@Transactional
public interface BaseService<T, ID extends Serializable> {

    /**
     * 新增实体
     * @param t
     * @return
     */
    T save(T t);

    /**
     * 删除实体
     * @param t
     */
    void delete(T t);

    /**
     * 通过主键删除
     * @param id
     */
    void delete(ID id);

    /**
     * 更新实体
     * @param t
     */
    void update(T t);

    /**
     * 查询全部
     * @return
     */
    List<T> findAll();

    /**
     * 自定义查询
     * @return
     */
    CustomerQuery<T, ID> customerQuery();

    /**
     * 自定义删除
     * @return
     */
    CustomerDelete<T, ID> customerDelete();

    /**
     * 自定义更新
     * @return
     */
    CustomerUpdate<T, ID> customerUpdate();

    /**
     * 获取单个实体
     * @param id
     * @return
     */
    T get(ID id);

    /**
     * 分页查询
     * @param t
     * @param pageBean 为空时不分页
     * @return
     */
    List<T> find(T t, PageBean pageBean);

    /**
     * 分页查询
     * @param t
     * @param orderBy 排序字段  例如 new String[] {"username desc"}
     * @param pageBean
     * @return
     */
    List<T> find(T t, String[] orderBy, PageBean pageBean);

    T getEntity(T t);

    int count(T t);

    TableData<T> findTableData(T t, PageBean pageBean);

    TableData<T> findTableData(T t, String[] orderBy, PageBean pageBean);

    TableData<T> findTableData(T t, LikeField[] likeFields, PageBean pageBean);

    TableData<T> findTableData(T t, LikeField[] likeFields, String[] orderBy, PageBean pageBean);

    TableData<T> findTableData(T t, String[] likeFields, String[] orderBy, PageBean pageBean);

    void batchInsert(List<T> list);

}
