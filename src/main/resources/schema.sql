CREATE TABLE IF NOT EXISTS genres
(
    id      SMALLINT GENERATED BY DEFAULT AS IDENTITY,
    name    CHARACTER VARYING(64)  NOT NULL UNIQUE,
    CONSTRAINT pk_genres PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS mpa
(
    id      SMALLINT  GENERATED BY DEFAULT AS IDENTITY,
    name    CHARACTER VARYING(5)   NOT NULL UNIQUE,
    CONSTRAINT pk_mpa PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS films (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY,
    title VARCHAR(128) NOT NULL,
    description VARCHAR(200),
    mpa_id SMALLINT NOT NULL,
    release_date DATE,
    duration INTEGER NOT NULL,
    CONSTRAINT pk_films PRIMARY KEY (id),
    CONSTRAINT chk_films_duration CHECK (duration > 0),
    FOREIGN KEY (mpa_id) REFERENCES mpa(id)
);

CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY,
    login VARCHAR(32) NOT NULL,
    username VARCHAR(128) NOT NULL,
    email VARCHAR(255) NOT NULL,
    birthday DATE,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_users_login UNIQUE (login),
    CONSTRAINT uq_users_email UNIQUE (email),
    CONSTRAINT chk_login_length CHECK (LENGTH(login) BETWEEN 3 AND 32)
);

CREATE TABLE IF NOT EXISTS film_genre (
    film_id BIGINT NOT NULL,
    genre_id SMALLINT NOT NULL,
    CONSTRAINT pk_film_genre PRIMARY KEY (film_id, genre_id),
    FOREIGN KEY (film_id) REFERENCES films(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS likes (
    film_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_likes PRIMARY KEY (film_id, user_id),
    FOREIGN KEY (film_id) REFERENCES films(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS friendship (
    user_id BIGINT NOT NULL,
    friend_id BIGINT NOT NULL,
    CONSTRAINT pk_friendship PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT no_self_friendship CHECK (user_id != friend_id)
);