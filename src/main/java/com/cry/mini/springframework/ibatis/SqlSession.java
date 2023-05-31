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
 * @since 2023/05/22 9:43
 */
public interface SqlSession {
    void setJdbcTemplate(JdbcTemplate jdbcTemplate);
    void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory);
    Object selectOne(String sqlid, Object[] args, PreparedStatementCallback pstmtcallback);
}
