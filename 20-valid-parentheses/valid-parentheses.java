class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        for(int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);

            // Opening brackets
            if(ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            }

            // Closing brackets
            else {
                if(stack.isEmpty()) {
                    return false;
                }

                char val = stack.pop();

                if(ch == ')' && val != '(' ||
                   ch == '}' && val != '{' ||
                   ch == ']' && val != '[') {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}