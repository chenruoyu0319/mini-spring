package com.cry.mini.springframework.beans;

/**
 * <p>
 * 功能描述: 处理String及其他类型
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/15 14:54
 */
public class StringEditor implements PropertyEditor {

    private Class<String> strClass;  //数据类型
    private String strFormat;  //指定格式
    private boolean allowEmpty;
    private Object value;

    public StringEditor(Class<String> strClass, boolean allowEmpty) throws IllegalArgumentException {
        this(strClass, "", allowEmpty);
    }

    public StringEditor(Class<String> strClass, String strFormat, boolean allowEmpty) throws IllegalArgumentException {
        this.strClass = strClass;
        this.strFormat = strFormat;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) {
        setValue(text);
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String getAsText() {
        return value.toString();
    }

    @Override
    public Object getValue() {
        return this.value;
    }
}
