package com.example.cab302javaproject;

import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDataTest {
    private UserData.UserDetails userDetails;

    @BeforeEach
    void setUp() {
        UUID uuid = UUID.randomUUID();
        String name = "Test User";
        String email = "test@example.com";
        String password = "password";
        String accountType = "Personal";
        Optional<UUID> linkingCode = Optional.empty();
        boolean notificationsPreference = true;
        String notificationsSnoozeDuration = "10 minutes";
        String notificationsReminderTime = "15 minutes before";
        userDetails = new UserData.UserDetails(uuid, name, email, password, accountType, linkingCode, notificationsPreference, notificationsSnoozeDuration, notificationsReminderTime);

        // Initialize userDetailsMap before each test
        LifestyleCalendar.userDetailsMap = new HashMap<>();
    }

    @Test
    void testIsValidLinkingCode() {
        // Test case for a valid linking code
        UUID managerUuid = UUID.randomUUID();
        UserData.UserDetails managerUserDetails = new UserData.UserDetails(managerUuid, "Manager User", "manager@example.com", "password", "Manager", Optional.of(managerUuid), true, "10 minutes", "15 minutes before");
        LifestyleCalendar.userDetailsMap.put(managerUuid, managerUserDetails);
        assertTrue(UserData.isValidLinkingCode(managerUuid.toString()));

        // Test case for an invalid linking code
        assertFalse(UserData.isValidLinkingCode(UUID.randomUUID().toString()));
    }

    @Test
    void testIsEmailRegistered() {
        // Test case for a registered email
        LifestyleCalendar.userDetailsMap.put(userDetails.getUuid(), userDetails);
        assertTrue(UserData.isEmailRegistered(userDetails.getEmail()));

        // Test case for an unregistered email
        assertFalse(UserData.isEmailRegistered("unregistered@example.com"));
    }

    @Test
    void testAuthenticateUser() {
        // Test case for a valid email and password
        LifestyleCalendar.userDetailsMap.put(userDetails.getUuid(), userDetails);
        assertTrue(UserData.authenticateUser(userDetails.getEmail(), userDetails.getPassword()));

        // Test case for an invalid email and password
        assertFalse(UserData.authenticateUser("invalid@example.com", "invalidPassword"));
    }

    @Test
    void testIsValidUUID() {
        // Test case for a valid UUID
        assertTrue(UserData.isValidUUID(UUID.randomUUID().toString()));

        // Test case for an invalid UUID
        assertFalse(UserData.isValidUUID("invalid-uuid"));
    }

    @Test
    void testGetUuid() {
        assertNotNull(userDetails.getUuid());
    }

    @Test
    void testGetName() {
        assertEquals("Test User", userDetails.getName());
    }

    @Test
    void testGetEmail() {
        assertEquals("test@example.com", userDetails.getEmail());
    }

    @Test
    void testGetPassword() {
        assertEquals("password", userDetails.getPassword());
    }

    @Test
    void testGetAccountType() {
        assertEquals("Personal", userDetails.getAccountType());
    }

    @Test
    void testGetLinkingCode() {
        assertFalse(userDetails.getLinkingCode().isPresent());
    }

    @Test
    void testGetNotificationsPreference() {
        assertTrue(userDetails.getNotificationsPreference());
    }

    @Test
    void testGetNotificationsSnoozeDuration() {
        assertEquals("10 minutes", userDetails.getNotificationsSnoozeDuration());
    }

    @Test
    void testGetNotificationsReminderTime() {
        assertEquals("15 minutes before", userDetails.getNotificationsReminderTime());
    }
}