# LeetCode 39: Combination Sum

## 📌 Problem Description

Given an array of distinct integers `candidates` and an integer `target`, return a list of all unique combinations of `candidates` where the chosen numbers sum to `target`.

You may use the same number from `candidates` an unlimited number of times.

The combinations can be returned in any order.

### Example 1

**Input:**

```text
candidates = [2,3,6,7]
target = 7
```

**Output:**

```text
[
    [2,2,3],
    [7]
]
```

### Explanation

The combination:

```text
2 + 2 + 3 = 7
```

and:

```text
7 = 7
```

are both valid.

---

# 💡 Intuition

The problem asks us to find:

> **All possible combinations that add up to a target.**

Whenever a problem asks us to generate all possible combinations, we should think about:

```text
Recursion
+
Backtracking
```

At every step, we can choose one of the candidate numbers.

For example:

```text
candidates = [2,3,6,7]
target = 7
```

We can choose:

```text
2
3
6
7
```

After choosing a number, we reduce the remaining target.

For example, if we choose `2`:

```text
target = 7

choose 2

remaining target = 7 - 2 = 5
```

Then we recursively solve:

```text
Combination Sum for target 5
```

---

# 🧠 How to Recognize This as Recursion + Backtracking

Look for these clues:

```text
"Find all combinations"
"Find all possible ways"
"Sum should equal target"
```

This suggests:

```text
Make a choice
     ↓
Explore that choice
     ↓
Undo the choice
     ↓
Try another choice
```

This is the classic backtracking pattern.

---

# ⭐ Most Important Observation

The problem says:

> We can use the same candidate multiple times.

Therefore, after choosing `candidates[i]`, we should **not** move to `i + 1`.

Instead:

```java
generate(candidates, target - candidates[i], i, current, result);
```

We pass:

```text
i
```

again.

This allows:

```text
2
2
2
2
...
```

to be selected repeatedly.

---

# 🔄 Compare With Subsets

### LeetCode 78: Subsets

```java
generate(nums, i + 1, ...)
```

because an element cannot be reused.

### LeetCode 39: Combination Sum

```java
generate(candidates, target - candidates[i], i, ...)
```

because an element can be reused.

### Mental Rule

```text
Can reuse element?
        |
       Yes
        ↓
      use i

Cannot reuse?
        |
       Yes
        ↓
    use i + 1
```

---

# 🛑 Base Cases

There are two important conditions.

## Case 1: Target becomes zero

```java
if (target == 0)
```

This means we found a valid combination.

For example:

```text
2 + 2 + 3 = 7
```

Remaining target:

```text
0
```

So we add the current combination:

```java
result.add(new ArrayList<>(current));
```

---

## Case 2: Target becomes negative

```java
if (target < 0)
```

This means the current combination has exceeded the target.

For example:

```text
target = 1

choose 2

remaining target = -1
```

There is no point exploring this branch further.

So we return.

---

# 🌳 Recursion Tree

For:

```text
candidates = [2,3,6,7]
target = 7
```

One important branch is:

```text
                     target = 7
                          |
                        choose 2
                          |
                     target = 5
                          |
                        choose 2
                          |
                     target = 3
                          |
                        choose 2
                          |
                     target = 1
                          |
                     choose 2
                          |
                     target = -1
                          |
                       return
```

Backtracking:

```text
[2,2,2,2]
      ↓
remove 2
      ↓
[2,2,2]
```

Now try `3`:

```text
[2,2,3]
```

Remaining target:

```text
7 - 2 - 2 - 3 = 0
```

Therefore:

```text
[2,2,3]
```

is added to the result.

Another valid branch is:

```text
[7]
```

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
    i,
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

This pattern appears in many problems involving:

* Subsets
* Combinations
* Permutations
* Combination Sum
* N-Queens
* Sudoku
* Maze/path problems

---

# 💻 Java Solution

```java
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
         backtrack(candidates, target, 0, new ArrayList<>(), ans);
        return ans;
    }
        
    
    void backtrack(int[] candidates ,int target ,int index ,List<Integer> current,
                            List<List<Integer>> ans ){
                                if(target==0){
                                    ans.add(new ArrayList<>(current));
                                    return ;
                                }
                                if(target<0 || index==candidates.length){
                                    return; 
                                }
                                current.add(candidates[index]);
                                backtrack(candidates, target - candidates[index],
                  index, current, ans);
                            current.remove(current.size()-1);
                            backtrack(candidates,target,index+1,current,ans);
                            
                            }
}
```

---

# 🔍 Dry Run

Consider:

```text
candidates = [2,3]
target = 7
```

Start:

```text
current = []
target = 7
```

Choose `2`:

```text
current = [2]
target = 5
```

Choose `2` again:

```text
current = [2,2]
target = 3
```

Choose `2` again:

```text
current = [2,2,2]
target = 1
```

Choose `2`:

```text
current = [2,2,2,2]
target = -1
```

Invalid.

Backtrack:

```text
[2,2,2,2]
       ↓
[2,2,2]
```

Now try `3`:

```text
[2,2,2,3]
```

Target:

```text
7 - 2 - 2 - 2 - 3 = -2
```

Invalid.

Backtrack further.

Eventually:

```text
[2,2,3]
```

Target:

```text
7 - 2 - 2 - 3 = 0
```

Valid!

So:

```text
[2,2,3]
```

is added.

---

# ⏱️ Complexity

The exact complexity depends on the values in `candidates` and `target`.

The recursion explores many possible combinations.

The important point is that this is an **exponential/backtracking problem**.

The recursion depth is at most approximately:

```text
target / minimumCandidate
```

because the smallest candidate can be repeatedly selected.

Space is also required for:

```text
current combination
+
recursion stack
+
output
```

---

# 🎯 Key Takeaways

The core idea is:

```text
Find all combinations
        ↓
Backtracking
        ↓
Choose a number
        ↓
Subtract it from target
        ↓
Recursively continue
        ↓
Undo the choice
```

The most important detail:

```java
generate(..., i, ...)
```

instead of:

```java
generate(..., i + 1, ...)
```

because:

> **The same candidate can be used unlimited times.**

---

# 🔑 Pattern Recognition

Remember these three problems together:

### LeetCode 78 — Subsets

```text
Choose elements
↓
Cannot reuse
↓
i + 1
```

### LeetCode 90 — Subsets II

```text
Choose elements
↓
Duplicates exist
↓
Sort + skip duplicates
```

### LeetCode 39 — Combination Sum

```text
Choose elements
↓
Need target sum
↓
Can reuse
↓
Use i again
```

---

# ⭐ Mental Model

Whenever you see:

> **"Find all combinations whose sum is target."**

Think:

```text
                    Target
                      ↓
                 Make a choice
                      ↓
              Subtract from target
                      ↓
                  Recursion
                      ↓
              Target == 0 ?
                /          \
              Yes           No
               ↓             ↓
             Store       Continue
                            ↓
                         Backtrack
```

And remember the golden rule:

> **Choose → Explore → Undo**

For Combination Sum:

> **Choose a candidate → reduce the target → recursively explore → undo the candidate.**
