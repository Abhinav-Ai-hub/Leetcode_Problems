# LeetCode 90: Subsets II

## 📌 Problem Description

Given an integer array `nums` that may contain duplicates, return all possible subsets (the power set).

The solution set must not contain duplicate subsets.

You may return the subsets in any order.

### Example

**Input:**

```text
nums = [1,2,2]
```

**Output:**

```text
[
    [],
    [1],
    [2],
    [1,2],
    [2,2],
    [1,2,2]
]
```

---

# 💡 Intuition

This problem is an extension of:

**LeetCode 78: Subsets**

In LeetCode 78, all elements are unique.

Here, duplicate elements are possible.

For every element, we still have the choice:

```text
Take
OR
Skip
```

So recursion and backtracking are still the correct pattern.

However, duplicates can cause duplicate subsets.

For example:

```text
nums = [1,2,2]
```

If we treat both `2`s independently at the same recursion level, we can generate:

```text
[2]
[2]
```

Therefore, we need to avoid choosing the same value twice at the same recursion level.

---

# 🧠 How to Recognize This as Recursion + Backtracking

Whenever a problem asks:

* Generate all subsets
* Generate all combinations
* Generate all possible choices
* Try different selections
* Include or exclude elements

think:

```text
Choice
  ↓
Recursion
  ↓
Backtrack
  ↓
Try another choice
```

For this problem, there is one additional complication:

```text
Duplicate elements
       ↓
Sort the array
       ↓
Skip duplicate choices
```

Therefore:

```text
Recursion + Backtracking + Duplicate Handling
```

---

# 🔑 Important Observation

First sort the array.

For:

```text
[2,1,2]
```

we get:

```text
[1,2,2]
```

Now duplicate values are next to each other.

This allows us to detect duplicates using:

```java
nums[i] == nums[i - 1]
```

---

# 🚨 The Most Important Line

```java
if (i > index && nums[i] == nums[i - 1]) {
    continue;
}
```

This means:

> If the current element is the same as the previous element, and we are at the same recursion level, skip it.

The condition:

```java
i > index
```

is extremely important.

It means that the duplicate is being considered as another choice at the same level.

---

# Example

Consider:

```text
nums = [1,2,2]
```

At the first level:

```text
[]
```

Possible choices:

```text
1
2
2
```

The two `2`s would create identical branches.

So we keep the first:

```text
[]
├── 1
└── 2
```

and skip the second `2`.

However, after choosing the first `2`:

```text
[]
 ↓
[2]
```

we are allowed to choose the second `2`:

```text
[2]
 ↓
[2,2]
```

Therefore:

```text
[2,2]
```

is valid.

---

# 🌳 Recursion Tree

For:

```text
nums = [1,2,2]
```

the important branches are:

```text
                         []
                    /          \
                  [1]          [2]
                /     \          \
             [1,2]   [1,2,2]    [2,2]
```

Final subsets:

```text
[]
[1]
[2]
[1,2]
[1,2,2]
[2,2]
```

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

For Subsets II, we add duplicate handling:

```text
Sort
  ↓
Skip duplicate at same level
  ↓
Choose
  ↓
Explore
  ↓
Undo
```

---

# 💻 Java Solution

```java
import java.util.*;

class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);

        HashSet<List<Integer>> set = new HashSet<>();

        subsetsWithDuphelper(nums, 0, new ArrayList<>(), set);

        return new ArrayList<>(set);
    }

    public void subsetsWithDuphelper(
        int[] nums,
        int index,
        List<Integer> current,
        HashSet<List<Integer>> set
    ) {

        if (index >= nums.length) {
            set.add(new ArrayList<>(current));
            return;
        }
        current.add(nums[index]);

        subsetsWithDuphelper(nums, index + 1, current, set);

        current.remove(current.size() - 1);

        subsetsWithDuphelper(nums, index + 1, current, set);
    }
}
```

---

# 🔍 Dry Run

Consider:

```text
nums = [1,2,2]
```

After sorting:

```text
[1,2,2]
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

Choose first `2`:

```text
current = [1,2]
```

Choose second `2`:

```text
current = [1,2,2]
```

Then backtrack:

```text
[1,2,2]
       ↓
[1,2]
```

Then:

```text
[1,2]
   ↓ remove 2
[1]
```

Now the second `2` at the same level is encountered.

Since:

```java
i > index
```

and:

```java
nums[i] == nums[i - 1]
```

we skip it.

Then we backtrack from `1`:

```text
[1]
 ↓
[]
```

Now choose `2`:

```text
[2]
```

Then choose the next `2`:

```text
[2,2]
```

So we get:

```text
[
    [],
    [1],
    [1,2],
    [1,2,2],
    [2],
    [2,2]
]
```

The ordering may differ, which is completely fine.

---

# 📊 Why Sorting Is Necessary

Without sorting:

```text
[2,1,2]
```

the duplicate values are separated.

After sorting:

```text
[1,2,2]
```

they become adjacent.

Now we can easily detect:

```java
nums[i] == nums[i - 1]
```

Therefore:

```text
Sorting
   ↓
Duplicates become adjacent
   ↓
Easy to skip duplicate branches
```

---

# ⏱️ Complexity

There can be up to `2ⁿ` possible subsets.

Each subset can contain up to `n` elements.

Therefore, the output-related time complexity is approximately:

```text
O(n × 2ⁿ)
```

Sorting takes:

```text
O(n log n)
```

So the overall complexity is dominated by generating the subsets.

### Space Complexity

Recursion depth:

```text
O(n)
```

Output storage can require:

```text
O(n × 2ⁿ)
```

space.

---

# 🎯 Key Takeaways

LeetCode 78:

```text
Unique elements
      ↓
Recursion + Backtracking
```

LeetCode 90:

```text
Duplicate elements
      ↓
Sort
      ↓
Recursion + Backtracking
      ↓
Skip duplicate choices at same level
```

The most important line is:

```java
if (i > index && nums[i] == nums[i - 1]) {
    continue;
}
```

---

# 🔑 Pattern Recognition

When you see:

```text
"Generate all subsets"
"Array contains duplicates"
"Return unique subsets"
```

think:

```text
Subsets
   ↓
Backtracking
   ↓
Sort
   ↓
Skip duplicates
```

The complete mental model is:

```text
                 Subsets
                    ↓
             Make a choice
                    ↓
               Recursion
                    ↓
             Backtrack
                    ↓
      Is this a duplicate choice?
             /             \
           Yes              No
            ↓                ↓
          Skip             Choose
```

---

# ⭐ Mental Model

For LeetCode 78:

> **Choose → Explore → Undo**

For LeetCode 90:

> **Sort → Skip duplicate choices → Choose → Explore → Undo**

Remember:

> **Duplicates are skipped only at the same recursion level, not across different levels.**

That is the central idea behind LeetCode 90.
