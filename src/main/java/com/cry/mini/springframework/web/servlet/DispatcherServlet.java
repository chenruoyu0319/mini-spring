package com.cry.mini.springframework.web.servlet;

import com.cry.mini.springframework.exception.BeansException;
import com.cry.mini.springframework.web.content.WebApplicationContext;
import com.cry.mini.springframework.web.content.support.AnnotationConfigWebApplicationContext;
import com.cry.mini.springframework.web.method.HandlerMethod;
import com.cry.mini.springframework.web.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * 功能描述: MVC核心控制器 Controller 控制器
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/10 11:48
 */
public class DispatcherServlet extends HttpServlet {

    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";
    public static final String HANDLER_ADAPTER_BEAN_NAME = "handlerAdapter";
    public static final String VIEW_RESOLVER_BEAN_NAME = "viewResolver";
    // DispatcherServlet 启动的上下文
    private WebApplicationContext webApplicationContext;
    // Listener 启动的上下文
    private WebApplicationContext parentApplicationContext;
    // url-mapping
    private HandlerMapping handlerMapping;
    // method-handler
    private HandlerAdapter handlerAdapter;
    private ViewResolver viewResolver;

    /**
     * Servlet 初始化方法，初始化主要处理从外部传入的资源，将 XML 文件内容解析后存入 mappingValues 内。
     * 最后调用 Refresh() 函数创建 Bean
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // parentApplicationContext
        this.parentApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        // minisMVC-servlet.xml
        String sContextConfigLocation = config.getInitParameter("contextConfigLocation");
        // 创建一个新的 WebApplicationContext
        this.webApplicationContext = new AnnotationConfigWebApplicationContext(sContextConfigLocation, this.parentApplicationContext);
        refresh();
    }


    protected void refresh() {
        // URL 映射到某个实例方法的过程
        initHandlerMappings(this.webApplicationContext);
        // 实例方法调用的过程
        initHandlerAdapters(this.webApplicationContext);
        //
        initViewResolvers(this.webApplicationContext);
    }


    protected void initHandlerMappings(WebApplicationContext wac) {
        this.handlerMapping = new RequestMappingHandlerMapping(wac);
    }

    protected void initHandlerAdapters(WebApplicationContext wac) {
        try {
            this.handlerAdapter = (HandlerAdapter) wac.getBean(HANDLER_ADAPTER_BEAN_NAME);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    protected void initViewResolvers(WebApplicationContext wac) {
        try {
            this.viewResolver = (ViewResolver) wac.getBean(VIEW_RESOLVER_BEAN_NAME);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        // 暂时没用到
        request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.webApplicationContext);
        try {
            // 分发请求
            doDispatch(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpServletRequest processedRequest = request;
        HandlerMethod handlerMethod = null;
        ModelAndView mv = null;
        handlerMethod = this.handlerMapping.getHandler(processedRequest);
        if (handlerMethod == null) {
            return;
        }
        HandlerAdapter ha = this.handlerAdapter;
        mv = ha.handle(processedRequest, response, handlerMethod);
        if (mv == null){
            return;
        }
        // 渲染jsp
        render(processedRequest, response, mv);
    }


    //用jsp 进行render
    protected void render(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) throws Exception {
        // jsp文件的名称
        String sTarget = mv.getViewName();
        // 传给jsp动态加载的参数
        Map<String,Object> modelMap = mv.getModel();
        View view = resolveViewName(sTarget, modelMap, request);
        view.render(modelMap, request, response);
    }

    protected View resolveViewName(String viewName, Map<String, Object> model,
                                   HttpServletRequest request) throws Exception {
        if (this.viewResolver != null) {
            View view = viewResolver.resolveViewName(viewName);
            if (view != null) {
                return view;
            }
        }
        return null;
    }
}
