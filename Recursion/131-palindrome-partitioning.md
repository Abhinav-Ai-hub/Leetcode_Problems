# 131. Palindrome Partitioning

## Problem Description

Given a string `s`, partition `s` such that every substring of the partition is a palindrome.

Return all possible palindrome partitioning of `s`.

A palindrome is a string that reads the same forward and backward.

### Example 1

**Input:**

```text
s = "aab"
```

**Output:**

```text
[["a","a","b"],["aa","b"]]
```

**Explanation:**

There are two valid palindrome partitions:

1. `"a" + "a" + "b"`
2. `"aa" + "b"`

Every substring in both partitions is a palindrome.

### Example 2

**Input:**

```text
s = "a"
```

**Output:**

```text
[["a"]]
```

### Example 3

**Input:**

```text
s = "efe"
```

**Output:**

```text
[["e","f","e"],["efe"]]
```

---

## Approach: Backtracking

### Intuition

We need to divide a string into multiple substrings such that every selected substring is a palindrome.

At every index, we have multiple choices because we can select a substring ending at different positions.

For example:

```text
s = "aab"
```

Starting from index `0`, the possible substrings are:

```text
"a"
"aa"
"aab"
```

We check whether each substring is a palindrome.

If it is a palindrome, we add it to our current partition and recursively process the remaining string.

If it is not a palindrome, we skip that choice.

Once the entire string has been processed, we store the current partition in the result list.

### Why Backtracking?

This problem requires generating all possible valid partitions.

We must explore every possible palindromic substring at each position.

The pattern is:

**Choose → Explore → Undo**

### Recursive State

We maintain:

* `index`: Starting position of the next substring.
* `current`: Stores the palindrome substrings selected so far.
* `result`: Stores all valid partitions.

### Base Case

When the index reaches the length of the string, the entire string has been partitioned successfully.

```java
if (index == s.length()) {
    result.add(new ArrayList<>(current));
    return;
}
```

We create a new ArrayList because `current` is modified during backtracking. We need to preserve a snapshot of the completed partition.

---

## Algorithm

1. Create an empty result list.
2. Start backtracking from index `0` with an empty current list.
3. If the index reaches the end of the string:

   * Store a copy of the current partition.
   * Return.
4. Iterate through all possible ending positions starting from the current index.
5. Extract the substring from `index` to `i`.
6. Check whether the substring is a palindrome.
7. If it is a palindrome:

   * Add it to the current partition.
   * Recursively process the remaining string starting at `i + 1`.
   * Remove the last selected substring.
8. Continue until all possible partitions have been explored.

---

## Java Solution

```java
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

        if (index == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = index; i < s.length(); i++) {

            String substring = s.substring(index, i + 1);

            if (isPalindrome(substring)) {

                current.add(substring);

                backtrack(s, i + 1, current, result);

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
```

---

## Dry Run

Consider:

```text
s = "aab"
```

Initially:

```text
index = 0
current = []
```

### Step 1: Select "a"

```text
current = ["a"]
index = 1
```

The remaining substring is `"ab"`.

At index `1`, we can select:

* `"a"`: Valid palindrome.
* `"ab"`: Invalid palindrome.

Select `"a"`:

```text
current = ["a", "a"]
index = 2
```

### Step 2: Select "b"

```text
current = ["a", "a", "b"]
index = 3
```

The entire string has been processed.

Store:

```text
["a", "a", "b"]
```

### Step 3: Backtrack

Remove `"b"` and then the second `"a"`.

The current partition becomes:

```text
["a"]
```

Now explore other choices.

### Step 4: Select "aa"

Return to index `0`.

```text
current = ["aa"]
index = 2
```

Select `"b"`:

```text
current = ["aa", "b"]
index = 3
```

Store:

```text
["aa", "b"]
```

### Step 5: Reject "aab"

The substring `"aab"` is not a palindrome, so we skip it.

### Final Output

```text
[
    ["a", "a", "b"],
    ["aa", "b"]
]
```

---

## Complexity Analysis

Let `n` be the length of the input string.

### Time Complexity

There are at most `2^(n-1)` possible ways to place or not place a partition boundary between adjacent characters.

For each partition, we may perform palindrome checks.

Using the straightforward substring and palindrome-checking implementation, a safe upper bound is:

**O(n² × 2^n)**

The output itself can also contain exponentially many partitions.

### Auxiliary Space Complexity

* Recursion depth: O(n)
* Current partition: O(n) total characters across its selected substrings.
* Palindrome checking: O(1) auxiliary space, excluding substring creation.

Therefore, auxiliary space is **O(n)**, excluding the output and temporary substring allocation.

---

## Pattern Recognition

Think of backtracking when:

1. You need to partition a string into multiple substrings.
2. Each substring must satisfy a particular condition.
3. You need all possible valid partitions.
4. Every choice determines where the next recursive call begins.

For Palindrome Partitioning:

| Concept                   | Purpose                                   |
| ------------------------- | ----------------------------------------- |
| `index`                   | Starting position of the next substring   |
| `i`                       | Ending position of the selected substring |
| `substring(index, i + 1)` | Extracts the current choice               |
| `isPalindrome()`          | Validates the selected substring          |
| `current.add()`           | Chooses the substring                     |
| `backtrack(i + 1)`        | Explores the remaining string             |
| `current.remove()`        | Undoes the selection                      |

### Key Takeaway

The most important insight is that we do not need to decide all partition boundaries at once.

Start at the current index, try every possible ending position, select only palindromic substrings, and recursively solve the remaining string.

**Choose → Explore → Undo**
