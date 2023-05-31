package com.cry.mini.springframework.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/19 10:44
 */
public interface PreparedStatementCallback {
    Object doInPreparedStatement(PreparedStatement stmt) throws SQLException;
}
