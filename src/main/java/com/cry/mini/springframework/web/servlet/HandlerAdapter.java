package com.cry.mini.springframework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 功能描述: 对方法的调用也单独抽取出来
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/11 17:43
 */
public interface HandlerAdapter {

    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;

}
