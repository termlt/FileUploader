CREATE TABLE author (
                        id BINARY(16) PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        surname VARCHAR(255) NOT NULL
);

ALTER TABLE books
    ADD COLUMN author_id BINARY(16),
    ADD CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES author(id);