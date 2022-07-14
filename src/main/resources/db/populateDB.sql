DELETE FROM voting;
DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM menu;
DELETE FROM restaurants;


INSERT INTO users (name, email, password)
VALUES ('User1', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('User2', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 0),
       ('ADMIN', 1);

INSERT INTO restaurants(name)
VALUES ('Paris-Way'),
       ('Italy-Way'),
       ('Russia-Way');

INSERT INTO menu(dish, price, restaurant_id, date)
VALUES ('Суп-пюре', '125', 0, '2022-07-14'),
       ('Салат Цезарь', '75', 0, '2022-07-14'),
       ('Картофельное пюре', '100', 2, '2022-07-14'),
       ('Котлета', '50', 2, '2022-07-14'),
       ('Чай', '30', 2, '2022-07-14'),
       ('Салат Министерский', '75', 2, '2022-07-14'),
       ('Кофе', '50', 0, '2022-07-14'),
       ('Солянка', '150', 2, '2022-07-14'),
       ('Картошка по француски', '150', 0, '2022-07-14'),
       ('Паста с грибами', '125', 1, '2022-07-14'),
       ('Капучино', '150', 1, '2022-07-14');

INSERT INTO voting(user_id, restaurant_id, date_time)
VALUES (1, 0, '2022-07-14'),
       (2, 2, '2022-07-14');