class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);

        HashSet<List<Integer>> set = new HashSet<>();

        subsetsWithDuphelper(nums, 0, new ArrayList<>(), set);

        return new ArrayList<>(set);
    }

    public void subsetsWithDuphelper(
        int[] nums,
        int index,
        List<Integer> current,
        HashSet<List<Integer>> set
    ) {

        if (index >= nums.length) {
            set.add(new ArrayList<>(current));
            return;
        }

        // Include nums[index]
        current.add(nums[index]);

        subsetsWithDuphelper(nums, index + 1, current, set);

        // Backtrack
        current.remove(current.size() - 1);

        // Don't include nums[index]
        subsetsWithDuphelper(nums, index + 1, current, set);
    }
}