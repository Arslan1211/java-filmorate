package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.exception.film.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.friend.AlreadyFriendException;
import ru.yandex.practicum.filmorate.exception.like.AlreadyLikedException;
import ru.yandex.practicum.filmorate.exception.like.LikeNotFoundException;
import ru.yandex.practicum.filmorate.exception.user.EmailAlreadyTakenException;
import ru.yandex.practicum.filmorate.exception.user.UserNotFoundException;

@RestControllerAdvice
public class ErrorHandler {

  @ExceptionHandler({
      NotFoundException.class,
      FilmNotFoundException.class,
      UserNotFoundException.class
  })
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleNotFoundException(final RuntimeException e) {
    return new ErrorResponse(e.getMessage());
  }

  @ExceptionHandler({
      AlreadyLikedException.class,
      AlreadyFriendException.class,
      LikeNotFoundException.class,
      EmailAlreadyTakenException.class,
      ValidationException.class
  })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleLikeException(final RuntimeException e) {
    return new ErrorResponse(e.getMessage());
  }
}
