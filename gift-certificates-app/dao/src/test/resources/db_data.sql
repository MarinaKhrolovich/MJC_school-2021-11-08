INSERT INTO tag (name)
VALUES ('sport'),
       ('massage');

INSERT INTO certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('sport','sport',10.0,30,'2021-12-23','2021-12-23'),
       ('massage','massage',10.0,30,'2021-12-01','2021-12-01');

INSERT INTO certificate_tag(certificate_id,tag_id) VALUES (1,1),(2,2);