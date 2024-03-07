import java.time.Duration;

public class ThreadBreaker {
    private volatile boolean canBreak;
    public void breakAfter(int seconds){
        try {
            Thread.sleep(Duration.ofSeconds(seconds));
            canBreak = true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isCanBreak(){
        return canBreak;
    }
}
