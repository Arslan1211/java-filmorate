package ru.yandex.practicum.filmorate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import ru.yandex.practicum.filmorate.validator.ValidationGroups;

@Data
public class UserCreateDto {

  @Email(message = "Неверный формат email")
  @NotBlank
  private String email;

  @NotBlank(message = "Логин не может быть пустым")
  @Pattern(regexp = "^\\S*$", message = "Логин не может содержать пробелы", groups = {
      ValidationGroups.Create.class,
      ValidationGroups.Update.class
  })
  private String login;

  private String name;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @PastOrPresent(message = "Дата рождения не может быть в будущем", groups = ValidationGroups.Create.class)
  @Past
  private LocalDate birthday;
}
