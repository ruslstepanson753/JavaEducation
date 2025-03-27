-- Создание таблицы users
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       login VARCHAR(255),
                       password VARCHAR(255),
                       nik_name VARCHAR(255),
                       questions JSONB,
                       question_positions JSONB,
                       topic VARCHAR(255)
);

-- Вставка тестового пользователя с данными в JSON-поля
INSERT INTO users (login, password, nik_name, questions, question_positions, topic)
VALUES (
           'test_user',
           'secure_password123',
           'TestNick',
           '{
             "Java": ["Что такое JVM?", "Как работает GC?"],
             "SQL": ["Что такое JOIN?", "Как работает индекс?"]
           }'::jsonb,
           '{
             "Java": 1,
             "SQL": 0
           }'::jsonb,
           'Programming'
       );