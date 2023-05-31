package com.cry.mini.springframework.examples.ibatis;

import com.cry.mini.springframework.examples.jdbc.User;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/22 13:12
 */
public interface UserDao {

   User getUserInfo(Integer userId);
}
