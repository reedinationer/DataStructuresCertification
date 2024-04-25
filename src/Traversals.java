import java.util.List;
import java.util.ArrayList;

/**
 * Your implementation of the pre-order, in-order, and post-order
 * traversals of a tree.
 */
public class Traversals<T extends Comparable<? super T>> {
    /*
     * DO NOT ADD ANY GLOBAL VARIABLES!
     */

    /**
     * Given the root of a binary search tree, generate a
     * pre-order traversal of the tree. The original tree
     * should not be modified in any way.
     * This must be done recursively.
     * Must be O(n).
     *
     * @param <T> Generic type.
     * @param root The root of a BST.
     * @return List containing the pre-order traversal of the tree.
     */
    public List<T> preorder(TreeNode<T> root) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        List<T> loopResults = new ArrayList<>();
        if (root != null) {
            loopResults.add(root.getData());
            loopResults.addAll(this.preorder(root.getLeft()));
            loopResults.addAll(this.preorder(root.getRight()));
        }
        return loopResults;
    }

    /**
     * Given the root of a binary search tree, generate an
     * in-order traversal of the tree. The original tree
     * should not be modified in any way.
     * This must be done recursively.
     * Must be O(n).
     *
     * @param <T> Generic type.
     * @param root The root of a BST.
     * @return List containing the in-order traversal of the tree.
     */
    public List<T> inorder(TreeNode<T> root) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        List<T> loopResults = new ArrayList<>();
        if (root != null) {
            loopResults.addAll(this.inorder(root.getLeft()));
            loopResults.add(root.getData());
            loopResults.addAll(this.inorder(root.getRight()));
        }
        return loopResults;
    }

    /**
     * Given the root of a binary search tree, generate a
     * post-order traversal of the tree. The original tree
     * should not be modified in any way.
     * This must be done recursively.
     * Must be O(n).
     *
     * @param <T> Generic type.
     * @param root The root of a BST.
     * @return List containing the post-order traversal of the tree.
     */
    public List<T> postorder(TreeNode<T> root) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        List<T> loopResults = new ArrayList<>();
        if (root != null) {
            loopResults.addAll(this.postorder(root.getLeft()));
            loopResults.addAll(this.postorder(root.getRight()));
            loopResults.add(root.getData());
        }
        return loopResults;
    }

//    public static void main(String[] args) {
//        args = new String[0];
//        Main.main(args);
//    }
}

//class Main{
//    public static void main(String[] args) {
//        TreeNode<Integer> node1 = new TreeNode<>(5);
//        TreeNode<Integer> node2 = new TreeNode<>(4);
//        TreeNode<Integer> node3 = new TreeNode<>(1);
//        TreeNode<Integer> node4 = new TreeNode<>(0);
//        TreeNode<Integer> node5 = new TreeNode<>(2);
//        TreeNode<Integer> node6 = new TreeNode<>(7);
//        TreeNode<Integer> node7 = new TreeNode<>(9);
//        TreeNode<Integer> node8 = new TreeNode<>(8);
//        node1.setLeft(node2);
//        node2.setLeft(node3);
//        node3.setLeft(node4);
//        node3.setRight(node5);
//        node1.setRight(node6);
//        node6.setRight(node7);
//        node7.setLeft(node8);
//
//        Traversals<Integer> trav = new Traversals<>();
//        System.out.println(Arrays.toString(trav.preorder(node1).toArray()));
//        System.out.println(Arrays.toString(trav.inorder(node1).toArray()));
//        System.out.println(Arrays.toString(trav.postorder(node1).toArray()));
//
//    }
//}
