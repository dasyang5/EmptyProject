package com.demo.utils.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Alex
 * @date 2021/9/23 15:35
 */
@Component
@Slf4j
public class JDBCUtil {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void batchInsert(List list) {

        StringBuilder sql = new StringBuilder("insert into ");

        if (list == null || list.size() == 0) {
            return;
        }

        Class clazz = list.get(0).getClass();

        Table table = (Table) clazz.getAnnotation(Table.class);
        if (table != null) {
            sql.append(table.name()).append("(");
        }

        Field[] fields = new Field[clazz.getDeclaredFields().length];

        int index = 0;

        for (Field field : clazz.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                sql.append(column.name()).append(",");
                fields[index++] = field;
            }
        }

        sql.deleteCharAt(sql.length() - 1);

        sql.append(") values(");
        for (int i = 0; i < index; i++) {
            sql.append("?,");
        }

        sql.deleteCharAt(sql.length() - 1);

        sql.append(")");

        log.info(sql.toString() + "   total: " + list.size());

        final int maxFields = index;

        jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                try {
                    Object obj = list.get(i);

                    for (int count = 0; count < maxFields; count++) {
                        fields[count].setAccessible(true);
                        preparedStatement.setObject(count+1, fields[count].get(obj));
                        fields[count].setAccessible(false);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });

    }

}
