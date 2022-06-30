CREATE TABLE "films" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar(50) NOT NULL,
  "desription" varchar,
  "release_date" timestamp,
  "duration" int,
  "mpa_id" [fk]
);

CREATE TABLE "mpa" (
  "id" SERIAL PRIMARY KEY,
  "mpa_name" varchar(5) NOT NULL
);

CREATE TABLE "genre" (
  "id" SERIAL PRIMARY KEY,
  "genre" varchar(20) NOT NULL
);

CREATE TABLE "film_genre" (
  "id" SERIAL PRIMARY KEY,
  "film_id" int NOT NULL,
  "genre_id" int
);

CREATE TABLE "users" (
  "id" SERIAL PRIMARY KEY,
  "email" varchar(50) UNIQUE NOT NULL,
  "login" varchar(20) UNIQUE NOT NULL,
  "name" varchar,
  "birthday" date
);

CREATE TABLE "friendship" (
  "id" SERIAL PRIMARY KEY,
  "user_id" int NOT NULL,
  "friend_id" int,
  "friend_status" boolean
);

CREATE TABLE "likes" (
  "id" SERIAL PRIMARY KEY,
  "film_id" int NOT NULL,
  "user_id" int
);

ALTER TABLE "films" ADD FOREIGN KEY ("mpa_id") REFERENCES "mpa" ("id");

ALTER TABLE "film_genre" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("id");

ALTER TABLE "film_genre" ADD FOREIGN KEY ("genre_id") REFERENCES "genre" ("id");

ALTER TABLE "friendship" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "friendship" ADD FOREIGN KEY ("friend_id") REFERENCES "users" ("id");

ALTER TABLE "likes" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("id");

ALTER TABLE "likes" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");
