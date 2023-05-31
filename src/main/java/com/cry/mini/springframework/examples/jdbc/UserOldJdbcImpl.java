package com.cry.mini.springframework.examples.jdbc;

import com.cry.mini.springframework.jdbc.core.OldJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/18 18:24
 */
public class UserOldJdbcImpl extends OldJdbcTemplate {

    @Override
    protected Object doInStatement(ResultSet rs) {
        //从jdbc数据集读取数据，并生成对象返回
        User rtnUser = null;
        try {
            if (rs.next()) {
                rtnUser = new User();
                rtnUser.setId(rs.getInt("id"));
                rtnUser.setName(rs.getString("name"));
                rtnUser.setBirthday(new Date(rs.getDate("birthday").getTime()));
            } else {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rtnUser;
    }


}
