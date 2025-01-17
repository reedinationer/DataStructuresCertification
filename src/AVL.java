import java.util.NoSuchElementException;

/**
 * Your implementation of the AVL tree rotations.
 */
public class AVL<T extends Comparable<? super T>> {
    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    private void checkNotNull(T data){
        if (data == null){
            throw new IllegalArgumentException();
        }
    }

    /**
     * Adds the element to the tree.
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary. This is as simple as calling the balance() method on the
     * current node, before returning it (assuming that your balance method
     * is written correctly from part 1 of this assignment).
     *
     * @param data The data to add.
     * @throws java.lang.IllegalArgumentException If data is null.
     */
    public void add(T data) {
        checkNotNull(data);
        root = rAdd(root, data);
        root = balance(root);
    }

    private AVLNode<T> rAdd(AVLNode<T> currentNode, T data){
        if(currentNode == null){
            this.size += 1;
            return new AVLNode<T>(data);
        } else if (data.compareTo(currentNode.getData()) < 0){
            // Data is less than current node
            currentNode.setLeft(rAdd(currentNode.getLeft(), data));
        } else if (data.compareTo(currentNode.getData()) > 0) {
            // Data is greater than current node
            currentNode.setRight(rAdd(currentNode.getRight(), data));
        }
        currentNode = balance(currentNode);
        return currentNode;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     *    simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     *    replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     *    replace the data, NOT predecessor. As a reminder, rotations can occur
     *    after removing the successor node.
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary. This is as simple as calling the balance() method on the
     * current node, before returning it (assuming that your balance method
     * is written correctly from part 1 of this assignment).
     * Do NOT return the same data that was passed in. Return the data that
     * was stored in the tree.
     * Hint: Should you use value equality or reference equality?
     *
     * @param data The data to remove.
     * @return The data that was removed.
     * @throws java.lang.IllegalArgumentException If the data is null.
     * @throws java.util.NoSuchElementException   If the data is not found.
     */
    public T remove(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        checkNotNull(data);
        AVLNode<T> dummy = new AVLNode<>(null);
        root = rRemove(root, data, dummy);
        root = balance(root);
        return (T) dummy.getData();
    }

    private AVLNode<T> rRemove(AVLNode<T> currentNode, T data, AVLNode<T> dummyNode){
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
                AVLNode<T> dummy2 = new AVLNode<>(null);
                currentNode.setRight(removeSuccessor(currentNode.getRight(), dummy2));
                currentNode.setData(dummy2.getData());
            }
        }
        return currentNode;
    }

    private AVLNode<T> removeSuccessor(AVLNode<T> current, AVLNode<T> dummy){
        if (current.getLeft() == null){
            dummy.setData(current.getData());
            return current.getRight();
        } else {
            current.setLeft(removeSuccessor(current.getLeft(), dummy));
        }
        return current;
    }


    /**
     * Updates the height and balance factor of a node using its
     * setter methods.
     * Recall that a null node has a height of -1. If a node is not
     * null, then the height of that node will be its height instance
     * data. The height of a node is the max of its left child's height
     * and right child's height, plus one.
     * The balance factor of a node is the height of its left child
     * minus the height of its right child.
     * This method should run in O(1).
     * You may assume that the passed in node is not null.
     * This method should only be called in rotateLeft(), rotateRight(),
     * and balance().
     *
     * @param currentNode The node to update the height and balance factor of.
     */

    private void updateHeightAndBF(AVLNode<T> currentNode) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (currentNode.getRight() == null & currentNode.getLeft() == null){ // Leaf node with no children
            currentNode.setHeight(0);
            currentNode.setBalanceFactor(0);
        } else if (currentNode.getRight() == null) { // Only left child exists
            currentNode.setHeight(currentNode.getLeft().getHeight() + 1);
            currentNode.setBalanceFactor(currentNode.getLeft().getHeight() + 1);
        } else if (currentNode.getLeft() == null) { // Only right child exists
            currentNode.setHeight(currentNode.getRight().getHeight() + 1);
            currentNode.setBalanceFactor(-1 - currentNode.getRight().getHeight());
        } else { // Reaching here means both children exist and must be compared
            if (currentNode.getRight().getHeight() >= currentNode.getLeft().getHeight()) {
                currentNode.setHeight(currentNode.getRight().getHeight() + 1);
            } else {
                currentNode.setHeight(currentNode.getLeft().getHeight() + 1);
            }
            currentNode.setBalanceFactor(currentNode.getLeft().getHeight() - currentNode.getRight().getHeight());
        }
    }

    /**
     * Method that rotates a current node to the left. After saving the
     * current's right node to a variable, the right node's left subtree will
     * become the current node's right subtree. The current node will become
     * the right node's left subtree.
     * Don't forget to recalculate the height and balance factor of all
     * affected nodes, using updateHeightAndBF().
     * This method should run in O(1).
     * You may assume that the passed in node is not null and that the subtree
     * starting at that node is right heavy. Therefore, you do not need to
     * perform any preliminary checks, rather, you can immediately perform a
     * left rotation on the passed in node and return the new root of the subtree.
     * This method should only be called in balance().
     *
     * @param currentNode The current node under inspection that will rotate.
     * @return The parent of the node passed in (after the rotation).
     */

    private AVLNode<T> rotateLeft(AVLNode<T> currentNode) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        AVLNode<T> cache = currentNode.getRight(); // Save a reference to Node B
        currentNode.setRight(cache.getLeft()); // Assign both nodes to point to this child
        cache.setLeft(currentNode);
        updateHeightAndBF(currentNode);
        updateHeightAndBF(cache);
        return cache;
    }

    /**
     * Method that rotates a current node to the right. After saving the
     * current's left node to a variable, the left node's right subtree will
     * become the current node's left subtree. The current node will become
     * the left node's right subtree.
     * Don't forget to recalculate the height and balance factor of all
     * affected nodes, using updateHeightAndBF().
     * This method should run in O(1).
     * You may assume that the passed in node is not null and that the subtree
     * starting at that node is left heavy. Therefore, you do not need to perform
     * any preliminary checks, rather, you can immediately perform a right
     * rotation on the passed in node and return the new root of the subtree.
     * This method should only be called in balance().
     *
     * @param currentNode The current node under inspection that will rotate.
     * @return The parent of the node passed in (after the rotation).
     */

    private AVLNode<T> rotateRight(AVLNode<T> currentNode) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        AVLNode<T> cache = currentNode.getLeft(); // Save a reference to Node B
        currentNode.setLeft(cache.getRight()); // Assign both nodes to point to this child
        cache.setRight(currentNode);
        updateHeightAndBF(currentNode);
        updateHeightAndBF(cache);
        return cache;
    }

    /**
     * This is the overarching method that is used to balance a subtree
     * starting at the passed in node. This method will utilize
     * updateHeightAndBF(), rotateLeft(), and rotateRight() to balance
     * the subtree. In part 2 of this assignment, this balance() method
     * will be used in your add() and remove() methods.
     *
     * The height and balance factor of the current node is first recalculated.
     * Based on the balance factor, a no rotation, a single rotation, or a
     * double rotation takes place. The current node is returned.
     *
     * You may assume that the passed in node is not null. Therefore, you do
     * not need to perform any preliminary checks, rather, you can immediately
     * check to see if any rotations need to be performed.
     *
     * This method should run in O(1).
     *
     * @param currentNode The current node under inspection.
     * @return The AVLNode that the caller should return.
     */
    private AVLNode<T> balance(AVLNode<T> currentNode) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!

        /* First, we update the height and balance factor of the current node. */
        updateHeightAndBF(currentNode);

        if (currentNode.getBalanceFactor() < -1) {
            if (currentNode.getRight().getBalanceFactor() == 1) {
                currentNode.setRight(rotateRight(currentNode.getRight()));
            }
            currentNode = rotateLeft(currentNode);
        } else if (currentNode.getBalanceFactor() > 1) {
            if (currentNode.getLeft().getBalanceFactor() == -1) {
                currentNode.setLeft(rotateLeft(currentNode.getLeft()));
            }
            currentNode = rotateRight(currentNode);
        }

        return currentNode;
    }

    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the tree.
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}