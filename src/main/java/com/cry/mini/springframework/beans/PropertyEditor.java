package com.cry.mini.springframework.beans;

/**
 * <p>
 * 功能描述: 类型转换器顶层接口
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/15 14:52
 */
public interface PropertyEditor {

    void setAsText(String text);

    void setValue(Object value);

    Object getValue();

    Object getAsText();

}
