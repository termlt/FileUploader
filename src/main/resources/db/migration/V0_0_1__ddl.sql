CREATE TABLE books (
                       id BINARY(16) PRIMARY KEY,
                       title VARCHAR(255),
                       author_id BINARY(16),
                       price INT,
                       FOREIGN KEY (author_id) REFERENCES author(id)
);