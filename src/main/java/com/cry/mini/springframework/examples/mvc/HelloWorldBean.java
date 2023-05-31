package com.cry.mini.springframework.examples.mvc;

import com.cry.mini.springframework.web.bind.annotation.RequestMapping;
import com.cry.mini.springframework.web.bind.annotation.ResponseBody;
import com.cry.mini.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/10 17:32
 */
public class HelloWorldBean {

    @RequestMapping("/test")
    public String doTest() {
        return "hello world for doGet!";
    }

    @RequestMapping("/param")
    public String doTestWithParam(String name, Integer age) {
        return "hello world for " + name + " doGet! " + age;
    }

    @RequestMapping("/obj")
    public String doTestWithObj(MVCObj mvcObj) {
        String obj = "hello world for " + mvcObj.getName() + " doGet! " + mvcObj.getAge() + " ,date is " + mvcObj.getDate();
        return obj;
    }

    @RequestMapping("/test7")
    @ResponseBody
    public MVCObj doTest7(MVCObj mvcObj) {
        mvcObj.setName(mvcObj.getName() + "---");
        mvcObj.setDate(new Date());
        return mvcObj;
    }

    @RequestMapping("/jsp/test")
    public ModelAndView doJspTest(MVCObj mvcObj) {
        ModelAndView mav = new ModelAndView("test", "msg", mvcObj.getName());
        return mav;
    }

    @RequestMapping("/jsp/error")
    public String doJspError(MVCObj mvcObj) {
        return "error";
    }

    @RequestMapping("/jsp/png")
    public String doJspPng(MVCObj mvcObj) {
        return "avatar";
    }

    @RequestMapping("/jsp/pdf")
    public String doJspPdf(MVCObj mvcObj) {
        return "red";
    }

}
