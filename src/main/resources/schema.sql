CREATE TABLE IF NOT EXISTS "films" (
                         "film_id" LONG GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                         "name" varchar(50) NOT NULL,
                         "DESCRIPTION" varchar(200),
                         "release_date" date,
                         "duration" int,
                         "mpa_id" LONG
);

CREATE TABLE IF NOT EXISTS "mpa" (
                       "mpa_id" LONG GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                       "mpa_name" varchar(5) NOT NULL
);

CREATE TABLE IF NOT EXISTS "genre" (
                         "genre_id" LONG GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                         "genre" varchar(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS "film_genre" (
                              "film_id" int NOT NULL,
                              "genre_id" int
);

CREATE TABLE IF NOT EXISTS "users" (
                         "email" varchar(50) PRIMARY KEY,
                         "login" varchar(20) UNIQUE NOT NULL,
                         "name" varchar,
                         "birthday" date
);

CREATE TABLE IF NOT EXISTS "friendship" (
                              "user_email" varchar NOT NULL,
                              "friend_email" varchar,
                              "friend_status" boolean
);

CREATE TABLE IF NOT EXISTS "likes" (
                         "film_id" int NOT NULL,
                         "user_email" varchar
);

ALTER TABLE "films" ADD FOREIGN KEY ("mpa_id") REFERENCES "mpa" ("mpa_id");

ALTER TABLE "film_genre" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("film_id");

ALTER TABLE "film_genre" ADD FOREIGN KEY ("genre_id") REFERENCES "genre" ("genre_id");

ALTER TABLE "friendship" ADD FOREIGN KEY ("user_email") REFERENCES "users" ("email");

ALTER TABLE "friendship" ADD FOREIGN KEY ("friend_email") REFERENCES "users" ("email");

ALTER TABLE "likes" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("film_id");

ALTER TABLE "likes" ADD FOREIGN KEY ("user_email") REFERENCES "users" ("email");