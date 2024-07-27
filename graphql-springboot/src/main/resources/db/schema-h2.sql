DROP TABLE IF EXISTS author;

CREATE TABLE t_author (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS book;

CREATE TABLE t_book (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    author_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES t_author(id)
);


