package com.cry.mini.springframework.examples.jdbc;

import com.cry.mini.springframework.beans.factory.annotation.Autowired;
import com.cry.mini.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/19 10:55
 */
public class UserService implements IUserService{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void doOldJdbcTemplate() {
        UserOldJdbcImpl userJdbc = new UserOldJdbcImpl();
        String sql = "select * from user";
        Object query = userJdbc.query(sql);
        System.out.println(query);
    }

    @Override
    public void doJdbcTemplate() {
//        User userInfo = getUserInfo(1);
        User userInfo = getUserInfo2(1);
        System.out.println("userInfo: " + userInfo);
    }

    @Override
    public User getUserInfo(int userid) {
        final String sql = "select id, name,birthday from user where id=" + userid;
        return (User) jdbcTemplate.query(
                // Object doInStatement(Statement stmt)
                (stmt) -> {
                    ResultSet rs = stmt.executeQuery(sql);
                    User rtnUser = null;
                    while (rs.next()) {
                        rtnUser = new User();
                        rtnUser.setId(userid);
                        rtnUser.setName(rs.getString("name"));
                        rtnUser.setBirthday(new java.util.Date(rs.getDate("birthday").getTime()));
                    }
                    return rtnUser;
                }
        );
    }


    @Override
    public User getUserInfo2(int userid) {
        final String sql = "select id, name,birthday from user where id=?";
        return (User) jdbcTemplate.query(sql, new Object[]{new Integer(userid)},
                (pstmt) -> {
                    ResultSet rs = pstmt.executeQuery();
                    User rtnUser = null;
                    while (rs.next()) {
                        rtnUser = new User();
                        rtnUser.setId(userid);
                        rtnUser.setName(rs.getString("name"));
                        rtnUser.setBirthday(new java.util.Date(rs.getDate("birthday").getTime()));
                    }
                    return rtnUser;
                }
        );
    }

    @Override
    public int update(int userid, String name) {
        final String sql = "update user set name=? where id=?";
        return jdbcTemplate.update(sql, new Object[]{name, new Integer(userid)}, null);
    }


    @Override
    public List<User> getUsers(int userid) {
        final String sql = "select id, name,birthday from user where id>?";
        return jdbcTemplate.query(sql, new Object[]{new Integer(userid)},
                (rs, i) -> {
                    User rtnUser = new User();
                    rtnUser.setId(rs.getInt("id"));
                    rtnUser.setName(rs.getString("name"));
                    rtnUser.setBirthday(new Date(rs.getDate("birthday").getTime()));
                    return rtnUser;
                }
        );
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
