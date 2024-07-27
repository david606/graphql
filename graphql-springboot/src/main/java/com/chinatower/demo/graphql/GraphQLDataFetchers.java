package com.chinatower.demo.graphql;

import com.chinatower.demo.mapper.AuthorMapper;
import com.chinatower.demo.mapper.BookMapper;
import com.chinatower.demo.model.Author;
import com.chinatower.demo.model.Book;
import graphql.language.Field;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class GraphQLDataFetchers {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AuthorMapper authorMapper;

    public DataFetcher<Book> getBookByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            Long bookId = Long.parseLong(dataFetchingEnvironment.getArgument("id"));
            String name = dataFetchingEnvironment.getArgument("name");
            return bookMapper.getBookWithAuthor(bookId, name);
        };
    }

    public DataFetcher<Author> getAuthorDataFetcher() {
        return dataFetchingEnvironment -> {
            Book book = dataFetchingEnvironment.getSource();
            return book.getAuthor();
        };
    }

    public DataFetcher<Book> selectBookByDynamicFields() {
        return dataFetchingEnvironment -> {
            Long bookId = Long.parseLong(dataFetchingEnvironment.getArgument("id"));
            String name = dataFetchingEnvironment.getArgument("name");
            List<Field> fields1 = dataFetchingEnvironment.getFields();
            /**
             * 如何获取查询结构(schema)的字段??????，暂时拿传入方法的参数举例
             * Object source = dataFetchingEnvironment.getSelectionSet();
             * {
             *   selectBookByDynamicFields(id: "2",name: "MySQL 入门与实践"){
             *     id
             *     name
             *     price
             *     author {
             *         id
             *         name
             *     }
             *   }
             * }
             */
            List<String> fields = new ArrayList<>();

            fields.add("a.id");
            fields.add("a.name");
            fields.add("b.id");
            fields.add("b.NAME");
            fields.add("b.price");
            fields.add("b.author_id");
            return bookMapper.selectBookByDynamicFields(bookId, name, fields);
        };
    }
}
