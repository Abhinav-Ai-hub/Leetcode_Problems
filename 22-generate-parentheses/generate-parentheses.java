class Solution {
    public boolean isvalid(String s){
        int count=0;
    for(char c :s.toCharArray()){
        if(c=='('){
            count=count+1;
        }
        else{
        count=count-1;
        }
        if(count<0){
            return false;
        }
  
        
    }
          return count==0;
}
void generateParenthesis(String curr,int n, List<String> ans){
    if(curr.length()==2*n){
        if(isvalid(curr)){
       ans.add(curr);
        }
        return;
    }

    generateParenthesis(curr+'(',n,ans);
    generateParenthesis(curr+')',n,ans);

}



    public List<String> generateParenthesis(int n) {
        List<String> ans=new ArrayList<>();
        generateParenthesis("",n,ans);
        return ans;
        
    }
}