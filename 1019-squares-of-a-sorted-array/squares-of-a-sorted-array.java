class Solution {
    public int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int i = 0;
        int j = n - 1;
        int k = n - 1;

        // We need a temporary array because we are overwriting the original
        int[] result = new int[n];

        while (i <= j) {
            int leftSq = nums[i] * nums[i];
            int rightSq = nums[j] * nums[j];

            if (rightSq > leftSq) {
                result[k] = rightSq;
                j--;
            } else {
                result[k] = leftSq;
                i++;
            }
            k--;
        }

        return result;
    }
}