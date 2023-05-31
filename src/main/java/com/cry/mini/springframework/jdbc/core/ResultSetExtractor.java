package com.cry.mini.springframework.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * 功能描述: JDBC返回的ResultSet => 对象集合
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/19 18:12
 */
public interface ResultSetExtractor<T> {
    T extractData(ResultSet rs) throws SQLException;
}
