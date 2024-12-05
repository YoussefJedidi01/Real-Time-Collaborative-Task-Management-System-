import java.rmi.Naming;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Connect to the RMI server
            TaskManagerInterface server = (TaskManagerInterface) Naming.lookup("rmi://localhost:5000/TaskManager");
            Scanner scanner = new Scanner(System.in);

            // User authentication
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            String loginResponse = server.login(username, password);
            if (!loginResponse.startsWith("Login successful")) {
                System.out.println(loginResponse);
                return;
            }
            System.out.println("Welcome, " + username + "!");

            // Main menu
            while (true) {
                System.out.println("\n1. View tasks");
                System.out.println("2. Add task");
                System.out.println("3. Update task");
                System.out.println("4. Delete task");
                System.out.println("5. Search tasks");
                System.out.println("6. Export tasks");
                System.out.println("7. Get statistics");
                System.out.println("8. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1: // View tasks
                        List<Task> tasks = server.getTasks(username);
                        if (tasks.isEmpty()) {
                            System.out.println("No tasks available.");
                        } else {
                            for (Task task : tasks) {
                                System.out.println(task);
                            }
                        }
                        break;

                    case 2: // Add task
                        System.out.print("Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Description: ");
                        String description = scanner.nextLine();
                        System.out.print("Priority: ");
                        String priority = scanner.nextLine();

                        // Validate status
                        String status;
                        do {
                            System.out.print("Status (finished, pending, starting): ");
                            status = scanner.nextLine();
                            if (!status.equals("finished") && !status.equals("pending") && !status.equals("starting")) {
                                System.out.println("Invalid status! Please enter one of: finished, pending, starting.");
                            }
                        } while (!status.equals("finished") && !status.equals("pending") && !status.equals("starting"));

                        // Validate assignedTo
                        String assignedTo;
                        do {
                            System.out.print("Assigned to (leave empty to assign to yourself): ");
                            assignedTo = scanner.nextLine();
                            if (assignedTo.isEmpty()) {
                                assignedTo = username; // Auto-assign to the user
                            } else if (!assignedTo.matches("user\\d+")) {
                                System.out.println("Invalid assignedTo format! Please use 'user1', 'user2', etc.");
                                assignedTo = null; // Reset for validation loop
                            }
                        } while (assignedTo == null);

                        System.out.println(server.addTask(title, description, priority, status, username, assignedTo));
                        break;

                    case 3: // Update task
                        System.out.print("Task ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("New title: ");
                        String newTitle = scanner.nextLine();
                        System.out.print("New description: ");
                        String newDescription = scanner.nextLine();
                        System.out.print("New priority: ");
                        String newPriority = scanner.nextLine();

                        // Validate status for update
                        String newStatus;
                        do {
                            System.out.print("New status (finished, pending, starting): ");
                            newStatus = scanner.nextLine();
                            if (!newStatus.equals("finished") && !newStatus.equals("pending") && !newStatus.equals("starting")) {
                                System.out.println("Invalid status! Please enter one of: finished, pending, starting.");
                            }
                        } while (!newStatus.equals("finished") && !newStatus.equals("pending") && !newStatus.equals("starting"));

                        // Validate assignedTo for update
                        String newAssignedTo;
                        do {
                            System.out.print("New assigned to (leave empty to assign to yourself): ");
                            newAssignedTo = scanner.nextLine();
                            if (newAssignedTo.isEmpty()) {
                                newAssignedTo = username; // Auto-assign to the user
                            } else if (!newAssignedTo.matches("user\\d+")) {
                                System.out.println("Invalid assignedTo format! Please use 'user1', 'user2', etc.");
                                newAssignedTo = null; // Reset for validation loop
                            }
                        } while (newAssignedTo == null);

                        System.out.println(server.updateTask(updateId, newTitle, newDescription, newPriority, newStatus, username, newAssignedTo));
                        break;

                    case 4: // Delete task
                        System.out.print("Task ID to delete: ");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.println(server.deleteTask(deleteId, username));
                        break;

                    case 5: // Search tasks
                        System.out.print("Search keyword: ");
                        String keyword = scanner.nextLine();
                        System.out.print("Filter by (title, assignedTo, status): ");
                        String filterBy = scanner.nextLine();
                        List<Task> searchResults = server.searchTasks(keyword, filterBy, username);
                        if (searchResults.isEmpty()) {
                            System.out.println("No tasks found.");
                        } else {
                            for (Task task : searchResults) {
                                System.out.println(task);
                            }
                        }
                        break;

                    case 6: // Export tasks
                        System.out.println(server.exportTasks(username));
                        break;

                    case 7: // Get statistics
                        System.out.println(server.getStatistics(username));
                        break;

                    case 8: // Exit
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

