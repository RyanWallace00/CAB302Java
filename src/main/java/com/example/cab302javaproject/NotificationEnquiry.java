package com.example.cab302javaproject;

import java.time.LocalDateTime;
import static com.example.cab302javaproject.LifestyleCalendar.*;
import static com.example.cab302javaproject.LifestyleCalendar.loggedInUser;

public class NotificationEnquiry {
    // Implement notifications
    public static void showNotifications() {
        // Iterate through the calendarDetailsMap and check for events occurring at the current time
        for (CalendarData.CalendarDetails calendarDetails : calendarDetailsMap.values()) {
            LocalDateTime eventDateTime = LocalDateTime.of(calendarDetails.getEventDate(), calendarDetails.getEventFrom());
            LocalDateTime currentDateTime = LocalDateTime.now();

            if (eventDateTime.isEqual(currentDateTime)) {
                // Check if notifications are enabled in the settings
                if (loggedInUser.getNotificationsPreference()) {
                    LifestyleCalendar.showNotification("Event Reminder", calendarDetails.getEventName() + " is starting now!");
                }
            } else {
                // Check for upcoming events based on the reminder time
                String reminderTime = loggedInUser.getNotificationsReminderTime();
                LocalDateTime reminderDateTime = eventDateTime.minusMinutes(Long.parseLong(reminderTime.split(" ")[0]));

                if (currentDateTime.isEqual(reminderDateTime)) {
                    if (loggedInUser.getNotificationsPreference()) {
                        LifestyleCalendar.showNotification("Event Reminder", calendarDetails.getEventName() + " is starting in " + reminderTime + "!");
                    }
                }
            }
        }
    }
}
