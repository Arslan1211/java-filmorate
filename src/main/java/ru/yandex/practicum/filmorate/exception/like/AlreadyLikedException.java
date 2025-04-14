package ru.yandex.practicum.filmorate.exception.like;

public class AlreadyLikedException extends RuntimeException {

  public AlreadyLikedException(Long id, Long userId) {
    super("Фильм с id:" + id + " уже был отмечен как понравившийся пользователю с id:" + userId);
  }
}
