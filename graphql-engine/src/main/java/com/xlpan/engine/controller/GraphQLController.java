package com.xlpan.engine.controller;

import com.xlpan.engine.initialize.GraphQLManager;
import graphql.ExecutionResult;
import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * ClassName: GraphQLController<br/>
 * Description: <br/>
 * date: 2019/6/28 5:38 PM<br/>
 *
 * @author chengluchao
 * @since JDK 1.8
 */
@RequestMapping("graphql")
@RestController
public class GraphQLController {

    @Autowired
    private GraphQLManager graphQLManager;

    @Autowired
    private GraphQL graphQL;

    @RequestMapping("query")
    @ResponseBody
    public Object query(@RequestParam("query") String query) {
        ExecutionResult result = this.graphQL.execute(query);
        return result.toSpecification();
    }
}
