# LeetCode 70: Climbing Stairs

## 📌 Problem Description

You are climbing a staircase. It takes `n` steps to reach the top.

Each time you can climb either:

* `1` step
* `2` steps

Given an integer `n`, return the number of distinct ways you can climb to the top.

### Example

**Input:**

```text
n = 3
```

**Output:**

```text
3
```

### Explanation

There are 3 ways to reach the top:

```text
1 + 1 + 1
1 + 2
2 + 1
```

---

# 💡 Intuition — How Do I Recognize Recursion?

The key observation is:

> To reach step `n`, the last move must either be `1` step or `2` steps.

So there are only two possibilities:

### Case 1: Last move is 1 step

Before taking the final 1 step, we must be at:

```text
n - 1
```

The number of ways to reach there is:

```text
ways(n - 1)
```

### Case 2: Last move is 2 steps

Before taking the final 2 steps, we must be at:

```text
n - 2
```

The number of ways to reach there is:

```text
ways(n - 2)
```

Therefore:

```text
ways(n) = ways(n - 1) + ways(n - 2)
```

This is a recursive pattern because the answer for `n` depends on smaller versions of the **same problem**.

---

# 🧠 Recursive Thinking

Think from the destination backwards.

For example:

```text
n = 5
```

The last move can be:

```text
5
↑
4 → +1
3 → +2
```

Therefore:

```text
ways(5) = ways(4) + ways(3)
```

And each of those problems can be broken down again.

```text
ways(5)
├── ways(4)
│   ├── ways(3)
│   └── ways(2)
│
└── ways(3)
    ├── ways(2)
    └── ways(1)
```

This naturally gives us recursion.

---

# 🛑 Base Cases

We need to stop the recursion at small values.

### If `n = 1`

There is only one way:

```text
1
```

So:

```text
ways(1) = 1
```

### If `n = 2`

There are two ways:

```text
1 + 1
2
```

So:

```text
ways(2) = 2
```

Therefore:

```java
if (n <= 2) {
    return n;
}
```

---

# 🔄 Recursive Formula

The complete recurrence is:

```text
ways(n) = ways(n - 1) + ways(n - 2)
```

This is exactly the Fibonacci pattern.

---

# 💻 Java Solution — Recursion

```java
class Solution {

    public int climbStairs(int n) {

        if (n <= 2) {
            return n;
        }

        return climbStairs(n - 1) + climbStairs(n - 2);
    }
}
```

---

# 🔍 Dry Run

Suppose:

```text
n = 4
```

We calculate:

```text
climbStairs(4)
```

Since `4 > 2`:

```text
climbStairs(4)
= climbStairs(3) + climbStairs(2)
```

Now:

```text
climbStairs(3)
= climbStairs(2) + climbStairs(1)
```

Using the base cases:

```text
climbStairs(2) = 2
climbStairs(1) = 1
```

Therefore:

```text
climbStairs(3) = 2 + 1
               = 3
```

And:

```text
climbStairs(4) = 3 + 2
               = 5
```

### Final Answer

```text
5
```

The five ways are:

```text
1 + 1 + 1 + 1
1 + 1 + 2
1 + 2 + 1
2 + 1 + 1
2 + 2
```

---

# 🌳 Recursion Tree

For `n = 5`:

```text
                    climbStairs(5)
                   /              \
          climbStairs(4)       climbStairs(3)
           /        \            /        \
     climbStairs(3) climbStairs(2) climbStairs(2) climbStairs(1)
       /      \
 climbStairs(2) climbStairs(1)
```

Notice something important:

```text
climbStairs(3)
```

is calculated multiple times.

Similarly:

```text
climbStairs(2)
```

is also calculated multiple times.

This is called **overlapping subproblems**.

---

# ⚠️ Problem With Simple Recursion

Although the recursive solution is easy to understand, it is inefficient.

For example:

```text
climbStairs(5)
```

calculates:

```text
climbStairs(3)
```

multiple times.

As `n` becomes larger, the number of recursive calls grows very quickly.

Therefore, the simple recursive solution has:

### Time Complexity

```text
O(2^n)
```

### Space Complexity

```text
O(n)
```

The `O(n)` space comes from the recursion call stack.

---

# 🚀 Better Approach

The recursive solution reveals an important DP pattern:

```text
ways(n) = ways(n - 1) + ways(n - 2)
```

We can store already calculated results using **memoization**.

This changes the time complexity from:

```text
O(2^n)
```

to:

```text
O(n)
```

---

# 💻 Java Solution — Recursion + Memoization

```java
class Solution {

    public int climbStairs(int n) {

        int[] dp = new int[n + 1];

        return solve(n, dp);
    }

    private int solve(int n, int[] dp) {

        if (n <= 2) {
            return n;
        }

        if (dp[n] != 0) {
            return dp[n];
        }

        dp[n] = solve(n - 1, dp) + solve(n - 2, dp);

        return dp[n];
    }
}
```

---

# 🧠 Why Memoization Works

Without memoization:

```text
solve(5)
├── solve(4)
│   ├── solve(3)
│   └── solve(2)
│
└── solve(3)
    ├── solve(2)
    └── solve(1)
```

`solve(3)` is calculated more than once.

With memoization:

```java
if (dp[n] != 0) {
    return dp[n];
}
```

Once we calculate:

```text
dp[3] = 3
```

we store it.

The next time we need `solve(3)`, we simply return:

```text
dp[3]
```

instead of calculating it again.

---

# 📊 Complexity

## Simple Recursion

```text
Time:  O(2^n)
Space: O(n)
```

## Recursion + Memoization

```text
Time:  O(n)
Space: O(n)
```

---

# 🎯 Key Takeaways

The most important thought process is:

```text
What can the last move be?
```

For this problem:

```text
Last move = 1 step
        OR
Last move = 2 steps
```

Therefore:

```text
ways(n) = ways(n - 1) + ways(n - 2)
```

Whenever you see:

* A problem that can be broken into smaller versions of itself
* The same subproblem appearing repeatedly
* A clear base case
* A recurrence such as `f(n) = f(n-1) + f(n-2)`

you should think:

> **Recursion first → then check whether memoization/DP can optimize it.**

---

# 🔑 Pattern Recognition

```text
Problem
   ↓
Break into smaller same problems
   ↓
Define base case
   ↓
Write recurrence
   ↓
Recursion
   ↓
Notice repeated calculations
   ↓
Memoization / Dynamic Programming
```

For Climbing Stairs:

```text
climbStairs(n)
        ↓
climbStairs(n-1) + climbStairs(n-2)
        ↓
Repeated subproblems
        ↓
Memoization
        ↓
O(n) solution
```

---

# ⭐ Final Mental Model

Remember this one line:

> **To reach step `n`, I must come from either `n-1` or `n-2`.**

Therefore:

```text
f(n) = f(n-1) + f(n-2)
```

That single observation gives us the entire recursive solution.
