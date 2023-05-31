package com.cry.mini.springframework.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * 功能描述: JDBC返回的ResultSet => 一个Object
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/19 18:11
 */
@FunctionalInterface
public interface RowMapper<T> {
    T mapRow(ResultSet rs, int rowNum) throws SQLException;
}
