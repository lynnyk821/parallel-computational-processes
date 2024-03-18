class Consumer {
    private final int n;
    private final Storage storage;
    public Consumer(Storage storage, int n) {
        this.n = n;
        this.storage = storage;
    }
    private void consumeTask(){
        for (int i = 0; i < n; i++) {
            try {
                Thread.sleep(1000);
                storage.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void run() {
        new Thread(this::consumeTask).start();
    }
}