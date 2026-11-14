class Solution {
    public int thirdMax(int[] nums) {
        Arrays.sort(nums);
        
        int j = nums.length - 1;
        int count = 1;
        int ans = nums[j];
        
        while (j > 0 && count < 3) {
            if (nums[--j] != nums[j + 1]) {
                if (++count == 3) {
                    ans = nums[j];
                }
            }
        }
        
        return ans;
    }
}