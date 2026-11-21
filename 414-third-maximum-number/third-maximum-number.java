class Solution {
    public int thirdMax(int[] nums) {
        Arrays.sort(nums);
        //[1,2,3]
        int count=1;
        for(int i=nums.length-2;i>=0;i--){
            if(nums[i]!=nums[i+1]){
                count++;//got one distinct elemeent
            }
            if(count==3){
                return nums[i];
            }

        }
        return nums[nums.length-1];
    }
}