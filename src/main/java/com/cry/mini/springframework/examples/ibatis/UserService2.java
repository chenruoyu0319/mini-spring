package com.cry.mini.springframework.examples.ibatis;

import com.cry.mini.springframework.beans.factory.annotation.Autowired;
import com.cry.mini.springframework.examples.jdbc.User;
import com.cry.mini.springframework.ibatis.SqlSession;
import com.cry.mini.springframework.ibatis.SqlSessionFactory;

import java.lang.reflect.Method;
import java.sql.ResultSet;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/19 10:55
 */
public class UserService2 implements IUserService2{

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Override
    public User getUserInfo(int userid) {
        // 后续改为加载Dao层接口
        Class<UserDao> userDaoClass = UserDao.class;
        Method getUserInfo = null;
        try {
            getUserInfo = userDaoClass.getDeclaredMethod("getUserInfo", Integer.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        String sqlid = userDaoClass.getName() + "." + getUserInfo.getName();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return (User) sqlSession.selectOne(sqlid, new Object[]{new Integer(userid)}, (pstmt) -> {
            ResultSet rs = pstmt.executeQuery();
            User rtnUser = null;
            if (rs.next()) {
                rtnUser = new User();
                rtnUser.setId(userid);
                rtnUser.setName(rs.getString("name"));
                rtnUser.setBirthday(new java.util.Date(rs.getDate("birthday").getTime()));
            } else {
            }
            return rtnUser;
        });
    }

    @Override
    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public User doUserInfo(int userid) {
        return getUserInfo(userid);
    }
}
