package com.cry.mini.springframework.jdbc.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/18 18:10
 */
public abstract class OldJdbcTemplate {
    public OldJdbcTemplate() {
    }

    public Object query(String sql) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Object rtnObj = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ibatis?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true",
                    "root","cry777!!");
            // 直接创建
//            stmt = con.createStatement();
            // 预编译
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
//           stmt.executeUpdate();
            //调用返回数据处理方法，由程序员自行实现
            rtnObj = doInStatement(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                con.close();
            } catch (Exception e) {
            }
        }
        return rtnObj;
    }

    protected abstract Object doInStatement(ResultSet rs);

}
