package com.chinatower.demo.controller;

import graphql.ExecutionResult;
import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * ClassName: GraphQLController
 */
@RequestMapping("graphql")
@Controller
public class GraphQLController {

    @Autowired
    private GraphQL graphQL;

    @RequestMapping("query")
    @ResponseBody
    public Object query(@RequestBody String query) {
//        System.out.println("query ::::::::::: " + query);
        ExecutionResult result = this.graphQL.execute(query);
        return result.toSpecification();
    }
}
