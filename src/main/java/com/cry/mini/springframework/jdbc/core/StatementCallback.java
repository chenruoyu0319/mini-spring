package com.cry.mini.springframework.jdbc.core;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/19 10:06
 */
@FunctionalInterface
public interface StatementCallback {

    Object doInStatement(Statement stmt) throws SQLException;
}
