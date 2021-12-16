create table tag
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL
);

INSERT INTO tag (name) VALUES ('sport'), ('massage');