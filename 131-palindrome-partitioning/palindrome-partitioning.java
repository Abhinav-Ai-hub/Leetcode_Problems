import java.util.*;

class Solution {

    public List<List<String>> partition(String s) {

        List<List<String>> result = new ArrayList<>();

        backtrack(s, 0, new ArrayList<>(), result);

        return result;
    }

    private void backtrack(
        String s,
        int index,
        List<String> current,
        List<List<String>> result
    ) {

        // Base case: entire string has been partitioned
        if (index == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Try every possible ending position
        for (int i = index; i < s.length(); i++) {

            String substring = s.substring(index, i + 1);

            // Only select palindromic substrings
            if (isPalindrome(substring)) {

                // Choose
                current.add(substring);

                // Explore remaining string
                backtrack(s, i + 1, current, result);

                // Undo
                current.remove(current.size() - 1);
            }
        }
    }

    private boolean isPalindrome(String str) {

        int left = 0;
        int right = str.length() - 1;

        while (left < right) {

            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }
}