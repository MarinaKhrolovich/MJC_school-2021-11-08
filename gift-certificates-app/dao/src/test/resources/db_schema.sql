create table certificate
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(100) NOT NULL,
    description      VARCHAR(100) NOT NULL,
    price            DOUBLE       NOT NULL,
    duration         INT          NOT NULL,
    create_date      TIMESTAMP(3) NULL DEFAULT NULL,
    last_update_date TIMESTAMP(3) NULL DEFAULT NULL
);

create table tag
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    CONSTRAINT unique_name UNIQUE(name)
);

create table certificate_tag
(
    certificate_id INT NOT NULL,
    tag_id         INT NOT NULL,
    PRIMARY KEY (certificate_id, tag_id),
    CONSTRAINT fk_certificate_id
        FOREIGN KEY (certificate_id)
            REFERENCES certificate(id) ON DELETE CASCADE,
    CONSTRAINT fk_tag_id
        FOREIGN KEY (tag_id)
            REFERENCES tag(id) ON DELETE CASCADE
);

create table users
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(45) NOT NULL,
    name VARCHAR(45),
    surname VARCHAR(45),
    CONSTRAINT unique_login UNIQUE(login)
);
