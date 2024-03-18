import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Storage {
    private final Semaphore full, empty;
    private final Lock lock = new ReentrantLock();
    private final Queue<String> storage = new LinkedList<>();
    public Storage(int storageSize) {
        full = new Semaphore(storageSize); // типу щоб продюсери не вилазили за макс кількість
        empty = new Semaphore(0); // типу wait для consume, якщо consumer захоче взяти з пустої черги item, то буде заблочений
    }
    public void consume(){
        try {
            empty.acquire();
            lock.lock();

            String item = storage.poll();
            System.out.println("Consumer: " + Thread.currentThread().getName() + " took item: " + item);

            lock.unlock();
            full.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void produce(int i){
        try {
            full.acquire();
            lock.lock();

            storage.add("Item: " + i);
            System.out.println("Producer: " + Thread.currentThread().getName() + " added item: " + i);

            lock.unlock();
            empty.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}