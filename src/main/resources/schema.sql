-- src/main/resources/schema.sql

CREATE TABLE IF NOT EXISTS Book (
    id INT NOT NULL,
    name VARCHAR(255) DEFAULT NULL,
    title VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
);
