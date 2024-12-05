import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManagerImplement extends UnicastRemoteObject implements TaskManagerInterface {
    private final List<Task> tasks;
    private final Map<String, User> users;
    private int nextId;

    public TaskManagerImplement() throws RemoteException {
        tasks = new ArrayList<>();
        users = new HashMap<>();
        nextId = 1;

        // Adding default users
        users.put("admin", new User("admin", "admin123"));
        users.put("user1", new User("user1", "password1"));
        users.put("user2", new User("user2", "password2"));
    }

    @Override
    public String login(String username, String password) throws RemoteException {
        User user = users.get(username);
        if (user != null && user.authenticate(password)) {
            return "Login successful. Welcome " + username + "!";
        }
        return "Login failed. Invalid username or password.";
    }

    @Override
    public List<Task> getTasks(String username) throws RemoteException {
        synchronized (tasks) {
            if ("admin".equals(username)) {
                return new ArrayList<>(tasks); // Admin can see all tasks
            }
            List<Task> result = new ArrayList<>();
            for (Task task : tasks) {
                if (task.getAssignedTo().equals(username)) {
                    result.add(task);
                }
            }
            return result;
        }
    }

    @Override
    public String addTask(String title, String description, String priority, String status, String userId, String assignedTo) throws RemoteException {
        synchronized (tasks) {
            // Auto-assign to self if assignedTo is empty
            if (assignedTo == null || assignedTo.isEmpty()) {
                assignedTo = userId;
            }

            // Validate inputs
            if (!assignedTo.matches("user\\d+")) {
                return "Invalid assignedTo format. Please use 'user1', 'user2', etc.";
            }
            if (!status.equals("finished") && !status.equals("pending") && !status.equals("starting")) {
                return "Invalid status. Status must be one of: finished, pending, starting.";
            }

            Task task = new Task(nextId++, title, description, priority, status, userId, LocalDateTime.now(), assignedTo);
            tasks.add(task);
            return "Task added: " + task;
        }
    }

    @Override
    public String updateTask(int id, String title, String description, String priority, String status, String userId, String assignedTo) throws RemoteException {
        synchronized (tasks) {
            // Validate inputs for status and assignedTo
            if (!status.equals("finished") && !status.equals("pending") && !status.equals("starting")) {
                return "Invalid status. Status must be one of: finished, pending, starting.";
            }
            if (!assignedTo.matches("user\\d+")) {
                return "Invalid assignedTo format. Please use 'user1', 'user2', etc.";
            }

            for (Task task : tasks) {
                if (task.getId() == id) {
                    if (!"admin".equals(userId) && !task.getAssignedTo().equals(userId)) {
                        return "Permission denied. You can only update tasks assigned to you.";
                    }
                    task.setTitle(title);
                    task.setDescription(description);
                    task.setPriority(priority);
                    task.setStatus(status);
                    task.setAssignedTo(assignedTo.isEmpty() ? userId : assignedTo);
                    task.setTimestamp(LocalDateTime.now());
                    return "Task updated: " + task;
                }
            }
            return "Task not found.";
        }
    }

    @Override
    public String deleteTask(int id, String userId) throws RemoteException {
        synchronized (tasks) {
            boolean removed = tasks.removeIf(task -> task.getId() == id && ("admin".equals(userId) || task.getAssignedTo().equals(userId)));
            return removed ? "Task deleted with ID: " + id : "Task not found.";
        }
    }

    @Override
    public List<Task> searchTasks(String keyword, String filterBy, String userId) throws RemoteException {
        synchronized (tasks) {
            List<Task> result = new ArrayList<>();
            tasks.stream()
                .filter(task -> "admin".equals(userId) || task.getAssignedTo().equals(userId))
                .filter(task -> {
                    if ("title".equalsIgnoreCase(filterBy)) return task.getTitle().contains(keyword);
                    if ("assignedTo".equalsIgnoreCase(filterBy)) return task.getAssignedTo().equals(keyword);
                    if ("status".equalsIgnoreCase(filterBy)) return task.getStatus().equalsIgnoreCase(keyword);
                    return false;
                })
                .forEach(result::add);
            return result;
        }
    }

    @Override
    public String exportTasks(String userId) throws RemoteException {
        synchronized (tasks) {
            StringBuilder sb = new StringBuilder();
            sb.append("ID,Title,Description,Priority,Status,UserID,Timestamp,AssignedTo\n");
            tasks.stream()
                .filter(task -> "admin".equals(userId) || task.getAssignedTo().equals(userId))
                .forEach(task -> sb.append(task.getId()).append(",")
                        .append(task.getTitle()).append(",")
                        .append(task.getDescription()).append(",")
                        .append(task.getPriority()).append(",")
                        .append(task.getStatus()).append(",")
                        .append(task.getUserId()).append(",")
                        .append(task.getTimestamp()).append(",")
                        .append(task.getAssignedTo()).append("\n"));
            return sb.toString();
        }
    }

    @Override
    public String getStatistics(String userId) throws RemoteException {
        synchronized (tasks) {
            long pending = tasks.stream().filter(task -> task.getStatus().equalsIgnoreCase("pending") && ("admin".equals(userId) || task.getAssignedTo().equals(userId))).count();
            long starting = tasks.stream().filter(task -> task.getStatus().equalsIgnoreCase("starting") && ("admin".equals(userId) || task.getAssignedTo().equals(userId))).count();
            long finished = tasks.stream().filter(task -> task.getStatus().equalsIgnoreCase("finished") && ("admin".equals(userId) || task.getAssignedTo().equals(userId))).count();
            return "Total tasks: " + tasks.size() + ", Pending: " + pending + ", Starting: " + starting + ", Finished: " + finished;
        }
    }
}

