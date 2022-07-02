CREATE TABLE "films" (
  "film_id" SERIAL PRIMARY KEY,
  "name" varchar(50) NOT NULL,
  "desсription" varchar(200),
  "release_date" date,
  "duration" int,
  "mpa_id" [fk]
);

CREATE TABLE "mpa" (
  "mpa_id" SERIAL PRIMARY KEY,
  "mpa_name" varchar(5) NOT NULL
);

CREATE TABLE "genre" (
  "genre_id" SERIAL PRIMARY KEY,
  "genre" varchar(20) NOT NULL
);

CREATE TABLE "film_genre" (
  "film_id" int NOT NULL,
  "genre_id" int,
  PRIMARY KEY ("film_id", "genre_id")
);

CREATE TABLE "users" (
  "email" varchar(50) PRIMARY KEY,
  "login" varchar(20) UNIQUE NOT NULL,
  "name" varchar,
  "birthday" date
);

CREATE TABLE "friendship" (
  "user_email" varchar NOT NULL,
  "friend_email" varchar,
  "friend_status" boolean,
  PRIMARY KEY ("user_email", "friend_email")
);

CREATE TABLE "likes" (
  "film_id" int NOT NULL,
  "user_email" varchar,
  PRIMARY KEY ("film_id", "user_email")
);

ALTER TABLE "films" ADD FOREIGN KEY ("mpa_id") REFERENCES "mpa" ("mpa_id");

ALTER TABLE "film_genre" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("film_id");

ALTER TABLE "film_genre" ADD FOREIGN KEY ("genre_id") REFERENCES "genre" ("genre_id");

ALTER TABLE "friendship" ADD FOREIGN KEY ("user_email") REFERENCES "users" ("email");

ALTER TABLE "friendship" ADD FOREIGN KEY ("friend_email") REFERENCES "users" ("email");

ALTER TABLE "likes" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("film_id");

ALTER TABLE "likes" ADD FOREIGN KEY ("user_email") REFERENCES "users" ("email");
