import java.util.Arrays;
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        HashSet<List<Integer>> set = new HashSet<>();
         backtrack(candidates, target, 0, new ArrayList<>(), set);
         return new ArrayList<>(set);
        
    }
    void backtrack(int[] candidates ,int target ,int index ,List<Integer> current,
                             HashSet<List<Integer>> set){
      
        if(target==0){
          set.add(new ArrayList<>(current));
            return;
        }
        if(target<0 || index==candidates.length){
            return;
        }
        current.add(candidates[index]);
        backtrack(candidates, target - candidates[index],
                  index+1, current, set);
        current.remove(current.size()-1);
           // DON'T TAKE
        int nextIndex = index + 1;

        // Skip duplicate values
        while (nextIndex < candidates.length &&
               candidates[nextIndex] == candidates[index]) {
            nextIndex++;
        }
        backtrack(candidates, target,
                  nextIndex, current, set);// why not target-candidates[i]= its because we still havent taken the next element so why to subtract it 
    }
}