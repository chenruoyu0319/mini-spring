package com.cry.mini.springframework.examples.mvc;

import com.cry.mini.springframework.beans.PropertyEditor;
import com.cry.mini.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>
 * 功能描述: 自定义的时间类型转换器
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/16 15:58
 */
public class CustomDateEditor implements PropertyEditor {

    private Class<?> dateClass; //数据类型
    private DateTimeFormatter datetimeFormatter; //指定格式
    private boolean allowEmpty;
    private Date value;

    public CustomDateEditor() throws IllegalArgumentException {
        this(Date.class, "yyyy-MM-dd", true);
    }

    public CustomDateEditor(Class<?> dateClass) throws IllegalArgumentException {
        this(dateClass, "yyyy-MM-dd", true);
    }

    public CustomDateEditor(Class<?> dateClass, boolean allowEmpty) throws IllegalArgumentException {
        this(dateClass, "yyyy-MM-dd", allowEmpty);
    }

    public CustomDateEditor(Class<?> dateClass, String pattern, boolean allowEmpty) throws IllegalArgumentException {
        this.dateClass = dateClass;
        this.datetimeFormatter = DateTimeFormatter.ofPattern(pattern);
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            setValue(null);
        } else {
            LocalDate localdate = LocalDate.parse(text, datetimeFormatter);
            setValue(Date.from(localdate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
    }

    @Override
    public void setValue(Object value) {
        this.value = (Date) value;
    }

    @Override
    public String getAsText() {
        Date value = this.value;
        if (value == null) {
            return "";
        } else {
            LocalDate localDate = value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return localDate.format(datetimeFormatter);
        }
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    public static void main(String[] args) {
        LocalDate localdate = LocalDate.parse("2023-05-17", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Date from = Date.from(localdate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        System.out.println(from);
    }
}
