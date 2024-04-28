import java.util.NoSuchElementException;

/**
 * Your implementation of a ExternalChainingHashMap.
 */
public class ExternalChainingHashMap<K, V> {
    public static final int INITIAL_CAPACITY = 13;
    public static final double MAX_LOAD_FACTOR = 0.67; // max load factor of ExternalChainingHashMap
    private ExternalChainingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new ExternalChainingHashMap with an initial capacity of INITIAL_CAPACITY.
     */
    public ExternalChainingHashMap() {
        //DO NOT MODIFY THIS METHOD!
        table = (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[INITIAL_CAPACITY];
    }

    private void checkKeyNotNull(K data){
        if (data == null) {
            throw new IllegalArgumentException();
        }
    }

    private void checkValueNotNull(V data){
        if (data == null) {
            throw new IllegalArgumentException();
        }
    }


    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use external chaining as your resolution
     * strategy. Add new entries to the front of an existing chain, but don't
     * forget to check the entire chain for duplicate keys first.
     *
     * If you find a duplicate key, then replace the entry's value with the new
     * one passed in. When replacing the old value, replace it at that position
     * in the chain, not by creating a new entry and adding it to the front.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the table would violate the max load factor if the data was
     * added. Resize if the load factor (LF) is greater than max LF (it is
     * okay if the load factor is equal to max LF). For example, let's say
     * the table is of length 5 and the current size is 3 (LF = 0.6). For
     * this example, assume that no elements are removed in between steps.
     * If another entry is attempted to be added, before doing anything else,
     * you should check whether (3 + 1) / 5 = 0.8 is larger than the max LF.
     * It is, so you would trigger a resize before you even attempt to add
     * the data or figure out if it's a duplicate. Be careful to consider the
     * differences between integer and double division when calculating load
     * factor.
     *
     * When regrowing, resize the length of the backing table to
     * (2 * old length) + 1. You should use the resizeBackingTable method to do so.
     *
     * @param key   The key to add.
     * @param value The value to add.
     * @return null if the key was not already in the map. If it was in the
     *         map, return the old value associated with it.
     * @throws java.lang.IllegalArgumentException If key or value is null.
     */
    public V put(K key, V value) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        checkValueNotNull(value);
        checkKeyNotNull(key);
        int index = Math.abs(key.hashCode() % table.length);
        ExternalChainingMapEntry<K, V> newEntry = new ExternalChainingMapEntry<>(key, value);
        if (table[index] == null) {
            // No entry in the HashMap at this index yet. We are safe to just add the Key, Value pair here.
            if (((double) this.size + 1) / (double) this.table.length > MAX_LOAD_FACTOR){
                this.resizeBackingTable(2 * this.table.length + 1);
                this.put(key, value); // We have a new array now, so we need to recurse this function on the resized array
                // Since we can't be sure whether the updated index will have values already or not.
            } else {
                table[index] = newEntry;
                this.size += 1;
            }
        } else {
            // There are existing entries. We need to check to make sure we do not duplicate the key.
            ExternalChainingMapEntry<K, V> someEntry = table[index];
            while (someEntry != null){ // We can expect a null entry at the end of the linked list.
                if (someEntry.getKey().equals(key)) {
                    V returnVal = someEntry.getValue();
                    someEntry.setValue(value);
                    return returnVal;
                }
                someEntry = someEntry.getNext();
            }
            // If we reach this part we did not find the key while iterating the linked entries.
            // First check that the new value will not make us exceed capacity
            if (((double) this.size + 1) / (double) this.table.length > MAX_LOAD_FACTOR){
                this.resizeBackingTable(2 * this.table.length + 1);
                this.put(key, value); // We have a new array now, so we need to recurse this function on the resized array
                // Since we can't be sure whether the updated index will have values already or not.
            }
            else {
                // No resize was needed. Just add to the front
                newEntry.setNext(table[index]); // Set new entry to point to existing head
                table[index] = newEntry; // Move array pointer to the newEntry, effectively extending the list by one.
                this.size += 1;
            }
        }
        return null;
    }

    /**
     * Removes the entry with a matching key from the map.
     *
     * @param key The key to remove.
     * @return The value associated with the key.
     * @throws java.lang.IllegalArgumentException If key is null.
     * @throws java.util.NoSuchElementException   If the key is not in the map.
     */
    public V remove(K key) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        checkKeyNotNull(key);
        int index = Math.abs(key.hashCode() % table.length);
        ExternalChainingMapEntry<K, V> someEntry = table[index];
        if (someEntry == null) {
            throw new NoSuchElementException(); // We reached the end of the list without finding it.
        }
        if (someEntry.getKey().equals(key)) {
            // First value is an edge case so we make sure that we check it properly
            V returnVal = someEntry.getValue();
            table[index] = someEntry.getNext();
            this.size -= 1;
            return returnVal;
        } else {
            // The first value was not the key we needed
            while (true) { // We can expect a null entry at the end of the linked list.
                if (someEntry.getNext() == null){
                    throw new NoSuchElementException(); // We reached the end of the list without finding it.
                }
                if (someEntry.getNext().getKey().equals(key)) { // If the next value to check is a match
                    V returnVal = someEntry.getNext().getValue();
                    try {
                        someEntry.setNext(someEntry.getNext().getNext());
                    } catch (NullPointerException e) {
                        // The value we want is the last one in the list. We need to just set our current node to have a null next
                        someEntry.setNext(null);
                    }
                    this.size -= 1;
                    return returnVal;
                }
                someEntry = someEntry.getNext();
            }
        }
    }

    /**
     * Helper method stub for resizing the backing table to length.
     * This method should be called in put() if the backing table needs to
     * be resized.
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you won't need to explicitly check for
     * duplicates.
     * Hint: You cannot just simply copy the entries over to the new table.
     *
     * @param length The new length of the backing table.
     */
    private void resizeBackingTable(int length) {
        ExternalChainingMapEntry<K, V>[] biggerTable = (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[length];
        int bigTableIndex;
        for (ExternalChainingMapEntry<K, V> kvExternalChainingMapEntry : this.table) {
            while (kvExternalChainingMapEntry != null){
                // There is at least one entry at this index. Loop through all entries to add them to the new table
                bigTableIndex = Math.abs(kvExternalChainingMapEntry.getKey().hashCode() % biggerTable.length); // We have a different index on the new table
                ExternalChainingMapEntry<K, V> newEntry = new ExternalChainingMapEntry<>(kvExternalChainingMapEntry.getKey(), kvExternalChainingMapEntry.getValue());
                newEntry.setNext(biggerTable[bigTableIndex]); // Set new entry to point to existing head
                biggerTable[bigTableIndex] = newEntry; // Move array pointer to the newEntry, effectively extending the list by one.
                kvExternalChainingMapEntry = kvExternalChainingMapEntry.getNext();
            }
        }
        this.table = biggerTable; // Reset the table of this instance to the resized table
    }

    /**
     * Returns the table of the map.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The table of the map.
     */
    public ExternalChainingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the map.
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    public static void main(String[] args){
        ExternalChainingHashMap<Integer, String> myMap = new ExternalChainingHashMap<>();
        myMap.put(0, "Intro:");
        myMap.put(13, "Level 2");
        myMap.put(26, "Level 3");
        myMap.put(1, "Hello");
        myMap.put(2, "World!");
        myMap.put(3, "How");
        myMap.put(4, "Are");
        myMap.put(5, "You");
//        myMap.put(6, "Doing");
//        myMap.put(7, "Today");
//        myMap.put(8, "World?");
        myMap.remove(27);
        System.out.println(myMap.table.length);
        for (int i=0; i < myMap.table.length; i++) {
            System.out.print(myMap.table[i]);
            System.out.print(" ");
        }
        System.out.print("\n");

        myMap.put(9, "Resized");
        myMap.put(10, "Yet?");

        for (int i=0; i < myMap.table.length; i++) {
            System.out.print(myMap.table[i]);
            System.out.print(" ");
        }
        System.out.print("\n");

        myMap.remove(10);

        for (int i=0; i < myMap.table.length; i++) {
            System.out.print(myMap.table[i]);
            System.out.print(" ");
        }
        System.out.print("\n");

    }
}