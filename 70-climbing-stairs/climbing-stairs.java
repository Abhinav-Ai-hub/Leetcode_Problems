

//top down with memo[]
class Solution {

    public int climbStairs(int n) {
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);

        return climbStairs(n - 1, memo) + climbStairs(n - 2, memo);
    }

    private int climbStairs(int n, int[] memo) {
        if (n < 0) return 0;
        if (n == 0 || n == 1) {
            memo[n] = 1;
            return memo[n];
        }
        if (memo[n] != -1) return memo[n];

        memo[n] = climbStairs(n - 1, memo) + climbStairs(n - 2, memo);
        return memo[n];
    }
}
