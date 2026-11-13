public class Solution {
    public int maxProfit(int[] prices) {
        int minprice=Integer.MAX_VALUE;
        int maxdiff=0;
        for(int i=0;i<prices.length;i++){
            minprice=Math.min(minprice, prices[i]);
            maxdiff=Math.max(maxdiff, prices[i]-minprice);
        }
        return maxdiff;

       
    }
}