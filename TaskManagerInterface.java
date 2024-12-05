import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface TaskManagerInterface extends Remote {
    String login(String username, String password) throws RemoteException;
    List<Task> getTasks(String username) throws RemoteException;
    String addTask(String title, String description, String priority, String status, String userId, String assignedTo) throws RemoteException;
    String updateTask(int id, String title, String description, String priority, String status, String userId, String assignedTo) throws RemoteException;
    String deleteTask(int id, String userId) throws RemoteException;
    List<Task> searchTasks(String keyword, String filterBy, String userId) throws RemoteException;
    String exportTasks(String userId) throws RemoteException;
    String getStatistics(String userId) throws RemoteException;
}

