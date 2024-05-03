package com.example.cab302javaproject;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CalendarDataTest {
    private CalendarData.CalendarDetails calendarDetails;
    private UserData.UserDetails loggedInUser;

    @BeforeEach
    void setUp() {
        UUID uuid = UUID.randomUUID();
        String eventName = "Test Event";
        String eventType = "Meeting";
        String eventDescription = "Test Description";
        LocalDate eventDate = LocalDate.now();
        LocalTime eventTimeFrom = LocalTime.of(9, 0);
        LocalTime eventTimeTo = LocalTime.of(10, 0);
        Optional<UUID> linkingCode = Optional.of(UUID.randomUUID());
        calendarDetails = new CalendarData.CalendarDetails(uuid, eventName, eventType, eventDescription, eventDate, eventTimeFrom, eventTimeTo, linkingCode);

        UUID userUuid = UUID.randomUUID();
        String name = "Test User";
        String email = "test@example.com";
        String password = "password";
        String accountType = "Personal";
        boolean notificationsPreference = true;
        String notificationsSnoozeDuration = "10 minutes";
        String notificationsReminderTime = "15 minutes before";
        loggedInUser = new UserData.UserDetails(userUuid, name, email, password, accountType, linkingCode, notificationsPreference, notificationsSnoozeDuration, notificationsReminderTime);
    }

    @Test
    void testIsEventForLoggedInUser() {
        // Test case for Personal account type
        loggedInUser = new UserData.UserDetails(loggedInUser.getUuid(), loggedInUser.getName(), loggedInUser.getEmail(), loggedInUser.getPassword(), "Personal", loggedInUser.getLinkingCode(), loggedInUser.getNotificationsPreference(), loggedInUser.getNotificationsSnoozeDuration(), loggedInUser.getNotificationsReminderTime());
        assertTrue(CalendarData.isEventForLoggedInUser(calendarDetails));

        // Test case for Manager account type
        loggedInUser = new UserData.UserDetails(loggedInUser.getUuid(), loggedInUser.getName(), loggedInUser.getEmail(), loggedInUser.getPassword(), "Manager", loggedInUser.getLinkingCode(), loggedInUser.getNotificationsPreference(), loggedInUser.getNotificationsSnoozeDuration(), loggedInUser.getNotificationsReminderTime());
        assertTrue(CalendarData.isEventForLoggedInUser(calendarDetails));

        // Test case for Employee account type with matching linking code
        loggedInUser = new UserData.UserDetails(loggedInUser.getUuid(), loggedInUser.getName(), loggedInUser.getEmail(), loggedInUser.getPassword(), "Employee", loggedInUser.getLinkingCode(), loggedInUser.getNotificationsPreference(), loggedInUser.getNotificationsSnoozeDuration(), loggedInUser.getNotificationsReminderTime());
        assertTrue(CalendarData.isEventForLoggedInUser(calendarDetails));

        // Test case for Employee account type without linking code
        loggedInUser = new UserData.UserDetails(loggedInUser.getUuid(), loggedInUser.getName(), loggedInUser.getEmail(), loggedInUser.getPassword(), "Employee", Optional.empty(), loggedInUser.getNotificationsPreference(), loggedInUser.getNotificationsSnoozeDuration(), loggedInUser.getNotificationsReminderTime());
        calendarDetails = new CalendarData.CalendarDetails(calendarDetails.getUuid(), calendarDetails.getEventName(), calendarDetails.getEventType(), calendarDetails.getEventDescription(), calendarDetails.getEventDate(), calendarDetails.getEventFrom(), calendarDetails.getEventTo(), Optional.of(loggedInUser.getUuid()));
        assertTrue(CalendarData.isEventForLoggedInUser(calendarDetails));
    }

    @Test
    void testGetUuid() {
        assertNotNull(calendarDetails.getUuid());
    }

    @Test
    void testGetEventName() {
        assertEquals("Test Event", calendarDetails.getEventName());
    }

    @Test
    void testGetEventDescription() {
        assertEquals("Test Description", calendarDetails.getEventDescription());
    }

    @Test
    void testGetEventType() {
        assertEquals("Meeting", calendarDetails.getEventType());
    }

    @Test
    void testGetEventFrom() {
        assertEquals(LocalTime.of(9, 0), calendarDetails.getEventFrom());
    }

    @Test
    void testGetEventTo() {
        assertEquals(LocalTime.of(10, 0), calendarDetails.getEventTo());
    }

    @Test
    void testGetLinkingCode() {
        assertTrue(calendarDetails.getLinkingCode().isPresent());
    }

    @Test
    void testGetEventDate() {
        assertEquals(LocalDate.now(), calendarDetails.getEventDate());
    }
}