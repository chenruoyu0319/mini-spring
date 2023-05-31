package com.cry.mini.springframework.ibatis;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/22 0:44
 */
public interface SqlSessionFactory {
    SqlSession openSession();
    MapperNode getMapperNode(String name);

}
