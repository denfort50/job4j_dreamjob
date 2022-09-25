CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email VARCHAR,
    password VARCHAR
);

ALTER TABLE users ADD CONSTRAINT email_unique UNIQUE (email);