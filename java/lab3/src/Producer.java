import java.util.Random;

class Producer {
    private final int n;
    private final Storage storage;
    public Producer(Storage storage, int n) {
        this.n = n;
        this.storage = storage;
    }
    private void produceTask(){
        for (int i = 0; i < n; i++) {
            try {
                Thread.sleep(1000);
                storage.produce(new Random().nextInt(0, 100));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void run() {
        new Thread(this::produceTask).start();
    }
}