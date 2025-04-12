package ru.yandex.practicum.filmorate.exception.like;

public class LikeNotFoundException extends RuntimeException {

  public LikeNotFoundException(Long id, Long userId) {
    super("Фильм с id" + id + " не понравилось пользователю с указанным с id:" + userId);
  }
}
