package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.validator.AfterDate;

/**
 * Film.
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Film {

  private Long id;

  @NotEmpty(message = "Название фильма не может быть пустым")
  private String name;

  @Size(max = 200, message = "Максимальная длина описания — 200 символов")
  private String description;

  @AfterDate(targetDate = "1895-12-28", message = "Дата релиза не может быть раньше 28 декабря 1895 года")
  private LocalDate releaseDate;

  @Positive(message = "Продолжительность фильма должна быть положительным числом")
  private Long duration;
}
