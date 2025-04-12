package ru.yandex.practicum.filmorate.storage.user;

import static java.util.Objects.isNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.user.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.ValidationGroups;

@Component
public class InMemoryUserStorage implements UserStorage {

  private final Map<Long, User> users = new HashMap<>();
  private Long idCounter = 1L;

  @Override
  public Collection<User> findAll() {
    return users.values();
  }

  @Override
  public User create(@Validated(ValidationGroups.Create.class) @RequestBody User user) {
    user.setId(idCounter++);
    if (isNull(user.getName())) {
      user.setName(user.getLogin());
    }
    users.put(user.getId(), user);
    return user;
  }

  @Override
  public User update(@Validated(ValidationGroups.Update.class) @RequestBody User user) {
    Long id = user.getId();

    if (user.getEmail() == null) {
      user.setEmail(users.get(id).getEmail());
    }
    users.put(user.getId(), user);
    return user;
  }

  @Override
  public User getById(Long id) {
    User user = users.get(id);
    if (isNull(user)) {
      String errorMessage = String.format("Пользователь с id: %d не найден", id);
      throw new UserNotFoundException(errorMessage);
    }
    return user;
  }
}
