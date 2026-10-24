# 17. Letter Combinations of a Phone Number

## Problem Description

Given a string containing digits from `2` to `9` inclusive, return all possible letter combinations that the number could represent.

Return the answer in any order.

A mapping of digits to letters, just like on a telephone keypad, is provided below. Note that `1` does not map to any letters.

### Phone Keypad Mapping

| Digit | Letters |
| ----- | ------- |
| 2     | abc     |
| 3     | def     |
| 4     | ghi     |
| 5     | jkl     |
| 6     | mno     |
| 7     | pqrs    |
| 8     | tuv     |
| 9     | wxyz    |

### Example 1

**Input:**

```text
digits = "23"
```

**Output:**

```text
["ad","ae","af","bd","be","bf","cd","ce","cf"]
```

**Explanation:**

The digit `2` maps to `a`, `b`, and `c`.

The digit `3` maps to `d`, `e`, and `f`.

Combining every letter from the first digit with every letter from the second digit produces nine combinations.

### Example 2

**Input:**

```text
digits = ""
```

**Output:**

```text
[]
```

**Explanation:**

An empty input contains no digits, so there are no letter combinations to return.

### Example 3

**Input:**

```text
digits = "2"
```

**Output:**

```text
["a","b","c"]
```

---

## Approach: Backtracking

### Intuition

Each digit corresponds to multiple possible letters.

For example:

```text
2 → abc
3 → def
```

For the input `"23"`, we must select one letter from `abc` and one letter from `def`.

We can generate the combinations by choosing a letter for the current digit and recursively processing the next digit.

The process continues until every digit has been processed.

Whenever a complete combination is generated, we add it to our result list.

### Why Backtracking?

This problem requires generating all possible combinations rather than finding just one answer.

At every position, we have multiple choices. We must explore each choice and continue until we reach a complete combination.

This is a classic backtracking pattern:

**Choose → Explore → Record the complete answer**

Unlike some other backtracking problems, we do not need to undo a choice explicitly here because Java strings are immutable and `current + ch` creates a new string.

---

## Algorithm

1. Create an empty list named `result` to store the combinations.
2. If the input string is empty, return the empty list.
3. Create an array mapping each digit to its corresponding letters.
4. Call the recursive function with:

   * The input digits.
   * The current index, initially `0`.
   * An empty string representing the current combination.
   * The keypad mapping.
   * The result list.
5. In the recursive function:

   * If `index == digits.length()`, add the current combination to the result list and return.
   * Otherwise, find the letters corresponding to the digit at the current index.
   * Iterate through each possible letter.
   * Append the letter to the current combination.
   * Recursively process the next digit using `index + 1`.
6. Return the result list.

---

## Java Solution

```java
class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        
        if (digits == null || digits.length() == 0) {
            return res;
        }
        
        Map<Character, String> digitToLetters = new HashMap<>();
        digitToLetters.put('2', "abc");
        digitToLetters.put('3', "def");
        digitToLetters.put('4', "ghi");
        digitToLetters.put('5', "jkl");
        digitToLetters.put('6', "mno");
        digitToLetters.put('7', "pqrs");
        digitToLetters.put('8', "tuv");
        digitToLetters.put('9', "wxyz");
        
        backtrack(digits, 0, new StringBuilder(), res, digitToLetters);
        
        return res;        
    }

    private void backtrack(String digits, int idx, StringBuilder comb, List<String> res, Map<Character, String> digitToLetters) {
        if (idx == digits.length()) {
            res.add(comb.toString());
            return;
        }
        
        String letters = digitToLetters.get(digits.charAt(idx));
        for (char letter : letters.toCharArray()) {
            comb.append(letter);
            backtrack(digits, idx + 1, comb, res, digitToLetters);
            comb.deleteCharAt(comb.length() - 1);
        }
    }    
}
```

---

## Dry Run

Consider:

```text
digits = "23"
```

Initially:

```text
index = 0
current = ""
```

The first digit is `2`, which maps to `abc`.

### Step 1: Choose `a`

```text
current = "a"
index = 1
```

The next digit is `3`, which maps to `def`.

Choose `d`:

```text
current = "ad"
index = 2
```

Since `index == digits.length()`, store `"ad"`.

Next, choose `e`:

```text
current = "ae"
```

Store `"ae"`.

Next, choose `f`:

```text
current = "af"
```

Store `"af"`.

The combinations starting with `a` are:

```text
ad, ae, af
```

### Step 2: Choose `b`

Return to the first digit and choose `b`.

The next digit again provides `d`, `e`, and `f`.

The combinations are:

```text
bd, be, bf
```

### Step 3: Choose `c`

Similarly, the combinations are:

```text
cd, ce, cf
```

### Final Result

```text
["ad","ae","af","bd","be","bf","cd","ce","cf"]
```

---

## Complexity Analysis

Let `n` be the length of the input string.

Each digit maps to either three or four letters.

### Time Complexity

The maximum number of combinations is:

```text
4^n
```

Every complete combination contains `n` characters, so constructing and storing all combinations requires:

**Time Complexity: O(n × 4^n)**

This includes the work required to construct and store the output strings.

### Space Complexity

The recursion depth is `O(n)`.

The output may contain up to `4^n` strings, each of length `n`, requiring `O(n × 4^n)` space.

* Auxiliary recursion space: `O(n)`.
* Output space: `O(n × 4^n)`.

---

## Pattern Recognition

Recognize backtracking when a problem asks you to generate all possible combinations and each position offers several choices.

Ask yourself:

1. What are the possible choices at the current position?
2. How do I move to the next position?
3. When is one complete answer formed?
4. Do I need to undo my choice before exploring another branch?

For this problem:

* **Choices:** Letters mapped to the current digit.
* **Next step:** Process the next digit using `index + 1`.
* **Base case:** All digits have been processed.
* **Undo:** Not required because each recursive call constructs a new string.

### Key Takeaway

The central idea is to choose one letter for each digit and recursively explore every possible continuation.

This pattern is useful for problems involving letter combinations, generating strings, and exploring multiple choices at each position.
