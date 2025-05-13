package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.UserCreateDto;
import ru.yandex.practicum.filmorate.dto.UserResponseDto;
import ru.yandex.practicum.filmorate.dto.UserUpdateDto;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/users")
  Collection<UserResponseDto> getAll() {
    return userService.getAll();
  }

  @GetMapping("/users/{id}")
  public UserResponseDto getUser(@PathVariable Long id) {
    return userService.getUser(id);
  }

  @PostMapping("/users")
  public UserResponseDto create(@Valid @RequestBody UserCreateDto user) {
    UserResponseDto created = userService.create(user);
    log.info("Пользователь с идентификатором:{} был добавлен: {}", created.getId(), created);
    return created;
  }

  @PutMapping("/users")
  public UserResponseDto update(@Valid @RequestBody UserUpdateDto user) {
    UserResponseDto updated = userService.update(user);
    log.info("Пользователь с идентификатором:{} был обновлен: {}", user.getId(), user);
    return updated;
  }

  @PutMapping("/users/{id}/friends/{friendId}")
  @ResponseStatus(HttpStatus.OK)
  public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
    userService.addFriend(id, friendId);
    log.info("Пользователь с идентификатором:{} добавил друга с идентификатором:{}", id, friendId);
  }

  @DeleteMapping("/users/{id}/friends/{friendId}")
  @ResponseStatus(HttpStatus.OK)
  public void removeFriend(@PathVariable Long id, @PathVariable Long friendId) {
    userService.removeFriend(id, friendId);
    log.info("Пользователь с идентификатором:{} удалил друга с идентификатором:{}", id, friendId);
  }

  @GetMapping("/users/{id}/friends")
  @ResponseStatus(HttpStatus.OK)
  public Collection<UserResponseDto> getFriends(@PathVariable Long id) {
    return userService.getFriends(id);
  }

  @GetMapping("/users/{id}/friends/common/{otherId}")
  @ResponseStatus(HttpStatus.OK)
  public Collection<UserResponseDto> getCommonFriends(@PathVariable Long id,
      @PathVariable Long otherId) {
    return userService.getCommonFriends(id, otherId);
  }
}
