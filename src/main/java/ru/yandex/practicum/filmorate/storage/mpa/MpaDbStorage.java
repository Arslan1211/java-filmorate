package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.base.BaseStorage;

import java.util.Collection;
import java.util.Optional;
import ru.yandex.practicum.filmorate.util.SqlQueries;

@Repository
public class MpaDbStorage extends BaseStorage<Mpa> implements MpaStorage {

  public MpaDbStorage(JdbcTemplate jdbcTemplate, MpaRowMapper rowMapper) {
    super(jdbcTemplate, rowMapper);
  }

  @Override
  public Collection<Mpa> getAll() {
    return getMany(SqlQueries.SQL_SELECT_ALL_MPA);
  }

  @Override
  public Mpa getMpa(Short id) {
    Optional<Mpa> one = getOne(SqlQueries.SQL_SELECT_ONE_MPA, id);
    if (one.isEmpty())
      throw new NotFoundException("Mpa id:" + id + " not found");
    return one.get();
  }
}
