INSERT INTO tag (id,name)
VALUES (1,'sport'),
       (2,'massage');

INSERT INTO users (id,login)
VALUES (1,'admin'),
       (2,'guest');

INSERT INTO certificate (id, name, description, price, duration, create_date, last_update_date)
VALUES (1, 'sport','sport',10.0,30,'2021-12-23','2021-12-23'),
       (2, 'massage','massage',10.0,30,'2021-12-01','2021-12-01');

INSERT INTO certificate_tag(certificate_id,tag_id) VALUES (1,1),(2,2);