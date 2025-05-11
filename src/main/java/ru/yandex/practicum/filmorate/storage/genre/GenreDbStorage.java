package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.base.BaseStorage;

import java.util.Collection;
import java.util.Optional;
import ru.yandex.practicum.filmorate.util.SqlQueries;

@Repository
public class GenreDbStorage extends BaseStorage<Genre> implements GenreStorage {



  public GenreDbStorage(JdbcTemplate jdbcTemplate, GenreRowMapper rowMapper) {
    super(jdbcTemplate, rowMapper);
  }

  @Override
  public Collection<Genre> getAll() {
    return getMany(SqlQueries.SQL_SELECT_ALL_GENRE);
  }

  @Override
  public Genre getGenre(Short id) {
    Optional<Genre> one = getOne(SqlQueries.SQL_SELECT_ONE_GENRE, id);
    if (one.isEmpty())
      throw new NotFoundException("Genre id:" + id + " not found");
    return one.get();
  }
}
