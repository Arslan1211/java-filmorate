package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.validator.ValidationGroups;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/users")
  Collection<User> findAll() {
    log.info("Получен HTTP-запрос на получение всех пользователей");
    return userService.findAll();
  }

  @GetMapping("/users/{id}")
  User getById(@PathVariable Long id) {
    log.info("Получен HTTP-запрос на получение пользователя по id: {}", id);
    return userService.getById(id);
  }

  @PostMapping("/users")
  User create(@Validated(ValidationGroups.Create.class) @RequestBody User user) {
    User created = userService.create(user);
    log.info("Получен HTTP-запрос на создание пользователя: {}", user);
    return created;
  }

  @PutMapping("/users")
  User update(@Validated(ValidationGroups.Update.class) @RequestBody User user) {
    log.info("Получен HTTP-запрос на обновление пользователя: {}", user);
    User updated = userService.update(user);
    log.info("Успешно выполнен HTTP-запрос на обновление пользователя: {}", user);
    return updated;
  }

  @PutMapping("/users/{id}/friends/{friendId}")
  @ResponseStatus(HttpStatus.OK)
  public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
    userService.addFriend(id, friendId);
    log.info("Пользователь с id:{} добавил в друзья пользователя с id:{}", id, friendId);
  }

  @DeleteMapping("/users/{id}/friends/{friendId}")
  @ResponseStatus(HttpStatus.OK)
  public void removeFriend(@PathVariable Long id, @PathVariable Long friendId) {
    userService.removeFriend(id, friendId);
    log.info("Пользователь с id:{} удалил из друзей пользователя с id:{}", id, friendId);
  }

  @GetMapping("/users/{id}/friends")
  @ResponseStatus(HttpStatus.OK)
  public Collection<User> getFriends(@PathVariable Long id) {
    log.info("Получен HTTP-запрос на вывод всех друзей по id: {}", id);
    return userService.getFriends(id);
  }

  @GetMapping("/users/{id}/friends/common/{otherId}")
  @ResponseStatus(HttpStatus.OK)
  public Collection<User> findCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
    log.info("Получен HTTP-запрос на вывод общих друзей по id: {} {}", id, otherId);
    return userService.findCommonFriends(id, otherId);
  }
}
