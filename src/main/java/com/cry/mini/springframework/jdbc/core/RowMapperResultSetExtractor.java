package com.cry.mini.springframework.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 功能描述: ResultSet的集合组装逻辑
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/19 18:13
 */
public class RowMapperResultSetExtractor<T> implements ResultSetExtractor<List<T>> {
    // 这个 RowMapper 既是作为一个参数又是作为一个用户程序传进去的。这很合理，因为确实只有用户程序自己知道自己的数据要如何映射
    private final RowMapper<T> rowMapper;

    public RowMapperResultSetExtractor(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    @Override
    public List<T> extractData(ResultSet rs) throws SQLException {
        List<T> results = new ArrayList<>();
        int rowNum = 0;
        //对结果集，循环调用mapRow进行数据记录映射
        while (rs.next()) {
            results.add(this.rowMapper.mapRow(rs, rowNum++));
        }
        return results;
    }

}
