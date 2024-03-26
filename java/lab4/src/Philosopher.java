public class Philosopher {
    private final int id;
    private final Fork left, right;
    public Philosopher(int id, Fork left, Fork right) {
        this.id = id;
        this.left = left;
        this.right = right;
    }
    private void doAction(String action) {
        try {
            System.out.println(Thread.currentThread().getName() + " " + action);
            Thread.sleep(((int) (Math.random() * 100)));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void philosopherTask(){
        doAction("Thinking");

        left.pickUpFork();
        doAction("Picked up left fork");

        right.pickUpFork();
        doAction("Picked up right fork - eating");

        left.putDownFork();
        doAction("Put down right fork");

        right.putDownFork();
        doAction("Put down right fork. Back to thinking");
    }
}
