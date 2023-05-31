package com.cry.mini.springframework.examples.mvc;

import com.cry.mini.springframework.beans.factory.annotation.Autowired;
import com.cry.mini.springframework.examples.jdbc.IUserService;
import com.cry.mini.springframework.examples.jdbc.User;
import com.cry.mini.springframework.web.bind.annotation.RequestMapping;
import com.cry.mini.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/19 17:41
 */
public class UserBeanController {

    @Autowired
    IUserService userService;

    @RequestMapping("/user/id")
    @ResponseBody
    public User getUserInfo(User user) {
        return userService.getUserInfo(user.getId());
    }

    @RequestMapping("/user/id2")
    @ResponseBody
    public User getUserInfo2(User user) {
        return userService.getUserInfo2(user.getId());
    }

    @RequestMapping("/user/update")
    @ResponseBody
    public int update(User user) {
        return userService.update(user.getId(), user.getName());
    }

    @RequestMapping("/users")
    @ResponseBody
    public List<User> getUsers(User user) {
        return userService.getUsers(user.getId());
    }
}
