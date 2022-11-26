package com.iduck.jdbc.util;

import com.iduck.common.constant.NumberConst;
import com.iduck.common.util.SpringContextHolder;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Jdbc工具类
 *
 * @author songYanBin
 * @since 2022/11/23
 */
public class JdbcHelper {
    /**
     * 分段提交默认数量
     */
    private static final int DEFAULT_SIZE = 900;

    /**
     * JdbcTemplate批量插入【分段提交】
     *
     * @param sql      sql
     * @param dataList 数据集合
     * @param consumer 参数设置回调
     * @param <T>      数据类型枚举
     */
    public static <T> void batchInsert(String sql,
                                       List<T> dataList,
                                       DiInjectConsumer<PreparedStatement, Integer, List<T>> consumer) {
        batchUpdate(SpringContextHolder.getBean(JdbcTemplate.class), sql, dataList, DEFAULT_SIZE, consumer);
    }

    /**
     * JdbcTemplate批量修改【分段提交】
     *
     * @param sql      sql
     * @param dataList 数据集合
     * @param consumer 参数设置回调
     * @param <T>      数据类型枚举
     */
    public static <T> void batchUpdate(String sql,
                                       List<T> dataList,
                                       DiInjectConsumer<PreparedStatement, Integer, List<T>> consumer) {
        batchUpdate(SpringContextHolder.getBean(JdbcTemplate.class), sql, dataList, DEFAULT_SIZE, consumer);
    }

    /**
     * JdbcTemplate批量修改【分段提交】
     *
     * @param jdbcTemplate jdbcTemplate
     * @param sql          sql
     * @param dataList     数据集合
     * @param intervalSize 提交数量
     * @param consumer     参数设置回调
     * @param <T>          数据枚举类型
     */
    public static <T> void batchUpdate(JdbcTemplate jdbcTemplate,
                                       String sql,
                                       List<T> dataList,
                                       int intervalSize,
                                       DiInjectConsumer<PreparedStatement, Integer, List<T>> consumer) {
        int begin = 0;
        int end = begin + intervalSize;
        int maxSize = dataList.size();
        List<T> subList = new ArrayList<>();

        // todo 这一块逻辑还需要优化,一时间不会写了
        if (maxSize < intervalSize) {
            subList.addAll(dataList.subList(begin, maxSize));
            execute(jdbcTemplate, sql, subList, consumer);
        } else {
            while (begin < maxSize) {
                subList.addAll(dataList.subList(begin, end));
                execute(jdbcTemplate, sql, subList, consumer);
                begin = end;
                end += intervalSize;
            }
            if (begin > maxSize) {
                begin -= intervalSize;
                subList.addAll(dataList.subList(begin, maxSize));
                execute(jdbcTemplate, sql, subList, consumer);
            }
        }
    }

    private static <T> void execute(JdbcTemplate jdbcTemplate, String sql, List<T> subList,
                                    DiInjectConsumer<PreparedStatement, Integer, List<T>> consumer) {
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            // SQL参数设置回调
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                consumer.accept(ps, i, subList);
            }

            // 设置提交数量
            @Override
            public int getBatchSize() {
                return subList.size();
            }
        });
        subList.clear();
    }

    @FunctionalInterface
    public interface DiInjectConsumer<P, I, T> {
        /**
         * 回调方法
         *
         * @param p PreparedStatement
         * @param i index
         * @param t 提交的数据集合
         * @throws SQLException 异常
         */
        void accept(P p, I i, T t) throws SQLException;
    }

    /**
     * 批量修改【分段提交】
     *
     * @param mapperFunc mapper方法
     * @param list       插入的数据集合
     * @param size       一次提交的数量
     * @param <T>        数据枚举
     */
    public static <T> void batchInsert(Consumer<List<T>> mapperFunc, List<T> list, int size) {
        batchUpdate(mapperFunc, list, size);
    }

    /**
     * 批量插入【分段提交】
     *
     * @param mapperFunc mapper方法
     * @param list       插入的数据集合
     * @param <T>        数据枚举
     */
    public static <T> void batchInsert(Consumer<List<T>> mapperFunc, List<T> list) {
        batchUpdate(mapperFunc, list, DEFAULT_SIZE);
    }

    /**
     * 批量修改【分段提交】
     *
     * @param mapperFunc 插入的数据集合
     * @param list       插入的数据集合
     * @param <T>        数据枚举
     */
    public static <T> void batchUpdate(Consumer<List<T>> mapperFunc, List<T> list) {
        batchUpdate(mapperFunc, list, DEFAULT_SIZE);
    }

    /**
     * 批量插入【分段提交】
     *
     * @param mapperFunc mapper方法
     * @param list       插入的数据集合
     * @param size       一次提交的数量
     * @param <T>        数据枚举
     */
    public static <T> void batchUpdate(Consumer<List<T>> mapperFunc, List<T> list, int size) {
        List<T> subList = new ArrayList<>();
        for (int i = NumberConst.ONE; i < list.size() + NumberConst.ONE; i++) {
            subList.add(list.get(i - NumberConst.ONE));
            if (i % size == NumberConst.ZERO) {
                // 执行mapper方法
                mapperFunc.accept(subList);
                subList.clear();
            }
        }
        if (subList.size() != NumberConst.ZERO) {
            mapperFunc.accept(subList);
            subList.clear();
        }
    }

    private JdbcHelper() {

    }
}
