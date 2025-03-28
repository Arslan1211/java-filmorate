package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.film.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

  private final Map<Long, Film> films = new HashMap<>();
  private Long idCounter = 1L;

  @PostMapping
  public Film create(@Valid @RequestBody Film film) {
    log.info("Получен HTTP-запрос на добавления фильма: {}", film);
    film.setId(idCounter++);
    films.put(film.getId(), film);
    log.info("Успешно обработан HTTP-запрос на добавления фильма: {}", film);
    return film;
  }

  @PutMapping
  public Film update(@Valid @RequestBody Film film) {
    log.info("Получен HTTP-запрос на обновление фильма: {}", film);
    Long id = film.getId();
    if (!films.containsKey(id)) {
      String errorMessage = String.format("Фильм с id: %d не найден", id);
      log.error(errorMessage);
      throw new FilmNotFoundException(errorMessage);
    }
    films.put(film.getId(), film);
    log.info("Успешно выполнен HTTP-запрос на обновление фильма: {}", film);
    return film;
  }

  @GetMapping
  public Collection<Film> findAll() {
    log.info("Получен HTTP-запрос на получение всех фильмов");
    return films.values();
  }
}
