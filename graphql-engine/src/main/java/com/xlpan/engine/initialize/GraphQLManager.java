package com.xlpan.engine.initialize;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * ClassName: GraphQLManager
 * Description:
 * date: 2020/9/27 10:11 上午
 *
 * @author chengluchao
 * @since JDK 1.8
 */
@Component
@Slf4j
public class GraphQLManager {
    @Autowired
    private GraphQLManagerProvider graphQLManagerProvider;

    @PostConstruct
    public void init() {
        try {
            log.info("graphql init");
            graphQLManagerProvider.createGraphQL();
        } catch (Exception e) {
            log.error("GraphQLManager#init", e);
        }
    }
}
