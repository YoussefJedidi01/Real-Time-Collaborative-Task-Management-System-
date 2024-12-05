import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task implements Serializable {
    private int id;
    private String title;
    private String description;
    private String priority;
    private String status;
    private String userId;
    private LocalDateTime timestamp;
    private String assignedTo;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy---HH:mm:ss");

    public Task(int id, String title, String description, String priority, String status, String userId, LocalDateTime timestamp, String assignedTo) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.userId = userId;
        this.timestamp = timestamp;
        this.assignedTo = assignedTo;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public String getStatus() { return status; }
    public String getUserId() { return userId; }
    public String getTimestamp() { return timestamp.format(formatter); }
    public String getAssignedTo() { return assignedTo; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setPriority(String priority) { this.priority = priority; }
    public void setStatus(String status) { this.status = status; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }

    @Override
    public String toString() {
        return "Task [ID=" + id + ", Title=" + title + ", Priority=" + priority + ", Status=" + status +
               ", UserID=" + userId + ", Timestamp=" + getTimestamp() + ", AssignedTo=" + assignedTo + "]";
    }
}

