package ru.yandex.practicum.filmorate.storage.user;

import java.util.Collection;
import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {

  Collection<User> findAll();

  User create(User user);

  User update(User user);

  User getById(Long id);
}
