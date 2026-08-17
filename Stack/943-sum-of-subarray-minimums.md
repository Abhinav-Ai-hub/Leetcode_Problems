# LeetCode 907: Sum of Subarray Minimums

## Problem Statement

Given an array of integers `arr`, find the sum of the minimum value of every contiguous subarray.

Since the answer may be very large, return the answer modulo:

```text
1,000,000,007
```

---

## Example

### Input

```text
arr = [3,1,2,4]
```

All subarrays are:

```text
[3]       → 3

[1]       → 1
[2]       → 2
[4]       → 4

[3,1]     → 1
[1,2]     → 1
[2,4]     → 2

[3,1,2]   → 1
[1,2,4]   → 1

[3,1,2,4] → 1
```

Sum:

```text
3 + 1 + 2 + 4
+ 1 + 1 + 2
+ 1 + 1
+ 1

= 17
```

Output:

```text
17
```

---

# Intuition

The brute-force approach would generate every subarray and find its minimum.

There are:

```text
O(n²)
```

subarrays.

Therefore, we need a better approach.

Instead of asking:

> What is the minimum of every subarray?

Ask:

> **For each element, in how many subarrays is this element the minimum?**

Then each element contributes:

```text
value × number of subarrays where it is minimum
```

---

# Key Idea

Consider:

```text
arr = [3,1,2]
```

Take the element:

```text
1
```

It can be the minimum of:

```text
[1]
[3,1]
[1,2]
[3,1,2]
```

So `1` contributes:

```text
1 × 4 = 4
```

Instead of explicitly generating those subarrays, we calculate how many choices we have for the left and right boundaries.

---

# Previous Smaller and Next Smaller

For every element `arr[i]`, find:

### Previous Smaller Element

The first element to the left that is smaller than `arr[i]`.

### Next Smaller Element

The first element to the right that is smaller than or equal to `arr[i]`.

These boundaries tell us how far the current element can expand while remaining the minimum.

---

# Why Stack?

The problem requires finding:

```text
Previous Smaller
Next Smaller
```

Efficiently.

This is a classic **Monotonic Stack** problem.

We maintain a stack of indices whose values are arranged monotonically.

The stack allows us to find the nearest smaller element in:

```text
O(n)
```

instead of:

```text
O(n²)
```

---

# Understanding `left` and `right`

Suppose:

```text
arr[i] = 2
```

and:

```text
left[i] = 3
right[i] = 2
```

This means:

```text
3 choices for the left boundary
2 choices for the right boundary
```

Therefore:

```text
number of subarrays
=
3 × 2
=
6
```

Since `2` is the minimum in all those subarrays:

```text
contribution
=
2 × 3 × 2
=
12
```

---

# Formula

For every index `i`:

```text
left[i]  = number of possible left boundaries
right[i] = number of possible right boundaries
```

Therefore:

```text
number of subarrays where arr[i] is minimum
=
left[i] × right[i]
```

Contribution:

```text
arr[i] × left[i] × right[i]
```

Final answer:

```text
Σ arr[i] × left[i] × right[i]
```

---

# Example

Consider:

```text
arr = [3,1,2]
```

For `1` at index `1`:

There is no smaller element on the left.

Therefore:

```text
left = 2
```

Why `2`?

The left boundary can be:

```text
index 1 → [1]
index 0 → [3,1]
```

On the right, there is no smaller element.

Therefore:

```text
right = 2
```

The right boundary can be:

```text
index 1 → [1]
index 2 → [1,2]
```

So:

```text
left × right
=
2 × 2
=
4
```

Therefore `1` contributes:

```text
1 × 4 = 4
```

---

# Algorithm

## Step 1: Calculate `left`

Traverse from left to right.

For every element:

```text
Remove elements from the stack
while they are greater than the current element.
```

Then:

```text
stack top = previous smaller element
```

If there is no previous smaller element:

```text
left[i] = i + 1
```

Otherwise:

```text
left[i] = i - stack.peek()
```

Push the current index.

---

## Step 2: Calculate `right`

Traverse from right to left.

For every element:

```text
Remove elements from the stack
while they are greater than OR equal to the current element.
```

Then:

```text
stack top = next smaller element
```

If there is no next smaller element:

```text
right[i] = n - i
```

Otherwise:

```text
right[i] = stack.peek() - i
```

Push the current index.

---

# Why `>` on One Side and `>=` on the Other?

This is extremely important when duplicate values exist.

Suppose:

```text
arr = [2,2]
```

Both elements have the same value.

If we treat both elements as the minimum for the same subarray, we would count that subarray twice.

Therefore, we need a **tie-breaking rule**.

We use:

```text
Left side:
arr[stack.peek()] > arr[i]

Right side:
arr[stack.peek()] >= arr[i]
```

This ensures that equal values are assigned consistently to only one occurrence.

---

# Java Solution

```java
907. Sum of Subarray Minimums
[3, 1, 2, 4]
17
class Solution {
    public int sumSubarrayMins(int[] arr) {
        int sum = 0;
        for (int i = 0; i < arr.length; i = i + 1) {
            // 3 1 2 
            //     i
            //     j
            // sum = 9
            // min = 2
            // T: O(n^2), S: O(1)
            int min = Integer.MAX_VALUE;
            for (int j = i; j < arr.length; j = j + 1) {
                min = Math.min(min, arr[j]);
                sum = (int)(((long)sum + min) % (1000000007));
            }
        }
        return sum;
    }
}
class Solution {
    public int sumSubarrayMins(int[] arr) {
        int[] nsei = new int[arr.length];
        int[] psei = new int[arr.length];
        for (int i = 0; i < arr.length; i = i + 1) {
            nsei[i] = arr.length;
            psei[i] = -1;
        }
        //   3 2 1
        // n 3 3 3
        // p * * *
        // T: O(n), S: O(n)
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i = i + 1) {
            while (true) {
                if (stack.isEmpty()) {
                    stack.push(i);
                    break;
                }
                int idx = stack.peek();
                if (arr[i] <= arr[idx]) {
                    nsei[idx] = i;
                    stack.pop();
                } else {
                    stack.push(i);
                    break;
                }
            }
        }
        stack.clear();
        for (int i = arr.length - 1; i >= 0; i = i - 1) {
            while (true) {
                if (stack.isEmpty()) {
                    stack.push(i);
                    break;
                }
                int idx = stack.peek();
                if (arr[i] < arr[idx]) {
                    psei[idx] = i;
                    stack.pop();
                } else {
                    stack.push(i);
                    break;
                }
            }
        }
        //   0 1 2
        //   3 1 2
        //  
        //.      i
        // n 1 3 3
        // p * * 1
        // 1 * 1 * 2
        // sum = 3 + 4 + 2 = 9
        // 2 * 2 = 4
        long sum = 0;
        for (int i = 0; i < arr.length; i = i + 1) {
            int leftBoundary = i - psei[i];
            int rightBoundary = nsei[i] - i;
            long contrib = (leftBoundary * rightBoundary) % 1000000007;
            contrib = (contrib * arr[i]) % 1000000007;
            sum = (sum + contrib) % 1000000007;
        }
        return (int) sum;
    }
}
```

---

# Dry Run

Consider:

```text
arr = [3,1,2]
```

We calculate:

```text
left  = [1,2,1]
right = [1,2,1]
```

Now calculate each contribution.

### Element `3`

```text
3 × 1 × 1
= 3
```

### Element `1`

```text
1 × 2 × 2
= 4
```

### Element `2`

```text
2 × 1 × 1
= 2
```

Total:

```text
3 + 4 + 2
= 9
```

But remember, the complete subarray list for `[3,1,2]` gives:

```text
3 + 1 + 2 + 1 + 1 + 1 = 9
```

Therefore:

```text
Answer = 9
```

---

# Complexity Analysis

### Time Complexity

```text
O(n)
```

Each element is:

- pushed into the stack once
- popped from the stack at most once

We make two passes.

Therefore:

```text
O(n)
```

---

### Space Complexity

```text
O(n)
```

We use:

```text
left[]
right[]
stack
```

---

# Key Takeaways

## Pattern Recognition

When you see:

```text
Sum of minimums of all subarrays
```

don't try to generate every subarray.

Instead think:

```text
For every element:
How many subarrays use this element as minimum?
```

Then:

```text
Previous Smaller
        +
Next Smaller
        ↓
Monotonic Stack
```

---

## Most Important Formula

```text
Contribution of arr[i]

= arr[i] × left[i] × right[i]
```

---

## Stack Pattern

```text
Previous Smaller:
    pop while >

Next Smaller:
    pop while >=
```

The difference between `>` and `>=` is important for handling duplicate values.

---

## Mental Model

Think of each element asking:

```text
"How far can I expand?"

             arr[i]
                ↓
       ← ← ←  i  → → →
       left side   right side

       left choices × right choices

                ↓

     Number of subarrays
       where I am minimum

                ↓

       arr[i] × count

                ↓

             Answer
```

> **The biggest shift in thinking:** Don't find the minimum for every subarray. Find the **subarrays for which each element is the minimum**.
```
