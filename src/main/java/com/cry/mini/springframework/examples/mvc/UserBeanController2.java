package com.cry.mini.springframework.examples.mvc;

import com.cry.mini.springframework.beans.factory.annotation.Autowired;
import com.cry.mini.springframework.examples.ibatis.UserService2;
import com.cry.mini.springframework.examples.jdbc.User;
import com.cry.mini.springframework.web.bind.annotation.RequestMapping;
import com.cry.mini.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/19 17:41
 */
public class UserBeanController2 {

    @Autowired
    UserService2 userService2;

    @RequestMapping("/user2/id")
    @ResponseBody
    public User getUserInfo(User user) {
        return userService2.getUserInfo(user.getId());
    }

}
