package com.cry.mini.springframework.beans.factory.annotation;

import com.cry.mini.springframework.beans.factory.BeanFactory;
import com.cry.mini.springframework.beans.factory.config.BeanPostProcessor;
import com.cry.mini.springframework.exception.BeansException;

import java.lang.reflect.Field;

/**
 * <p>
 * 功能描述: @Autowired 的处理类 ,利用反射获取所有标注了 @Autowired 注解的成员变量，尝试从ioc容器中获取他的Bean，然后注入属性。
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/06 16:32
 */
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {

    private BeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 刚实例化完的bean
        Object result = bean;
        Class clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null) {
            //对每一个属性进行判断，如果带有@Autowired注解则进行处理
            for (Field field : fields) {
                /** 核心代码开始*/
                boolean isAutowired = field.isAnnotationPresent(Autowired.class);
                if (isAutowired) {
                    //根据属性名查找同名的bean: 这里开始尝试注入同名的result对象里的成员变量bean
                    String fieldName = field.getName();
                    Object autowiredObj = this.getBeanFactory().getBean(fieldName);
                    //设置属性值，完成注入
                    try {
                        field.setAccessible(true);
                        field.set(bean, autowiredObj);
                        /** 核心代码结束*/
                        System.out.println("autowire " + fieldName + " for bean " + beanName + ", the field type is " + autowiredObj.getClass().getSimpleName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
