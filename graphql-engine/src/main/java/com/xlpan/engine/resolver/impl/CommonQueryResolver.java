package com.xlpan.engine.resolver.impl;

import com.xlpan.engine.exception.EngineErrorCodeEnmu;
import com.xlpan.engine.exception.EngineException;
import com.xlpan.engine.resolver.IQueryResolver;
import org.springframework.stereotype.Component;

/**
 * ClassName: CommonQueryResolver
 * Description:
 * date: 2020/9/28 8:33 上午
 *
 * @author chengluchao
 * @since JDK 1.8
 */
@Component
public class CommonQueryResolver implements IQueryResolver {

    public Object excute(String functionName, Object[] args) {
        // TODO 暂时没有功能实现
        throw new EngineException(EngineErrorCodeEnmu.REQ_NOT_EXIT_ERROR);
    }
}