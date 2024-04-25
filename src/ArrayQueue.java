import java.util.Arrays;
import java.util.NoSuchElementException;
import java.lang.IllegalArgumentException;

/**
 * Your implementation of an ArrayQueue.
 */
public class ArrayQueue<T> {

    /*
     * The initial capacity of the ArrayQueue.
     *
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 9;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * This is the constructor that constructs a new ArrayQueue.
     * Recall that Java does not allow for regular generic array creation,
     * so instead we cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayQueue() {
        // DO NOT MODIFY THIS METHOD!
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    private T[] resizeArray(){
//        System.out.println("Array before resize:");
//        System.out.println(Arrays.toString(backingArray));
        T[] newArray = (T[]) new Object[this.backingArray.length * 2];
        for (int i=0; i<size; i++){
//            System.out.println("Index: " + useIndex);
            newArray[i] = backingArray[(front + i) % backingArray.length];
        }
//        System.out.println("Array after resize:");
//        System.out.println(Arrays.toString(newArray));
        return newArray;
    }

    private void checkNotNull(T data){
        if (data == null) {
            throw new IllegalArgumentException();
        }
    }
    /**
     * Adds the data to the back of the queue.
     * If sufficient space is not available in the backing array, resize it to
     * double the current length. When resizing, copy elements to the
     * beginning of the new array and reset front to 0.
     * Method should run in amortized O(1) time.
     *
     * @param data the data to add to the back of the queue
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        checkNotNull(data);
        if (this.size == this.backingArray.length) {
            this.backingArray = this.resizeArray();
            this.front = 0;
        }
        this.backingArray[(front + size) % backingArray.length] = data;
        this.size += 1;
    }

    private void checkRemovePossible(){
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
    }
    /**
     * Removes and returns the data from the front of the queue.
     * Do not shrink the backing array.
     * Replace any spots that you dequeue from with null.
     * If the queue becomes empty as a result of this call, do not reset
     * front to 0.
     * Method should run in O(1) time.
     *
     * @return the data formerly located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        checkRemovePossible();
        T result = this.backingArray[this.front];
        this.backingArray[this.front] = null;
        this.front = (this.front + 1) % this.backingArray.length;
        this.size -= 1;
        return result;
    }

    /**
     * Returns the backing array of the queue.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the queue
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the queue.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the queue
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}

