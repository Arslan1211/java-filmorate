package ru.yandex.practicum.filmorate.exception.user;

public class EmailAlreadyTakenException extends RuntimeException {

  public EmailAlreadyTakenException(String email) {
    super("Пользователь с email " + email + " уже зарегистрирован");
  }
}
