package com.cry.mini.springframework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * 功能描述: 渲染前端文件, 可以是jsp也可以是pdf word等文件
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/17 14:12
 */
public interface View {
    // 获取 HTTP 请求的 request 和 response，以及中间产生的业务数据 Model，最后写到 response 里面
    void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception;

    default String getContentType() {
        return null;
    }

    void setContentType(String contentType);

    void setUrl(String url);

    String getUrl();

    void setRequestContextAttribute(String requestContextAttribute);

    String getRequestContextAttribute();
}
