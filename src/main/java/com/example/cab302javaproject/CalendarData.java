package com.example.cab302javaproject;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static com.example.cab302javaproject.LifestyleCalendar.*;
import static com.example.cab302javaproject.UserData.*;

public class CalendarData {
    public static boolean isEventForLoggedInUser(CalendarDetails event) {
        if (loggedInUser.getAccountType().equals("Personal") &&
                event.getLinkingCode() != null &&
                event.getLinkingCode().isPresent() &&
                event.getLinkingCode().get().equals(loggedInUser.getUuid())) {
            return true;
        } else if (loggedInUser.getAccountType().equals("Manager") &&
                event.getLinkingCode() != null &&
                event.getLinkingCode().isPresent() &&
                event.getLinkingCode().get().equals(loggedInUser.getLinkingCode().get())) {
            return true;
        } else if (loggedInUser.getAccountType().equals("Employee")) {
            if (loggedInUser.getLinkingCode() != null &&
                    loggedInUser.getLinkingCode().isPresent() &&
                    event.getLinkingCode() != null &&
                    event.getLinkingCode().isPresent() &&
                    event.getLinkingCode().get().equals(loggedInUser.getLinkingCode().get())) {
                return true;
            } else if ((loggedInUser.getLinkingCode() == null ||
                    !loggedInUser.getLinkingCode().isPresent()) &&
                    event.getLinkingCode() != null &&
                    event.getLinkingCode().isPresent() &&
                    event.getLinkingCode().get().equals(loggedInUser.getUuid())) {
                return true;
            }
        }
        return false;
    }

    // Method to load user data from file
    public static void loadCalendarData() { // Defines a private method to load calendar data from a file
        File file = new File("src/main/resources/calendarData.dat"); // Creates a new instance of File with the filename "calendarData.dat"

        if (file.exists() && file.length() > 0) { // Checks if the file exists and has a non-zero length
            try {
                FileInputStream fileIn = new FileInputStream(file); // Creates a new instance of FileInputStream with the file
                ObjectInputStream objectIn = new ObjectInputStream(fileIn); // Creates a new instance of ObjectInputStream with the FileInputStream
                LifestyleCalendar.calendarDetailsMap = (HashMap<UUID, CalendarDetails>) objectIn.readObject(); // Reads the calendarDetailsMap object from the ObjectInputStream
                objectIn.close(); // Closes the ObjectInputStream
                fileIn.close(); // Closes the FileInputStream

            } catch (IOException | ClassNotFoundException e) { // Catches IOException and ClassNotFoundException
                e.printStackTrace(); // Prints the stack trace in case of an exception
            }
        }
        //} else {
        //System.out.println("calendarData.dat file is empty or does not exist.");
        //   calendarDetailsMap = new HashMap<UUID, CalendarDetails>(); // Creates a new instance of HashMap and assigns it to the calendarDetailsMap
        //}
    }

    public static void saveCalendarData() { // Defines a private method to save calendar data to a file
        try {
            FileOutputStream fileOut = new FileOutputStream("src/main/resources/calendarData.dat"); // Creates a new instance of FileOutputStream with the filename "calendarData.dat"
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut); // Creates a new instance of ObjectOutputStream with the FileOutputStream

            objectOut.writeObject(calendarDetailsMap); // Writes the calendarDetailsMap object to the ObjectOutputStream
            objectOut.close(); // Closes the ObjectOutputStream
            fileOut.close(); // Closes the FileOutputStream
        } catch (Exception e) { // Catches any Exception
            e.printStackTrace(); // Prints the stack trace in case of an exception
        }
    }

    public static class CalendarDetails implements Serializable { // Defines a private static nested class CalendarDetails that implements the Serializable interface
        private final UUID uuid; // Declares a final instance variable uuid of type UUID
        private final String eventName; // Declares a final instance variable eventName of type String
        private final String eventDescription; // Declares a final instance variable eventDescription of type String
        private final String eventType; // Declares a final instance variable eventType of type String
        private final LocalTime eventTimeFrom; // Declares a final instance variable eventTimeFrom of type DateFormat
        private final LocalTime eventTimeTo; // Declares a final instance variable eventTimeTo of type DateFormat
        private final LocalDate eventDate; // Declares a final instance variable eventTo of type ZonedDateTime
        private transient Optional<UUID> linkingCode; // Declares a transient instance variable linkingCode of type Optional<UUID>
        private static final long serialVersionUID = 1L; // Declares a static final serialVersionUID field required for Serializable classes

        public CalendarDetails(UUID uuid, String eventName, String eventType, String eventDescription, LocalDate eventDate, LocalTime eventTimeFrom, LocalTime eventTimeTo, Optional<UUID> linkingCode) { // Defines a constructor that takes parameters for all instance variables
            this.uuid = uuid; // Initializes the uuid instance variable
            this.eventName = eventName; // Initializes the eventName instance variable
            this.eventType = eventType; // Initializes the eventType instance variable
            this.eventDescription = eventDescription; // Initializes the eventDescription instance variable
            this.eventDate = eventDate;
            this.eventTimeFrom = eventTimeFrom; // Initializes the eventFrom instance variable
            this.eventTimeTo = eventTimeTo; // Initializes the eventTo instance variable
            this.linkingCode = linkingCode; // Initializes the linkingUsers instance variable
        }

        public UUID getUuid() { // Defines a public method to get the uuid
            return uuid; // Returns the uuid instance variable
        }

        public String getEventName() { // Defines a public method to get the eventName
            return eventName; // Returns the eventName instance variable
        }

        public String getEventDescription() { // Defines a public method to get the eventDescription
            return eventDescription; // Returns the eventDescription instance variable
        }

        public String getEventType() { // Defines a public method to get the eventType
            return eventType; // Returns the eventType instance variable
        }

        public LocalTime getEventFrom() { // Defines a public method to get the eventFrom
            return eventTimeFrom; // Returns the eventTimeFrom instance variable
        }

        public LocalTime getEventTo() { // Defines a public method to get the eventTo
            return eventTimeTo; // Returns the eventTimeTo instance variable
        }

        public Optional<UUID> getLinkingCode() { // Defines a public method to get the linkingCode
            return linkingCode; // Returns the linkingCode instance variable
        }

        public LocalDate getEventDate() {
            LocalDate selectedDate = eventDate;
            return selectedDate;
        }
    }
}
