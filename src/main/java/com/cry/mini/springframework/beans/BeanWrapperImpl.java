package com.cry.mini.springframework.beans;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <p>
 * 功能描述: bean包装类 —— 参数值与对象属性进行绑定
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/15 17:48
 */
public class BeanWrapperImpl extends PropertyEditorRegistrySupport {
    Object wrappedObject; //目标对象
    Class<?> clz;
    PropertyValues pvs; //参数值

    public BeanWrapperImpl(Object object) {
        super(); //不同数据类型的参数转换器editor
        this.wrappedObject = object;
        this.clz = object.getClass();
    }

    public void setBeanInstance(Object object) {
        this.wrappedObject = object;
    }

    public Object getBeanInstance() {
        return wrappedObject;
    }

    //绑定参数值
    public void setPropertyValues(PropertyValues pvs) {
        this.pvs = pvs;
        for (PropertyValue pv : this.pvs.getPropertyValues()) {
            setPropertyValue(pv);
        }
    }

    //绑定具体某个参数
    public void setPropertyValue(PropertyValue pv) {
        //拿到参数处理器
        BeanPropertyHandler propertyHandler = new BeanPropertyHandler(pv.getName());
        //先尝试获取自定义的类型转换器Editor
        PropertyEditor pe = this.getCustomEditor(propertyHandler.getPropertyClz());
        if (pe == null) {
            //获取不到再获取默认的类型转换器Editor
            pe = this.getDefaultEditor(propertyHandler.getPropertyClz());
        }
        //设置参数值
        pe.setAsText((String) pv.getValue());
        //调用setter设置目标对象的属性值
        propertyHandler.setValue(pe.getValue());
    }

    //一个内部类，用于处理参数，通过getter()和setter()操作属性
    class BeanPropertyHandler {
        Method writeMethod = null;
        Method readMethod = null;
        Class<?> propertyClz = null;

        public Class<?> getPropertyClz() {
            return propertyClz;
        }

        public BeanPropertyHandler(PropertyValue pv) {
            try {
//                Class<?> aClass = Class.forName(pv.getType());
//                if (BaseTypeUtil.STANDARD_NUMBER_TYPES.contains(aClass)){
//                    this.propertyClz = aClass;
//                    return;
//                }
                String propertyName = pv.getName();
                //获取参数对应的属性及类
                Field field = clz.getDeclaredField(propertyName);
                propertyClz = field.getType();
                //获取设置属性的方法，按照约定为setXxxx()
                this.writeMethod = clz.getDeclaredMethod("set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), propertyClz);
                //获取读属性的方法，按照约定为getXxxx()
                this.readMethod = clz.getDeclaredMethod("get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), propertyClz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public BeanPropertyHandler(String propertyName) {
            try {
                //获取参数对应的属性及类
                Field field = clz.getDeclaredField(propertyName);
                propertyClz = field.getType();
                //获取设置属性的方法，按照约定为setXxxx()
                this.writeMethod = clz.getDeclaredMethod("set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), propertyClz);
                //获取读属性的方法，按照约定为getXxxx()
                this.readMethod = clz.getDeclaredMethod("get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //调用getter读属性值
        public Object getValue() {
            Object result = null;
            writeMethod.setAccessible(true);
            try {
                result = readMethod.invoke(wrappedObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        //调用setter设置属性值
        public void setValue(Object value) {
            writeMethod.setAccessible(true);
            try {
                writeMethod.invoke(wrappedObject, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
