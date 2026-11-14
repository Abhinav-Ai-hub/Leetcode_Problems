
class Solution {
    public int pivotIndex(int[] nums) {
        int n = nums.length;
        int[] prefix = nums.clone();
        int[] suffix = nums.clone();

        // Build prefix sums
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i] + prefix[i - 1];
        }

        // Build suffix sums
        for (int j = n - 2; j >= 0; j--) {
            suffix[j] = suffix[j] + suffix[j + 1];
        }
        for (int i = 0; i < n; i++) {
    if (prefix[i] - nums[i] == suffix[i] - nums[i]) {
        return i;
    }
}



        return -1;
    }
}



