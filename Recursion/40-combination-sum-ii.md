# LeetCode 40: Combination Sum II

## 📌 Problem Description

Given a collection of candidate numbers `candidates` and a target integer `target`, find all unique combinations in `candidates` where the candidate numbers sum to `target`.

Each number in `candidates` may be used **at most once** in the combination.

The solution set must not contain duplicate combinations.

---

## Example

### Input

```text
candidates = [10,1,2,7,6,1,5]
target = 8
```

### Output

```text
[
    [1,1,6],
    [1,2,5],
    [1,7],
    [2,6]
]
```

The order of the combinations does not matter.

---

# 💡 Intuition

The problem asks us to find:

> All possible combinations whose sum is equal to `target`.

Whenever a problem asks us to generate all possible combinations, we should think:

```text
Recursion
+
Backtracking
```

At every step, we choose one candidate and reduce the remaining target.

For example:

```text
target = 8

choose 2

remaining target = 8 - 2
                  = 6
```

Then recursively find combinations that make `6`.

---

# 🧠 How to Recognize This as Recursion + Backtracking

Look for:

```text
"Find all combinations"
"Sum should equal target"
"Return all unique combinations"
```

This suggests:

```text
Make a choice
     ↓
Explore
     ↓
Undo the choice
     ↓
Try another choice
```

This is the classic backtracking pattern.

---

# ⭐ The Two Important Differences From LeetCode 39

LeetCode 40 is very similar to:

**LeetCode 39: Combination Sum**

But there are two major differences.

---

## 1. Each element can be used only once

In LeetCode 39:

```text
2 can be used multiple times
```

So we use:

```java
generate(..., i, ...)
```

In LeetCode 40:

```text
Each array element can be used at most once
```

So we use:

```java
generate(..., i + 1, ...)
```

### Important Rule

```text
Can reuse element?
        |
       Yes
        ↓
      use i

Cannot reuse element?
        |
       Yes
        ↓
    use i + 1
```

---

# 2. The Array Can Contain Duplicates

For example:

```text
[1,1,2]
```

If we blindly choose both `1`s at the same recursion level, we can generate duplicate combinations.

Therefore, we first sort:

```text
[1,1,2]
```

Now duplicates are adjacent.

Then we skip duplicate choices at the same recursion level:

```java
if (i > index && candidates[i] == candidates[i - 1]) {
    continue;
}
```

---

# 🔥 Understanding the Duplicate Condition

This line is the heart of the problem:

```java
if (i > index && candidates[i] == candidates[i - 1]) {
    continue;
}
```

It means:

> If the current value is the same as the previous value, and both are being considered at the same recursion level, skip the current one.

---

## Why `i > index`?

Suppose:

```text
candidates = [1,1,2]
```

At the first level:

```text
             []
          /  |  \
         1   1   2
```

The two `1`s would create duplicate branches.

So the second `1` is skipped.

But after choosing the first `1`:

```text
[]
 ↓
[1]
```

we are allowed to choose the second `1`:

```text
[1]
 ↓
[1,1]
```

because these are two different occurrences in the input.

Therefore:

```text
Skip duplicate at SAME level
BUT
Allow duplicate at DIFFERENT levels
```

This is the key concept.

---

# 🌳 Recursion Tree

Consider:

```text
candidates = [1,1,2,5]
target = 3
```

Sorted:

```text
[1,1,2,5]
```

The important recursion structure is:

```text
                         []
                    /          \
                   1            2
                 /   \
              [1,1] [1,2]
```

`[1,2]` reaches the target:

```text
1 + 2 = 3
```

`[1,1]` reaches:

```text
1 + 1 = 2
```

so it cannot continue to `3` using the remaining values.

The second `1` at the root is skipped because it would generate duplicate branches.

---

# 🔄 Backtracking Pattern

The standard pattern is:

```java
// Choose
current.add(candidates[i]);

// Explore
generate(
    candidates,
    target - candidates[i],
    i + 1,
    current,
    result
);

// Undo
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

---

# 🛑 Base Cases

## Case 1: Target becomes zero

```java
if (target == 0)
```

We found a valid combination.

Example:

```text
1 + 7 = 8
```

Remaining target:

```text
0
```

So we store the current combination.

```java
result.add(new ArrayList<>(current));
```

---

## Case 2: Target becomes negative

```java
if (target < 0)
```

The current combination has exceeded the target.

There is no point exploring further.

So we return.

Because the array is sorted, we can also avoid reaching this case frequently using:

```java
if (candidates[i] > target) {
    break;
}
```

---

# 💻 Java Solution

```java
import java.util.Arrays;
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        HashSet<List<Integer>> set = new HashSet<>();
         backtrack(candidates, target, 0, new ArrayList<>(), set);
         return new ArrayList<>(set);
        
    }
    void backtrack(int[] candidates ,int target ,int index ,List<Integer> current,
                             HashSet<List<Integer>> set){
      
        if(target==0){
          set.add(new ArrayList<>(current));
            return;
        }
        if(target<0 || index==candidates.length){
            return;
        }
        current.add(candidates[index]);
        backtrack(candidates, target - candidates[index],
                  index+1, current, set);
        current.remove(current.size()-1);
           // DON'T TAKE
        int nextIndex = index + 1;

        // Skip duplicate values
        while (nextIndex < candidates.length &&
               candidates[nextIndex] == candidates[index]) {
            nextIndex++;
        }
        backtrack(candidates, target,
                  nextIndex, current, set);// why not target-candidates[i]= its because we still havent taken the next element so why to subtract it 
    }
}
```

---

# 🔍 Dry Run

Consider:

```text
candidates = [1,1,2,5]
target = 3
```

Start:

```text
current = []
target = 3
```

Choose first `1`:

```text
current = [1]
target = 2
```

Now choose second `1`:

```text
current = [1,1]
target = 1
```

No remaining candidate can make `1`.

Backtrack:

```text
[1,1]
   ↓
[1]
```

Now choose `2`:

```text
current = [1,2]
target = 0
```

Valid combination:

```text
[1,2]
```

Store it.

Backtrack:

```text
[1,2]
   ↓
[1]
```

Then backtrack again:

```text
[1]
 ↓
[]
```

At the root, the second `1` is skipped:

```java
if (i > index && candidates[i] == candidates[i - 1])
```

Then choose `2`:

```text
[2]
```

Remaining target:

```text
1
```

No valid combination.

Final result:

```text
[
    [1,2]
]
```

---

# 📊 Complexity

Sorting takes:

```text
O(n log n)
```

The backtracking portion can explore exponentially many subsets/combinations.

A commonly used upper-bound description is:

```text
Time: O(2ⁿ)
```

excluding the cost of copying the output lists.

The output itself can contain exponentially many combinations, so the actual total work can be larger when accounting for copying each combination.

### Auxiliary Space

Recursion depth can be:

```text
O(n)
```

excluding the output.

---

# 🎯 Key Takeaways

The entire problem can be remembered as:

```text
Combination Sum
       ↓
Backtracking
       ↓
Sort
       ↓
Skip duplicates at same level
       ↓
Choose
       ↓
Move to i + 1
       ↓
Explore
       ↓
Undo
```

The two most important lines are:

### Skip duplicates

```java
if (i > index && candidates[i] == candidates[i - 1]) {
    continue;
}
```

### Don't reuse the same element

```java
generate(
    candidates,
    target - candidates[i],
    i + 1,
    current,
    result
);
```

---

# 🔑 Pattern Recognition: 39 vs 40

| Problem                    | Reuse element? | Duplicate input? | Recursive call |
| -------------------------- | -------------- | ---------------- | -------------- |
| **39. Combination Sum**    | Yes            | No               | `i`            |
| **40. Combination Sum II** | No             | Yes              | `i + 1`        |

### Mental shortcut

```text
39 → Reuse → i

40 → Once → i + 1
       +
   Duplicate → Skip
```

---

# ⭐ Final Mental Model

When you see **LeetCode 40**, immediately think:

> **"This is Combination Sum, but each array element can be used only once and duplicate combinations are not allowed."**

So:

```text
Sort
 ↓
Backtracking
 ↓
Skip duplicate at same level
 ↓
Choose candidate
 ↓
Call recursion with i + 1
 ↓
Backtrack
```

The golden rule:

> **Same level → skip duplicate. Different levels → duplicate values can still be selected if they are separate elements.**
