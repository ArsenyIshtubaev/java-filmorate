CREATE TABLE IF NOT EXISTS "MPA" (
                                     "MPA_ID" int PRIMARY KEY AUTO_INCREMENT,
                                     "MPA_NAME" varchar(5) NOT NULL
);

CREATE TABLE IF NOT EXISTS "FILMS" (
                         "FILM_ID" long PRIMARY KEY AUTO_INCREMENT,
                         "FILM_NAME" varchar(50) UNIQUE NOT NULL,
                         "DESCRIPTION" varchar(200),
                         "RELEASE_DATE" date,
                         "DURATION" int,
                         "MPA_ID" int
);

CREATE TABLE IF NOT EXISTS "GENRE" (
                         "GENRE_ID" int PRIMARY KEY AUTO_INCREMENT,
                         "GENRE" varchar(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS "FILM_GENRE" (
                              "FILM_ID" long NOT NULL,
                              "GENRE_ID" int,
                           PRIMARY KEY ("FILM_ID", "GENRE_ID")
);

CREATE TABLE IF NOT EXISTS "USERS" (
                        "USER_ID" long PRIMARY KEY AUTO_INCREMENT,
                         "EMAIL" varchar(50) UNIQUE NOT NULL,
                         "LOGIN" varchar(20) NOT NULL,
                         "USER_NAME" varchar,
                         "BIRTHDAY" date
);

CREATE TABLE IF NOT EXISTS "FRIENDSHIP" (
                              "USER_ID" long NOT NULL,
                              "FRIEND_ID" long,
                              "FRIEND_STATUS" boolean,
                              PRIMARY KEY ("USER_ID", "FRIEND_ID")
);

CREATE TABLE IF NOT EXISTS "LIKES" (
                         "FILM_ID" long NOT NULL,
                         "USER_ID" long,
                         PRIMARY KEY ("FILM_ID", "USER_ID")
);

ALTER TABLE "FILMS" ADD FOREIGN KEY ("MPA_ID") REFERENCES "MPA" ("MPA_ID") ON DELETE CASCADE;

ALTER TABLE "FILM_GENRE" ADD FOREIGN KEY ("FILM_ID") REFERENCES "FILMS" ("FILM_ID");

ALTER TABLE "FILM_GENRE" ADD FOREIGN KEY ("GENRE_ID") REFERENCES "GENRE" ("GENRE_ID");

ALTER TABLE "FRIENDSHIP" ADD FOREIGN KEY ("USER_ID") REFERENCES "USERS" ("USER_ID");

ALTER TABLE "FRIENDSHIP" ADD FOREIGN KEY ("FRIEND_ID") REFERENCES "USERS" ("USER_ID");

ALTER TABLE "LIKES" ADD FOREIGN KEY ("FILM_ID") REFERENCES "FILMS" ("FILM_ID");

ALTER TABLE "LIKES" ADD FOREIGN KEY ("USER_ID") REFERENCES "USERS" ("USER_ID");