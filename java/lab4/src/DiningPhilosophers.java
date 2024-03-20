import java.util.Arrays;

public class DiningPhilosophers {
    private final Fork[] forks;
    private final Philosopher[] philosophers;
    public DiningPhilosophers(int num) {
        this.philosophers = new Philosopher[num];
        this.forks = new Fork[num];
        Arrays.setAll(this.forks, element -> new Fork());
    }
    public void execute(){
        for(int i = 0; i < this.philosophers.length; i++){
            if(i == this.philosophers.length - 1){
                philosophers[i] = new Philosopher(i, forks[(i + 1) % this.forks.length], forks[i]);
            } else {
                philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % this.forks.length]);
            }
            new Thread(philosophers[i]::philosopherTask, "Philosopher " + (i + 1)).start();
        }
    }
}
