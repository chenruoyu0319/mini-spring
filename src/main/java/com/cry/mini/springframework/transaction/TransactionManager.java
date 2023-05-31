package com.cry.mini.springframework.transaction;

import com.cry.mini.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/29 15:36
 */

public class TransactionManager {
    @Autowired
    private DataSource dataSource;
    Connection conn = null;

    protected void doBegin() throws SQLException {
        conn = dataSource.getConnection();
        if (conn.getAutoCommit()) {
            conn.setAutoCommit(false);
            System.out.println("数据库连接开启了事务!");
        }
    }

    protected void doCommit() throws SQLException {
        conn.commit();
        System.out.println("数据库连接提交了事务!");
    }

    public TransactionManager() {
    }

    public TransactionManager(DataSource dataSource, Connection conn) {
        this.dataSource = dataSource;
        this.conn = conn;
    }
}
