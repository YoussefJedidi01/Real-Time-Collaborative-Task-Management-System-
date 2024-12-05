# Real-Time Collaborative Task Management System

A **Real-Time Collaborative Task Management System** that allows users to manage and track tasks collaboratively in real time using Java RMI (Remote Method Invocation). The system enables users to create, update, assign, and delete tasks while interacting with others simultaneously. This project supports both admins and regular users with different permissions.

## Features
- **Task Creation**: Users can create tasks with a title, description, priority, status, and assignment.
- **Task Assignment**: Tasks can be assigned to a specific user (e.g., `user1`, `user2`), or to the user creating the task by default.
- **Task Update**: Users can update task details (title, description, status, assignment) but can only update their own tasks.
- **Task Deletion**: Users can delete their own tasks, while admins can delete any task.
- **Task Viewing**: Users can view only the tasks assigned to them, while admins can view all tasks.
- **Search and Filter**: Search for tasks by title, status, or assigned user.
- **Statistics**: Get task statistics, such as the count of pending, starting, and finished tasks assigned to the user.
- **Task Export**: Export tasks in CSV format, showing relevant task information.
  
## Technologies
- **Java**: Programming language used for backend implementation.
- **RMI (Remote Method Invocation)**: For communication between client and server over a network.
- **Multithreading**: Each client action (e.g., add, update, delete task) runs in its own thread on the server to ensure parallel processing. **Threads are synchronized** to ensure that data integrity is maintained when multiple clients interact with the system concurrently.

## Project Structure
- **Client.java**: The client-side application where users interact with the system, performing tasks like viewing, adding, and updating tasks.
- **Server.java**: The server-side application that handles requests from clients and manages task data.
- **TaskManagerInterface.java**: The interface that defines the methods for task management (e.g., addTask, updateTask, getTasks).
- **TaskManagerImplement.java**: The implementation of the `TaskManagerInterface` where the task management logic is executed.
- **Task.java**: The model class representing a task with details like ID, title, description, priority, status, and assignment.
- **User.java**: The model class representing a user with username and password for authentication.
- **ClientActionHandler.java**: This class is responsible for handling client actions (such as adding, updating, or deleting tasks) in separate threads. It ensures that each client request is processed independently, improving the overall performance of the system by allowing concurrent operations.

## Installation
1. **Clone the repository:**

   ```bash
   git clone https://github.com/YoussefJedidi01/Real-Time-Collaborative-Task-Management-System-
   cd task-management-system
2. **Compile the Java files:**
javac *.java
3. **Start the server:**
java Server.java
4. **Run the client:**
java Client.java
"Here, you can run multiple clients with multiple actions simultaneously."

## Usage
1. **Login:**

  The client will prompt you for a username and password. Default users include:
    - Username: admin, Password: admin123
    - Username: user1, Password: password1
    - Username: user2, Password: password2

2. **Main Menu Options:**

    View tasks: View tasks assigned to the user.
    Add a new task: Add a new task (ensure to follow the format for assignedTo).
    Update an existing task: Update a task's details (only tasks assigned to you can be updated).
    Delete a task: Delete a task (can only delete tasks assigned to you unless you are an admin).
    Search tasks: Search tasks by keyword or filter (e.g., title, status).
    Export tasks: Export tasks in CSV format.
    Get task statistics: Get statistics of tasks assigned to the user (e.g., pending, finished, etc.).

## Contributing
Contributions are welcome! If you'd like to improve this project, feel free to fork the repository, make your changes, and create a pull request.

## Steps to Contribute:
1. Fork the repository.  
2. Create a new branch for your changes.  
3. Make your changes and commit them.  
4. Push your branch to your forked repository.  
5. Open a pull request to merge your changes into the main repository.

## License
This project is open source and available under the MIT License.
