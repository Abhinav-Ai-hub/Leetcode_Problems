class Solution {
    public int calPoints(String[] operations) {
        int sum=0;
        Deque<Integer> stack = new ArrayDeque<>();
    for(String ops:operations){
        if(ops.equals("C")){
         stack.pop();
        }
        else if(ops.equals("D")){
            int value=stack.peek();
         
            value=value*2;
            stack.push(value);
        }
        else if(ops.equals("+")){
            int value1=stack.pop();
            int value2=stack.peek();
            stack.push(value1);
            stack.push(value1+value2);
        }
        else{
            int value3 = Integer.parseInt(ops);
            stack.push(value3);

        }
    }
    while(!stack.isEmpty()){
         sum=stack.pop()+sum;
    }
    return sum;
    }
}
