# LeetCode 503: Next Greater Element II

## Problem Statement

Given a circular integer array `nums`, return the next greater element for every element in `nums`.

The **next greater element** of an element `x` is the first element greater than `x` that appears while traversing the array in the next positions.

Since the array is circular, after reaching the last element, we continue from the beginning of the array.

If no greater element exists, return `-1`.

---

## Examples

### Example 1

Input:

```text
nums = [1,2,1]
```

Output:

```text
[2,-1,2]
```

Explanation:

- For `1` at index `0` → next greater element is `2`.
- For `2` → no greater element exists → `-1`.
- For `1` at index `2` → circularly, we reach `2` at index `1` → `2`.

---

### Example 2

Input:

```text
nums = [1,2,3,4,3]
```

Output:

```text
[2,3,4,-1,4]
```

---

## Intuition

This problem is very similar to **LeetCode 496: Next Greater Element I**.

The difference is that this array is **circular**.

Consider:

```text
nums = [1,2,1]
```

Normally, for the last `1`, there is nothing to its right.

But because the array is circular:

```text
[1,2,1]
       ↓
       1 → 1 → 2 → ...
```

The last `1` can find `2` after wrapping around.

Therefore, we need to effectively process the array **twice**.

Instead of creating a new array of size `2n`, we can use:

```java
int index = i % n;
```

This converts:

```text
i = 0 → index = 0
i = 1 → index = 1
i = 2 → index = 2

i = 3 → index = 0
i = 4 → index = 1
i = 5 → index = 2
```

So we simulate:

```text
[1,2,1,1,2,1]
```

without actually creating it.

---

## Thought Process

We need the **next greater element**.

Therefore, we use a **Monotonic Decreasing Stack**.

The stack contains indices whose next greater element has not been found yet.

Whenever:

```text
current element > element at stack top
```

we have found the next greater element for the stack-top element.

So:

```text
nums[index] > nums[stack.peek()]
```

means:

```text
ans[stack.pop()] = nums[index]
```

---

## Why Do We Traverse Twice?

Suppose:

```text
nums = [1,2,1]
```

The last `1` needs to look at the beginning:

```text
1 → 2
```

One traversal isn't enough.

Therefore:

```java
for (int i = 0; i < 2 * n; i++)
```

allows every element to see the elements that come after it, including elements at the beginning of the array.

---

## Why `i % n`?

We don't actually want to create another array.

Instead:

```java
int index = i % n;
```

For:

```text
nums = [1,2,1]
```

we get:

```text
i       i % n       actual index
--------------------------------
0          0             0
1          1             1
2          2             2
3          0             0
4          1             1
5          2             2
```

Thus, the array behaves as if it were:

```text
[1,2,1,1,2,1]
```

---

## Algorithm

1. Create an answer array and initialize every position with `-1`.
2. Create an empty stack.
3. Traverse from `0` to `2 * n - 1`.
4. Calculate the actual index:

```java
int index = i % n;
```

5. While:
   - Stack is not empty.
   - Current element is greater than the element at the stack top.

   Pop the stack and update its answer.

6. During the **first traversal only**, push the current index into the stack.
7. Return the answer array.

---

## Java Solution

```java
import java.util.Stack;

class Solution {
    public int[] nextGreaterElements(int[] nums) {

        int n = nums.length;

        int[] ans = new int[n];

        // Initially, no greater element is known
        for (int i = 0; i < n; i++) {
            ans[i] = -1;
        }

        Stack<Integer> stack = new Stack<>();

        // Traverse twice to simulate circular array
        for (int i = 0; i < 2 * n; i++) {

            int index = i % n;

            while (!stack.isEmpty() &&
                    nums[index] > nums[stack.peek()]) {

                ans[stack.pop()] = nums[index];
            }

            // Push indices only during first traversal
            if (i < n) {
                stack.push(index);
            }
        }

        return ans;
    }
}
```

---

## Dry Run

Input:

```text
nums = [1,2,1]
```

Initial:

```text
ans = [-1,-1,-1]
stack = []
```

### i = 0

```text
index = 0 % 3 = 0
nums[index] = 1
```

Stack is empty.

Push index `0`.

```text
stack = [0]
```

---

### i = 1

```text
index = 1
nums[index] = 2
```

Compare:

```text
2 > nums[0]
2 > 1
```

Therefore:

```text
ans[0] = 2
```

Pop `0`.

Push `1`.

```text
stack = [1]
ans = [2,-1,-1]
```

---

### i = 2

```text
index = 2
nums[index] = 1
```

Compare:

```text
1 > 2 ❌
```

Push `2`.

```text
stack = [1,2]
```

---

### i = 3

Now we wrap around.

```text
index = 3 % 3 = 0
```

So:

```text
nums[index] = 1
```

Compare with stack top:

```text
1 > 1 ❌
```

No change.

We don't push again because this is the second traversal.

---

### i = 4

```text
index = 4 % 3 = 1
nums[index] = 2
```

Now:

```text
2 > nums[2]
2 > 1
```

Therefore:

```text
ans[2] = 2
```

Pop `2`.

Now stack top is `1`:

```text
2 > nums[1]
2 > 2 ❌
```

Stop.

Final:

```text
ans = [2,-1,2]
```

---

## Complexity Analysis

### Time Complexity

```text
O(n)
```

Although we traverse `2n` positions, each element is pushed once and popped at most once.

Therefore:

```text
O(2n) = O(n)
```

---

### Space Complexity

```text
O(n)
```

The stack can contain at most `n` indices.

The answer array also requires `O(n)` space.

---

## Key Takeaways

### 1. Next Greater Element

Think:

```text
Monotonic Stack
```

### 2. Circular Array

Think:

```text
Traverse 2n times
```

### 3. Avoid Creating a New Array

Use:

```java
i % n
```

to simulate circular traversal.

### 4. Stack Type

For Next Greater Element:

```text
Monotonic Decreasing Stack
```

### 5. Important Pattern

```text
Current > Stack Top
        ↓
Current is the Next Greater Element
        ↓
Pop Stack Top
        ↓
Update answer
```

### Core Template

```java
for (int i = 0; i < 2 * n; i++) {

    int index = i % n;

    while (!stack.isEmpty() &&
            nums[index] > nums[stack.peek()]) {

        ans[stack.pop()] = nums[index];
    }

    if (i < n) {
        stack.push(index);
    }
}
```

### Difference Between 496 and 503

| Problem | Array | Main Technique |
|---|---|---|
| **496: Next Greater Element I** | Normal | Monotonic Stack + HashMap |
| **503: Next Greater Element II** | Circular | Monotonic Stack + `2n` traversal |

The **core stack logic remains the same**. The only major addition in 503 is handling the **circular nature of the array**.
