DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id integer,
    firstName varchar,
    lastName varchar,
    email varchar
);
