package ru.yandex.practicum.filmorate.storage.user;

import java.util.Collection;
import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {

  Collection<User> findAll();

  User create(User user);

  User update(User user);

  User getById(Long id);

  void checkUserExists(Long id);

  void addFriend(Long id, Long friendId);

  Collection<User> getFriendsOfUser(Long id);

  Collection<User> getCommonFriends(Long id, Long otherId);

  void removeFriend(Long id, Long friendId);

  boolean isEmailUsed(String email);
}
