package ru.yandex.practicum.filmorate.util;

public interface SqlQueries {

  String SQL_SELECT_ALL = """
      SELECT * FROM users
      """;

  String SQL_INSERT_USER = """
      INSERT INTO users (login, username, email, birthday)
      VALUES (?, ?, ?, ?)
      """;

  String SQL_UPDATE_USER = """
      UPDATE users
      SET login = ?, username = ?, email= ?,  birthday = ?
      WHERE id = ?
      """;

  String SQL_SELECT_ONE = """
      SELECT * FROM users WHERE id = ?
      """;

  String SQL_CHECK_FRIENDSHIP = """
      SELECT EXISTS (
          SELECT 1 FROM friendship
          WHERE user_id = ? AND friend_id = ?
      )
      """;

  String SQL_ADD_FRIEND = """
      INSERT INTO friendship(user_id, friend_id)
      VALUES(?, ?)
      """;

  String SQL_SELECT_FRIENDS = """
      SELECT u.*
      FROM users u
      JOIN friendship f ON u.id = f.friend_id
      WHERE f.user_id = ?;
      """;

  String SQL_SELECT_COMMON_FRIENDS = """
      SELECT u.*
      FROM friendship f1
      JOIN friendship f2 ON f1.friend_id = f2.friend_id
      JOIN users u ON u.id = f1.friend_id
      WHERE f1.user_id = ?
        AND f2.user_id = ?
      """;

  String SQL_REMOVE_FRIEND = """
      DELETE FROM friendship
      WHERE user_id = ? AND friend_id = ?
      """;

  String SQL_CHECK_USER_EXISTS = """
      SELECT EXISTS(SELECT 1 FROM users WHERE id = ?)
      """;

  String SQL_CHECK_EMAIL_USED = """
      SELECT EXISTS(SELECT 1 FROM users WHERE email = ?)
      """;

  String SQL_CHECK_LIKE_EXISTS = """
      SELECT COUNT(*) > 0 FROM likes WHERE film_id = ? AND user_id = ?
      """;

  String SQL_ADD_LIKE = """
      INSERT INTO likes(film_id, user_id)
      VALUES(?, ?)
      """;
  String SQL_REMOVE_LIKE = """
      DELETE FROM likes
      WHERE film_id = ? AND user_id = ?
      """;
  String SQL_SELECT_ALL_MPA = """
      SELECT * FROM mpa ORDER BY id
      """;

  String SQL_SELECT_ONE_MPA = """
      SELECT * FROM mpa WHERE id = ?
      """;

  String SQL_SELECT_ALL_FILM = """
      SELECT
          f.*,
          m.name AS mpa_name,
          (
              SELECT STRING_AGG(CONCAT(g.id, ':', g.name), '|' ORDER BY g.id)
              FROM film_genre fg
              JOIN genres g ON fg.genre_id = g.id
              WHERE fg.film_id = f.id
          ) AS genres_data
      FROM films f
      LEFT JOIN mpa m ON m.id = f.mpa_id
      ORDER BY f.id;
      """;

  String SQL_SELECT_ONE_FILM = """
      SELECT
          f.*,
          m.name AS mpa_name,
          (
              SELECT STRING_AGG(CONCAT(g.id, ':', g.name), '|' ORDER BY g.id)
              FROM film_genre fg
              JOIN genres g ON fg.genre_id = g.id
              WHERE fg.film_id = f.id
          ) AS genres_data
      FROM films f
      LEFT JOIN mpa m ON m.id = f.mpa_id
      WHERE f.id = ?
      """;

  String SQL_CHECK_FILM_EXISTS = """
      SELECT COUNT(*) > 0 FROM films WHERE id = ?
      """;

  String SQL_INSERT = """
      INSERT INTO films (title, description, duration, release_date, mpa_id)
      VALUES (?, ?, ?, ?, ?)
      """;

  String SQL_UPDATE_FILM = """
      UPDATE films
      SET title = ?, description = ?, duration= ?,  release_date = ?, mpa_id = ?
      WHERE id = ?
      """;

  String DELETE_FILM_GENRES = """
      DELETE FROM film_genre
      WHERE film_id = ?
      """;

  String INSERT_FILM_GENRE = """
      INSERT INTO film_genre(film_id, genre_id)
      VALUES (?, ?)
      """;

  String SQL_SELECT_POPULAR = """
      SELECT
          f.*,
          m.name AS mpa_name,
          COUNT(l.film_id) as likes_count,
          (
              SELECT STRING_AGG(CONCAT(g.id, ':', g.name), '|' ORDER BY g.id)
              FROM film_genre fg
              JOIN genres g ON fg.genre_id = g.id
              WHERE fg.film_id = f.id
          ) AS genres_data
      FROM films f
      LEFT JOIN mpa m ON m.id = f.mpa_id
      LEFT JOIN likes l ON f.id = l.film_id
      GROUP BY
         f.id, f.title, f.description, f.duration, f.release_date, f.mpa_id, m.name
      ORDER BY likes_count DESC
      LIMIT ?;
      """;

  String SQL_SELECT_ALL_GENRE = """
      SELECT * FROM genres ORDER BY id
      """;

  String SQL_SELECT_ONE_GENRE = """
      SELECT * FROM genres WHERE id = ?
      """;
}
