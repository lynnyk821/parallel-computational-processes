public class Consumer {
    private final int n;
    private final Storage storage;
    public Consumer(Storage storage, int n) {
        this.n = n;
        this.storage = storage;
    }
    public void start(){
        new Thread(() -> {
            for(int i = 0; i < n; i++){
                try {
                    Thread.sleep(1000);
                    storage.poll();
                    System.out.println("Consumer: " + Thread.currentThread().getName() + " polled item" );
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
