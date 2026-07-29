class Solution {
    public int evalRPN(String[] tokens) {
        String expstr="+-*/";
        Stack<Integer> stack=new Stack<>();
        for(int i=0;i<tokens.length;i++){
            String token=tokens[i];
            if(expstr.contains(token)){
                int a =stack.pop();
                int b=stack.pop();
                int val=0;
                switch(token){
                    case"+":
                     val=b+a;
                    break;

                    case"-":
                    val=b-a;
                    break;

                    case"*":
                    val=b*a;
                    break;

                    case"/":
                     val=b/a;
                    break;
                }
                stack.push(val);


            }else{
            stack.push(Integer.parseInt(token));
            }
        }
        return stack.peek();
        
    }
}