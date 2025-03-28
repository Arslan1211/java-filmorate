package ru.yandex.practicum.filmorate.controller;

import static java.util.Objects.isNull;

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
import ru.yandex.practicum.filmorate.exception.user.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

  private final Map<Long, User> users = new HashMap<>();
  private Long idCounter = 1L;

  @PostMapping
  public User create(@Valid @RequestBody User user) {
    log.info("Получен HTTP-запрос на создание пользователя: {}", user);
    user.setId(idCounter++);
    if (isNull(user.getName())) {
      user.setName(user.getLogin());
    }
    users.put(user.getId(), user);
    log.info("Успешно обработан HTTP-запрос на создание пользователя: {}", user);
    return user;
  }

  @PutMapping
  public User update(@Valid @RequestBody User user) {
    log.info("Получен HTTP-запрос на обновление пользователя: {}", user);
    Long id = user.getId();
    if (!users.containsKey(id)) {
      String errorMessage = String.format("Пользователь с id: %d не найден", id);
      log.error(errorMessage);
      throw new UserNotFoundException(errorMessage);
    }
    users.put(user.getId(), user);
    log.info("Успешно выполнен HTTP-запрос на обновление пользователя: {}", user);
    return user;
  }

  @GetMapping
  public Collection<User> findAll() {
    log.info("Получен HTTP-запрос на получение всех пользователей");
    return users.values();
  }
}
