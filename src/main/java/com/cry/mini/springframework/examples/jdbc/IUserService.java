package com.cry.mini.springframework.examples.jdbc;

import com.cry.mini.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/29 16:14
 */
public interface IUserService {

    void doOldJdbcTemplate();

    void doJdbcTemplate();

    User getUserInfo(int userid);

    User getUserInfo2(int userid);

    int update(int userid, String name);

    List<User> getUsers(int userid);

    JdbcTemplate getJdbcTemplate();

    void setJdbcTemplate(JdbcTemplate jdbcTemplate);

}
