package com.xlpan.engine.provider;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * ClassName: MutationCommonProvider
 * Description:
 * date: 2020/9/27 10:38 上午
 *
 * @author chengluchao
 * @since JDK 1.8
 */
@Slf4j
@Component
public class MutationCommonProvider implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment environment) throws Exception {
        return null;
    }
}
