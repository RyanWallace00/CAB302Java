package calendar;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CalendarItem {
    private UUID ID;
    private String title;
    private String description;
    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;
    private boolean isDone;
    private List<UUID> userIDs; // Array to store associated user UUIDs

    // TODO Now: Add a constructor to initialize the item with the description,
    // and isDone as false, with a single parameter for the description
    public CalendarItem(String title, String description, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, UUID userId) {
        this.ID = UUID.randomUUID(); //ID;
        this.title = title;
        this.description = description;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
        this.isDone = false;
        this.userIDs.add(userId);
    }

    // TODO Now: Add getters and setters for each field
    public UUID getID() {
        return ID;
    }

//    public void setID(UUID ID) {
//        this.ID = ID;
//    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(LocalDateTime dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }
    public LocalDateTime getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeTo(LocalDateTime dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
