import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;


public class Sorting {
    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of: O(n log n)
     * And a best case running time of: O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: You may need to create a helper method that merges two arrays
     * back into the original T[] array. If two data are equal when merging,
     * think about which subarray you should pull from first.
     *
     * You may assume that the passed in array and comparator are both valid
     * and will not be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array to be sorted.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (arr.length < 2) return;
        else {
            int dividingIndex = arr.length / 2;
            T[] leftArray = (T[]) new Object[dividingIndex];
            T[] rightArray = (T[]) new Object[arr.length - dividingIndex];
            for (int i=0; i<dividingIndex; i++){
                leftArray[i] = arr[i];
            }
            for (int i=dividingIndex; i<arr.length; i++){
                rightArray[i - dividingIndex] = arr[i];
            }
//            System.out.println(Arrays.toString(leftArray));
            mergeSort(leftArray, comparator);
            mergeSort(rightArray, comparator);

//            int leftIndex = 0, rightIndex = 0, mainIndex = 0; // index into arr
//
//            // While both sides have items left
//            while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
//                if (comparator.compare(leftArray[leftIndex], rightArray[rightIndex]) <= 0) {
//                    arr[mainIndex++] = leftArray[leftIndex++];
//                } else {
//                    arr[mainIndex++] = rightArray[rightIndex++];
//                }
//            }
//
//            // Copy the remainder of the left subarray, if any
//            while (leftIndex < leftArray.length) {
//                arr[mainIndex++] = leftArray[leftIndex++];
//            }
//
//            // Copy the remainder of the right subarray, if any
//            while (rightIndex < rightArray.length) {
//                arr[mainIndex++] = rightArray[rightIndex++];
//            }

            int leftIndex = 0;
            int rightIndex = 0;
            for (int i=0; i < arr.length; i++){
                if (leftIndex == leftArray.length){
                    arr[i] = rightArray[rightIndex];
                    rightIndex += 1;
                    continue;
                } else if (rightIndex == rightArray.length) {
                    arr[i] = leftArray[leftIndex];
                    leftIndex += 1;
                    continue;
                }
                if (comparator.compare(leftArray[leftIndex], rightArray[rightIndex]) <= 0){
                    arr[i] = leftArray[leftIndex];
                    leftIndex += 1;
                } else {
                    arr[i] = rightArray[rightIndex];
                    rightIndex += 1;
                }
            }
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of: O(kn)
     * And a best case running time of: O(kn)
     *
     * Feel free to make an initial O(n) passthrough of the array to
     * determine k, the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * You may use an ArrayList or LinkedList if you wish, but it should only
     * be used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with merge sort. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * You may assume that the passed in array is valid and will not be null.
     *
     * @param arr The array to be sorted.
     */
    public static void lsdRadixSort(int[] arr) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i=0; i < buckets.length; i++){
            buckets[i] = new LinkedList<Integer>();
        }
        int largestK = getLargestK(arr);
        for (int k = 1; k <= largestK; k++) { // Loop through digits
            for (int i : arr) { // Loop through items in array to put them in buckets
                String numberString = String.valueOf(i);
                numberString = numberString.replace("-", "");
                if (numberString.length() < k) { // Not enough digits, so we fill to the left with 0's
                    buckets[9].add(i);
                } else {
                    int significantDigit = Character.getNumericValue(numberString.charAt(numberString.length() - k));
                    if (i < 0) {
                        buckets[9 - significantDigit].add(i);
                    } else {
                        buckets[9 + significantDigit].add(i);
                    }
                }
            }
            int useIndex = 0;
            for (LinkedList<Integer> b: buckets){ // Now empty buckets back into array
                while (!b.isEmpty()) {
                    arr[useIndex] = b.getFirst();
                    b.removeFirst();
                    useIndex += 1;
                }
            }
        }
    }

    private static int getLargestK(int[] arr) {
        int largestK = 0; // What is the longest digit number?
        for (int i : arr) {
            String numberString = String.valueOf(i);
            numberString = numberString.replace("-", "");
            char[] digits = numberString.toCharArray();
            if (digits.length > largestK) {
                largestK = digits.length;
            }
        }
        return largestK;
    }

    public static void main(String[] args){
        Integer[] anArray = new Integer[9];
        anArray[0] = -2147483647;
        anArray[1] = -922;
        anArray[2] = -800;
        anArray[3] = -796;
        anArray[4] = -785;
        anArray[5] = 104;
        anArray[6] = 151;
        anArray[7] = 2147483646;
        anArray[8] = 2147483647;
//        anArray[9] = -2147483648;
        Sorter integerComparator = new Sorter();
        mergeSort(anArray, integerComparator);
        System.out.println(Arrays.toString(anArray));
        System.out.println(integerComparator.getTimesCalled());
    }
}

class Sorter implements Comparator<Integer> {
    int timesCalled;

    public void setTimesCalled(int timesCalled) {
        this.timesCalled = timesCalled;
    }

    public int getTimesCalled() {
        return timesCalled;
    }

    // Compare by roll number in ascending order
    public int compare(Integer a, Integer b) {
        this.setTimesCalled(this.timesCalled + 1);
        return a.compareTo(b);
    }

}