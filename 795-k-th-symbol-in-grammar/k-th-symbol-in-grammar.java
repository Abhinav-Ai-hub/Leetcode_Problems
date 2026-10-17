class Solution {
    public int kthGrammar(int n, int k) {
      return  kthGrammarhelper( n,  k,0);
        
    }
    public int kthGrammarhelper(int n, int k,int flag){
        if(n==1){
            return flag%2==0? 0:1; // conditional that if flag is even keep it 0 and if one then flip it to 1
        }
        if(k%2==0){
           return kthGrammarhelper(n-1,k/2,flag+1);
        }
         return kthGrammarhelper(n-1,(k+1)/2,flag);
    }
}