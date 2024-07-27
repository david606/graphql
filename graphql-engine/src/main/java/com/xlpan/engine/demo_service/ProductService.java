package com.xlpan.engine.demo_service;

import com.xlpan.engine.demo_service.bean.Product;
import com.xlpan.engine.resolver.IQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * ClassName: UserService
 * Description: 对应graphql的业务端实现
 * date: 2020/9/28 9:07 上午
 *
 * @author chengluchao
 * @since JDK 1.8
 */
@Slf4j
@Service
public class ProductService implements IQueryResolver {

    public Product queryProductById(Long id) {
        return Product.builder()
                .id(id)
                .name("product")
                .price(592L)
                .sppu("post")
                .build();
    }
}
