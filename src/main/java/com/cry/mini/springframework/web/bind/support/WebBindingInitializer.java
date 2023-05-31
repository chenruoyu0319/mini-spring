package com.cry.mini.springframework.web.bind.support;


import com.cry.mini.springframework.beans.BeanWrapperImpl;
import com.cry.mini.springframework.web.bind.WebDataBinder;

public interface WebBindingInitializer {
	void initBinder(WebDataBinder binder);
}
