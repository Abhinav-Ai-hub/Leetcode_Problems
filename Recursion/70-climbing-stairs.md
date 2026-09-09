# LeetCode 70: Climbing Stairs

## Problem Statement

You are climbing a staircase.

It takes `n` steps to reach the top.

Each time you can either climb:

- `1` step
- `2` steps

Return the number of distinct ways you can climb to the top.

---

## Example 1

### Input

```text
n = 2
```

### Output

```text
2
```

### Explanation

There are two ways:

```text
1 + 1
```

or

```text
2
```

---

## Example 2

### Input

```text
n = 3
```

### Output

```text
3
```

### Explanation

The three possible ways are:

```text
1 + 1 + 1
1 + 2
2 + 1
```

---

# Intuition

The most important question is:

> How can we reach step `n`?

There are only two possibilities.

### Possibility 1

We came from step `n - 1` by taking:

```text
1 step
```

### Possibility 2

We came from step `n - 2` by taking:

```text
2 steps
```

Therefore:

```text
ways[n] = ways[n - 1] + ways[n - 2]
```

This is the recurrence relation.

---

# Why Dynamic Programming?

Notice that to calculate:

```text
ways[5]
```

we need:

```text
ways[4] + ways[3]
```

And:

```text
ways[4]
```

already depends on:

```text
ways[3] + ways[2]
```

So the same smaller problems are repeatedly used.

Instead of calculating them again and again, we store their answers.

This is the basic idea of:

```text
Dynamic Programming
```

---

# Recognition Trick

When you see:

```text
Count the number of ways
+
Current state depends on previous states
```

think:

```text
Dynamic Programming
```

Here:

```text
dp[i] = number of ways to reach step i
```

---

# Base Cases

For:

```text
n = 1
```

There is only:

```text
1
```

way.

Therefore:

```text
dp[1] = 1
```

For:

```text
n = 2
```

There are:

```text
1 + 1
2
```

Therefore:

```text
dp[2] = 2
```

So:

```text
dp[1] = 1
dp[2] = 2
```

---

# Recurrence Relation

For every `i >= 3`:

```text
dp[i] = dp[i - 1] + dp[i - 2]
```

Why?

Because the final move is either:

```text
1 step
```

or:

```text
2 steps
```

Therefore:

```text
                 Step i
                /       \
          from i-1      from i-2
             ↓             ↓
          1 step        2 steps
```

So:

```text
dp[i] = dp[i-1] + dp[i-2]
```

---

# Dry Run

Consider:

```text
n = 5
```

Start with:

```text
dp[1] = 1
dp[2] = 2
```

### Step 3

```text
dp[3] = dp[2] + dp[1]

      = 2 + 1

      = 3
```

### Step 4

```text
dp[4] = dp[3] + dp[2]

      = 3 + 2

      = 5
```

### Step 5

```text
dp[5] = dp[4] + dp[3]

      = 5 + 3

      = 8
```

Therefore:

```text
Answer = 8
```

---

# DP Table

For `n = 5`:

| Step | Number of Ways |
|------|----------------|
| 1 | 1 |
| 2 | 2 |
| 3 | 3 |
| 4 | 5 |
| 5 | 8 |

Pattern:

```text
1, 2, 3, 5, 8...
```

---

# Java Solution — DP Array

```java
class Solution {
    public int climbStairs(int n) {

        int[] dp = new int[n + 1];

        dp[1] = 1;

        if (n >= 2) {
            dp[2] = 2;
        }

        for (int i = 3; i <= n; i++) {

            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }
}
```

---

# Space Optimization

Look at the recurrence:

```text
dp[i] = dp[i - 1] + dp[i - 2]
```

To calculate `dp[i]`, we only need:

```text
dp[i-1]
dp[i-2]
```

We don't need the entire array.

So instead of:

```text
dp[1]
dp[2]
dp[3]
dp[4]
dp[5]
...
```

we can maintain only:

```text
prev2
prev1
current
```

---

# Optimized Java Solution

```java
class Solution {
    public int climbStairs(int n) {

        if (n <= 2) {
            return n;
        }

        int prev2 = 1;
        int prev1 = 2;

        for (int i = 3; i <= n; i++) {

            int current = prev1 + prev2;

            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }
}
```

---

# Dry Run of Optimized Version

For:

```text
n = 5
```

Initially:

```text
prev2 = 1
prev1 = 2
```

### i = 3

```text
current = 2 + 1
        = 3
```

Update:

```text
prev2 = 2
prev1 = 3
```

---

### i = 4

```text
current = 3 + 2
        = 5
```

Update:

```text
prev2 = 3
prev1 = 5
```

---

### i = 5

```text
current = 5 + 3
        = 8
```

Update:

```text
prev2 = 5
prev1 = 8
```

Return:

```text
8
```

---

# Complexity Analysis

## DP Array Approach

### Time

```text
O(n)
```

We calculate every step once.

### Space

```text
O(n)
```

Because we store the entire DP array.

---

## Optimized Approach

### Time

```text
O(n)
```

We still calculate each step once.

### Space

```text
O(1)
```

We only store three variables.

---

# Key Takeaways

## Pattern Recognition

When you see:

```text
Number of ways to reach a state
```

ask:

> **Can I reach this state from a small number of previous states?**

Here:

```text
step i
  ↓
from i-1 OR i-2
```

Therefore:

```text
dp[i] = dp[i-1] + dp[i-2]
```

---

## Mental Model

Think of yourself standing at step `i`.

To reach it, your last jump must have been:

```text
      i
     / \
   i-1  i-2
    ↓     ↓
  +1     +2
```

So:

```text
ways(i) = ways(i-1) + ways(i-2)
```

---

## Important Connection

This produces the Fibonacci-like sequence:

```text
1, 2, 3, 5, 8, 13, 21...
```

But don't memorize it as "Fibonacci."

Instead remember the reasoning:

> **The final move can only be 1 or 2 steps, so count all ways to reach the two previous positions.**

That thought process is what lets you recognize similar **Dynamic Programming** problems.
