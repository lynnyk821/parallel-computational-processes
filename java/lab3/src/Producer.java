public class Producer {
    private final int n;
    private final Storage storage;
    public Producer(Storage storage, int n) {
        this.n = n;
        this.storage = storage;
    }
    public void start(){
        new Thread(() -> {
            for(int i = 0; i < n; i++){
                try {
                    Thread.sleep(1000);
                    storage.add("item");
                    System.out.println("Producer: " + Thread.currentThread().getName() + " added item");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
