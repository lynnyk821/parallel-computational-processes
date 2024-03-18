public class Main {
    public static void main(String[] args) {
        Storage storage = new Storage(100);

        for(int i = 0; i < 2; i++){
            Producer producer = new Producer(storage, 2);
            producer.start();
        }
    }
}