class Solution {
    public List<String> restoreIpAddresses(String s) {

List<String> result = new ArrayList<>();
    restoreIpAddresses(s,0,new ArrayList<>(),result);
    return result;
        
    }
   void restoreIpAddresses(String s,int index,List<String> current, List<String> result){
    //base case

    if(current.size()==4){
        if(index==s.length()){
          result.add(String.join(".", current));
        }
        return;
   }

    for(int len=1;len<=3;len++){

      if(index+len>s.length()){
        break;// beacause then there will be no point in going throug the recusrive tree as substring hi nhi ban payegii
      }  
      String segment=s.substring(index,index+len);

      if(segment.length()>1 && segment.charAt(0)=='0'){
        break;
      }

      int num=Integer.parseInt(segment);
      if(num>255){
        break;
      }
      current.add(segment);
      restoreIpAddresses(s,index+len,current,result);
      current.remove(current.size()-1);
    }

   }
}