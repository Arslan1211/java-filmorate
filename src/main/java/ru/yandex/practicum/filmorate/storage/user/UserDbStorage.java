package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.AlreadyFriendException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.base.BaseStorage;

import java.util.Collection;
import java.util.Optional;
import ru.yandex.practicum.filmorate.util.SqlQueries;

@Repository("userDbStorage")
public class UserDbStorage extends BaseStorage<User> implements UserStorage {

  public UserDbStorage(JdbcTemplate jdbcTemplate, UserRowMapper rowMapper) {
    super(jdbcTemplate, rowMapper);
  }

  @Override
  public Collection<User> getAll() {
    return getMany(SqlQueries.SQL_SELECT_ALL);
  }

  @Override
  public User save(User user) {
    Long id = insertAndReturnId(SqlQueries.SQL_INSERT_USER,
        Long.class,
        user.getLogin(),
        user.getName(),
        user.getEmail(),
        user.getBirthday()
    );

    if (id == null)
      return null;
    user.setId(id);
    return user;
  }

  @Override
  public User update(User user) {
    int updated = super.update(SqlQueries.SQL_UPDATE_USER,
        user.getLogin(),
        user.getName(),
        user.getEmail(),
        user.getBirthday(),
        user.getId());
    if (updated > 0)
      return user;
    return null;
  }

  @Override
  public void checkUserExists(Long id) throws NotFoundException {
    boolean exists = exists(SqlQueries.SQL_CHECK_USER_EXISTS, id);
    if (!exists)
      throw new NotFoundException("User id:" + id + " not found");
  }

  @Override
  public User getUser(Long id) {
    Optional<User> one = getOne(SqlQueries.SQL_SELECT_ONE, id);
    if (one.isEmpty())
      throw new NotFoundException("User id:" + id + " not found");
    return one.get();
  }

  @Override
  public boolean isEmailUsed(String email) {
    return exists(SqlQueries.SQL_CHECK_EMAIL_USED, email);
  }

  @Override
  public void addFriend(Long id, Long friendId) {
    checkUserExists(id);
    checkUserExists(friendId);

    Boolean alreadyAdded = jdbcTemplate.queryForObject(
        SqlQueries.SQL_CHECK_FRIENDSHIP,
        Boolean.class,
        id,
        friendId
    );

    if (Boolean.TRUE.equals(alreadyAdded)) {
      throw new AlreadyFriendException(id, friendId);
    }

    int updatedRows = update(SqlQueries.SQL_ADD_FRIEND, id, friendId);
  }

  @Override
  public void removeFriend(Long id, Long friendId) {
    checkUserExists(id);
    checkUserExists(friendId);
    int updatedRows = update(SqlQueries.SQL_REMOVE_FRIEND, id, friendId);
  }

  @Override
  public Collection<User> getFriendsOfUser(Long id) {
    checkUserExists(id);
    return getMany(SqlQueries.SQL_SELECT_FRIENDS, id);
  }

  @Override
  public Collection<User> getCommonFriends(Long id, Long otherId) {
    checkUserExists(id);
    checkUserExists(otherId);
    return getMany(SqlQueries.SQL_SELECT_COMMON_FRIENDS, id, otherId);
  }
}