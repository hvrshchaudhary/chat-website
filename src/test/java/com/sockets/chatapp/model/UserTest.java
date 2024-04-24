package com.sockets.chatapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("1", "testUser", "password123");
    }

    @Test
    void testGetId() {
        assertThat(user.getId()).isEqualTo("1");
    }

    @Test
    void testSetId() {
        user.setId("2");
        assertThat(user.getId()).isEqualTo("2");
    }

    @Test
    void testGetUsername() {
        assertThat(user.getUsername()).isEqualTo("testUser");
    }

    @Test
    void testSetUsername() {
        user.setUsername("newUsername");
        assertThat(user.getUsername()).isEqualTo("newUsername");
    }

    @Test
    void testGetPassword() {
        assertThat(user.getPassword()).isEqualTo("password123");
    }

    @Test
    void testSetPassword() {
        user.setPassword("newPassword");
        assertThat(user.getPassword()).isEqualTo("newPassword");
    }

    @Test
    void testToString() {
        assertThat(user.toString()).contains("testUser");
        assertThat(user.toString()).doesNotContain("password123");
    }

    // hashCode and equals should also be tested to ensure they work as expected
    // this typically involves creating multiple user objects and verifying that
    // equal objects have the same hash code and that nonequal objects do not
    // and that equals behaves consistently with the contract
}
