class Solution {
    Map <Integer,Integer> map=new HashMap<>();
    public int climbStairsHelper(int n){
        if(map.containsKey(n)){
            return map.get(n);
        }
        if(n==0){
            return 1;
            
        }
        if(n<0){
            return 0;
        }
        int res=climbStairsHelper(n-1)+climbStairsHelper(n-2);
        map.put(n,res);
        return res;
    }
    public int climbStairs(int n) {
        return climbStairsHelper(n);


    }
}