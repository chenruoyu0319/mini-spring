package com.cry.mini.springframework.web.method.annotation;

import com.cry.mini.springframework.http.converter.HttpMessageConverter;
import com.cry.mini.springframework.web.bind.WebDataBinder;
import com.cry.mini.springframework.web.bind.annotation.ResponseBody;
import com.cry.mini.springframework.web.bind.support.WebBindingInitializer;
import com.cry.mini.springframework.web.bind.support.WebDataBinderFactory;
import com.cry.mini.springframework.web.method.HandlerMethod;
import com.cry.mini.springframework.web.servlet.HandlerAdapter;
import com.cry.mini.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * <p>
 * 功能描述: 对方法的调用也单独抽取出来
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/11 17:41
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {
    WebBindingInitializer webBindingInitializer;
    HttpMessageConverter messageConverter;

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return handleInternal(request, response, (HandlerMethod) handler);
    }

    //迁移了之前DispatcherServlet中的doGet方法执行反射的部分
    private ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        ModelAndView mv = null;
        try {
            mv = invokeHandlerMethod(request, response, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    protected ModelAndView invokeHandlerMethod(HttpServletRequest request,
                                               HttpServletResponse response,
                                               HandlerMethod handlerMethod) throws Exception {
        WebDataBinderFactory binderFactory = new WebDataBinderFactory();
        // 实际调用方法的参数
        Parameter[] methodParameters = handlerMethod.getMethod().getParameters();
        // 我们需要进行绑定操作的目标参数对象
        Object[] methodParamObjs = new Object[methodParameters.length];
        int i = 0;
        //对实际调用方法里的每一个参数，处理绑定
        for (Parameter methodParameter : methodParameters) {
            // 实例化目标对象
            Object methodParamObj = methodParameter.getType().newInstance();
            //给这个参数创建WebDataBinder
            // target = "",objectName = "arg0",clz = "java.lang.String"
            // target = MVCObj对象,objectName = "arg0",clz = "com.cry.mini.springframework.examples.mvc.MVCObj"
            WebDataBinder wdb = binderFactory.createBinder(request, methodParamObj, methodParameter.getName());
            webBindingInitializer.initBinder(wdb);
            wdb.bind(request, wdb);
            methodParamObjs[i] = methodParamObj;
            // 循环过程就是按照参数在方法中出现的次序逐个绑定的，所以这个次序是很重要的。
            i++;
        }
        Method invocableMethod = handlerMethod.getMethod();
        // controller对象+handler方法+所需参数
        Object returnObj = invocableMethod.invoke(handlerMethod.getBean(), methodParamObjs);
        ModelAndView mav = null;
        //如果是ResponseBody注解，仅仅返回值，则转换数据格式后直接写到response
        if (invocableMethod.isAnnotationPresent(ResponseBody.class)) { //ResponseBody
            this.messageConverter.write(returnObj, response);
        } else {
            // 返回前端页面
            if (returnObj instanceof ModelAndView) {
                mav = (ModelAndView) returnObj;
            } else if (returnObj instanceof String) {
                String sTarget = (String) returnObj;
                mav = new ModelAndView();
                mav.setViewName(sTarget);
            }
        }
        return mav;
    }

    public WebBindingInitializer getWebBindingInitializer() {
        return webBindingInitializer;
    }

    public void setWebBindingInitializer(WebBindingInitializer webBindingInitializer) {
        this.webBindingInitializer = webBindingInitializer;
    }

    public HttpMessageConverter getMessageConverter() {
        return messageConverter;
    }

    public void setMessageConverter(HttpMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }
}
