# LeetCode 416: Partition Equal Subset Sum

## 📌 Problem Description

Given an integer array `nums`, return `true` if you can partition the array into two subsets such that the sum of the elements in both subsets is equal.

Otherwise, return `false`.

Each element can be used only once.

### Example 1

**Input:**

```text
nums = [1,5,11,5]
```

**Output:**

```text
true
```

### Explanation

The array can be divided into:

```text
[1,5,5] = 11
[11]    = 11
```

Therefore, the answer is:

```text
true
```

---

# 💡 Intuition

At first, the problem looks like:

> Can I divide the array into two equal subsets?

Instead of directly constructing two subsets, calculate the total sum.

For:

```text
nums = [1,5,11,5]
```

Total:

```text
1 + 5 + 11 + 5 = 22
```

If two subsets have equal sum:

```text
22 / 2 = 11
```

Therefore, we only need to ask:

> **Can I select some elements whose sum is 11?**

This converts the problem into a **Subset Sum** problem.

---

# 🧠 How to Recognize the Recursion Pattern

The key question is:

> For each element, should I take it or skip it?

Every element gives us two choices:

```text
Take
  OR
Skip
```

Therefore:

```text
Recursion
```

The recursive state is:

```text
(index, target)
```

Meaning:

> Can I create `target` using elements from `index` onward?

---

# 🔄 Recursive Formula

At every element:

```text
f(index, target)
```

has two choices.

### Choice 1: Take the element

If we take `nums[index]`:

```text
target becomes target - nums[index]
```

So:

```text
f(index + 1, target - nums[index])
```

### Choice 2: Skip the element

If we don't take it:

```text
f(index + 1, target)
```

Therefore:

```text
f(index, target)
=
take OR skip
```

or:

```text
f(index, target)
=
f(index + 1, target - nums[index])
OR
f(index + 1, target)
```

---

# 🛑 Base Cases

## Target becomes zero

```java
if (target == 0) {
    return true;
}
```

We successfully created the required subset.

---

## No elements remain

```java
if (index == nums.length) {
    return false;
}
```

We couldn't create the required sum.

---

# ⚠️ Important Observation: Odd Total

Suppose:

```text
nums = [1,2,4]
```

Total:

```text
7
```

We cannot divide `7` equally:

```text
7 / 2 = 3.5
```

Therefore:

```java
if (sum % 2 != 0) {
    return false;
}
```

This is an important early check.

---

# 🌳 Recursion Tree

Suppose:

```text
nums = [1,5,11,5]
target = 11
```

The first element gives two choices:

```text
                    target = 11
                   /           \
                Take           Skip
                 1               1
                 ↓               ↓
              target 10       target 11
```

Then the next element again gives two choices:

```text
                 target 10
                /         \
             Take         Skip
              5             5
              ↓             ↓
           target 5      target 10
```

Eventually:

```text
1 + 5 + 5 = 11
```

So:

```text
target = 0
```

and we return:

```text
true
```

---

# 🔥 Overlapping Subproblems

Pure recursion can calculate the same state multiple times.

For example:

```text
solve(index = 3, target = 5)
```

might be reached through different paths.

Instead of recalculating it, store the answer.

This gives:

```text
Recursion
    ↓
Repeated states
    ↓
Memoization
    ↓
Dynamic Programming
```

---

# 🚀 Recursion + Memoization

We use:

```java
Boolean[][] dp;
```

where:

```text
dp[index][target]
```

stores:

> Whether it is possible to create `target` using elements from `index` onward.

There are three states:

```text
null  → not calculated
true  → possible
false → impossible
```

---

# 💻 Java Solution

```java
class Solution {

    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        // Equal partition is impossible
        // if total sum is odd
        if (sum % 2 != 0) {
            return false;
        }

        int target = sum / 2;

        Boolean[][] dp =
            new Boolean[nums.length][target + 1];

        return solve(nums, 0, target, dp);
    }

    private boolean solve(
            int[] nums,
            int index,
            int target,
            Boolean[][] dp) {

        // Required sum created
        if (target == 0) {
            return true;
        }

        // No elements remaining
        if (index == nums.length || target < 0) {
            return false;
        }

        // Already calculated
        if (dp[index][target] != null) {
            return dp[index][target];
        }

        // Take current element
        boolean take = solve(
            nums,
            index + 1,
            target - nums[index],
            dp
        );

        // Skip current element
        boolean skip = solve(
            nums,
            index + 1,
            target,
            dp
        );

        dp[index][target] = take || skip;

        return dp[index][target];
    }
}
Another Solution
class Solution {
    public boolean canPartition(int[] nums) {

        int sum = 0;

        // Calculate total sum
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        // Odd sum cannot be divided into two equal integer sums
        if (sum % 2 != 0) {
            return false;
        }

        int target = sum / 2;

        return canPartitionHelper(nums, 0, target);
    }

    boolean canPartitionHelper(int[] nums, int index, int target) {

        // We found a subset with required sum
        if (target == 0) {
            return true;
        }

        // No elements left or target became negative
        if (index == nums.length || target < 0) {
            return false;
        }

        // Take nums[index]
        boolean take = canPartitionHelper(
            nums,
            index + 1,
            target - nums[index]
        );

        // Don't take nums[index]
        boolean notTake = canPartitionHelper(
            nums,
            index + 1,
            target
        );

        return take || notTake;
    }
}
```

---

# 🔍 Dry Run

Consider:

```text
nums = [1,5,11,5]
```

### Step 1 — Calculate total

```text
1 + 5 + 11 + 5 = 22
```

### Step 2 — Find target

```text
22 / 2 = 11
```

Now:

```text
solve(0, 11)
```

### Step 3 — Take 1

```text
target = 11 - 1
       = 10
```

Now:

```text
solve(1, 10)
```

Take `5`:

```text
target = 10 - 5
       = 5
```

Take the next `5`:

```text
target = 5 - 5
       = 0
```

Therefore:

```text
true
```

The subset is:

```text
[1,5,5]
```

The remaining element is:
