package com.demo.config.jpa;

import com.demo.bean.PageBean;
import com.demo.bean.TableData;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Alex
 * @date 5/10/2021 4:20 PM
 */
public class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID>{

    @Autowired
    protected BaseRepository<T, ID> baseRepository;


    @Override
    public T save(T t) {
        t = baseRepository.save(t);
        return t;
    }

    @Override
    public void delete(T t) {
        baseRepository.delete(t);
    }

    @Override
    public void delete(ID id) {
        baseRepository.deleteById(id);
    }

    @Override
    public void update(T t) {
        baseRepository.save(t);
    }

    @Override
    public List<T> findAll() {
        return StreamSupport.stream(baseRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public CustomerQuery<T, ID> customerQuery() {
        return new CustomerQuery<T, ID>(baseRepository, (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @Override
    public CustomerDelete<T, ID> customerDelete() {
        return new CustomerDelete<T, ID>(baseRepository, (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @Override
    public CustomerUpdate<T, ID> customerUpdate() {
        return new CustomerUpdate<T, ID>(baseRepository, (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @Override
    public T get(ID id) {
        Optional<T> t = baseRepository.findById(id);
        return t.orElse(null);
    }

    @Override
    public List<T> find(T t, PageBean pageBean) {
        return baseRepository.findByEntity(t, pageBean);
    }

    @Override
    public List<T> find(T t, String[] orderBy, PageBean pageBean) {
        return baseRepository.findByEntity(t, pageBean, orderBy);
    }

    @Override
    public T getEntity(T t) {

        List<T> list = find(t, null);

        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public int count(T t) {
        return ((int) (baseRepository.countByEntity(t)));
    }

    @Override
    public TableData<T> findTableData(T t, PageBean pageBean) {
        return baseRepository.findTableDataByEntity(t, pageBean);
    }

    @Override
    public TableData<T> findTableData(T t, String[] orderBy, PageBean pageBean) {
        return baseRepository.findTableDataByEntity(t, orderBy, pageBean);
    }

    @Override
    public TableData<T> findTableData(T t, LikeField[] likeFields, PageBean pageBean) {
        return baseRepository.findTableDataByEntityWithLike(t, likeFields, pageBean);
    }

    @Override
    public TableData<T> findTableData(T t, LikeField[] likeFields, String[] orderBy, PageBean pageBean) {
        return baseRepository.findTableDataByEntityWithLike(t, likeFields, pageBean, orderBy);
    }

    @Override
    public TableData<T> findTableData(T t, String[] likeFields, String[] orderBy, PageBean pageBean) {

        LikeField[] array = new LikeField[likeFields.length];

        for (int i = 0; i < likeFields.length; i++) {
            array[i] = new LikeField(likeFields[i]);
        }

        return baseRepository.findTableDataByEntityWithLike(t, array, pageBean, orderBy);
    }

    @Override
    public void batchInsert(List<T> list) {
        baseRepository.batchInsert(list);
    }

}
