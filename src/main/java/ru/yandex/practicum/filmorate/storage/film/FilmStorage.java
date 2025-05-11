package ru.yandex.practicum.filmorate.storage.film;

import java.util.Collection;
import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {

  Collection<Film> findAll();

  Film getById(Long id);

  Film create(Film film);

  Film update(Film film);

  Collection<Film> findBest(Long count);

  void checkFilmExists(Long filmId);
}
