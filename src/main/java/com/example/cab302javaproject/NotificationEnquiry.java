package com.example.cab302javaproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

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
