package com.example.cab302javaproject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserData {
    public static boolean isValidLinkingCode(String linkingCode) { // Defines a public method to check if a linking code is valid
        // Iterate over userDetailsMap to find manager profiles
        for (UserDetails userDetails : LifestyleCalendar.userDetailsMap.values()) { // Iterates over the values in the userDetailsMap
            if (Objects.equals(userDetails.getAccountType(), "Manager")) { // Checks if the current UserDetails object is of type "Manager"
                Optional<UUID> managerLinkingCode = userDetails.getLinkingCode(); // Gets the linking code of the manager
                if (managerLinkingCode.isPresent() && managerLinkingCode.get().toString().equals(linkingCode)) { // Checks if the manager's linking code matches the provided linking code
                    return true; // Valid linking code found
                }
            }
        }
        return false; // No matching linking code found
    }

    public static boolean isEmailRegistered(String email) { // Defines a public method to check if an email is already registered
        // Iterate over userDetailsMap to check if email is already registered
        for (UserDetails userDetails : LifestyleCalendar.userDetailsMap.values()) { // Iterates over the values in the userDetailsMap
            if (userDetails.getEmail().equals(email)) { // Checks if the email of the current UserDetails object matches the provided email
                return true; // Email already registered
            }
        }
        return false; // Email not registered
    }

    public static void saveUserData() {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/main/resources/userData.dat");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            String encryptedData = EncryptionUtils.encrypt(new Gson().toJson(LifestyleCalendar.userDetailsMap));
            objectOut.writeObject(encryptedData);
            objectOut.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadUserData() {
        File file = new File("src/main/resources/userData.dat");
        if (file.exists() && file.length() > 0) {
            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                String encryptedData = (String) objectIn.readObject();
                String decryptedData = EncryptionUtils.decrypt(encryptedData);
                LifestyleCalendar.userDetailsMap = new Gson().fromJson(decryptedData, new TypeToken<HashMap<UUID, UserDetails>>() {}.getType());
                objectIn.close();
                fileIn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LifestyleCalendar.userDetailsMap = new HashMap<>();
        }
    }

    public static boolean isValidUUID(String str) { // This method checks if a given string is a valid UUID (Universally Unique Identifier)
        try {
            UUID uuid = UUID.fromString(str); // Attempt to create a UUID object from the input string using the correct method
            return true; // If no exception is thrown, the string is a valid UUID
        } catch (IllegalArgumentException e) {
            return false; // If an exception is thrown, the string is not a valid UUID
        }
    }

    public static boolean authenticateUser(String email, String password) { // Defines a public method to authenticate a user
        for (UserDetails userDetails : LifestyleCalendar.userDetailsMap.values()) { // Iterates over the values in the userDetailsMap
            if (userDetails.getEmail().equalsIgnoreCase(email) && userDetails.getPassword().equals(password)) { // Checks if the email and password match the current UserDetails object, ignoring case sensitivity on email field
                LifestyleCalendar.loggedInUser = userDetails; // Updates the loggedInUser instance variable with the authenticated user's details
                return true; // User authenticated successfully
            }
        }
        return false; // User authentication failed
    }

    public static class UserDetails implements Serializable { // Defines a public static nested class UserDetails that implements the Serializable interface
        private final UUID uuid; // Declares a final instance variable uuid of type UUID
        private final String name; // Declares a final instance variable name of type String
        private final String email; // Declares a final instance variable email of type String
        private final String password; // Declares a final instance variable password of type String
        private final String accountType; // Declares a final instance variable accountType of type String
        private transient Optional<UUID> linkingCode; // Declares a transient instance variable linkingCode of type Optional<UUID>
        private final Boolean notificationsPreference; // Declares a final instance variable notificationsPreference of type Boolean
        private final String notificationsSnoozeDuration; // Declares a final instance variable notificationsSnoozeDuration of type String
        private final String notificationsReminderTime; // Declares a final instance variable notificationsReminderTime of type String
        private static final long serialVersionUID = 1L; // Declares a static final serialVersionUID field required for Serializable classes

        public UserDetails (UUID uuid, String name, String email, String password, String accountType, Optional<UUID> linkingCode, Boolean notificationsPreference, String notificationsSnoozeDuration, String notificationsReminderTime) { // Defines a constructor that takes parameters for all instance variables
            this.uuid = uuid; // Initializes the uuid instance variable
            this.name = name; // Initializes the name instance variable
            this.email = email; // Initializes the email instance variable
            this.password = password; // Initializes the password instance variable
            this.accountType = accountType; // Initializes the accountType instance variable
            this.linkingCode = linkingCode; // Initializes the linkingCode instance variable
            this.notificationsPreference = notificationsPreference; // Initializes the notificationsPreference instance variable
            this.notificationsSnoozeDuration = notificationsSnoozeDuration; // Initializes the notificationsSnoozeDuration instance variable
            this.notificationsReminderTime = notificationsReminderTime; // Initializes the notificationsReminderTime instance variable
        }

        public void writeObject(ObjectOutputStream out) throws IOException { // Defines a public method for custom serialization of the linkingCode field
            out.defaultWriteObject(); // Performs the default serialization for non-transient instance variables
            out.writeBoolean(linkingCode.isPresent()); // Writes a boolean indicating if linkingCode is present
            linkingCode.ifPresent(uuid -> { // Executes the lambda expression if linkingCode is present
                try {
                    out.writeObject(uuid); // Writes the UUID object if present
                } catch (IOException e) {
                    e.printStackTrace(); // Prints the stack trace in case of an IOException
                }
            });
        }

        public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException { // Defines a public method for custom deserialization of the linkingCode field
            in.defaultReadObject(); // Performs the default deserialization for non-transient instance variables
            boolean isPresent = in.readBoolean(); // Reads a boolean indicating if linkingCode is present
            if (isPresent) {
                linkingCode = Optional.of((UUID) in.readObject()); // Sets linkingCode to the deserialized UUID object if present
            } else {
                linkingCode = Optional.empty(); // Sets linkingCode to an empty Optional if not present
            }
        }

        public UUID getUuid() { // Defines a public method to get the uuid
            return uuid; // Returns the uuid instance variable
        }

        public String getName() { // Defines a public method to get the name
            return name; // Returns the name instance variable
        }

        public String getEmail() { // Defines a public method to get the email
            return email; // Returns the email instance variable
        }

        public String getPassword() { // Defines a public method to get the password
            return password; // Returns the password instance variable
        }

        public String getAccountType() { // Defines a public method to get the accountType
            return accountType; // Returns the accountType instance variable
        }

        public Optional<UUID> getLinkingCode() { // Defines a public method to get the linkingCode
            return linkingCode; // Returns the linkingCode instance variable
        }

        public boolean getNotificationsPreference() { // Defines a public method to get the notificationPreference
            return notificationsPreference; // Returns the notificationPreference instance variable
        }

        public String getNotificationsSnoozeDuration() { // Defines a public method to get the notificationsSnoozeDuration
            return notificationsSnoozeDuration; // Returns the notificationsSnoozeDuration instance variable
        }

        public String getNotificationsReminderTime() { // Defines a public method to get the notificationsReminderTime
            return notificationsReminderTime; // Returns the notificationsReminderTime instance variable
        }
    }
}
