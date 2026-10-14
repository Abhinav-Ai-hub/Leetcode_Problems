# LeetCode 77: Combinations

## Problem Description

Given two integers `n` and `k`, return all possible combinations of `k` numbers chosen from the range `[1, n]`.

The order of the combinations does not matter.

### Example 1

**Input:**

```text
n = 4, k = 2
```

**Output:**

```text
[[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
```

**Explanation:**

There are six ways to select two numbers from the numbers `1`, `2`, `3`, and `4`.

### Example 2

**Input:**

```text
n = 1, k = 1
```

**Output:**

```text
[[1]]
```

## 1. Hint — How to Recognize Backtracking

The question asks us to generate every possible combination of `k` numbers from `1` to `n`.

This suggests backtracking because:

* We need to explore multiple possible selections.
* Each selection must contain exactly `k` elements.
* After exploring one choice, we must undo it and try another.

The pattern is:

**Choose → Explore → Undo**

Because combinations do not depend on order, `[1,2]` and `[2,1]` are considered the same combination.

To avoid duplicate combinations, we use a starting index and only choose numbers greater than the previously selected number.

## 2. Intuition

Suppose:

```text
n = 4
k = 2
```

We need to select two numbers from:

```text
1, 2, 3, 4
```

We begin with an empty list:

```text
current = []
```

First, choose `1`.

```text
current = [1]
```

Now we can select `2`, `3`, or `4`.

This generates:

```text
[1,2]
[1,3]
[1,4]
```

After exploring every combination beginning with `1`, we backtrack and choose `2`.

This generates:

```text
[2,3]
[2,4]
```

Finally, choosing `3` generates:

```text
[3,4]
```

The complete result is:

```text
[[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
```

## 3. Algorithm

1. Create an empty result list.
2. Start the recursive function from number `1`.
3. Maintain a list named `current` to store the current combination.
4. If `current.size() == k`, add a copy of `current` to the result and return.
5. Iterate through the available numbers from `start` to `n`.
6. Add the current number to `current`.
7. Recursively explore the next numbers by passing `i + 1` as the new starting index.
8. Remove the last selected number to backtrack.
9. Return the result after exploring all possibilities.

## 4. Java Solution

```java
import java.util.*;

class Solution {

    public List<List<Integer>> combine(int n, int k) {

        List<List<Integer>> result = new ArrayList<>();

        generate(1, n, k, new ArrayList<>(), result);

        return result;
    }

    private void generate(
            int start,
            int n,
            int k,
            List<Integer> current,
            List<List<Integer>> result) {

        // A complete combination has been created
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Try every available number
        for (int i = start; i <= n; i++) {

            // Choose
            current.add(i);

            // Explore
            generate(
                i + 1,
                n,
                k,
                current,
                result
            );

            // Undo / Backtrack
            current.remove(current.size() - 1);
        }
    }
}
```

## 5. Understanding the Important Variables

### `start`

The `start` variable specifies the smallest number that can be selected next.

For example, if the current combination is:

```text
[2]
```

the next recursive call starts from `3`.

This prevents generating `[2,1]` after already generating `[1,2]`.

### `current`

The `current` list stores the combination being constructed.

For example:

```text
current = [1,3]
```

means that we have currently selected the numbers `1` and `3`.

### `result`

The `result` list stores all completed combinations.

We add a copy of `current` because backtracking modifies the original list.

### `current.size() == k`

This is the base case.

When the current list contains exactly `k` numbers, we have found one complete combination.

We save it and return.

## 6. Why Do We Pass `i + 1`?

Consider this recursive call:

```java
generate(i + 1, n, k, current, result);
```

Suppose the current number is `2`.

After choosing `2`, the next call starts at `3`.

This ensures:

* Numbers are selected in increasing order.
* A number is not selected again in the same combination.
* Duplicate combinations caused by different orderings are avoided.

For combination problems where candidates can be reused, the recursive index may instead remain `i`. Always check the problem's reuse condition.

## 7. Backtracking: Choose, Explore, Undo

```java
current.add(i);

generate(i + 1, n, k, current, result);

current.remove(current.size() - 1);
```

**Choose:**

Add a number to the current combination.

**Explore:**

Recursively find all combinations that can be formed after this choice.

**Undo:**

Remove the number so another choice can be explored.

Backtracking allows us to reuse the same list instead of creating a new list at every recursive call.

## 8. Recursion Tree

For `n = 4` and `k = 2`:

```text
                         []
                    /    |    \
                  [1]   [2]   [3]
                / | \   / \     \
           [1,2][1,3][1,4][2,3][2,4][3,4]
```

Every path represents a sequence of choices.

Whenever the current combination reaches size `k`, we add it to the result.

## 9. Complexity Analysis

The number of combinations is:

C(n, k) = n! / (k! * (n-k)!)

Each combination contains `k` elements.

**Time Complexity:**

O(k * C(n, k)), accounting for copying all combinations, with additional search overhead.

**Output Space Complexity:**

O(k * C(n, k)), because we store all combinations.

**Auxiliary Space Complexity:**

O(k), for the current combination and recursion depth, excluding the output.

## 10. Pattern Recognition

Think of backtracking when a problem asks you to:

* Generate all possible combinations.
* Select a fixed number of elements.
* Explore alternative choices.
* Undo choices to explore other branches.

For this problem:

**Generate combinations + fixed selection size + order does not matter = Backtracking with a starting index.**

## 11. Comparison With Related Problems

| Problem                    | Main technique                                                          |
| -------------------------- | ----------------------------------------------------------------------- |
| LC 46 — Permutations       | Backtracking with `used[]`                                              |
| LC 77 — Combinations       | Backtracking with `start`                                               |
| LC 78 — Subsets            | Backtracking; every selection size is allowed                           |
| LC 39 — Combination Sum    | Backtracking; candidates can be reused                                  |
| LC 40 — Combination Sum II | Backtracking; each occurrence can be used once, with duplicate skipping |

## Final Takeaway

Remember this pattern:

```text
Need all combinations
        ↓
Choose a number
        ↓
Explore the remaining numbers
        ↓
Undo the choice
        ↓
Continue until k numbers are selected
```

**LeetCode 77 = Backtracking + Starting Index + Fixed Combination Size**
