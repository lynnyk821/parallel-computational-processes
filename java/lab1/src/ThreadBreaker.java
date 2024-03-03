import java.time.Duration;

public class ThreadBreaker {
    private boolean canBreak;
    public void breakAfter(int seconds){
        try {
            Thread.sleep(Duration.ofSeconds(seconds));
            canBreak = true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public synchronized boolean isCanBreak(){
        return canBreak;
    }
}
