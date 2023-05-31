package com.cry.mini.springframework.http.converter;

import com.cry.mini.springframework.util.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>
 * 功能描述: Object to json 控制
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/17 10:37
 */
public class DefaultHttpMessageConverter implements HttpMessageConverter {
    // MME以前的标准: 浏览器直接渲染
    String defaultContentType = "text/json;charset=UTF-8";
    // MME官方现在标准: 浏览器下载该json文件
//    String defaultContentType = "json/application;charset=UTF-8";
    String defaultCharacterEncoding = "UTF-8";
    // 真正执行Object to json
    ObjectMapper objectMapper;

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void write(Object obj, HttpServletResponse response) throws IOException {
        response.setContentType(defaultContentType);
        response.setCharacterEncoding(defaultCharacterEncoding);
        writeInternal(obj, response);
        response.flushBuffer();
    }

    private void writeInternal(Object obj, HttpServletResponse response) throws IOException {
        String sJsonStr = this.objectMapper.writeValuesAsString(obj);
        // response.getWriter().append(returnObj.toString());
        PrintWriter pw = response.getWriter();
        pw.write(sJsonStr);
    }
}
