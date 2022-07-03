INSERT INTO "mpa" ("mpa_id", "mpa_name") VALUES (1, 'G');
INSERT INTO "mpa" ("mpa_id", "mpa_name") VALUES (2, 'PG');
INSERT INTO "mpa" ("mpa_id", "mpa_name") VALUES (3, 'PG-13');
INSERT INTO "mpa" ("mpa_id", "mpa_name") VALUES (4, 'R');
INSERT INTO "mpa" ("mpa_id", "mpa_name") VALUES (5, 'NC-17');


INSERT INTO "films" ("film_id", "name", "DESCRIPTION", "release_date", "duration", "mpa_id") VALUES (1, 'Зеленая миля', 'The green mile', '1999-01-01', 199, 1);
INSERT INTO "films" ("film_id", "name", "DESCRIPTION", "release_date", "duration", "mpa_id") VALUES (2, 'Список Шиндлера', 'Schindler List', '1993-01-01', 195, 2);

INSERT INTO "genre" ("genre_id", "genre") VALUES (1, 'Comedy');
INSERT INTO "genre" ("genre_id", "genre") VALUES (2, 'Drama');
INSERT INTO "genre" ("genre_id", "genre") VALUES (3, 'Cartoon');
INSERT INTO "genre" ("genre_id", "genre") VALUES (4, 'Thriller');
INSERT INTO "genre" ("genre_id", "genre") VALUES (5, 'Documentary');
INSERT INTO "genre" ("genre_id", "genre") VALUES (6, 'Action');

INSERT INTO "film_genre" ("film_id", "genre_id") VALUES (1, 2);
INSERT INTO "film_genre" ("film_id", "genre_id") VALUES (2, 2);

INSERT INTO "users" ("email", "login", "name", "birthday") VALUES ('Rita@ya.ru', 'pepilsinka', 'Rita', '1992-03-29');
INSERT INTO "users" ("email", "login", "name", "birthday") VALUES ('Rass@ya.ru', 'Rass123', 'Rass', '1990-08-20');
INSERT INTO "users" ("email", "login", "name", "birthday") VALUES ('Natasha@ya.ru', 'Nat', 'Natali', '1992-06-15');

INSERT INTO "friendship" ("user_email", "friend_email", "friend_status") VALUES ('Rita@ya.ru', 'Rass@ya.ru', false);
INSERT INTO "friendship" ("user_email", "friend_email", "friend_status") VALUES ('Natasha@ya.ru', 'Rass@ya.ru', true);
INSERT INTO "friendship" ("user_email", "friend_email", "friend_status") VALUES ('Natasha@ya.ru', 'Rita@ya.ru', true);

INSERT INTO "likes" ("film_id", "user_email") VALUES (1, 'Rita@ya.ru');
INSERT INTO "likes" ("film_id", "user_email") VALUES (2, 'Natasha@ya.ru');