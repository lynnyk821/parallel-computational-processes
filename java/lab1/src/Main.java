public class Main {
    public static void main(String[] args) {
        ThreadBreaker threadBreaker = new ThreadBreaker();

        ThreadsExecutor threadsExecutor = new ThreadsExecutor(5, 5, threadBreaker);
        threadsExecutor.execute();

        threadBreaker.breakAfter(2);
    }
}