import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;

/**
 * My implementation of an ArrayList.
 */
public class ArrayList<T> {
    /*
     * The initial capacity of the ArrayList.
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;
    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * This is the constructor that constructs a new ArrayList.
     * Recall that Java does not allow for regular generic array creation,
     * so instead we cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        //DO NOT MODIFY THIS METHOD!
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    private T[] resize() {
        // Resize array to twice as large. Copy all existing data to the newly made array.
        this.size = this.size * 2;
        T[] newArray;
        newArray = (T[]) new Object[this.size];
        for (int i=0; i<backingArray.length; i++){
            newArray[i] = backingArray[i];
        }
        return newArray;
    }

    private void checkNull(T data){
        // Call this method every time data is entered to ensure it is not null values.
        if (data == null) {
            throw new IllegalArgumentException("Tried to add null data");
        }
    }

    private void checkRemovalCanHappen(){
        // Method to check that the array is not empty, and we can in fact remove items from it.
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
    }

    /**
     * Adds the data to the front of the list.
     * This add may require elements to be shifted.
     * Method should run in O(n) time.
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        checkNull(data);
        if (size == this.backingArray.length) {
            this.backingArray = this.resize(); // Resize array before adding to front
        }
        // Now shift all items
        for (int i=this.size - 1; i>=1; i--){
            this.backingArray[i] = this.backingArray[i-1]; // TODO: Fix an out of bounds error
        }
        this.backingArray[0] = data;
        this.size += 1;
    }

    /**
     * Adds the data to the back of the list.
     * Method should run in amortized O(1) time.
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        checkNull(data);
        int useIndex = size; // Save value in case array gets resized
        if (size == this.backingArray.length) {
            this.backingArray = this.resize(); // Resize array before adding to front
        }
        this.backingArray[useIndex] = data;
        this.size += 1;
    }

    /**
     * Removes and returns the first data of the list.
     * Do not shrink the backing array.
     * This remove may require elements to be shifted.
     * Method should run in O(n) time.
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        checkRemovalCanHappen();
        T result = this.backingArray[0]; // Save value for return
        for (int i=0; i<=this.size - 2; i++){
            this.backingArray[i] = this.backingArray[i+1]; // TODO: Fix out of bounds error
        }
        this.backingArray[this.size - 1] = null;
        this.size -= 1;
        return result;
    }

    /**
     * Removes and returns the last data of the list.
     * Do not shrink the backing array.
     * Method should run in O(1) time.
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        checkRemovalCanHappen();
        T result = this.backingArray[this.size - 1];
        this.backingArray[this.size - 1] = null;
        return result;
    }

    /**
     * Returns the backing array of the list.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the list.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    public static void main(String[] args) {
        args = new String[2];
        args[0] = "Hello";
        args[1] = "World";
        Main.main(args);
    }
}


class Main{
    public static void main(String[] args) {
        System.out.print("Hello and welcome!\n");
        ArrayList<Integer> myList = new ArrayList<Integer>();
        myList.addToFront(1);
        myList.addToFront(2);
        myList.addToFront(3);
        myList.addToFront(4);
        myList.addToFront(5);
        myList.addToFront(6);
        myList.addToFront(7);
        myList.addToFront(8);
        myList.addToFront(9);
        myList.removeFromFront();
        myList.addToBack(1);
        myList.removeFromBack();
        // ArrayList<Integer> myList = new ArrayList<Integer>(10);
        System.out.print("\n");
        System.out.print(myList);

//        for (int i = 1; i <= 5; i++) {
//            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
//            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
//            System.out.println("i = " + i);
//        }
    }
}

