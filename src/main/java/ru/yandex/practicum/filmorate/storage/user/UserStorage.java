package ru.yandex.practicum.filmorate.storage.user;

import java.util.Collection;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {

  Collection<User> findAll();

  User create(User user);

  User update(User user);

  User getById(Long id) throws NotFoundException;

  void checkUserExists(Long id) throws NotFoundException;

  Collection<User> getFriendsOfUser(Long id);

  Collection<User> getCommonFriends(Long id, Long otherId);

  void addFriend(Long id, Long friendId);

  void removeFriend(Long id, Long friendId);

  boolean isEmailUsed(String email);
}
