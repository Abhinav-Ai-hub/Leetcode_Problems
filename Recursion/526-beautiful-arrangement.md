# LeetCode 526: Beautiful Arrangement

## Problem Description

Suppose you have an integer `n`.

We need to arrange the numbers:

```text
1, 2, 3, ..., n
```

into an array such that for every position `i`:

```text
arr[i] % i == 0
```

or

```text
i % arr[i] == 0
```

The position numbering starts from `1`.

Return the number of such beautiful arrangements.

### Example 1

```text
Input:
n = 2

Output:
2
```

The two valid arrangements are:

```text
[1, 2]
[2, 1]
```

Both satisfy the condition.

### Example 2

```text
Input:
n = 1

Output:
1
```

The only arrangement is:

```text
[1]
```

---

# 💡 1. Hint — How to Recognize Backtracking

The important part of the problem is:

> Arrange numbers from `1` to `n` while satisfying a condition at every position.

We are building an arrangement one position at a time.

At every position:

1. Choose an unused number.
2. Check whether it is valid for the current position.
3. Put it there.
4. Recursively fill the next position.
5. Remove/undo the choice.
6. Try another number.

This gives us:

```text
Choose
   ↓
Explore
   ↓
Undo
```

That is the classic **Backtracking** pattern.

Another clue is that every number can be used only once.

Therefore, we need:

```java
boolean[] used
```

to keep track of which numbers have already been selected.

---

# 🧠 2. Intuition

Imagine we have:

```text
n = 3
```

We have three positions:

```text
Position:  1   2   3
```

and three numbers:

```text
1   2   3
```

We start at position `1`.

For every unused number, we check:

```java
num % position == 0
```

or

```java
position % num == 0
```

If the number is valid, we choose it and move to the next position.

For example:

```text
position = 1
choose 2

Arrangement:
[2]
```

Then we recursively solve:

```text
position = 2
```

Once the recursive call finishes, we undo the choice:

```java
used[2] = false;
```

and try another number.

---

# 🔄 3. Backtracking Pattern

The complete pattern is:

```text
For every number:

    Is it unused?
          ↓
        Yes
          ↓
    Is it valid?
          ↓
        Yes
          ↓
       Choose
          ↓
       Explore
          ↓
        Undo
          ↓
    Try next number
```

In code:

```java
used[num] = true;

count += generate(position + 1, n, used);

used[num] = false;
```

The first line is the **choose** step.

The recursive call is the **explore** step.

The last line is the **undo/backtrack** step.

---

# 🎯 4. Validity Condition

For position `position` and number `num`, the arrangement is valid when:

```java
num % position == 0
```

OR

```java
position % num == 0
```

Therefore:

```java
if (num % position == 0 ||
    position % num == 0)
```

### Example

Position:

```text
4
```

Number:

```text
2
```

Check:

```text
4 % 2 == 0
```

Therefore, `2` can be placed at position `4`.

---

# 💻 5. Java Solution

```java
class Solution {

    public int countArrangement(int n) {
        boolean[] used = new boolean[n + 1];

        return backtrack(1, n, used);
    }

    private int backtrack(int pos, int n, boolean[] used) {
     int count = 0;
        if(pos > n) {
            return 1;
        }


       

        for(int num = 1; num <= n; num++) {

            if(!used[num] &&
               (num % pos == 0 || pos % num == 0)) {

                used[num] = true;

                // Recursive call separately
                int result = backtrack(pos + 1, n, used);// we are incrementing the position to check for the next elements as we are not adding elements for checking we generally do this.

                // Add the result separately
                count = count + result;//since recursive call doesnt affect count so it will remain unaffected after every recursive call 

                // Backtrack
                used[num] = false;
            }
        }

        return count;
    }
}
```

---

# 🧩 6. Code Explanation

## Step 1: Create `used[]`

```java
boolean[] used = new boolean[n + 1];
```

`used[num]` tells us whether a number has already been placed.

For example:

```text
used[2] = true
```

means number `2` is already being used.

---

## Step 2: Start from position 1

```java
return generate(1, n, used);
```

We begin filling the arrangement from position `1`.

---

## Step 3: Base Case

```java
if (position > n) {
    return 1;
}
```

If:

```text
position > n
```

it means every position has been successfully filled.

Therefore, we found one beautiful arrangement.

Return:

```text
1
```

Why `1`?

Because this recursive path represents exactly one valid arrangement.

---

## Step 4: Try Every Number

```java
for (int num = 1; num <= n; num++)
```

We consider every number from `1` to `n`.

---

## Step 5: Ignore Used Numbers

```java
if (used[num]) {
    continue;
}
```

A number can appear only once.

Therefore, if it has already been used, we skip it.

---

## Step 6: Check the Condition

```java
if (num % position == 0 ||
    position % num == 0)
```

Only valid numbers can be placed at the current position.

---

## Step 7: Choose

```java
used[num] = true;
```

We decide:

> Let's put this number at the current position.

---

## Step 8: Explore

```java
count += generate(
    position + 1,
    n,
    used
);
```

Now move to the next position.

---

## Step 9: Undo

```java
used[num] = false;
```

This is extremely important.

It means:

> We have finished exploring all arrangements that start with this choice. Now remove this choice so that another number can be tried.

This is the **backtracking** step.

---

# 🌳 7. Recursion Tree for `n = 2`

```text
                         Position 1
                        /          \
                     choose 1     choose 2
                       /              \
                  Position 2       Position 2
                     |                |
                  choose 2         choose 1
                     |                |
                   [1,2]            [2,1]
```

Both are valid.

Therefore:

```text
Answer = 2
```

---

# ⏱️ 8. Complexity

There can be up to `n!` possible permutations.

Therefore, the worst-case time complexity is approximately:

```text
O(n!)
```

The `used[]` array requires:

```text
O(n)
```

The recursion depth is also:

```text
O(n)
```

Therefore, auxiliary space is:

```text
O(n)
```

---

# 🔍 9. Why `used[]` Is Required

This problem is closely related to:

**LeetCode 46 — Permutations**

In both problems, we are arranging numbers and every number can be used only once.

For example:

```text
Numbers = [1, 2, 3]
```

After choosing:

```text
2
```

we cannot choose `2` again.

Therefore:

```java
used[2] = true;
```

After backtracking:

```java
used[2] = false;
```

This allows `2` to be considered again in another branch.

---

# 🧠 10. Pattern Recognition

When you see:

```text
"Arrange"
+
"Every element can be used once"
+
"Each position has a condition"
+
"Count all valid arrangements"
```

think:

```text
Permutation
      ↓
Backtracking
      ↓
used[]
      ↓
Choose valid unused element
      ↓
Explore
      ↓
Undo
```

The mental shortcut is:

> **Arrangement + position-based condition + use each number once = Backtracking with `used[]`.**

---

# 🔥 11. Comparison With LeetCode 46

| Feature                | LeetCode 46           | LeetCode 526             |
| ---------------------- | --------------------- | ------------------------ |
| Goal                   | Generate permutations | Count valid arrangements |
| Every number used once | Yes                   | Yes                      |
| `used[]` required      | Yes                   | Yes                      |
| Position condition     | No                    | Yes                      |
| Backtracking           | Yes                   | Yes                      |
| Return type            | List of permutations  | Count                    |

The major difference is that **526 adds a validity condition before choosing a number**.

---

# 🚀 12. Final Mental Model

Remember this:

```text
I have positions.

For the current position,
I can try every unused number.

But I only choose it if:

num % position == 0
OR
position % num == 0

Then:

Choose
   ↓
Recursive call
   ↓
Undo
   ↓
Try next number
```

So the complete pattern is:

```text
Position
   ↓
Unused number
   ↓
Valid?
   ↓
Choose
   ↓
Explore next position
   ↓
Undo
   ↓
Count
```

**LeetCode 526 = Permutation-style Backtracking + Position Validity Condition**
