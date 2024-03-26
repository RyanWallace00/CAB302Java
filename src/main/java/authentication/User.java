package authentication;
import java.lang.*;

import java.util.ArrayList;
import java.util.UUID;

import calendar.CalendarItem;

public class User {
    private UUID userUUID;
    private String username;
    private String password;
    private ArrayList<CalendarItem> calendarItems;
    private String userType;
    public User(String username, String password, String userType) {
        this.userUUID = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.calendarItems = new ArrayList<>();
        this.userType = userType;
    }

    public UUID getUUID() {
        return userUUID;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the to-do items of the user.
     * @return A reference to the list of to-do items.
     */
    public ArrayList<CalendarItem> getCalendarItems() {
        //allow for multiple peoples, i.e. boss setting employee and therfore not resetting variable when log off, assign caladnerItems a user aswell??
        return calendarItems;
    }

    // Function to check if a number exists in an ArrayList
    public boolean ifCalendarItemExists (int targetIndex){   //(User user, int targetIndex) {
        // Get the calendar items of the user
        ArrayList<CalendarItem> calendarItems = getCalendarItems();

        // Check if the index is within the range of the list size
        return targetIndex >= 0 && targetIndex < calendarItems.size();
    }
}
