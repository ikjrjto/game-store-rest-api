package com.sumdu.edu.ua.ivan.gamestore.repository;

import com.sumdu.edu.ua.ivan.gamestore.entity.Role;
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
class RoleRepositoryTest {

    @Autowired
    private RoleRepository repository;

    @Test
    void RoleRepository_Create_ReturnRoleNotNull() {
        Role role = new Role();
        role.setName("ROLE_USER");

        Role savedRole = repository.save(role);

        Assertions.assertThat(savedRole).isNotNull();
        Assertions.assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    void RoleRepository_FindAll_ReturnMoreThanOneRole() {
        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");

        repository.save(userRole);
        repository.save(adminRole);

        List<Role> roleList = repository.findAll();

        Assertions.assertThat(roleList).isNotNull();
        Assertions.assertThat(roleList.size()).isEqualTo(2);
    }

    @Test
    void RoleRepository_FindById_ReturnRoleNotNull() {
        Role role = new Role();
        role.setName("ROLE_USER");

        repository.save(role);
        Role role1 = repository.findById(role.getId()).get();

        Assertions.assertThat(role1).isNotNull();
    }

    @Test
    void RoleRepository_FindByName_ReturnRoleNotNull() {
        Role role = new Role();
        role.setName("ROLE_USER");

        repository.save(role);
        Role role1 = repository.findByName(role.getName()).get();

        Assertions.assertThat(role1).isNotNull();
    }

    @Test
    void RoleRepository_Update_ReturnRoleNotNull() {
        Role role = new Role();
        role.setName("ROLE_USER");

        repository.save(role);
        Role role1 = repository.findById(role.getId()).get();
        role1.setName("ROLE_USER_UPD");
        Role savedRole = repository.save(role1);

        Assertions.assertThat(savedRole).isNotNull();
        Assertions.assertThat(savedRole.getName()).isNotNull();
    }

    @Test
    void RoleRepository_Delete_ReturnRoleIsEmpty() {
        Role role = new Role();
        role.setName("ROLE_USER");

        repository.save(role);
        repository.deleteById(role.getId());
        Optional<Role> roleOptional = repository.findById(role.getId());

        Assertions.assertThat(roleOptional).isEmpty();
    }
}