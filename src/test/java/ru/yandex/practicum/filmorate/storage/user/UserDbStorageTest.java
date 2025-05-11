package ru.yandex.practicum.filmorate.storage.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@AutoConfigureTestDatabase
@Import({UserDbStorage.class, UserRowMapper.class})
class UserDbStorageTest {

  @Autowired
  private UserDbStorage storage;
  private final User user = new User(
      "dolore",
      "Nick Name",
      "mail@mail.ru",
      LocalDate.of(1946, 8, 20)
  );

  @Test
  void create() {
    int count = storage.findAll().size();

    User created = storage.create(user);

    Assertions.assertNotNull(created.getId());
    Assertions.assertEquals(count + 1, storage.findAll().size());
  }

  @Test
  void get() {
    User created = storage.create(user);
    User found = storage.getById(created.getId());

    Assertions.assertEquals(user.getName(), found.getName());
    Assertions.assertEquals(user.getLogin(), found.getLogin());
    Assertions.assertEquals(user.getEmail(), found.getEmail());
    Assertions.assertEquals(user.getBirthday(), found.getBirthday());
  }

  @Test
  void getNonExisting() {
    Long nonExistentId = 100L;
    NotFoundException exception = assertThrows(
        NotFoundException.class,
        () -> storage.getById(nonExistentId)
    );
    assertThat(exception.getMessage()).contains("User id:100 not found");
  }

  @Test
  void updateUser() {
    User created = storage.create(user);

    created.setLogin("new_login");
    created.setName("new_name");
    created.setEmail("new_email@mail.com");

    storage.update(created);
    User found = storage.getById(created.getId());

    Assertions.assertEquals("new_login", found.getLogin());
    Assertions.assertEquals("new_name", found.getName());
    Assertions.assertEquals("new_email@mail.com", found.getEmail());
  }
}