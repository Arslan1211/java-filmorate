package ru.yandex.practicum.filmorate.storage.user;

import static java.util.Objects.isNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.user.EmailAlreadyTakenException;
import ru.yandex.practicum.filmorate.exception.user.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

@Component
public class InMemoryUserStorage implements UserStorage {

  private final Map<Long, User> users = new HashMap<>();
  private final Set<String> emails = new HashSet<>();
  private Long idCounter = 1L;

  @Override
  public Collection<User> findAll() {
    return users.values();
  }

  @Override
  public User create(User user) {
    if (emails.contains(user.getEmail())) {
      throw new EmailAlreadyTakenException(user.getEmail());
    }

    user.setId(idCounter++);
    if (isNull(user.getName())) {
      user.setName(user.getLogin());
    }
    users.put(user.getId(), user);
    emails.add(user.getEmail());
    return user;
  }

  @Override
  public User update(User user) {
    Long id = user.getId();

    if (isNull(user.getEmail())) {
      user.setEmail(users.get(id).getEmail());
    }
    users.put(user.getId(), user);
    emails.remove(user.getEmail());
    emails.add(user.getEmail());
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
