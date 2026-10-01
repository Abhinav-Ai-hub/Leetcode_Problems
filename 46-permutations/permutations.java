import java.util.*;

class Solution {

    private void recurPermute(
        int[] nums,
        List<Integer> ds,
        List<List<Integer>> ans,
        boolean[] freq
    ) {

        // Base case: permutation is complete
        if (ds.size() == nums.length) {
            ans.add(new ArrayList<>(ds));
            return;
        }

        // Try every element
        for (int i = 0; i < nums.length; i++) {

            // Check whether this index is already used
            if (!freq[i]) {

                // Pick the element
                freq[i] = true;
                ds.add(nums[i]);

                // Recursively build the remaining permutation
                recurPermute(nums, ds, ans, freq);

                // Backtrack: remove the element
                ds.remove(ds.size() - 1);
                freq[i] = false;
            }
        }
    }

    public List<List<Integer>> permute(int[] nums) {

        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> ds = new ArrayList<>();

        boolean[] freq = new boolean[nums.length];

        recurPermute(nums, ds, ans, freq);

        return ans;
    }
}