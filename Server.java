import java.rmi.Naming;

public class Server {
    public static void main(String[] args) {
        try {
            // Start the RMI registry on port 5000
            java.rmi.registry.LocateRegistry.createRegistry(5000);
            
            // Create an instance of the TaskManager implementation
            TaskManagerInterface server = new TaskManagerImplement();
            
            // Bind the TaskManager implementation to the RMI registry
            Naming.rebind("rmi://localhost:5000/TaskManager", server);
            
            System.out.println("Task Manager Server started...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

