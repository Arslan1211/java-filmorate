package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * User.
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

  private Long id;

  private String name;

  @NotBlank(message = "Логин не может быть пустым")
  @Pattern(regexp = "^\\S*$", message = "Логин не может содержать пробелы")
  private String login;

  @NotEmpty(message = "Электронная почта не может быть пустой")
  @Email(message = "Электронная почта должна содержать символ @")
  private String email;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @Past
  private LocalDate birthday;
}
