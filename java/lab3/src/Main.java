public class Main {
    public static void main(String[] args) {
        Storage storage = new Storage(10);

        for (int i = 0; i < 2; i++) {
            new Producer(storage, 10).run();
        }
        for (int i = 0; i < 4; i++) {
            new Consumer(storage, 5).run();
        }
    }
}