package ru.yandex.practicum.filmorate.service.user;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserStorage userStorage;

  public User create(User user) {
    user.removeFriends();
    return userStorage.create(user);
  }

  public User update(User user) {
    User existing = userStorage.getById(user.getId());

    Optional.ofNullable(user.getEmail()).ifPresent(existing::setEmail);
    Optional.ofNullable(user.getBirthday()).ifPresent(existing::setBirthday);
    Optional.ofNullable(user.getName()).ifPresent(existing::setName);
    Optional.ofNullable(user.getLogin()).ifPresent(existing::setLogin);

    return userStorage.update(existing);
  }

  public Collection<User> findAll() {
    return userStorage.findAll();
  }

  public User getById(Long id) {
    return userStorage.getById(id);
  }

  public void addFriend(Long id, Long friendId) {
    if (id.equals(friendId)) {
      throw new ValidationException(id + " = " + friendId);
    }

    User user = userStorage.getById(id);
    User friend = userStorage.getById(friendId);

    user.addFriend(friendId);
    friend.addFriend(id);
  }

  public void removeFriend(Long id, Long friendId) {
    User user1 = userStorage.getById(id);
    User user2 = userStorage.getById(friendId);

    user1.removeFriend(friendId);
    user2.removeFriend(id);
  }

  public Set<User> findCommonFriends(Long id, Long otherId) {
    if (id.equals(otherId)) {
      return Set.of();
    }

    Set<Long> friends1 = userStorage.getById(id).getFriends();
    Set<Long> friends2 = userStorage.getById(otherId).getFriends();

    return friends1.stream()
        .filter(friends2::contains)
        .map(userStorage::getById)
        .collect(Collectors.toSet());
  }

  public Collection<User> getFriends(Long id) {
    User user = userStorage.getById(id);
    return user.getFriends().stream()
        .map(userStorage::getById)
        .collect(Collectors.toList());
  }
}
