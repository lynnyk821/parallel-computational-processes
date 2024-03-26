import java.util.concurrent.locks.ReentrantLock;

public class Fork {
    private final ReentrantLock lock = new ReentrantLock();
    public void pickUpFork() { lock.lock(); }
    public void putDownFork() { lock.unlock(); }
}