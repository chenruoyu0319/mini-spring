package com.cry.mini.springframework.web.servlet.view;

import com.cry.mini.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * 功能描述:  渲染图片
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/17 16:24
 */
public class PicView implements View {
//    public static final String DEFAULT_CONTENT_TYPE = "text/html;charset=ISO-8859-1";
    public static final String DEFAULT_CONTENT_TYPE = "image/png;charset=UTF-8";
    private String contentType = DEFAULT_CONTENT_TYPE;
    private String requestContextAttribute;
    private String beanName;
    private String url;

    @Override
    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //获取model，写到request的Attribute中：
        for (Map.Entry<String, Object> e : model.entrySet()) {
            request.setAttribute(e.getKey(), e.getValue());
        }
        //输出到目标JSP
        // 转向: 在本地web容器内跳转访问另一个资源, 此时服务器仍hold住了此次请求, 没有返回客户端
        // sPath: 本地资源路径, 在前后两次执行后，地址栏不变，仍是当前文件的地址而不是sPath的地址 "/" + sTarget + ".jsp";
        request.getRequestDispatcher(getUrl()).forward(request, response);
        // 重定向: 客户端向服务端发送请求之后,服务器返回一个响应,客户端接收到响应之后又向服务端发送一次请求,一共是2次请求,前后页不共用一个request
        // sPath: 在前后两次执行后，地址栏发生改变，是sPath的地址。
//        response.sendRedirect(sPath);

    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public void setRequestContextAttribute(String requestContextAttribute) {
        this.requestContextAttribute = requestContextAttribute;
    }

    @Override
    public String getRequestContextAttribute() {
        return this.requestContextAttribute;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

}
