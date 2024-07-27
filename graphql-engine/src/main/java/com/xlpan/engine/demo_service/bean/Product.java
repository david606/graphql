package com.xlpan.engine.demo_service.bean;

import lombok.Builder;
import lombok.Data;

/**
 * ClassName: Product
 * Description:
 * date: 2020/9/28 9:11 上午
 *
 * @author chengluchao
 * @since JDK 1.8
 */
@Data
@Builder
public class Product {
    private Long id;
    private String sppu;
    private String name;
    private Long price;
}
