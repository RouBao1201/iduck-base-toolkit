package com.iduck.jdbc.util;

import cn.hutool.core.util.StrUtil;

/**
 * Sql工具类
 *
 * @author songYanBin
 * @since 2022/11/23
 */
public class SqlHelper {

    private static final String SPACE_WHERE = " where";

    private static final String SPACE_WHERE_SPACE = " where ";

    private static final String SPACE_ONE_EQUAL_ONE_SPACE = " 1 = 1 ";

    private final StringBuilder sqlBuilder;

    public static SqlHelper builder(String sql) {
        return new SqlHelper(sql);
    }

    public SqlHelper startLike(String column, String arg) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" like '")
                .append(arg)
                .append("%' ");
        return this;
    }

    public SqlHelper endLike(String column, String arg) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" like '%")
                .append(arg)
                .append("' ");
        return this;
    }

    public SqlHelper notStartLike(String column, String arg) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" not like '")
                .append(arg)
                .append("%' ");
        return this;
    }

    public SqlHelper notEndLike(String column, String arg) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" not like '%")
                .append(arg)
                .append("' ");
        return this;
    }

    public SqlHelper like(String column, String arg) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" like '%")
                .append(arg)
                .append("%' ");
        return this;
    }

    public SqlHelper notLike(String column, String arg) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" not like '%")
                .append(arg)
                .append("%' ");
        return this;
    }

    public SqlHelper between(String column, String begin, String end) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" between '")
                .append(begin)
                .append("' AND '")
                .append(end)
                .append("' ");
        return this;
    }

    public SqlHelper between(String column, int begin, int end) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" between ")
                .append(begin)
                .append(" and ")
                .append(end)
                .append(" ");
        return this;
    }

    public SqlHelper in(String column, int[] args) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" in (")
                .append(StrUtil.join(",", args))
                .append(") ");
        return this;
    }

    public SqlHelper in(String column, String[] args) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" in ('")
                .append(StrUtil.join("','", args))
                .append("') ");
        return this;
    }

    public SqlHelper notIn(String column, int[] args) {
        this.sqlBuilder.append(" and ")
                .append(column)
                .append(" not in (")
                .append(StrUtil.join(",", args))
                .append(") ");
        return this;
    }

    public SqlHelper notIn(String column, String[] args) {
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

    private SqlHelper(String sql) {
        this.sqlBuilder = new StringBuilder(sql);
        initCheck(sqlBuilder);
    }
}
