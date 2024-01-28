package com.sumdu.edu.ua.ivan.gamestore.repository;

import com.sumdu.edu.ua.ivan.gamestore.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    void UserRepository_Save_ReturnUserNotNull() {
        User user = new User();
        user.setName("User 1");
        user.setUsername("user1");
        user.setEmail("user@email.com");
        user.setPassword("user1");

        User saved = repository.save(user);

        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getId()).isGreaterThan(0);
    }

    @Test
    void UserRepository_FindAll_ReturnMoreThanOneUser() {
        User user1 = new User();
        user1.setName("User 1");
        user1.setUsername("user1");
        user1.setEmail("user1@email.com");
        user1.setPassword("user1");

        User user2 = new User();
        user2.setName("User 2");
        user2.setUsername("user2");
        user2.setEmail("user2@email.com");
        user2.setPassword("user2");

        repository.save(user1);
        repository.save(user2);

        List<User> userList = repository.findAll();

        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isEqualTo(2);
    }

    @Test
    void UserRepository_FindById_ReturnUserNotNull() {
        User user1 = new User();
        user1.setName("User 1");
        user1.setUsername("user1");
        user1.setEmail("user1@email.com");
        user1.setPassword("user1");

        repository.save(user1);

        User user = repository.findById(user1.getId()).get();

        Assertions.assertThat(user).isNotNull();
    }

    @Test
    void UserRepository_FindByEmail_ReturnUserNotNull() {
        User user1 = new User();
        user1.setName("User 1");
        user1.setUsername("user1");
        user1.setEmail("user1@email.com");
        user1.setPassword("user1");

        repository.save(user1);

        User user = repository.findByEmail(user1.getEmail()).get();

        Assertions.assertThat(user).isNotNull();
    }

    @Test
    void UserRepository_FindByUsernameOrEmail_ReturnUserNotNull() {
        User user1 = new User();
        user1.setName("User 1");
        user1.setUsername("user1");
        user1.setEmail("user1@email.com");
        user1.setPassword("user1");

        repository.save(user1);

        User user = repository.findByUsernameOrEmail(user1.getUsername(), user1.getEmail()).get();

        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user).isEqualTo(user1);
    }

    @Test
    void UserRepository_FindByUsername_ReturnUserNotNull() {
        User user1 = new User();
        user1.setName("User 1");
        user1.setUsername("user1");
        user1.setEmail("user1@email.com");
        user1.setPassword("user1");

        repository.save(user1);

        User user = repository.findByUsername(user1.getUsername()).get();

        Assertions.assertThat(user).isNotNull();
    }

    @Test
    void UserRepository_ExistsByUsername_ReturnTrueForUser() {
        User user1 = new User();
        user1.setName("User 1");
        user1.setUsername("user1");
        user1.setEmail("user1@email.com");
        user1.setPassword("user1");

        repository.save(user1);

        Boolean existsByUsername = repository.existsByUsername(user1.getUsername());

        Assertions.assertThat(existsByUsername).isTrue();
    }

    @Test
    void UserRepository_ExistsByEmail_ReturnTrueForUser() {
        User user1 = new User();
        user1.setName("User 1");
        user1.setUsername("user1");
        user1.setEmail("user1@email.com");
        user1.setPassword("user1");

        repository.save(user1);

        Boolean existsByUsername = repository.existsByEmail(user1.getEmail());

        Assertions.assertThat(existsByUsername).isTrue();
    }

    @Test
    void UserRepository_update_ReturnUserNotNull() {
        User user1 = new User();
        user1.setName("User 1");
        user1.setUsername("user1");
        user1.setEmail("user1@email.com");
        user1.setPassword("user1");

        repository.save(user1);

        User user = repository.findById(user1.getId()).get();
        user.setName("User 1 upd");
        User updatedUser = repository.save(user);

        Assertions.assertThat(updatedUser).isNotNull();
        Assertions.assertThat(updatedUser.getName()).isNotNull();
    }

    @Test
    void UserRepository_Delete_ReturnUserIsEmpty() {
        User user1 = new User();
        user1.setName("User 1");
        user1.setUsername("user1");
        user1.setEmail("user1@email.com");
        user1.setPassword("user1");

        repository.save(user1);

        repository.deleteById(user1.getId());
        Optional<User> optionalUser = repository.findById(user1.getId());

        Assertions.assertThat(optionalUser).isEmpty();
    }
}