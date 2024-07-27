package com.xlpan.engine.provider;

import com.xlpan.engine.exception.EngineException;
import com.xlpan.engine.initialize.ValueTypeTranslator;
import com.xlpan.engine.resolver.IQueryResolver;
import com.xlpan.engine.resolver.impl.CommonQueryResolver;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLFieldDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * ClassName: QueryCommonProvider
 * Description:
 * date: 2020/9/27 10:31 上午
 *
 * @author chengluchao
 * @since JDK 1.8
 */
@Slf4j
@Component
public class QueryCommonProvider implements DataFetcher {

    @Autowired
    private List<IQueryResolver> queryResolverList;

    @Autowired
    private CommonQueryResolver commonQueryResolver;


    /**
     * graphql执行业务实现
     *
     * @param environment
     * @return
     * @throws Exception
     */
    @Override
    public Object get(DataFetchingEnvironment environment) throws Exception {
        GraphQLFieldDefinition fieldDefinition = environment.getFieldDefinition();
        String name = fieldDefinition.getName();
        Object[] traslatedArgs = new Object[0];
        Method currentMethord = null;
        IQueryResolver curResolver = null;
        for (IQueryResolver resolver : this.queryResolverList) {
            Method[] methods = resolver.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(name)) {
                    currentMethord = method;
                    curResolver = resolver;
                    break;
                }
            }
        }
        if (currentMethord == null) {
            return doExcute(name, traslatedArgs);
        }
        Method real = AopUtils.getMostSpecificMethod(currentMethord,
                AopUtils.getTargetClass(curResolver));
        try {
            traslatedArgs = ValueTypeTranslator
                    .translateArgs(real, environment.getArguments(),
                            fieldDefinition.getArguments());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return doExcute(name, traslatedArgs);
    }

    /**
     * 遍历service和method寻找匹配的serviceMethod
     *
     * @param functionName
     * @param args
     * @return
     */
    private Object doExcute(String functionName, Object[] args) {
        for (IQueryResolver resolver : this.queryResolverList) {
            Method[] methods = resolver.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(functionName)) {
                    try {
                        return method.invoke(resolver, args);
                    } catch (IllegalAccessException e) {
                        throw new EngineException(e.getMessage());
                    } catch (InvocationTargetException e) {
                        Throwable cause = e.getTargetException();
                        if (cause instanceof EngineException) {
                            throw (EngineException) cause;
                        }
                        throw new EngineException(e.getCause().getMessage());
                    }
                }
            }
        }
        //找不到 走common
        this.commonQueryResolver.excute(functionName, args);
        return null;
    }

}
