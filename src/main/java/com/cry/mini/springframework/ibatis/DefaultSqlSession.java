package com.cry.mini.springframework.ibatis;

import com.cry.mini.springframework.jdbc.core.JdbcTemplate;
import com.cry.mini.springframework.jdbc.core.PreparedStatementCallback;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/22 9:45
 */
public class DefaultSqlSession implements SqlSession{

    JdbcTemplate jdbcTemplate;
    SqlSessionFactory sqlSessionFactory;

    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
    public SqlSessionFactory getSqlSessionFactory() {
        return this.sqlSessionFactory;
    }

    @Override
    public Object selectOne(String sqlid, Object[] args, PreparedStatementCallback pstmtcallback) {
        System.out.println(sqlid);
        String sql = this.sqlSessionFactory.getMapperNode(sqlid).getSql();
        System.out.println(sql);
        return jdbcTemplate.query(sql, args, pstmtcallback);
    }

    private void buildParameter(){
    }

    private Object resultSet2Obj() {
        return null;
    }
}
