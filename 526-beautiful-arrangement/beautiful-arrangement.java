class Solution {

    public int countArrangement(int n) {
        boolean[] used = new boolean[n + 1];

        return backtrack(1, n, used);
    }

    private int backtrack(int pos, int n, boolean[] used) {
 int count = 0;
        if(pos > n) {
            return 1;
        }

       

        for(int num = 1; num <= n; num++) {

            if(!used[num] &&
               (num % pos == 0 || pos % num == 0)) {

                used[num] = true;

                // Recursive call separately
                int result = backtrack(pos + 1, n, used);

                // Add the result separately
                count = count + result;

                // Backtrack
                used[num] = false;
            }
        }

        return count;
    }
}