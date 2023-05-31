package com.cry.mini.springframework.examples.ibatis;

import com.cry.mini.springframework.examples.jdbc.User;
import com.cry.mini.springframework.ibatis.SqlSessionFactory;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/29 16:12
 */
public interface IUserService2 {

     User getUserInfo(int userid);

     SqlSessionFactory getSqlSessionFactory();

    void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory);

    User doUserInfo(int userid);
}
