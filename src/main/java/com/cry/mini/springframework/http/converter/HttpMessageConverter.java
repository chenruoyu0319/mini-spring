package com.cry.mini.springframework.http.converter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/17 10:35
 */
public interface HttpMessageConverter {
    void write(Object obj, HttpServletResponse response) throws IOException;
}
