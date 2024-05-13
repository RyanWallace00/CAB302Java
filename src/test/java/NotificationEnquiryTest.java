package com.example.cab302javaproject;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class NotificationEnquiryTest {
    @BeforeEach
    void setUp() {
        // Initialize the necessary data before each test
        LifestyleCalendar.calendarDetailsMap = new HashMap<>();
        LifestyleCalendar.loggedInUser = new UserData.UserDetails(
                UUID.randomUUID(),
                "Test User",
                "test@example.com",
                "password",
                "Personal",
                Optional.empty(),
                true,
                "10 minutes",
                "15 minutes before"
        );

        // Add a sample calendar event
        CalendarData.CalendarDetails calendarDetails = new CalendarData.CalendarDetails(
                UUID.randomUUID(),
                "Test Event",
                "Meeting",
                "Test Description",
                LocalDate.now(),
                LocalTime.now(),
                LocalTime.now().plusHours(1),
                Optional.empty()
        );
        LifestyleCalendar.calendarDetailsMap.put(calendarDetails.getUuid(), calendarDetails);
    }

    @Test
    void testShowNotifications() {
        // This test ensures that the showNotifications method runs without throwing an exception
        assertDoesNotThrow(() -> NotificationEnquiry.showNotifications());
    }
}