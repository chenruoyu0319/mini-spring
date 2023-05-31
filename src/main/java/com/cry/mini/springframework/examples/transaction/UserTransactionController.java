package com.cry.mini.springframework.examples.transaction;

import com.cry.mini.springframework.beans.factory.annotation.Autowired;
import com.cry.mini.springframework.examples.ibatis.IUserService2;
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
public class UserTransactionController {

    @Autowired
    IUserService2 userService2;

    @RequestMapping("/do/userInfo/id")
    @ResponseBody
    public User doUserInfo(User user) {
        return userService2.doUserInfo(user.getId());
    }

}
