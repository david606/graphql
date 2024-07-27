package com.xplan.testpostconstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * ClassName: Bservie
 * Description:
 * date: 2020/9/27 3:53 下午
 *
 * @author chengluchao
 * @since JDK 1.8
 */
@Component
public class Bservie {
    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        Cbean c = new Cbean();
        c.setId(1);
        c.setName("name");
        //获取BeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
        //动态注册bean.
        defaultListableBeanFactory.registerSingleton("cbean",c);
    }
}
