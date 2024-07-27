package com.xplan.testpostconstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ClassName: Asevice
 * Description:
 * date: 2020/9/27 3:53 下午
 *
 * @author chengluchao
 * @since JDK 1.8
 */
@Component
public class Asevice {
    //TODO 把Bservie放到上面就可以解决异常
    @Autowired
    private Cbean cbean;

    @Autowired
    private Bservie bservie;

}
