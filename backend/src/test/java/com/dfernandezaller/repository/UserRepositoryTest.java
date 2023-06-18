package com.dfernandezaller.repository;

import com.dfernandezaller.repository.entity.User;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@MicronautTest
public class UserRepositoryTest {

    @Inject
    UserRepository userRepository;


    @AfterEach
    void cleanup() {
        userRepository.deleteById("test@example.com");
    }

    @Test
    public void testSaveUser() {
        User user = User.builder()
                .email("test@example.com")
                .name("Test User")
                .build();

        userRepository.save(user);

        Optional<User> retrievedUser = userRepository.findById("test@example.com");

        Assertions.assertTrue(retrievedUser.isPresent());
        Assertions.assertEquals("Test User", retrievedUser.get().getName());
    }

    @Test
    public void testUpdateUser() {
        User user = User.builder()
                .email("test@example.com")
                .name("Test User")
                .build();
        userRepository.save(user);

        user.setName("Updated User");
        userRepository.update(user);

        Optional<User> updatedUser = userRepository.findById("test@example.com");

        Assertions.assertTrue(updatedUser.isPresent());
        Assertions.assertEquals("Updated User", updatedUser.get().getName());
    }

    @Test
    public void testDeleteUser() {
        User user = User.builder()
                .email("test@example.com")
                .name("Test User")
                .build();
        userRepository.save(user);

        userRepository.delete(user);

        Optional<User> deletedUser = userRepository.findById("test@example.com");

        Assertions.assertFalse(deletedUser.isPresent());
    }


}
