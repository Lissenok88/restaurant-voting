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

INSERT INTO MENUS(name, price, restaurant_id, local_date)
VALUES ('Салат Цезарь', '75', 1, CURRENT_DATE),
       ('Суп-пюре', '125', 1, CURRENT_DATE),
       ('Картошка по француски', '150', 1, CURRENT_DATE),
       ('Кофе', '50', 1, CURRENT_DATE),
       ('Паста с грибами', '125', 2, CURRENT_DATE),
       ('Капучино', '150', 2, CURRENT_DATE),
       ('Салат Министерский', '75', 3, CURRENT_DATE),
       ('Солянка', '150', 3, CURRENT_DATE),
       ('Картофельное пюре', '100', 3, CURRENT_DATE),
       ('Котлета', '50', 3, CURRENT_DATE),
       ('Чай', '30', 3, CURRENT_DATE);

INSERT INTO VOTE(restaurant_id, user_id, date_time)
VALUES (1, 1, CURRENT_DATE),
       (3, 2, CURRENT_DATE);