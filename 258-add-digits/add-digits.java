class Solution {
    public int addDigits(int num) {
        int sum=0;
        int digit=0;
        while(num>0){
            digit=num%10;
             sum=sum+digit;
            num=num/10;
        }
    
       while(sum>9){
      return  addDigits(sum);

       } 
        return sum;
    }
}