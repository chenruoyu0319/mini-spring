package com.cry.mini.springframework.core;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/04/18 17:10
 */

public class ClassPathXmlResource implements Resource {
    Document document;
    Element rootElement;
    Iterator<Element> elementIterator;

    public ClassPathXmlResource(String fileName) {
        // SAXReader这个对象是 dom4j 包内提供的
        SAXReader saxReader = new SAXReader();
        URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
        //将配置文件装载进来，生成一个迭代器，可以用于遍历
        try {
            this.document = saxReader.read(xmlPath);
            // beans
            this.rootElement = document.getRootElement();
            // 配置文件中的每一个<bean>的迭代器
            this.elementIterator = this.rootElement.elementIterator();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public ClassPathXmlResource(URL xmlPath) {
        // SAXReader这个对象是 dom4j 包内提供的
        SAXReader saxReader = new SAXReader();
        //将配置文件装载进来，生成一个迭代器，可以用于遍历
        try {
            this.document = saxReader.read(xmlPath);
            // beans
            this.rootElement = document.getRootElement();
            // 配置文件中的每一个<bean>的迭代器
            this.elementIterator = this.rootElement.elementIterator();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasNext() {
        return this.elementIterator.hasNext();
    }

    @Override
    public Object next() {
        return this.elementIterator.next();
    }
}
