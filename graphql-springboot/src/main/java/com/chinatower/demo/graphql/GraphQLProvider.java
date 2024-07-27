package com.chinatower.demo.graphql;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

/**
 * 实现功能：将GraphQL对象载入待Spring容器，并且完成GraphQL对象初始化的功能
 */
@Component
public class GraphQLProvider {

    @Autowired
    GraphQLDataFetchers graphQLDataFetchers;


    private GraphQL graphQL;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    /**
     * 加载schema
     *
     * @throws IOException
     */
    @PostConstruct
    public void init() throws IOException {
        File file = ResourceUtils.getFile("classpath:user.graphqls");
        GraphQLSchema graphQLSchema = buildGraphQLSchema(file);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    /**
     * 根据给定的文件构建GraphQL模式。
     * <pre>
     * 此方法通过解析指定文件中的GraphQL类型定义，然后使用这些定义来构建一个可执行的GraphQL模式。
     * 解析过程使用GraphQL的SchemaParser来处理输入文件，然后通过SchemaGenerator创建可执行的模式。
     * 这个方法的核心在于将定义文件转换为可被GraphQL引擎使用的实际模式实例。
     *
     * @param file 包含GraphQL类型定义的文件。这个文件通常使用GraphQL的SDL（Schema Definition Language）
     *             来定义类型系统。
     * @return 返回构建好的GraphQL模式实例，这个实例可以用于处理GraphQL查询和突变。
     */
    private GraphQLSchema buildGraphQLSchema(File file) {
        // 使用SchemaParser解析输入文件中的GraphQL类型定义。
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(file);

        // 使用解析后的类型定义和额外的配置（通过buildWiring方法提供）来创建一个可执行的GraphQL模式。

        // 返回构建好的GraphQL模式。
        return new SchemaGenerator().makeExecutableSchema(typeRegistry, buildWiring());
    }


    /**
     * 根据给定的SDL字符串构建GraphQL模式。
     * <pre>
     * 此方法使用GraphQL Java库来解析SDL字符串，配置运行时 wiring，然后生成可执行的GraphQL模式。
     * 这个模式可以然后用于处理GraphQL查询和突变。
     *
     * @param sdl 字符串形式的GraphQL模式定义语言（SDL）。
     * @return 构建好的GraphQL模式对象。
     */
    private GraphQLSchema buildSchema(String sdl) {
        // 解析SDL字符串以生成类型定义注册表。
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);

        // 构建运行时wiring，这定义了如何处理GraphQL查询中的字段和其他元素。
        RuntimeWiring runtimeWiring = buildWiring();

        // 创建一个SchemaGenerator实例，用于生成GraphQL Schema。
        SchemaGenerator schemaGenerator = new SchemaGenerator();

        // 使用类型定义注册表和运行时wiring生成可执行的GraphQL Schema。
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("BookQuery")
                        .dataFetcher("bookByParams", graphQLDataFetchers.getBookByIdDataFetcher()))
                .type(newTypeWiring("BookQuery")
                        .dataFetcher("selectBookByDynamicFields", graphQLDataFetchers.selectBookByDynamicFields()))
                .type(newTypeWiring("Book")
                        .dataFetcher("author", graphQLDataFetchers.getAuthorDataFetcher()))
                .build();
    }
}