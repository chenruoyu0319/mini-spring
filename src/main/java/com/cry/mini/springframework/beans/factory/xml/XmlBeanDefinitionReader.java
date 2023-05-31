package com.cry.mini.springframework.beans.factory.xml;

import com.cry.mini.springframework.beans.PropertyValue;
import com.cry.mini.springframework.beans.PropertyValues;
import com.cry.mini.springframework.beans.factory.config.BeanDefinition;
import com.cry.mini.springframework.beans.factory.config.ConstructorArgumentValue;
import com.cry.mini.springframework.beans.factory.config.ConstructorArgumentValues;
import com.cry.mini.springframework.beans.factory.support.AbstractBeanFactory;
import com.cry.mini.springframework.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 功能描述: 解析好的xml转化为BeanDefinition
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/04/18 17:16
 */
public class XmlBeanDefinitionReader {

    AbstractBeanFactory beanFactory;

    public XmlBeanDefinitionReader(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            //解析配置
            Element element = (Element) resource.next();
            //获取Bean的基本信息
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            //处理属性
            List<Element> propertyElements = element.elements("property");
            PropertyValues PVS = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for (Element e : propertyElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                String pRef = e.attributeValue("ref");
                String pV = "";
                boolean isRef = false;
                if (pValue != null && !pValue.equals("")) {
                    // property标签使用value
                    isRef = false;
                    pV = pValue;
                } else if (pRef != null && !pRef.equals("")) {
                    // property标签使用ref
                    isRef = true;
                    pV = pRef;
                    refs.add(pRef);
                }
                PVS.addPropertyValue(new PropertyValue(pType, pName, pV,isRef));
            }
            //属性列表
            beanDefinition.setPropertyValues(PVS);
            //
            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);
            //处理构造器参数
            List<Element> constructorElements = element.elements("constructor-arg");
            ConstructorArgumentValues AVS = new ConstructorArgumentValues();
            for (Element e : constructorElements) {
                String aType = e.attributeValue("type");
                String aName = e.attributeValue("name");
                String aValue = e.attributeValue("value");
                String aRef = e.attributeValue("ref");
                String aV = "";
                boolean isRef = false;
                if (aValue != null && !aValue.equals("")) {
                    // constructor-arg标签使用value
                    isRef = false;
                    aV = aValue;
                } else if (aRef != null && !aRef.equals("")) {
                    // constructor-arg标签使用ref
                    isRef = true;
                    aV = aRef;
                }
                AVS.addArgumentValue(new ConstructorArgumentValue(aType, aName,aV,isRef));
            }
            beanDefinition.setConstructorArgumentValues(AVS);
            //将Bean的定义存放到beanDefinitions
            this.beanFactory.registerBeanDefinition(beanID, beanDefinition);
        }
    }
}
