DROP TABLE user_roles IF EXISTS;
DROP TABLE menu IF EXISTS;
DROP TABLE restaurants IF EXISTS;
DROP TABLE users IF EXISTS;
DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

CREATE TABLE users
(
    id               INTEGER IDENTITY PRIMARY KEY,
    name             VARCHAR(255)            NOT NULL,
    email            VARCHAR(255)            NOT NULL,
    password         VARCHAR(255)            NOT NULL,
    registered       TIMESTAMP DEFAULT now() NOT NULL,
    enabled          BOOLEAN   DEFAULT TRUE  NOT NULL,
    calories_per_day INTEGER   DEFAULT 2000  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
    ON USERS (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
  id INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(255)      NOT NULL,
  date TIMESTAMP         NOT NULL
);
CREATE UNIQUE INDEX name_unique_date ON restaurants(name, date);

CREATE TABLE menu
(
    dish VARCHAR(255)         NOT NULL,
    price INTEGER             NOT NULL,
    restaurant_id INTEGER     NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE TABLE voting
(
    vote INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    restaurant_id INTEGER NOT NULL,
    date_time TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants ON DELETE CASCADE
);
CREATE UNIQUE INDEX user_unique_date_time ON voting(user_id, date_time)