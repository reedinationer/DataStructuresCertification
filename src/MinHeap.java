import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

     /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * This is the constructor that constructs a new MinHeap.
     *
     * Recall that Java does not allow for regular generic array creation,
     * so instead we cast a Comparable[] to a T[] to get the generic typing.
     */
    public MinHeap() {
        //DO NOT MODIFY THIS METHOD!
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    private int upHeap(int currentIndex){
        // Return the current index if no swaps are made
        if (currentIndex == 1){
            return currentIndex; // We made it to root node so stop up heaping
        }
        int parentIndex = currentIndex / 2;
        if (this.backingArray[currentIndex].compareTo(this.backingArray[parentIndex]) <= 0){
            T valueToUpHeap = this.backingArray[currentIndex]; // Store value
            this.backingArray[currentIndex] = this.backingArray[parentIndex];
            this.backingArray[parentIndex] = valueToUpHeap;
            return parentIndex;
        }
        return currentIndex;
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * Method should run in amortized O(log n) time.
     *
     * @param data The data to add.
     * @throws java.lang.IllegalArgumentException If the data is null.
     */
    public void add(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (data == null){
            throw new IllegalArgumentException();
        }
        if (size + 1 == backingArray.length){ // Resize is needed
            T[] newArray = (T[]) new Comparable[backingArray.length * 2];
            for (int i=0; i<backingArray.length; i++){
                newArray[i] = backingArray[i]; // Copy all the items to the new array
            }
            this.backingArray = newArray;
        }
        this.size += 1;
        this.backingArray[size] = data;
        // Now upheap the value as far as possible
        int process_index = this.size;
        int nextIndex = 0;
        while (true){
            nextIndex = this.upHeap(process_index);
            if (nextIndex == process_index){
                break;
            }
            process_index = nextIndex; // Change iteration next go around
        }
    }

    private int downheap(int currentIndex){
        // Return the current index if no swaps are made
        int leftIndex = currentIndex * 2;
        int rightIndex = currentIndex * 2 + 1;
        if (rightIndex <= this.size && this.backingArray[rightIndex] != null){
            // Two child instance because tree is complete
            if (this.backingArray[leftIndex].compareTo(this.backingArray[rightIndex]) <= 0){ // Check which child is larger
                if (this.backingArray[leftIndex].compareTo(this.backingArray[currentIndex]) <= 0){ // Do we swap this smaller child
                    T valueToDownHeap = this.backingArray[currentIndex]; // Store value
                    this.backingArray[currentIndex] = this.backingArray[leftIndex];
                    this.backingArray[leftIndex] = valueToDownHeap;
                    return leftIndex;
                } else {
                    return currentIndex;
                }
            } else {
                if (this.backingArray[rightIndex].compareTo(this.backingArray[currentIndex]) <= 0){ // Do we swap this smaller child?
                    T valueToDownHeap = this.backingArray[currentIndex]; // Store value
                    this.backingArray[currentIndex] = this.backingArray[rightIndex];
                    this.backingArray[rightIndex] = valueToDownHeap;
                    return rightIndex;
                } else {
                    return currentIndex;
                }
            }
        } else if (leftIndex <= this.size && this.backingArray[leftIndex] != null){
            // One child instance
            if (this.backingArray[leftIndex].compareTo(this.backingArray[currentIndex]) <= 0){ // The child is lower, so we need to swap
                T valueToDownHeap = this.backingArray[currentIndex]; // Store value
                this.backingArray[currentIndex] = this.backingArray[leftIndex];
                this.backingArray[leftIndex] = valueToDownHeap;
                return leftIndex;
            }
        }
        return currentIndex; // Otherwise there are no children, so we do nothing
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * Method should run in O(log n) time.
     *
     * @return The data that was removed.
     * @throws java.util.NoSuchElementException If the heap is empty.
     */
    public T remove() {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (this.size == 0){
            throw new NoSuchElementException();
        }
        T returnVal = this.backingArray[1]; // Save minimum value for the end of the function call
        this.backingArray[1] = this.backingArray[this.size]; // Copy last value to the head node
        this.backingArray[this.size] = null;
        this.size -= 1; // update the size so the downheap works correctly
        // Now downheap the value in the head node
        int process_index = 1;
        int nextIndex = 0;
        while (true){
            nextIndex = this.downheap(process_index);
            if (nextIndex == process_index){
                break;
            }
            process_index = nextIndex; // Change iteration next go around
        }
        return returnVal;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
    public static void main(String[] args){
        MinHeap<Integer> myHeap = new MinHeap<>();
        // [null, 0, 7, 14, 21, 28, 35, 42, 49, 56, 63, 70, 77, 84, 91, 98, 105, 112, 119, 126, 133, 140, 147, 154, 161, 168]
        myHeap.add(0);
        myHeap.add(7);
        myHeap.add(14);
        myHeap.add(21);
        myHeap.add(28);
        myHeap.add(35);
        myHeap.add(42);
        myHeap.add(49);
        myHeap.add(56);
        myHeap.add(63);
        myHeap.add(70);
        myHeap.add(77);
        myHeap.add(84);
        myHeap.add(91);
        myHeap.add(98);
        myHeap.add(105);
        myHeap.add(112);
        myHeap.add(119);
        myHeap.add(126);
        myHeap.add(133);
        myHeap.add(140);
        myHeap.add(147);
        myHeap.add(154);
        myHeap.add(161);
        myHeap.add(168);
        System.out.println(Arrays.toString(myHeap.getBackingArray()));
//        myHeap.add(62);
//        myHeap.add(45);
//        myHeap.add(99);
//        myHeap.add(85);
        myHeap.add(175);
        System.out.println(Arrays.toString(myHeap.getBackingArray()));
        System.out.println("[null, 0, 7, 14, 21, 28, 35, 42, 49, 56, 63, 70, 77, 84, 91, 98, 105, 112, 119, 126, 133, 140, 147, 154, 161, 168, 175, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null] <- expected");
    }
}