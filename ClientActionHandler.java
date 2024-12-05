public class ClientActionHandler<T> extends Thread {
    private final TaskCallable<T> task; // Task to execute
    private T result; // Result of the task

    public ClientActionHandler(TaskCallable<T> task) {
        this.task = task;
    }

    @Override
    public void run() {
        result = task.call(); // Execute the task
    }

    public T getResult() {
        return result; // Retrieve the result
    }
}

@FunctionalInterface
interface TaskCallable<T> {
    T call(); // Functional interface for callable tasks
}

