-- Создание таблицы users
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       login VARCHAR(255),
                       password VARCHAR(255),
                       nik_name VARCHAR(255),
                       questions JSONB,
                       question_positions JSONB,
                       topic VARCHAR(255),
                       good_answers SMALLINT
);

