class Solution {
    public int[] nextGreaterElements(int[] nums) {
        Stack<Integer> stack=new Stack<>();
        int ans[]=new int[nums.length];
    
        for(int i=0;i<nums.length;i++){
            ans[i]=-1;
        }

        for(int i=0;i<nums.length;i++){
            while(!stack.isEmpty() && nums[i]>nums[stack.peek()]){
                ans[stack.pop()]=nums[i];
                
            }
            stack.push(i);
        }
        for(int i=0;i<nums.length;i++){
            while(!stack.isEmpty() && nums[i]>nums[stack.peek()]){
                ans[stack.pop()]=nums[i];
          
            }
            stack.push(i);
        }
      
                
                        
        return ans;        
    }
}
