package com.cry.mini.springframework.beans.factory.support;

import com.cry.mini.springframework.beans.PropertyValue;
import com.cry.mini.springframework.beans.PropertyValues;
import com.cry.mini.springframework.beans.factory.BeanFactory;
import com.cry.mini.springframework.beans.factory.BeanFactoryAware;
import com.cry.mini.springframework.beans.factory.FactoryBean;
import com.cry.mini.springframework.beans.factory.config.BeanDefinition;
import com.cry.mini.springframework.beans.factory.config.ConstructorArgumentValue;
import com.cry.mini.springframework.beans.factory.config.ConstructorArgumentValues;
import com.cry.mini.springframework.exception.BeansException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 功能描述: 基于代码复用、解耦的原则，我们可以对通用部分代码进行抽象, 抽象来源于原SimpleBeanFactory
 * refresh()、getBean()、registerBeanDefinition() 等
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/06 17:24
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements BeanFactory, BeanDefinitionRegistry {

    protected final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    protected final List<String> beanDefinitionNames = new ArrayList<>();
    // 实例化的bean
    // springbean的二级缓存
    private final Map<String, Object> earlySingletonObjects = new HashMap<String, Object>(16);

    public AbstractBeanFactory() {
    }

    // 对所有的 Bean 调用了一次 getBean()，利用 getBean() 方法中的 createBean() 创建 Bean 实例，
    // 就可以只用一个方法把容器中所有的 Bean 的实例创建出来了。
    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 容器的核心方法, 对外通过上下文暴露
     *
     * @param beanName beanName
     * @return bean
     * @throws BeansException BeansException
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        //先尝试直接拿Bean实例
        Object singleton = this.getSingleton(beanName);
        //如果此时还没有这个Bean的实例，则获取它的定义来创建实例
        if (singleton == null) {
            //如果没有实例，则尝试从毛胚实例中获取
            singleton = this.earlySingletonObjects.get(beanName);
            if (singleton == null) {
                //如果连毛胚都没有，则创建bean实例并注册
                //获取bean的定义
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                if (beanDefinition == null) {
                    return null;
                }
                // 创建这个bean
                singleton = createBean(beanDefinition);
                // 新注册这个bean实例
                this.registerSingleton(beanName, singleton);
                // 对于实现了BeanFactoryAware的对象需要设置BeanFactory属性，目前在aop中用到
                if (singleton instanceof BeanFactoryAware) {
                    ((BeanFactoryAware) singleton).setBeanFactory(this);
                }
                /** 以上实例化完成, 以下进入初始化阶段 */
                // 在以前已经调用了构造方法和配置文件的DI注入,并实现了ioc容器的注入
                /** beanpostprocessor, 所谓后置处理处理的是实例化后的bean */
                // 预留beanpostprocessor位置
                // step 1: postProcessBeforeInitialization
                singleton = applyBeanPostProcessorsBeforeInitialization(singleton, beanName);
                // step 2: afterPropertiesSet
                // step 3: init-method
                if (beanDefinition.getInitMethodName() != null && !beanDefinition.equals("")) {
                    invokeInitMethod(beanDefinition, singleton);
                }
                // step 4: postProcessAfterInitialization
                applyBeanPostProcessorsAfterInitialization(singleton, beanName);
            }
        }
        //process Factory Bean
        if (singleton instanceof FactoryBean) {
            System.out.println("factory bean -------------- " + beanName + "----------------" + singleton);
            Object factoryBeanInstance = this.getObjectForBeanInstance(singleton, beanName);
            System.out.println("factory bean instance-------------- " + beanName + "----------------" + factoryBeanInstance.getClass());
            return factoryBeanInstance;
        }
        return singleton;
    }

    private void invokeInitMethod(BeanDefinition beanDefinition, Object obj) {
        Class clz = beanDefinition.getClass();
        Method method = null;
        try {
            method = clz.getMethod(beanDefinition.getInitMethodName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean containsBean(String beanName) {
        return this.containsSingleton(beanName);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

    //BeanDefinitionRegistry
    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(name, beanDefinition);
        this.beanDefinitionNames.add(name);
        if (!beanDefinition.isLazyInit()) {
            try {
                getBean(name);
            } catch (BeansException e) {
            }
        }
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitionMap.get(beanName);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }


    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String beanName) {
        return this.beanDefinitionMap.get(beanName).getClass();
    }

    /**
     * 实例化bean
     *
     * @param beanDefinition beanDefinition
     * @return bean
     */
    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        //创建毛胚bean实例
        Object obj = doCreateBean(beanDefinition);
        //存放到毛胚实例缓存中
        this.earlySingletonObjects.put(beanDefinition.getId(), obj);
        try {
            clz = Class.forName(beanDefinition.getClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //完善bean，主要是处理属性
        populateBean(beanDefinition, clz, obj);
        return obj;
    }

    //doCreateBean创建毛胚实例，仅仅调用构造方法，没有进行属性处理
    private Object doCreateBean(BeanDefinition bd) {
        Class clz = null;
        Object obj = null;
        Constructor<?> con = null;
        try {
            clz = Class.forName(bd.getClassName());
            // 处理构造器参数
            ConstructorArgumentValues constructorArgumentValues = bd.getConstructorArgumentValues();
            //如果有参数  //如果有参数
            if (constructorArgumentValues != null && !constructorArgumentValues.isEmpty()) {
                // 构造器类型
                Class<?>[] paramTypes = new Class<?>[constructorArgumentValues.getArgumentCount()];
                // 构造器值
                Object[] paramValues = new Object[constructorArgumentValues.getArgumentCount()];
                //对每一个参数，分数据类型分别处理
                for (int i = 0; i < constructorArgumentValues.getArgumentCount(); i++) {
                    ConstructorArgumentValue constructorArgumentValue = constructorArgumentValues.getIndexedArgumentValue(i);
                    boolean isRef = constructorArgumentValue.getIsRef();
                    if (!isRef) {
                        // 普通类型走数据类型
                        if ("String".equals(constructorArgumentValue.getType()) || "java.lang.String".equals(constructorArgumentValue.getType())) {
                            paramTypes[i] = String.class;
                            paramValues[i] = constructorArgumentValue.getValue();
                        } else if ("Integer".equals(constructorArgumentValue.getType()) || "java.lang.Integer".equals(constructorArgumentValue.getType())) {
                            paramTypes[i] = Integer.class;
                            paramValues[i] = Integer.valueOf((String) constructorArgumentValue.getValue());
                        } else if ("int".equals(constructorArgumentValue.getType())) {
                            paramTypes[i] = int.class;
                            paramValues[i] = Integer.valueOf((String) constructorArgumentValue.getValue());
                        } else { //默认为string
                            paramTypes[i] = String.class;
                            paramValues[i] = constructorArgumentValue.getValue();
                        }
                    } else {
                        //is ref, create the dependent beans
                        try {
                            paramTypes[i] = Class.forName(constructorArgumentValue.getType());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            //再次调用getBean创建ref的bean实例(递归)
                            paramValues[i] = getBean((String) constructorArgumentValue.getValue());
                        } catch (BeansException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    //按照特定构造器创建实例
                    con = clz.getConstructor(paramTypes);
                    obj = con.newInstance(paramValues);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else { //如果没有参数，直接创建实例
                obj = clz.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("create bean : " + bd.getId() + " : " + obj.toString());
        return obj;
    }

    // 填充bean
    private void populateBean(BeanDefinition beanDefinition, Class clz, Object obj) {
        handleProperties(beanDefinition, clz, obj);
    }

    private void handleProperties(BeanDefinition bd, Class clz, Object obj) {
        // 处理属性
        System.out.println("handle properties for bean : " + bd.getId());
        PropertyValues propertyValues = bd.getPropertyValues();
        //如果有属性
        if (propertyValues != null && !propertyValues.isEmpty()) {
            for (int i = 0; i < propertyValues.size(); i++) {
                //对每一个属性，分数据类型分别处理
                PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                String pType = propertyValue.getType();
                String pName = propertyValue.getName();
                Object pValue = propertyValue.getValue();
                boolean isRef = propertyValue.getIsRef();
                Class<?>[] paramTypes = new Class<?>[1];
                Object[] paramValues = new Object[1];
                if (!isRef) {
                    // 如果不是ref，只是普通属性
                    // 对每一个属性，分数据类型分别处理
                    if ("String".equals(pType) || "java.lang.String".equals(pType)) {
                        paramTypes[0] = String.class;
                        paramValues[0] = pValue;
                    } else if ("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
                        paramTypes[0] = Integer.class;
                        paramValues[0] = Integer.valueOf((String) pValue);
                    } else if ("int".equals(pType)) {
                        paramTypes[0] = int.class;
                        paramValues[0] = Integer.valueOf((String) pValue);
                    } else { // 默认为string
                        paramTypes[0] = String.class;
                        paramValues[0] = pValue;
                    }
                } else {
                    //is ref, create the dependent beans
                    try {
                        paramTypes[0] = Class.forName(pType);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        //再次调用getBean创建ref的bean实例(递归)
                        paramValues[0] = getBean((String) pValue);
                    } catch (BeansException e) {
                        e.printStackTrace();
                    }
                }
                //按照setXxxx规范查找setter方法，调用setter方法设置属性
                String methodName = "set" + pName.substring(0, 1).toUpperCase()
                        + pName.substring(1);
                Method method = null;
                try {
                    method = clz.getMethod(methodName, paramTypes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (method == null){
                        System.out.println("目标类【"+clz+"】中没有找到【" + methodName + "】方法");
                    }
                    method.invoke(obj, paramValues);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        // Now we have the bean instance, which may be a normal bean or a FactoryBean.
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }
        Object object = null;
        FactoryBean<?> factory = (FactoryBean<?>) beanInstance;
        object = getObjectFromFactoryBean(factory, beanName);
        return object;
    }

    abstract public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    abstract public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
