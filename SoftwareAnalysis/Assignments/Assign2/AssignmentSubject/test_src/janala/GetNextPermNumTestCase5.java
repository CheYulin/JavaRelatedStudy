package janala;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by cheyulin on 11/29/16.
 */

public class GetNextPermNumTestCase5 {
    private static util.IntArrayUtil jarUtil = new util.IntArrayUtil();
    private static tests.homework.IntArrayUtil srcUtil = new tests.homework.IntArrayUtil();

    @Test
    public void testGetNextPermNum() throws Exception {
        int[] arr0 = {1, 1, 1, 1, 1, 1, 1};
        int[] arr1 = {1, 1, 1, 1, 1, 1, 1};

        jarUtil.getNextPermutationNumber(arr0);
        srcUtil.getNextPermutationNumber(arr1);

        assertArrayEquals(arr0, arr1);
    }
}
