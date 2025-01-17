import java.util.Arrays;
import java.util.Comparator;

/**
 * Your implementation of various iterative sorting algorithms.
 */
public class IterativeSorting {
    /**
     * Implement bubble sort.
     * It should be:
     * in-place
     * stable
     * adaptive
     * Have a worst case running time of: O(n^2)
     * And a best case running time of: O(n)
     * <p>
     * NOTE: You should implement bubble sort with the last swap optimization.
     * <p>
     * You may assume that the passed in array and comparator
     * are both valid and will never be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array that must be sorted after the method runs.
     *
     */

    private static <T> void swapArrayValues(T[] arr, int index1, int index2) {
        T cache;
        cache = arr[index1]; // Store value outside array momentarily
        arr[index1] = arr[index2];
        arr[index2] = cache;
    }

    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        boolean swapMade;
        int startIndex;
        int lastSwapIndex;
        lastSwapIndex = arr.length - 1;
        for (int endInd = arr.length - 1; endInd > 0; endInd--) {
            if (lastSwapIndex < endInd) {
                continue;
            }
            swapMade = false;
            startIndex = 0;
            while (startIndex < endInd) {
                if (comparator.compare(arr[startIndex], arr[startIndex + 1]) > 0) {
                    lastSwapIndex = startIndex;
                    swapArrayValues(arr, startIndex, startIndex + 1);
                    swapMade = true;
                }
                startIndex += 1;
            }
            if (!swapMade){ // If we made no swaps for an iteration, the array is sorted.
                break;
            }
        }
    }

    /**
     * Implement selection sort.
     * <p>
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of: O(n^2)
     * And a best case running time of: O(n^2)
     * <p>
     * You may assume that the passed in array and comparator
     * are both valid and will never be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array that must be sorted after the method runs.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        // Method searches for largest value and places it in the back of the array each loop
        int largestIndex; // Store index where we saw the largest value
        for (int endInd = arr.length - 1; endInd > 0; endInd--) {
            if (comparator.compare(arr[0], arr[1]) > 0) {
                    largestIndex = 0;
            } else {
                largestIndex = 1;
            }
            for (int curInd = 2; curInd <= endInd; curInd++) {
                if (comparator.compare(arr[curInd], arr[largestIndex]) > 0) {
                    largestIndex = curInd;
                }
            }
            swapArrayValues(arr, largestIndex, endInd);
        }
    }

    /**
     * Implement insertion sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of: O(n^2)
     * And a best case running time of: O(n)
     * <p>
     * You may assume that the passed in array and comparator
     * are both valid and will never be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array that must be sorted after the method runs.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        // Method will sort left to right, placing the next value in the correct place
        for (int sortedInd = 1; sortedInd <= arr.length - 1; sortedInd++) {
            for (int curInd = sortedInd - 1; curInd >= 0; curInd--) {
                if (comparator.compare(arr[curInd], arr[curInd + 1]) > 0) {
                    swapArrayValues(arr, curInd, curInd + 1);
                } else {
                    break;
                }
            }
        }
    }
}

//    public static void main(String[] args){
//        args = new String[0];
//        Integer[] anArray;
//        anArray = new Integer[10];
//        anArray[0] = 11;
//        anArray[1] = 10;
//        anArray[2] = 9;
//        anArray[3] = 8;
//        anArray[4] = 7;
//        anArray[5] = 6;
//        anArray[6] = 5;
//        anArray[7] = 4;
//        anArray[8] = 3;
//        anArray[9] = 2;
//        Sorter integerComparator = new Sorter();
//        bubbleSort(anArray, integerComparator);
////        selectionSort(anArray, integerComparator);
////        insertionSort(anArray, integerComparator);
//        System.out.println(Arrays.toString(anArray));
//        System.out.println(integerComparator.getTimesCalled());
//    }
//}
//
//class Sorter implements Comparator<Integer> {
//    int timesCalled;
//
//    public void setTimesCalled(int timesCalled) {
//        this.timesCalled = timesCalled;
//    }
//
//    public int getTimesCalled() {
//        return timesCalled;
//    }
//
//    // Compare by roll number in ascending order
//    public int compare(Integer a, Integer b) {
//        this.setTimesCalled(this.timesCalled + 1);
//        return a.compareTo(b);
//    }
//}