package com.chinatower.demo.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_author")
public class Author {
    @TableId
    private Long id;
    private String name;
}
