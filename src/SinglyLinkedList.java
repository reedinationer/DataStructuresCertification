import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;

/**
 * Your implementation of a Singly-Linked List.
 */
public class SinglyLinkedList<T> {
    /*
     * Do not add new instance variables or modify existing ones.
     */
    private SinglyLinkedListNode<T> head;
    private SinglyLinkedListNode<T> tail;
    private int size;
    /*
     * Do not add a constructor.
     */

    private void checkNotNull(T data){
        if (data == null){
            throw new IllegalArgumentException();
        }
    }

    /**
     * Adds the element to the front of the list.
     * Method should run in O(1) time.
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        checkNotNull(data);
        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data); // Defaults to null next value
        if (size == 0){
            this.tail = newNode;
        } else {
            newNode.setNext(this.head);
        }
        this.head = newNode;
        this.size += 1;
    }

    /**
     * Adds the element to the back of the list.
     * Method should run in O(1) time.
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        checkNotNull(data);
        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data); // Defaults to null next value
        if (size == 0){
            this.head = newNode;
        } else {
            this.tail.setNext(newNode);
        }
        this.tail = newNode;
        this.size += 1;
    }

    private void checkNotEmpty(){
        if (this.size == 0){
            throw new NoSuchElementException();
        }
    }

    /**
     * Removes and returns the first data of the list.
     * Method should run in O(1) time.
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        checkNotEmpty();
        T result = this.head.getData();
        if (this.size == 1){
            this.head = null;
            this.tail = null;
        } else {
            this.head = this.head.getNext();
        }
        this.size -= 1;
        return result;
    }

    /**
     * Removes and returns the last data of the list.
     * Method should run in O(n) time.
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        checkNotEmpty();
        T result = this.tail.getData();
        if (this.size == 1){
            this.head = null;
            this.tail = null;
        } else {
            SinglyLinkedListNode<T> cursor = this.head; // We will need to iterate until cursor is one node away from the tail
            while (cursor.getNext().getNext() != null) {
                cursor = cursor.getNext();
            }
            this.tail = cursor;
            this.tail.setNext(null);
        }
        this.size -= 1;
        return result;
    }

    /**
     * Returns the head node of the list.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public SinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the list.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public SinglyLinkedListNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
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
    public static void main(String[] args){
        System.out.println("Running SinglyLinkedList...\n");
        SinglyLinkedList<String> myList = new SinglyLinkedList<>();
        myList.addToBack("0a");
        myList.addToBack("1a");
        myList.addToBack("2a");
        myList.addToBack("3a");
        myList.addToBack("4a");
        myList.addToBack("5a");
        myList.addToBack("6a");
        SinglyLinkedListNode<String> cursor = myList.head;
        String output = cursor.getData();
        while (cursor.getNext() != null) {
            output = output + cursor.getNext().getData();
            cursor = cursor.getNext();
        }
        System.out.println(output);

        myList.removeFromFront();

        cursor = myList.head;
        output = cursor.getData();
        while (cursor.getNext() != null) {
            output = output + cursor.getNext().getData();
            cursor = cursor.getNext();
        }
        System.out.println(output);
    }
}