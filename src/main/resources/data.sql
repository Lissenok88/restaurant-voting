INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANTS(name)
VALUES ('Paris-Way'),
       ('Italy-Way'),
       ('Russia-Way');

INSERT INTO MENU(name, price, restaurant_id, local_date)
VALUES ('Суп-пюре', '125', 1, '2022-07-14'),
       ('Салат Цезарь', '75', 1, '2022-07-14'),
       ('Картофельное пюре', '100', 3, '2022-07-14'),
       ('Котлета', '50', 3, '2022-07-14'),
       ('Чай', '30', 3, '2022-07-14'),
       ('Салат Министерский', '75', 3, '2022-07-14'),
       ('Кофе', '50', 1, '2022-07-14'),
       ('Солянка', '150', 3, '2022-07-14'),
       ('Картошка по француски', '150', 1, '2022-07-14'),
       ('Паста с грибами', '125', 2, '2022-07-14'),
       ('Капучино', '150', 2, '2022-07-14');

INSERT INTO VOTE(user_id, restaurant_id, date_time)
VALUES (1, 1, '2022-07-14'),
       (2, 3, '2022-07-14');