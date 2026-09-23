class Solution {

    public List<String> letterCasePermutation(String s) {
        List<String> result = new ArrayList<>();

        helper(s, 0, "", result);

        return result;
    }

    private void helper(String s, int index, String current, List<String> result) {

        // Base case
        if (index == s.length()) {
            result.add(current);
            return;
        }

        char ch = s.charAt(index);

        // If it is a letter
        if (Character.isLetter(ch)) {

            // Choice 1: lowercase
            helper(s, index + 1, current + Character.toLowerCase(ch), result);

            // Choice 2: uppercase
            helper(s, index + 1, current + Character.toUpperCase(ch), result);

        } else {

            // Digit → only one choice
            helper(s, index + 1, current + ch, result);
        }
    }
}