package com.cry.mini.springframework.util;

/**
 * <p>
 * 功能描述: 真正执行Object to json
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/17 10:55
 */
public interface ObjectMapper {
    void setDateFormat(String dateFormat);

    void setDecimalFormat(String decimalFormat);

    String writeValuesAsString(Object obj);
}
