# LeetCode 46: Permutations

## 📌 Problem Description

Given an array `nums` of distinct integers, return all the possible permutations.

You can return the answer in any order.

A permutation is an arrangement of all the elements where the order of the elements matters.

### Example

**Input:**

```text
nums = [1,2,3]
```

**Output:**

```text
[
    [1,2,3],
    [1,3,2],
    [2,1,3],
    [2,3,1],
    [3,1,2],
    [3,2,1]
]
```

There are:

```text
3! = 6
```

possible permutations.

---

# 💡 Intuition

The important phrase in this problem is:

> **Generate all permutations.**

A permutation means that **every element must be used exactly once**, but the order can change.

For every position, we ask:

> Which unused element should I place here?

For:

```text
nums = [1,2,3]
```

the first position has three choices:

```text
1
2
3
```

If we choose `1`, then only `2` and `3` remain.

So:

```text
Choose
   ↓
Explore
   ↓
Undo
```

This is the classic **Backtracking** pattern.

---

# 🧠 How to Recognize This as Backtracking

Whenever the problem says:

```text
Generate all arrangements
Generate all permutations
Try every ordering
```

think:

```text
Backtracking
```

The reason is that we need to explore multiple possible choices.

The general pattern is:

```text
Choose an element
        ↓
Mark it as used
        ↓
Recursively choose the next element
        ↓
Undo the choice
        ↓
Try another element
```

---

# 🔄 Recursive Thought Process

Suppose:

```text
nums = [1,2,3]
```

Initially:

```text
current = []
```

We need to fill position `0`.

Possible choices:

```text
1
2
3
```

Suppose we choose `1`:

```text
current = [1]
```

Now we recursively fill position `1`.

`1` cannot be used again, so the choices are:

```text
2
3
```

Suppose we choose `2`:

```text
current = [1,2]
```

Now only `3` remains:

```text
current = [1,2,3]
```

We have a complete permutation.

Then we backtrack:

```text
[1,2,3]
    ↓
remove 3
    ↓
[1,2]
```

Then try another choice.

---

# 🔥 Why `used[]`?

Every element must be used exactly once.

Therefore, we maintain:

```java
boolean[] used
```

For:

```text
nums = [1,2,3]
```

initially:

```text
used = [false,false,false]
```

If we choose `1`:

```text
used = [true,false,false]
```

This prevents `1` from being selected again.

After backtracking:

```text
used = [false,false,false]
```

so `1` can be used in another permutation.

---

# 🌳 Recursion Tree

For:

```text
nums = [1,2,3]
```

the recursion looks like:

```text
                         []
                  /       |       \
                 1        2        3
               /   \    /   \    /   \
             1,2  1,3 2,1  2,3 3,1  3,2
              |     |   |    |   |     |
            1,2,3 1,3,2 ...
```

Every root-to-leaf path represents one complete permutation.

---

# 🔁 Two Recursive Functions

Because we are specifically implementing this using recursive calls instead of a `for` loop, we separate two responsibilities.

### `generate()`

Moves to the next position:

```text
position 0
    ↓
position 1
    ↓
position 2
    ↓
position 3
```

### `choose()`

Recursively tries every possible number for the current position.

Instead of:

```java
for (int i = 0; i < nums.length; i++)
```

we use:

```java
choose(..., i + 1, ...)
```

Therefore, the loop itself is replaced by recursion.

---

# 🛑 Base Case

When:

```java
index == nums.length
```

all positions have been filled.

Therefore:

```java
result.add(new ArrayList<>(current));
```

We add a copy of the current permutation.

---

# 💻 Java Solution

```java
class Solution {
    private void recurPermute(int[] nums, List<Integer> ds, List<List<Integer>> ans, boolean []freq) {
        if(ds.size() == nums.length) {
            ans.add(new ArrayList<>(ds));
            return;
        }
        for(int i = 0; i < nums.length; i++) {
            if(!freq[i]) {// we are checking if the index element has already been used or free to be used
                freq[i] = true;// here we are declaring that yes now the elemtn at the endex i is picked
                ds.add(nums[i]);
                recurPermute(nums, ds, ans, freq);
                ds.remove(ds.size() - 1);
                freq[i] = false;// declaring that while backtracking since we removed the last element so now that elemnent can be used thatswhy in map we turn it to false again so that by condition checking it becomes true and enters the if clause
            }
        }
    }
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> ds = new ArrayList<>();
        boolean freq[] = new boolean[nums.length];
        recurPermute(nums, ds, ans, freq);
        return ans;
    }
}
```

---

# 🔍 Dry Run

Consider:

```text
nums = [1,2,3]
```

Initially:

```text
current = []
used = [false,false,false]
```

### Choose `1`

```text
current = [1]
used = [true,false,false]
```

### Choose `2`

```text
current = [1,2]
used = [true,true,false]
```

### Choose `3`

```text
current = [1,2,3]
```

Now:

```text
index == nums.length
```

so:

```text
result = [[1,2,3]]
```

Backtrack:

```text
current = [1,2]
used = [true,true,false]
```

Remove `2`:

```text
current = [1]
used = [true,false,false]
```

Now choose `3`:

```text
current = [1,3]
```

Then choose `2`:

```text
current = [1,3,2]
```

So another permutation is generated.

The same process continues for starting elements `2` and `3`.

---

# ⏱️ Complexity

For `n` elements, there are:

```text
n!
```

permutations.

For every permutation, we spend `O(n)` time constructing/copying it.

Therefore:

### Time Complexity

```text
O(n × n!)
```

### Space Complexity

The recursion depth is:

```text
O(n)
```

The `used` array requires:

```text
O(n)
```

The result itself contains:

```text
n! × n
```

elements.

Therefore, including the output:

```text
O(n × n!)
```

---

# ⚡ Important Difference: Subsets vs Permutations

| Problem              | Main Question                                         |
| -------------------- | ----------------------------------------------------- |
| 78. Subsets          | Should I include this element?                        |
| 39. Combination Sum  | Which element should I choose next?                   |
| **46. Permutations** | **Which unused element should occupy this position?** |

For subsets:

```text
Include / Exclude
```

For permutations:

```text
Choose any unused element
        ↓
Recursively fill next position
        ↓
Backtrack
```

---

# 🎯 Final Mental Shortcut

When you see:

> **"Generate all permutations."**

Think immediately:

```text
Permutation
     ↓
Every position needs an element
     ↓
Choose any UNUSED element
     ↓
Mark used
     ↓
Recursive call
     ↓
Backtrack
     ↓
Try next unused element
```

The core pattern is:

```text
Choose → Explore → Undo
```

And because you requested a solution using recursive calls rather than a `for` loop, the iteration over candidate elements is itself handled by:

```java
choose(..., i + 1, ...)
```

So this problem is still pure recursive backtracking.
