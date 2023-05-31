package com.cry.mini.springframework.web.servlet;

/**
 * <p>
 * 功能描述: View工厂
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/17 15:25
 */
public interface ViewResolver {
    //根据 View 的名字找到实际的 View
    //不用写死 JSP 路径, 而是可以通过 resolveViewName() 方法来获取一个 View
    //拿到目标 View 之后,我们把实际渲染的功能交给 View 自己完成
    View resolveViewName(String viewName) throws Exception;
}
