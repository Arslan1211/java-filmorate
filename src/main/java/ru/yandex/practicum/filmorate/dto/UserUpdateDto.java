package ru.yandex.practicum.filmorate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import ru.yandex.practicum.filmorate.validator.ValidationGroups;

@Data
@AllArgsConstructor
public class UserUpdateDto {
  @NotNull
  private Long id;

  @NotEmpty(message = "Электронная почта не может быть пустой", groups = ValidationGroups.Create.class)
  @Email(message = "Электронная почта должна содержать символ @", groups = {
      ValidationGroups.Create.class,
      ValidationGroups.Update.class
  })
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
