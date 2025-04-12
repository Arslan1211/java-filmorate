package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.exception.friend.AlreadyFriendException;
import ru.yandex.practicum.filmorate.validator.ValidationGroups;

/**
 * User.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

  private Long id;

  private String name;

  @NotBlank(message = "Логин не может быть пустым")
  @Pattern(regexp = "^\\S*$", message = "Логин не может содержать пробелы", groups = {
      ValidationGroups.Create.class,
      ValidationGroups.Update.class
  })
  private String login;

  @NotEmpty(message = "Электронная почта не может быть пустой", groups = ValidationGroups.Create.class)
  @Email(message = "Электронная почта должна содержать символ @", groups = {
      ValidationGroups.Create.class,
      ValidationGroups.Update.class
  })
  private String email;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @PastOrPresent(message = "Дата рождения не может быть в будущем", groups = ValidationGroups.Create.class)
  private LocalDate birthday;

  private Set<Long> friends = new HashSet<>();

  public void addFriend(Long id) {
    if (!friends.add(id)) {
      throw new AlreadyFriendException(this.id, id);
    }
  }

  public void removeFriend(Long id) {
    friends.remove(id);
  }

  public void removeFriends() {
    friends.clear();
  }
}
