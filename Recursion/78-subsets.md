# LeetCode 78: Subsets

## 📌 Problem Description

Given an integer array `nums` containing unique elements, return all possible subsets (the power set).

The solution set must not contain duplicate subsets.

You may return the subsets in any order.

### Example

**Input:**

```text
nums = [1,2,3]
```

**Output:**

```text
[
  [],
  [1],
  [2],
  [3],
  [1,2],
  [1,3],
  [2,3],
  [1,2,3]
]
```

---

# 💡 Intuition

The main idea is:

> For every element, we have a choice: include it in the subset or don't include it.

For example:

```text
        1
       / \
    Take  Skip
```

Then the same decision is made for `2` and `3`.

Therefore, the problem naturally forms a recursion tree.

Every path from the root represents one possible subset.

---

# 🧠 How to Recognize This as a Recursion/Backtracking Problem

When a problem asks us to:

* Generate all subsets
* Generate all combinations
* Generate all possible choices
* Try multiple possibilities
* Include/exclude elements

we should think:

```text
Choice
  ↓
Recursion
  ↓
Undo the choice
  ↓
Try another choice
```

This is the basic pattern of **backtracking**.

---

# 🌳 Recursion Tree

For:

```text
nums = [1,2,3]
```

The choices can be visualized as:

```text
                         []
                    /          \
                  [1]          []
                /     \       /   \
             [1,2]    [1]   [2]    []
              /  \      \    \      \
       [1,2,3] [1,2]  [1,3] [2,3]   [3]
```

The actual implementation can generate the same subsets using a loop and recursion.

---

# 🔄 Backtracking Pattern

The fundamental pattern is:

```java
current.add(nums[i]);

generate(nums, i + 1, current, result);

current.remove(current.size() - 1);
```

Think:

```text
Choose
  ↓
Explore
  ↓
Undo
```

### Choose

```java
current.add(nums[i]);
```

Add the current number to the subset.

### Explore

```java
generate(nums, i + 1, current, result);
```

Recursively generate all possibilities after choosing that number.

### Undo

```java
current.remove(current.size() - 1);
```

Remove the number so that we can explore another possibility.

---

# 💻 Java Solution

```java
class Solution {
    public List<List<Integer>> subsets(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();

        subsetshelper(nums, 0, new ArrayList<>(), result);

        return result;
    }

    public void subsetshelper(
        int[] nums,
        int index,
        List<Integer> current,
        List<List<Integer>> result
    ) {

        // Base case
        if (index == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }

        // TAKE nums[index]
        current.add(nums[index]);

        subsetshelper(nums, index + 1, current, result);

        // BACKTRACK
        current.remove(current.size() - 1);

        // DON'T TAKE nums[index]
        subsetshelper(nums, index + 1, current, result);
    }
}
```

---

# 🔍 Dry Run

Consider:

```text
nums = [1,2]
```

Initially:

```text
current = []
```

Add it:

```text
result = [[]]
```

Choose `1`:

```text
current = [1]
```

Add it:

```text
result = [[], [1]]
```

Choose `2`:

```text
current = [1,2]
```

Add it:

```text
result = [[], [1], [1,2]]
```

Now recursion finishes.

Backtrack:

```text
[1,2]
   ↓ remove 2
[1]
```

Then return to the previous level.

Backtrack again:

```text
[1]
 ↓ remove 1
[]
```

Now choose `2`:

```text
[2]
```

Add it:

```text
result = [
    [],
    [1],
    [1,2],
    [2]
]
```

Final answer:

```text
[
    [],
    [1],
    [1,2],
    [2]
]
```

---

# 📊 Why Are There 2ⁿ Subsets?

Every element has exactly two possibilities:

```text
Include
   OR
Exclude
```

For `n` elements:

```text
2 × 2 × 2 × ... × 2
          n times
```

Therefore:

```text
Number of subsets = 2ⁿ
```

For example:

```text
n = 3

2³ = 8
```

So `[1,2,3]` has exactly 8 subsets.

---

# ⏱️ Complexity

There are `2ⁿ` subsets.

Each subset can contain up to `n` elements.

Therefore:

### Time Complexity

```text
O(n × 2ⁿ)
```

### Space Complexity

The recursion/backtracking structure requires approximately:

```text
O(n)
```

auxiliary recursion depth, excluding the space required to store the output.

The output itself contains `2ⁿ` subsets and can require:

```text
O(n × 2ⁿ)
```

space.

---

# 🎯 Key Takeaways

The most important idea is:

```text
For every element:
        ↓
    Make a choice
      /      \
   Take     Skip
      \      /
       Recursion
```

For backtracking, remember:

```text
Choose
   ↓
Explore
   ↓
Undo
```

In code:

```java
current.add(nums[i]);

generate(nums, i + 1, current, result);

current.remove(current.size() - 1);
```

---

# 🔑 Pattern Recognition

When you see:

```text
"Generate all possible..."
"Find all subsets..."
"Find all combinations..."
"Choose or don't choose..."
```

Immediately ask:

> **Does every element give me a choice?**

If yes, think:

```text
Recursion
    +
Backtracking
```

For this problem:

```text
Subset
  ↓
Every element → Include / Exclude
  ↓
Recursion
  ↓
Undo previous choice
  ↓
Backtracking
```

---

# ⭐ Mental Model

Don't try to memorize the code.

Remember this:

> **I am standing at an element. I choose it, explore everything possible after choosing it, then undo my choice and try the next possibility.**

That is the essence of recursion + backtracking.
