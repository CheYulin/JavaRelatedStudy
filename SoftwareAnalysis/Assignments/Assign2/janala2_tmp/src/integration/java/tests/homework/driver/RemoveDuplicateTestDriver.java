package tests.homework.driver;

import catg.CATG;
import janala.Main;

import java.util.Arrays;

/**
 * Created by cheyulin on 11/28/16.
 */

public class RemoveDuplicateTestDriver {
    private static util.IntArrayUtil jarUtil = new util.IntArrayUtil();
    private static tests.homework.IntArrayUtil srcUtil = new tests.homework.IntArrayUtil();

    public static void main(String[] args) {
        for (int arrLen = 1; arrLen < 10; arrLen++) {
            int[] arr0 = Utility.generateArray(arrLen);
            int[] arr1 = Arrays.copyOf(arr0, arr0.length);
            int[] input = Arrays.copyOf(arr0, arr0.length);

            CATG.BeginScope();
            int arr0Len = srcUtil.removeDuplicateElements(arr0);
            CATG.EndScope();
            int arr1Len = jarUtil.removeDuplicateElements(arr1);

            Utility.printInput(input);
            Utility.compareAndPrintResult(arr0, arr0Len, arr1, arr1Len);
        }
    }
}
