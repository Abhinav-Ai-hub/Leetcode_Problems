class Solution {
    private void recurPermute(int[] nums, List<Integer> ds, List<List<Integer>> ans, boolean []freq) {
        if(ds.size() == nums.length) {
            ans.add(new ArrayList<>(ds));
            return;
        }
        for(int i = 0; i < nums.length; i++) {
            if(!freq[i]) {// we are checking if the index element has already been used or free to be used
                freq[i] = true;// here we are declaring that yes now the elemtn at the endex i is picked
                ds.add(nums[i]);
                recurPermute(nums, ds, ans, freq);
                ds.remove(ds.size() - 1);
                freq[i] = false;// declaring that while backtracking since we removed the last element so now that elemnent can be used thatswhy in map we turn it to false again so that by condition checking it becomes true and enters the if clause
            }
        }
    }
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> ds = new ArrayList<>();
        boolean freq[] = new boolean[nums.length];
        recurPermute(nums, ds, ans, freq);
        return ans;
    }
}