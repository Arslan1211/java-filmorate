package ru.yandex.practicum.filmorate.storage.film;

import static java.util.Objects.isNull;

import jakarta.validation.Valid;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.film.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

@Component
public class InMemoryFilmStorage implements FilmStorage {

  private final Map<Long, Film> films = new HashMap<>();
  private Long idCounter = 1L;

  @Override
  public Collection<Film> findAll() {
    return films.values();
  }

  @Override
  public Film getById(Long id) {
    Film film = films.get(id);
    String errorMessage = String.format("Фильм с id: %d не найден", id);
    if (isNull(film)) {
      throw new FilmNotFoundException(errorMessage);
    }
    return film;
  }

  @Override
  public Film create(@Valid @RequestBody Film film) {
    film.setId(idCounter++);
    films.put(film.getId(), film);
    return film;
  }

  @Override
  public Film update(@Valid @RequestBody Film film) {
    Long id = film.getId();
    if (!films.containsKey(id)) {
      String errorMessage = String.format("Фильм с id: %d не найден", id);
      throw new FilmNotFoundException(errorMessage);
    }
    films.put(film.getId(), film);
    return film;
  }

  @Override
  public Collection<Film> findBest(Long count) {
    Comparator<? super Film> comparator = Comparator.comparingInt(f -> f.getLikes().size());
    return films.values().stream()
        .sorted(comparator.reversed())
        .limit(count)
        .collect(Collectors.toList());
  }
}
