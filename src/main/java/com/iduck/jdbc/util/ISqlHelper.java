package com.iduck.jdbc.util;

import cn.hutool.core.util.StrUtil;

/**
 * Sql工具类
 *
 * @author songYanBin
 * @Copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/23
 */
public class ISqlHelper {

    private static final String SPACE_WHERE = " where";

    private static final String SPACE_WHERE_SPACE = " where ";

    private static final String SPACE_ONE_EQUAL_ONE_SPACE = " 1 = 1 ";

    private final StringBuilder sqlBuilder;

    public static ISqlHelper builder(String sql) {
        return new ISqlHelper(sql);
    }

    public ISqlHelper startLike(String column, String arg) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" like '")
                .append(arg)
                .append("%' ");
        return this;
    }

    public ISqlHelper endLike(String column, String arg) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" like '%")
                .append(arg)
                .append("' ");
        return this;
    }

    public ISqlHelper notStartLike(String column, String arg) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" not like '")
                .append(arg)
                .append("%' ");
        return this;
    }

    public ISqlHelper notEndLike(String column, String arg) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" not like '%")
                .append(arg)
                .append("' ");
        return this;
    }

    public ISqlHelper like(String column, String arg) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" like '%")
                .append(arg)
                .append("%' ");
        return this;
    }

    public ISqlHelper notLike(String column, String arg) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" not like '%")
                .append(arg)
                .append("%' ");
        return this;
    }

    public ISqlHelper between(String column, String begin, String end) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" between '")
                .append(begin)
                .append("' AND '")
                .append(end)
                .append("' ");
        return this;
    }

    public ISqlHelper between(String column, int begin, int end) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" between ")
                .append(begin)
                .append(" and ")
                .append(end)
                .append(" ");
        return this;
    }

    public ISqlHelper in(String column, int[] args) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" in (")
                .append(StrUtil.join(",", args))
                .append(") ");
        return this;
    }

    public ISqlHelper in(String column, String[] args) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" in ('")
                .append(StrUtil.join("','", args))
                .append("') ");
        return this;
    }

    public ISqlHelper notIn(String column, int[] args) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" not in (")
                .append(StrUtil.join(",", args))
                .append(") ");
        return this;
    }

    public ISqlHelper notIn(String column, String[] args) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" not in ('")
                .append(StrUtil.join("','", args))
                .append("') ");
        return this;
    }

    public String build() {
        return this.sqlBuilder.toString();
    }

    /**
     * 初始化sql检查
     *
     * @param sql 初始sql
     */
    private void initCheck(StringBuilder sql) {
        String sqlStr = String.valueOf(sql);
        if (sqlStr.contains(SPACE_WHERE) && !sqlStr.endsWith(SPACE_WHERE) && !sqlStr.endsWith(SPACE_WHERE_SPACE)) {
            return;
        } else if (sqlStr.endsWith(SPACE_WHERE) || sqlStr.endsWith(SPACE_WHERE_SPACE)) {
            sql.append(SPACE_ONE_EQUAL_ONE_SPACE);
        } else {
            sql.append(" where")
                    .append(SPACE_ONE_EQUAL_ONE_SPACE);
        }
    }

    private ISqlHelper(String sql) {
        this.sqlBuilder = new StringBuilder(sql);
        initCheck(sqlBuilder);
    }
}
