package ru.yandex.practicum.filmorate.service.film;

import java.util.Collection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Service
@RequiredArgsConstructor
public class FilmService {

  private final UserStorage userStorage;
  private final FilmStorage filmStorage;

  public Film create(Film film) {
    film.removeLikes();
    return filmStorage.create(film);
  }

  public Film update(Film film) {

    Film existing = filmStorage.getById(film.getId());

    Optional.ofNullable(film.getName()).ifPresent(existing::setName);
    Optional.ofNullable(film.getDuration()).ifPresent(existing::setDuration);
    Optional.ofNullable(film.getDescription()).ifPresent(existing::setDescription);
    Optional.ofNullable(film.getReleaseDate()).ifPresent(existing::setReleaseDate);

    return filmStorage.update(existing);
  }

  public Film addLike(Long id, Long userId) {
    Film film = filmStorage.getById(id);
    userStorage.getById(userId);
    film.addLike(userId);
    return film;
  }

  public Film removeLike(Long id, Long userId) {
    Film film = filmStorage.getById(id);
    userStorage.getById(userId);
    film.removeLike(userId);
    return film;
  }

  public Collection<Film> findBest(Long count) {
    return filmStorage.findBest(count);
  }

  public Collection<Film> findAll() {
    return filmStorage.findAll();
  }

  public Film getById(Long id) {
    return filmStorage.getById(id);
  }
}
