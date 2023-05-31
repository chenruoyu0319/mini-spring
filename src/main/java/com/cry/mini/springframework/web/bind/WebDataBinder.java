package com.cry.mini.springframework.web.bind;

import com.cry.mini.springframework.beans.BeanWrapperImpl;
import com.cry.mini.springframework.beans.PropertyEditor;
import com.cry.mini.springframework.beans.PropertyValues;
import com.cry.mini.springframework.util.WebUtils;
import com.cry.mini.springframework.web.bind.support.WebBindingInitializer;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 功能描述: BeanWrapperImpl的控制器
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/15 13:56
 */
public class WebDataBinder {

    // 初始化的目标对象
    private Object target;
    // 目标对象的类型
    private Class<?> clz;
    // 目标对象的名称: arg0 arg1 ...
    private String objectName;
    private BeanWrapperImpl propertyAccessor;

    public WebDataBinder(Object target, String targetName) {
        this.target = target;
        this.objectName = targetName;
        this.clz = this.target.getClass();
        this.propertyAccessor = new BeanWrapperImpl(this.target);
    }

    public void bind(HttpServletRequest request,WebDataBinder wdb) {
        // 1.把 Request 里的参数解析成 PropertyValues: 到这一步全是字符串
        PropertyValues mpvs = assignParameters(request);
        // 2.把 Request 里的参数值添加到绑定参数中: 暂时没用上
        addBindValues(mpvs, request);
        // 3.把两者绑定在一起。
        doBind(mpvs);
    }

    private void doBind(PropertyValues mpvs) {
        applyPropertyValues(mpvs);
    }

    //实际将参数值与对象属性进行绑定的方法
    protected void applyPropertyValues(PropertyValues mpvs) {
        BeanWrapperImpl propertyAccessor = getPropertyAccessor();
        propertyAccessor.setPropertyValues(mpvs);
    }

    //注册参数转换器editor
    protected BeanWrapperImpl getPropertyAccessor() {
        return this.propertyAccessor;
    }

    //将Request参数解析成PropertyValues
    private PropertyValues assignParameters(HttpServletRequest request) {
        // "age" -> "18"
        // "name" -> "小红"
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, "");
        return new PropertyValues(map);
    }

    // 注册自定义的类型转换器
    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        getPropertyAccessor().registerCustomEditor(requiredType, propertyEditor);
    }

    protected void addBindValues(PropertyValues mpvs, HttpServletRequest request) {

    }


}
