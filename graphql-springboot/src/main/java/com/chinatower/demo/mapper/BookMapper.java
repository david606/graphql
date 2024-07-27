package com.chinatower.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chinatower.demo.model.Book;

import java.util.List;
import java.util.Map;

public interface BookMapper extends BaseMapper<Book> {

    public Book getBookWithAuthor(Long id, String name);

    public Book selectBookByDynamicFields(Long id, String name, List<String> fields);
    public Book selectBookByDynamicFields(Long id, String name, Object[] fields);
}