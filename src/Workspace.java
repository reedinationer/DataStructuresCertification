//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


class Workspace {
    /**
     * This method should return the largest number in the passed in array.
     * Constraints:
     * 1 <= nums.length <= 100
     * -10000 <= nums[i] <= 10000
     *
     * @param nums An array containing integers.
     * @return The largest number in nums.
     */
    public static int findMax(int[] nums) {
        int max = nums[0];
        for (int num : nums) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }
}

//public class Main {
//    public static void main(String[] args) {
//        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
//        // to see how IntelliJ IDEA suggests fixing it.
//        System.out.print("Hello and welcome!\n");
//        int[] intArray = new int[]{1, 2, 3, 4, 5};
//        int largest = FindMax.findMax(intArray);
//        System.out.println("Largest number found was: " + String.valueOf(largest));
//
////        for (int i = 1; i <= 5; i++) {
////            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
////            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
////            System.out.println("i = " + i);
////        }
//    }
//}