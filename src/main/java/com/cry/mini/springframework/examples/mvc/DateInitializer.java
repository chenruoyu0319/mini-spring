package com.cry.mini.springframework.examples.mvc;

import com.cry.mini.springframework.beans.BeanWrapperImpl;
import com.cry.mini.springframework.web.bind.WebDataBinder;
import com.cry.mini.springframework.web.bind.support.WebBindingInitializer;

import java.util.Date;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/16 16:15
 */
public class DateInitializer implements WebBindingInitializer {

    @Override
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Date.class, "yyyy-MM-dd", false));
    }

    public static void main(String[] args) {
        System.out.println(new Date());
    }
}
