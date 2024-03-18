import java.util.Random;

class IntArrayGenerator {
    private final int size;
    private final int[] array;
    public IntArrayGenerator(int size){
        this.size = size;
        this.array = new int[size];
    }
    public void fillArray(){
        int randomIndex = new Random().nextInt(0, size);
        for(int i = 0; i < this.size; i++){
            this.array[i] = i;
            if(randomIndex == i) this.array[i] = -1;
        }
    }
    public int[] getArray() {
        return this.array;
    }
}
