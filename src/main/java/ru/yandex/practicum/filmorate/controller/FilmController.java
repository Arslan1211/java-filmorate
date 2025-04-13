package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Collection;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FilmController {

  private final FilmService filmService;

  @GetMapping("/films")
  public Collection<Film> findAll() {
    log.info("Получен HTTP-запрос на получение всех фильмов");
    return filmService.findAll();
  }

  @GetMapping("/films/{id}")
  public Film getById(@PathVariable Long id) {
    log.info("Получен HTTP-запрос на получение фильма по id: {}", id);
    return filmService.getById(id);
  }

  @PostMapping("/films")
  public Film create(@Valid @RequestBody Film film) {
    log.info("Получен HTTP-запрос на добавления фильма: {}", film);
    validate(film);
    Film created = filmService.create(film);
    log.info("Успешно обработан HTTP-запрос на добавления фильма: {}", created);
    return created;
  }

  @PutMapping("/films")
  public Film update(@Valid @RequestBody Film film) {
    log.info("Получен HTTP-запрос на обновление фильма: {}", film);
    validate(film);
    Film updated = filmService.update(film);
    log.info("Успешно выполнен HTTP-запрос на обновление фильма: {}", updated);
    return updated;
  }

  @PutMapping("/films/{id}/like/{userId}")
  public Film like(@PathVariable Long id, @PathVariable Long userId) {
    Film film = filmService.addLike(id, userId);
    log.info("User id:{} has liked film id:{}", userId, id);
    return film;
  }

  @DeleteMapping("/films/{id}/like/{userId}")
  public Film unlike(@PathVariable Long id, @PathVariable Long userId) {
    Film film = filmService.removeLike(id, userId);
    log.info("User id:{} has removed like from film id:{}", userId, id);
    return film;
  }

  @GetMapping("/films/popular")
  public Collection<Film> findBest(@RequestParam(defaultValue = "10") Long count) {
    if (count <= 0) {
      throw new ValidationException("count > 0");
    }
    log.info("Успешно выполнен HTTP-запрос на поиск лучших фильмов: {}", count);
    return filmService.findBest(count);
  }

  private void validate(Film film) throws ValidationException {
    try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      Validator validator = factory.getValidator();
      Set<ConstraintViolation<Film>> violations = validator.validate(film);
      if (!violations.isEmpty()) {
        throw new ValidationException(violations.iterator().next().getMessage());
      }
    }
  }
}
