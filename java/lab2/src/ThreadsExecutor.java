import java.util.concurrent.CountDownLatch;

class ThreadsExecutor {
    private final int[] array;
    private final int split;
    public ThreadsExecutor(int[] array, int split) {
        this.array = array;
        this.split = Math.min(split, array.length);
    }
    private Number getMinInBorders(int[] array, int leftBorder, int rightBorder){
        int min = array[leftBorder], index = leftBorder;
        for(int i = leftBorder + 1; i <= rightBorder; i++){
            if(min > array[i]){
                min = array[i];
                index = i;
            }
        }
        return new Number(min, index);
    }
    private Number[] getMinNumbers(){
        int aLen = array.length, subArrLen = aLen / split;

        if(split <= 1){
            Number minNumber = getMinInBorders(array, 0, array.length - 1);
            return new Number[] { minNumber };
        }

        Number[] minNumbers = new Number[split];
        CountDownLatch countDownLatch = new CountDownLatch(split);

        for (int i = 0; i < split; i++) {
            int copyOfI = i;
            int left = i * subArrLen,
                right = (i == split - 1) ? aLen - 1 : (left + subArrLen - 1);

            new Thread(() -> {
                Number minNumberInSubArr = getMinInBorders(this.array, left, right);
                minNumbers[copyOfI] = minNumberInSubArr;
                countDownLatch.countDown();
            }).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return minNumbers;
    }
    public void execute(){
        Number[] minNumbers = getMinNumbers();
        System.out.println("Print all min numbers: ");

        for(Number num : minNumbers)
            System.out.println("Number: " + num.getNumber() + " Index: " + num.getIndex()) ;
        System.out.println();

        int[] minIntNumbers = new int[minNumbers.length];
        for (int i = 0; i < minNumbers.length; i++) {
            minIntNumbers[i] = minNumbers[i].getNumber();
        }

        Number min = getMinInBorders(minIntNumbers, 0, minNumbers.length - 1);
        System.out.println("Min: " + min.getIndex() + " Index: " + min.getIndex());
    }
}
