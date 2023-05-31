package com.cry.mini.springframework.web.content.support;

import com.cry.mini.springframework.core.Resource;
import com.cry.mini.springframework.web.MappingValue;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/10 11:42
 */
@Deprecated
public class XmlConfigReader {

    public XmlConfigReader() {

    }

    public Map<String, MappingValue> loadConfig(Resource res) {
        Map<String,MappingValue> mappings = new HashMap<>();
        while (res.hasNext()) {
            //读所有的节点，解析id, class和value
            Element element = (Element) res.next();
            // url
            String beanID = element.attributeValue("id");
            // class
            String beanClassName = element.attributeValue("class");
            // method
            String beanMethod = element.attributeValue("value");
            mappings.put(beanID, new MappingValue(beanID, beanClassName, beanMethod));
        }
        return mappings;
    }
}
