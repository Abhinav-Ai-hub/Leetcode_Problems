class Solution {
    public String decodeString(String s) {

        Stack<Integer> number = new Stack<>();
        Stack<String> prevstring = new Stack<>();

        String current = "";
        int num = 0;

        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);

            if (Character.isDigit(ch)) {

                num = num * 10 + (ch - '0');

            } 
            else if (ch == '[') {

                number.push(num);
                prevstring.push(current);

                num = 0;
                current = "";

            } 
            else if (ch == ']') {

                int repeat = number.pop();
                String previous = prevstring.pop();

                current = previous + current.repeat(repeat);

            } 
            else {

                current += ch;
            }
        }

        return current;
    }
}