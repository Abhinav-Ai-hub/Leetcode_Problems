class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        // If total sum is odd, cannot partition into two equal subsets
        if (sum % 2 != 0) {
            return false;
        }

        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true; // Base case: sum 0 is always achievable

        for (int num : nums) {
            // Traverse backwards so num isn't used multiple times in the same step
            for (int j = target; j >= num; j--) {
                if (dp[j - num]) {
                    dp[j] = true;
                }
            }
            // Early exit if target is already reached
            if (dp[target]) {
                return true;
            }
        }

        return dp[target];
    }
}