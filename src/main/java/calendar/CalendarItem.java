package calendar;

public class CalendarItem {
    private String description;
    private boolean isDone;

    // TODO Now: Add a constructor to initialize the item with the description,
    // and isDone as false, with a single parameter for the description
    public CalendarItem(String description) {
        this.description = description;
        this.isDone = false;
    }

    // TODO Now: Add getters and setters for each field
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
