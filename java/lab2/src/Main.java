public class Main {
    private static void printIntArray(int[] array){
        System.out.println("Generated array: ");
        for (int num : array)
            System.out.print(num + " ");
        System.out.println("\n");
    }
    public static void main(String[] args) {
        IntArrayGenerator arrayGenerator = new IntArrayGenerator(10);
        arrayGenerator.fillArray();

        int[] array = arrayGenerator.getArray();
        printIntArray(array);

        ThreadsExecutor threadsExecutor = new ThreadsExecutor(array, 2);
        threadsExecutor.execute();
    }
}