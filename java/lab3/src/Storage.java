import java.util.LinkedList;
import java.util.Queue;

public class Storage {
    private final int maxSize;
    private final Queue<String> storage = new LinkedList<>();
    public Storage(int size) {
        this.maxSize = size;
    }
    public synchronized void add(String data) {
        while (storage.size() >= maxSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
        storage.add(data);
        notifyAll();
    }
    public synchronized String poll() {
        while (storage.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
        String data = storage.poll();
        notifyAll();

        return data;
    }
}