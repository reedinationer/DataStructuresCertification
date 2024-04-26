
/**
 * Your implementation of a BST.
 */
public class BST<T extends Comparable<? super T>> {
    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /*
     * Do not add a constructor.
     */

    private void checkNotNull(T data){
        if (data == null){
            throw new IllegalArgumentException();
        }
    }

    private BSTNode<T> rAdd(BSTNode<T> currentNode, T data){
        if(currentNode == null){
            this.size += 1;
            return new BSTNode<T>(data);
        } else if (data.compareTo(currentNode.getData()) < 0){
            // Data is less than current node
            currentNode.setLeft(rAdd(currentNode.getLeft(), data));
        } else if (data.compareTo(currentNode.getData()) > 0){
            // Data is greater than current node
            currentNode.setRight(rAdd(currentNode.getRight(), data));
        }
        return currentNode;
    }

    /**
     * Adds the data to the tree.
     * This must be done recursively.
     * The new data should become a leaf in the tree.
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     * Should be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data The data to add to the tree.
     * @throws java.lang.IllegalArgumentException If data is null.
     */
    public void add(T data) {
        checkNotNull(data);
        root = rAdd(root, data);
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     * This must be done recursively.
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the SUCCESSOR to
     * replace the data. You should use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do NOT return the same data that was passed in. Return the data that
     * was stored in the tree.
     * Hint: Should you use value equality or reference equality?
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data The data to remove.
     * @return The data that was removed.
     * @throws java.lang.IllegalArgumentException If data is null.
     * @throws java.util.NoSuchElementException   If the data is not in the tree.
     */
    public T remove(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        checkNotNull(data);
        BSTNode<T> dummy = new BSTNode<>(null);
        root = rRemove(root, data, dummy);
        return (T) dummy.getData();
    }

    private BSTNode<T> rRemove(BSTNode<T> currentNode, T data, BSTNode<T> dummyNode){
        if (currentNode == null){
            // Data not found
            throw new java.util.NoSuchElementException();
        } else if (data.compareTo(currentNode.getData()) < 0) {
            // Traverse left branch
            currentNode.setLeft(rRemove(currentNode.getLeft(), data, dummyNode));
        } else if (data.compareTo(currentNode.getData()) > 0){
            // Traverse right branch
            currentNode.setRight(rRemove(currentNode.getRight(), data, dummyNode));
        } else {
            // We have found the matching data
            dummyNode.setData(currentNode.getData());
            this.size -= 1;
            if (currentNode.getLeft() == null & currentNode.getRight() == null) {
                return null;
            } else if (currentNode.getLeft() == null){
                return currentNode.getRight();
            } else if (currentNode.getRight() == null){
                return currentNode.getLeft();
            } else {
                BSTNode<T> dummy2 = new BSTNode<>(null);
                currentNode.setRight(removeSuccessor(currentNode.getRight(), dummy2));
                currentNode.setData(dummy2.getData());
            }
        }
        return currentNode;
    }

    private BSTNode<T> removeSuccessor(BSTNode<T> current, BSTNode<T> dummy){
        if (current.getLeft() == null){
            dummy.setData(current.getData());
            return current.getRight();
        } else {
            current.setLeft(removeSuccessor(current.getLeft(), dummy));
        }
        return current;
    }

    /**
     * Returns the root of the tree.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    public static void main(String[] args) {
        BST<Integer> myTree = new BST<>();
        myTree.add(0);
        myTree.remove(0);
        System.out.print(myTree.getRoot() == null);

//        System.out.println(Arrays.toString(trav.preorder(node1).toArray()));
//        System.out.println(Arrays.toString(trav.inorder(node1).toArray()));
//        System.out.println(Arrays.toString(trav.postorder(node1).toArray()));
    }
}