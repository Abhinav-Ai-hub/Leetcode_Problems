# LeetCode 784: Letter Case Permutation

## 📌 Problem Description

Given a string `s`, you can transform every letter individually to be lowercase or uppercase.

Return a list of all possible strings that can be generated.

Digits should remain unchanged.

### Example 1

**Input:**

```text
s = "a1b2"
```

**Output:**

```text
["a1b2","a1B2","A1b2","A1B2"]
```

---

### Example 2

**Input:**

```text
s = "3z4"
```

**Output:**

```text
["3z4","3Z4"]
```

---

# 💡 Intuition

The important observation is:

> Every letter has exactly two possible choices.

For example:

```text
a → a / A
b → b / B
c → c / C
```

But a digit has only one possibility:

```text
1 → 1
5 → 5
9 → 9
```

Therefore, for every character:

```text
Letter
 /    \
lower  upper
```

This naturally creates a recursion tree.

---

# 🧠 How to Recognize This as Recursion

Whenever a problem says:

> Generate all possible combinations by making a choice at every position.

Think:

```text
Choice
   ↓
Recursive call
   ↓
Another choice
   ↓
Recursive call
```

Here, every letter gives two choices:

```text
lowercase
uppercase
```

Therefore:

```text
Letter Case Permutation
          ↓
      Two choices
          ↓
       Recursion
          ↓
     Backtracking
```

---

# 🔄 Recursive State

We only need:

```text
index
```

because `index` tells us which character we are currently processing.

Define:

```text
generate(chars, index)
```

as:

> Generate all valid permutations from `index` onward.

---

# 🌳 Recursion Tree

For:

```text
s = "a1b"
```

the recursion tree is:

```text
                    ""
                    |
                    a
                  /   \
                 a     A
                 |     |
                a1    A1
               / \    / \
            a1b a1B A1b A1B
```

Therefore:

```text
["a1b", "a1B", "A1b", "A1B"]
```

---

# 🔍 Step-by-Step Logic

Suppose:

```text
chars = ['a', '1', 'b']
```

### Index 0

Character:

```text
'a'
```

It is a letter.

So we have two choices:

```text
'a'
'A'
```

---

### Index 1

Character:

```text
'1'
```

It is a digit.

Therefore there is only one choice:

```text
'1'
```

We simply move to the next index.

---

### Index 2

Character:

```text
'b'
```

Again, two choices:

```text
'b'
'B'
```

---

### Index 3

We have processed the complete string.

Therefore:

```java
result.add(new String(chars));
```

---

# 🛑 Base Case

When:

```java
if (index == chars.length)
```

the complete permutation has been created.

So we add it to the result:

```java
result.add(new String(chars));
```

We use:

```java
new String(chars)
```

because we need to store a separate `String` object representing the current state.

---

# 💻 Java Solution

```java
import java.util.*;

class Solution {

    public List<String> letterCasePermutation(String s) {

        List<String> result = new ArrayList<>();

        generate(s.toCharArray(), 0, result);

        return result;
    }

    private void generate(
            char[] chars,
            int index,
            List<String> result) {

        // Complete string created
        if (index == chars.length) {
            result.add(new String(chars));
            return;
        }

        // Current character is a letter
        if (Character.isLetter(chars[index])) {

            // Choice 1: lowercase
            chars[index] =
                Character.toLowerCase(chars[index]);

            generate(chars, index + 1, result);

            // Choice 2: uppercase
            chars[index] =
                Character.toUpperCase(chars[index]);

            generate(chars, index + 1, result);

        } else {

            // Digit has only one possibility
            generate(chars, index + 1, result);
        }
    }
}

Another solution
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
```

---

# 🔍 Dry Run

Consider:

```text
s = "a1b"
```

Initially:

```text
chars = ['a', '1', 'b']
index = 0
```

### Choose lowercase `a`

```text
chars = ['a', '1', 'b']
```

Move to index `1`.

Digit `1` remains unchanged.

Move to index `2`.

Now choose lowercase `b`:

```text
"a1b"
```

Add it to the result.

Then choose uppercase `b`:

```text
"a1B"
```

Add it.

Now return to index `0`.

Choose uppercase `A`:

```text
chars = ['A', '1', 'b']
```

Again process `b`.

We get:

```text
"A1b"
"A1B"
```

Final result:

```text
["a1b", "a1B", "A1b", "A1B"]
```

---

# 🔥 Why Don't We Need `used[]`?

In **LeetCode 46: Permutations**, we needed:

```java
boolean[] used
```

because we could choose **any unused element** at every position.

Here, we process the string from left to right:

```text
index 0
   ↓
index 1
   ↓
index 2
   ↓
index 3
```

We never rearrange characters.

We only change:

```text
lowercase ↔ uppercase
```

Therefore, no `used[]` array is required.

---

# ⚡ Comparison with LeetCode 46

| Problem                          | Choice                            |
| -------------------------------- | --------------------------------- |
| 46. Permutations                 | Choose any unused element         |
| **784. Letter Case Permutation** | **Choose lowercase or uppercase** |

### 46:

```text
used[]
```

is required because elements can be arranged in any order.

### 784:

```text
index
```

is enough because characters remain in their original positions.

---

# ⏱️ Complexity

Suppose the string contains `L` letters.

Each letter has two possibilities:

```text
lowercase
OR
uppercase
```

Therefore, there are:

```text
2^L
```

possible strings.

For every result, we create a string of length `n`.

### Time Complexity

```text
O(n × 2^L)
```

where:

```text
L = number of letters
n = length of string
```

### Output Space

```text
O(n × 2^L)
```

because we store all generated strings.

### Recursion Stack

```text
O(n)
```

---

# 🎯 Final Mental Shortcut

When you see:

> **"Generate all possible strings by changing the case of letters."**

Think:

```text
Every character
      ↓
Is it a letter?
   /       \
 Yes       No
 /           \
lower/upper   keep same
     \        /
      recursion
          ↓
     next index
          ↓
      base case
```

The core pattern is:

```text
Letter
 ↓
Two choices
 ↓
Recursive calls
 ↓
Backtracking
```

And the most important difference from **46. Permutations** is:

> **46 rearranges elements; 784 only changes the case of each element while preserving its position.**
