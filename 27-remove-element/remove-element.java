class Solution {
    public int removeElement(int[] nums, int val) {
        int i=0;
        int count=0;
        int j=0;
        while(i<nums.length){
            if(nums[i]!=val){
                count+=1;
                nums[j]=nums[i];
                j++;
            }
            i++;
        }
        return count;
    }
}