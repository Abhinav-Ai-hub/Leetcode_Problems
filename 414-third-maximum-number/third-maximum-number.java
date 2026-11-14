/*class Solution {
    public int thirdMax(int[] nums) {
        Arrays.sort(nums);
        
        int j = nums.length - 1;
        int count = 1;
        int ans = nums[j];
        
        while (j > 0 && count < 3) {
            if (nums[--j] != nums[j + 1]) {// handles the duplicate cases
                if (++count == 3) { //makes sure that 3rd last element is returned
                    ans = nums[j];
                }
            }
        }
        
        return ans;
    }
}*/

class Solution {
    public int thirdMax(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        PriorityQueue <Integer> pq=new PriorityQueue();
        for(int i=0;i<nums.length;i++){
            if(set.contains(nums[i])){
                continue;
            }
            if(pq.size()<3){
                pq.add(nums[i]);
                set.add(nums[i]);
            }
            else{
                if(nums[i]>pq.peek()){
                  set.remove(pq.poll());
                  pq.add(nums[i]);
                  set.add(nums[i]);
                }
            }
        }
        if(pq.size()!=2){
             return pq.peek();

        }
        pq.poll();
        return pq.peek();

      
    }
}
    