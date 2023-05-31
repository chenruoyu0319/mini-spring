package com.cry.mini.springframework.examples.ioc;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/04/11 0:53
 */
public class AServiceImpl implements AService {

    private String property1;
    private String property2;
    private String name;
    private int level;
    private BaseService ref1;
    private BaseConService refCon;

    public AServiceImpl() {
    }

    public AServiceImpl(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public AServiceImpl(String name, int level, BaseConService refCon) {
        this.name = name;
        this.level = level;
        this.refCon = refCon;
    }

    @Override
    public void sayHello() {
        System.out.println(this.property1 + "," + this.property2);
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    public BaseService getRef1() {
        return ref1;
    }

    public void setRef1(BaseService ref1) {
        this.ref1 = ref1;
    }


    @Override
    public String toString() {
        return "AServiceImpl{" +
                "property1='" + property1 + '\'' +
                ", property2='" + property2 + '\'' +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", ref1=" + ref1 +
                ", refCon=" + refCon +
                '}';
    }
}
