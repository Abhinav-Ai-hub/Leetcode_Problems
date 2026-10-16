# LeetCode 216: Combination Sum III

## Problem Description

Find all valid combinations of `k` numbers that add up to `n`, subject to the following conditions:

* Only numbers from `1` to `9` can be used.
* Each number can be used at most once.
* Every combination must contain exactly `k` numbers.
* The sum of the selected numbers must equal `n`.

Return all valid combinations.

The order of the combinations does not matter.

### Example 1

**Input:**

```text
k = 3, n = 7
```

**Output:**

```text
[[1,2,4]]
```

**Explanation:**

The combination `[1,2,4]` contains exactly three distinct numbers, and their sum is:

```text
1 + 2 + 4 = 7
```

### Example 2

**Input:**

```text
k = 3, n = 9
```

**Output:**

```text
[[1,2,6],[1,3,5],[2,3,4]]
```

**Explanation:**

Each combination contains exactly three distinct numbers whose sum is `9`.

### Example 3

**Input:**

```text
k = 4, n = 1
```

**Output:**

```text
[]
```

**Explanation:**

It is impossible to select four distinct positive numbers from `1` to `9` whose sum is `1`.

---

## 1. Hint — How to Recognize Backtracking

The problem asks us to generate every combination satisfying three conditions:

1. Select exactly `k` numbers.
2. Use numbers only from `1` to `9`.
3. Make their sum equal to `n`.

This suggests backtracking because we need to explore different selections and keep only those that satisfy all the conditions.

The pattern is:

**Choose → Explore → Undo**

We also need a starting index because combinations do not depend on the order of their elements.

For example, `[1,2,4]` and `[2,1,4]` represent the same combination.

By always selecting numbers in increasing order, we avoid generating duplicate combinations.

### Pattern Recognition

When you see:

* Find all possible combinations.
* Select exactly `k` elements.
* The sum must equal a target.
* Each candidate can be used only once.

Think:

**Backtracking + Starting Index + Target Sum + Pruning**

---

## 2. Intuition

Suppose:

```text
k = 3
n = 7
```

We need three distinct numbers whose sum is `7`.

Start with:

```text
current = []
target = 7
start = 1
```

Choose `1`:

```text
current = [1]
target = 6
```

Choose `2`:

```text
current = [1,2]
target = 4
```

Choose `4`:

```text
current = [1,2,4]
target = 0
```

We have selected exactly three numbers, and the remaining target is zero.

Therefore, `[1,2,4]` is a valid combination.

The key idea is to subtract every selected number from the remaining target rather than repeatedly calculating the sum of the current combination.

---

## 3. Algorithm

1. Create an empty result list.
2. Start the recursive function from number `1`.
3. Maintain a list called `current` to store the selected numbers.
4. Maintain `target` as the remaining sum required.
5. If `current.size() == k`, check whether `target == 0`.
6. If both conditions are satisfied, add a copy of `current` to the result.
7. Otherwise, iterate through candidates from `start` to `9`.
8. Skip candidates greater than the remaining target.
9. Choose a candidate by adding it to `current`.
10. Recursively explore the remaining numbers using `i + 1` and `target - i`.
11. Remove the selected candidate to backtrack.
12. Return all valid combinations.

---

## 4. Java Solution

```java
import java.util.*;

class Solution {

    public List<List<Integer>> combinationSum3(int k, int n) {

        List<List<Integer>> result = new ArrayList<>();

        generate(1, k, n, new ArrayList<>(), result);

        return result;
    }

    private void generate(
            int start,
            int k,
            int target,
            List<Integer> current,
            List<List<Integer>> result) {

        // Exactly k numbers have been selected
        if (current.size() == k) {

            // Accept only combinations with the required sum
            if (target == 0) {
                result.add(new ArrayList<>(current));
            }

            return;
        }

        // Try available numbers from start to 9
        for (int i = start; i <= 9 && i <= target; i++) {

            // Choose
            current.add(i);

            // Explore
            generate(
                i + 1,
                k,
                target - i,
                current,
                result
            );

            // Undo / Backtrack
            current.remove(current.size() - 1);
        }
    }
}
```

---

## 5. Understanding the Important Variables

### `start`

The smallest number that can be selected next.

```java
generate(i + 1, k, target - i, current, result);
```

If we choose `3`, the next recursive call starts from `4`.

This prevents reusing numbers and avoids duplicate combinations.

### `k`

The required number of elements in each combination.

```java
current.size() == k
```

When the current combination contains `k` elements, we stop extending that branch.

### `target`

The sum still required to form a valid combination.

Initially:

```text
target = n
```

After selecting a number:

```text
target = target - selectedNumber
```

When `target == 0` and exactly `k` numbers have been selected, the combination is valid.

### `current`

Stores the numbers selected in the current recursive path.

For example:

```text
current = [1,2,4]
```

We add a copy to the result because `current` is modified during backtracking.

---

## 6. Why Do We Use `i + 1`?

The recursive call is:

```java
generate(i + 1, k, target - i, current, result);
```

Every number can be used at most once.

If we choose `2`, the next recursive call begins from `3`.

Therefore, we cannot choose `2` again.

It also ensures that numbers remain in increasing order.

### Compare LC 39 and LC 216

| Feature                            | LC 39: Combination Sum | LC 216: Combination Sum III |
| ---------------------------------- | ---------------------- | --------------------------- |
| Candidate numbers                  | Given in an array      | Fixed range from 1 to 9     |
| Reuse candidates?                  | Yes                    | No                          |
| Recursive index                    | `i`                    | `i + 1`                     |
| Target sum                         | Yes                    | Yes                         |
| Exact number of elements required? | No                     | Yes, exactly `k`            |

---

## 7. Backtracking: Choose, Explore, Undo

```java
current.add(i);

generate(
    i + 1,
    k,
    target - i,
    current,
    result
);

current.remove(current.size() - 1);
```

### Choose

Add the selected number to the current combination.

### Explore

Recursively explore all possibilities using the remaining numbers.

### Undo

Remove the last selected number so that another candidate can be tried.

This allows us to explore different combinations using the same list.

---

## 8. Pruning

Pruning means avoiding branches that cannot produce a valid answer.

In this solution:

```java
for (int i = start; i <= 9 && i <= target; i++)
```

We only consider numbers that are no greater than the remaining target.

For example, if:

```text
target = 3
```

choosing `4` cannot lead to a valid combination because all selected numbers are positive.

Therefore, we do not explore that choice.

Pruning reduces unnecessary recursive calls.

---

## 9. Recursion Tree

For `k = 3` and `n = 7`:

```text
                         []
                       target=7
                          |
                        Choose 1
                          |
                         [1]
                       target=6
                          |
                        Choose 2
                          |
                        [1,2]
                       target=4
                      /       \
                 Choose 3   Choose 4
                    |          |
                 [1,2,3]    [1,2,4]
                 target=1   target=0
                    |          |
                 Invalid      Valid
```

The combination `[1,2,4]` is accepted because it contains exactly three numbers and its sum equals `7`.

The algorithm continues exploring the remaining branches to find every valid combination.

---

## 10. Complexity Analysis

There are only nine possible candidate numbers.

Let `C` be the number of valid combinations.

**Time Complexity:**

A simple upper bound for exploring subsets is `O(2^9)`, excluding the cost of copying results.

The output and copying cost is `O(k * C)`.

**Auxiliary Space Complexity:**

`O(k)` for the recursion stack and the current combination.

**Output Space Complexity:**

`O(k * C)` to store all valid combinations.

---

## 11. Pattern Recognition Summary

| Problem clue              | What to think                            |
| ------------------------- | ---------------------------------------- |
| Find all combinations     | Backtracking                             |
| Exactly `k` elements      | Stop when `current.size() == k`          |
| Sum must equal `n`        | Maintain a remaining target              |
| Each number used once     | Move to `i + 1`                          |
| Candidates are positive   | Prune candidates greater than the target |
| Avoid duplicate orderings | Use a starting index                     |

---

## Final Mental Model

```text
Start from 1
    ↓
Choose a number
    ↓
Subtract it from target
    ↓
Move to the next number
    ↓
Have exactly k numbers?
    ↓
Yes → Is target zero?
    ↓
Yes → Save the combination
    ↓
Undo the choice
    ↓
Try another number
```

**LeetCode 216 = Backtracking + Fixed Combination Size + Target Sum + No Reuse + Pruning**
