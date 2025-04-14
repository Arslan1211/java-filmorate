package ru.yandex.practicum.filmorate.exception.friend;

public class AlreadyFriendException extends RuntimeException {

  public AlreadyFriendException(Long id1, Long id2) {
    super("Пользователь с id:" + id1 + " уже есть в друзьях id:" + id2);
  }
}
