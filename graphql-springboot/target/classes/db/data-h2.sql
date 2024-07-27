-- 首先填充 author 表
INSERT INTO t_author (id, name) VALUES (1, '张三');
INSERT INTO t_author (id, name) VALUES (2, '李四');
INSERT INTO t_author (id, name) VALUES (3, '王五');

-- 然后填充 book 表，假设每本书都有一个作者
INSERT INTO t_book (id, name, price, author_id) VALUES (1, 'Java 编程思想', 99.99, 1);
INSERT INTO t_book (id, name, price, author_id) VALUES (2, 'MySQL 入门与实践', 79.99, 2);
INSERT INTO t_book (id, name, price, author_id) VALUES (3, 'Spring Boot 实战', 119.99, 3);
