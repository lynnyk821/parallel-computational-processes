public class ThreadsExecutor {
    private final Thread[] threads;
    private final Integer incrementStep;
    private final ThreadBreaker threadBreaker;
    public ThreadsExecutor(int numberOfThreads, Integer incrementStep, ThreadBreaker threadBreaker) {
        this.threads = new Thread[numberOfThreads];
        this.incrementStep = incrementStep;
        this.threadBreaker = threadBreaker;
    }
    private void printThreadInformation(String threadName, long sum){
        System.out.println("Thread " + threadName + ": Sum = " + sum);
    }
    public void expectBreak() {
        long sum = 0;
        while (!threadBreaker.isCanBreak()) {
            sum += incrementStep;
        }
        printThreadInformation(Thread.currentThread().getName(), sum);
    }
    public void execute(){
        for(int i = 0; i < threads.length; i++){
            (threads[i] = new Thread(() -> expectBreak())).start();
        }
    }
}
