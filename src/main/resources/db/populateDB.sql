DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM menu;


INSERT INTO users (name, email, password)
VALUES ('User1', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 0),
       ('ADMIN', 1);

INSERT INTO restaurants(name, date)
VALUES ('Paris-Way', '2022-07-13'),
       ('Italy-Way', '2022-07-13'),
       ('Russia-Way', '2022-07-13');

INSERT INTO menu(dish, price, restaurant_id)
VALUES ('Суп-пюре', '125', 0),
       ('Салат Цезарь', '75', 0),
       ('Картофельное пюре', '100', 2),
       ('Котлета', '50', 2),
       ('Чай', '30', 2),
       ('Салат Министерский', '75', 2),
       ('Кофе', '50', 0),
       ('Солянка', '150', 2),
       ('Картошка по француски', '150', 0),
       ('Паста с грибами', '125', 1),
       ('Капучино', '150', 1);

INSERT INTO voting(vote, user_id, restaurant_id, date_time)
VALUES (1, 1, 0, '2022-07-13'),
       (1, 2, 2, '2022-07-13');