class Solution {

    private void recurPermute(
        int[] nums,
        int i,
        List<Integer> ds,
        List<List<Integer>> ans,
        boolean[] freq
    ) {

        // We have checked all elements for this level
        if (i == nums.length) {
            return;
        }

        // Complete permutation
        if (ds.size() == nums.length) {
            ans.add(new ArrayList<>(ds));
            return;
        }

        // Choice: nums[i] is free
        if (!freq[i]) {

            freq[i] = true;
            ds.add(nums[i]);

            // Move to the next recursion level
            recurPermute(nums, 0, ds, ans, freq);

            // Backtrack
            ds.remove(ds.size() - 1);
            freq[i] = false;
        }

        // Instead of the for loop:
        // i++ becomes another recursive call
        recurPermute(nums, i + 1, ds, ans, freq);
    }

    public List<List<Integer>> permute(int[] nums) {

        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> ds = new ArrayList<>();
        boolean[] freq = new boolean[nums.length];

        recurPermute(nums, 0, ds, ans, freq);

        return ans;
    }
}