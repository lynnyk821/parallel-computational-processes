public class ThreadsExecutor {
    private final Thread[] threads;
    private final Integer incrementStep;
    private final ThreadBreaker threadBreaker;
    public ThreadsExecutor(int numberOfThreads, Integer incrementStep, ThreadBreaker threadBreaker) {
        this.threads = new Thread[numberOfThreads];
        this.incrementStep = incrementStep;
        this.threadBreaker = threadBreaker;
    }
    private void printThreadInformation(String threadName, long sum, long steps){
        String id = threadName.replace("Thread-", "");
        System.out.println("Id: " + id + ": Sum: " + sum + " Steps: " + steps);
    }
    public void expectBreak() {
        long sum = 0, steps = 0;
        while (!threadBreaker.isCanBreak()) {
            sum += incrementStep;
            steps += 1;
        }
        printThreadInformation(Thread.currentThread().getName(), sum, steps);
    }
    public void execute(){
        for(int i = 0; i < threads.length; i++){
            (threads[i] = new Thread(() -> expectBreak())).start();
        }
    }
}
