package ru.yandex.practicum.filmorate.storage.film;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.base.BaseStorage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import ru.yandex.practicum.filmorate.util.SqlQueries;

@Repository("filmDbStorage")
public class FilmDbStorage extends BaseStorage<Film> implements FilmStorage {


  @Autowired
  public FilmDbStorage(JdbcTemplate jdbcTemplate, FilmRowMapper rowMapper) {
    super(jdbcTemplate, rowMapper);
  }

  @Override
  public Collection<Film> getAll() {
    Collection<Film> many = getMany(SqlQueries.SQL_SELECT_ALL_FILM);
    return many;
  }

  @Override
  public Film getFilm(Long id) {
    Optional<Film> one = getOne(SqlQueries.SQL_SELECT_ONE_FILM, id);
    return one.orElseThrow(() -> new NotFoundException("Film id:" + id + " not found"));
  }

  @Override
  public void checkFilmExists(Long id) {
    boolean exists = exists(SqlQueries.SQL_CHECK_FILM_EXISTS, id);
    if (!exists) {
      throw new NotFoundException("Film id:" + id + " not found");
    }
  }

  @Override
  public Film save(Film film) {

    Set<Short> genreIds = film.getGenres().stream()
        .map(Genre::getId)
        .collect(Collectors.toSet());

    checkGenresExist(genreIds);

    Long id = insertAndReturnId(SqlQueries.SQL_INSERT,
        Long.class,
        film.getName(),
        film.getDescription(),
        film.getDuration(),
        film.getReleaseDate(),
        film.getMpa().getId()
    );

    if (id == null) {
      return null;
    }

    film.setId(id);
    saveFilmGenres(film);
    /* берем актуальный объект из базы, т.к. в получаемом объекте
     * информация о жанрах/mpa может быть неполной (допускается
     * наличие только их id, без названий) */
    return getFilm(id);
  }

  @Override
  public Film update(Film film) {
    super.update(SqlQueries.SQL_UPDATE_FILM,
        film.getName(),
        film.getDescription(),
        film.getDuration(),
        film.getReleaseDate(),
        film.getMpa().getId(),
        film.getId());

    saveFilmGenres(film);
    /* берем актуальный объект из базы, т.к. в получаемом объекте
     * информация о жанрах/mpa может быть неполной (допускается
     * наличие только их id, без названий) */
    return getFilm(film.getId());
  }

  @Override
  public Collection<Film> getTop(int count) {
    return getMany(SqlQueries.SQL_SELECT_POPULAR, count);
  }

  private void saveFilmGenres(Film film) {
    final Collection<Genre> genres = film.getGenres();

    Set<Short> genreIds = genres.stream()
        .map(Genre::getId)
        .collect(Collectors.toSet());

    update(SqlQueries.DELETE_FILM_GENRES, film.getId());

    if (genreIds.isEmpty()) {
      return;
    }

    jdbcTemplate.batchUpdate(SqlQueries.INSERT_FILM_GENRE, genreIds, genreIds.size(),
        (ps, genreId) -> {
          ps.setLong(1, film.getId());
          ps.setInt(2, genreId);
        });
  }

  /*  private void checkGenresExist(Set<Short> ids) throws NotFoundException {
      if (ids.isEmpty()) {
        return;
      }

      String sql = "SELECT t.id FROM (VALUES " +
          ids.stream()
              .map(id -> "(?)")
              .collect(Collectors.joining(",")) +
          ") AS t(id) WHERE t.id NOT IN (SELECT id FROM genres)";

      List<Short> invalidIds = jdbcTemplate.queryForList(sql, Short.class, ids.toArray());
      if (!invalidIds.isEmpty())
        throw new NotFoundException("Genres are invalid: " + invalidIds);
    }*/
  private void checkGenresExist(Set<Short> ids) throws NotFoundException {
    if (ids == null || ids.isEmpty()) {
      return;
    }

  /*// Для PostgreSQL
  String sql = "SELECT unnest(?::smallint[]) AS id " +
      "EXCEPT " +
      "SELECT id FROM genres";*/

    // Для H2 или других СУБД без unnest
    String sql = "SELECT t.id FROM (VALUES " +
        String.join(",", Collections.nCopies(ids.size(), "(?)")) +
        ") AS t(id) WHERE t.id NOT IN (SELECT id FROM genres)";

    Short[] idsArray = ids.toArray(new Short[0]);
    List<Short> invalidIds = jdbcTemplate.queryForList(
        sql,
        new Object[]{idsArray},
        Short.class
    );

    if (!invalidIds.isEmpty()) {
      throw new NotFoundException("Invalid genre IDs: " + invalidIds);
    }
  }
}