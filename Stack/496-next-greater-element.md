# LeetCode 496: Next Greater Element I

## Problem Statement

The next greater element of some element `x` in an array is the first greater element that is to the right of `x`.

You are given two distinct 0-indexed integer arrays:

- `nums1`
- `nums2`

where `nums1` is a subset of `nums2`.

For each element in `nums1`, find its next greater element in `nums2`.

If no greater element exists, return `-1`.

Return an array `ans` where:

```text
ans[i] = next greater element of nums1[i] in nums2
```

---

## Examples

### Example 1

Input:

```text
nums1 = [4,1,2]
nums2 = [1,3,4,2]
```

Output:

```text
[-1,3,-1]
```

Explanation:

- For `4`: there is no greater element to its right → `-1`
- For `1`: next greater element is `3`
- For `2`: there is no greater element → `-1`

---

### Example 2

Input:

```text
nums1 = [2,4]
nums2 = [1,2,3,4]
```

Output:

```text
[3,-1]
```

Explanation:

- For `2`: next greater element is `3`
- For `4`: there is no greater element → `-1`

---

## Intuition

The problem asks for the **next greater element**.

A brute-force solution would look to the right for every element:

```text
For every element:
    Check all elements to its right
```

This can take `O(n²)` time.

Instead, we use a **Monotonic Decreasing Stack**.

While traversing `nums2` from left to right:

- Keep elements whose next greater element has not been found yet.
- When the current number is greater than the element at the top of the stack:
  - The current number is the next greater element of that stack element.
  - Pop that element.
  - Store the relationship in a HashMap.

For example:

```text
nums2 = [1, 3, 4, 2]
```

Process `1`:

```text
Stack = [1]
```

Process `3`:

```text
3 > 1

Next greater of 1 = 3

Stack = [3]
```

Process `4`:

```text
4 > 3

Next greater of 3 = 4

Stack = [4]
```

Process `2`:

```text
2 < 4

Stack = [4,2]
```

Therefore:

```text
1 → 3
3 → 4
4 → -1
2 → -1
```

We store these relationships in a HashMap.

---

## Thought Process

The most important observation is:

```text
nums1 is a subset of nums2
```

Therefore, instead of solving the next greater element problem separately for every element of `nums1`, we can:

1. Find the next greater element for **every element of nums2**.
2. Store the results in a HashMap.
3. Use the HashMap to answer the elements of `nums1`.

This allows us to process `nums2` only once.

---

## Algorithm

### Step 1: Create a Stack

Create a stack to store elements whose next greater element has not been found.

```java
Stack<Integer> stack = new Stack<>();
```

---

### Step 2: Create a HashMap

The HashMap stores:

```text
element → next greater element
```

Example:

```text
1 → 3
3 → 4
4 → -1
2 → -1
```

---

### Step 3: Traverse nums2

For every element `num`:

If:

```text
num > stack.peek()
```

then `num` is the next greater element of `stack.peek()`.

So:

```java
map.put(stack.pop(), num);
```

Continue popping while the current element is greater.

Then push the current element.

---

### Step 4: Handle Remaining Elements

After processing `nums2`, some elements may still remain in the stack.

Those elements don't have a greater element to their right.

Therefore:

```java
map.put(stack.pop(), -1);
```

---

### Step 5: Build nums1 Answer

For every element in `nums1`, simply look up its answer:

```java
ans[i] = map.get(nums1[i]);
```

---

## Java Solution

```java
import java.util.*;

class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {

        Stack<Integer> stack = new Stack<>();
        HashMap<Integer, Integer> map = new HashMap<>();

        // Find next greater element for every element in nums2
        for (int num : nums2) {

            while (!stack.isEmpty() && num > stack.peek()) {
                map.put(stack.pop(), num);
            }

            stack.push(num);
        }

        // Remaining elements have no greater element
        while (!stack.isEmpty()) {
            map.put(stack.pop(), -1);
        }

        // Find answers for nums1
        int[] ans = new int[nums1.length];

        for (int i = 0; i < nums1.length; i++) {
            ans[i] = map.get(nums1[i]);
        }

        return ans;
    }
}
```

---

## Dry Run

Input:

```text
nums1 = [4,1,2]
nums2 = [1,3,4,2]
```

### Process nums2

#### `1`

```text
Stack = [1]
```

#### `3`

Since:

```text
3 > 1
```

We found:

```text
1 → 3
```

Stack:

```text
[3]
```

#### `4`

Since:

```text
4 > 3
```

We found:

```text
3 → 4
```

Stack:

```text
[4]
```

#### `2`

Since:

```text
2 < 4
```

Push `2`:

```text
[4,2]
```

End of traversal.

Remaining elements:

```text
4 → -1
2 → -1
```

HashMap becomes:

```text
1 → 3
3 → 4
4 → -1
2 → -1
```

---

### Now process nums1

```text
nums1 = [4,1,2]
```

Lookup:

```text
4 → -1
1 → 3
2 → -1
```

Final answer:

```text
[-1,3,-1]
```

---

## Complexity Analysis

Let:

- `n = nums2.length`
- `m = nums1.length`

### Time Complexity

```text
O(n + m)
```

Every element in `nums2` is pushed and popped at most once.

Then we perform `O(1)` HashMap lookup for every element of `nums1`.

---

### Space Complexity

```text
O(n)
```

The stack and HashMap can contain up to `n` elements.

---

## Key Takeaways

### Pattern Recognition

Whenever you see:

```text
Next Greater Element
```

think:

```text
Monotonic Stack
```

### Stack Type

For Next Greater Element:

```text
Monotonic Decreasing Stack
```

### Important Pattern

```text
Current element > Stack Top
        ↓
Stack Top has found its Next Greater Element
        ↓
Pop Stack Top
        ↓
Store the relationship
```

### Why HashMap?

`nums1` is a subset of `nums2`.

So we first calculate:

```text
Every element in nums2 → Next Greater Element
```

and then directly query the answers for `nums1`.

### Core Template

```java
for (int num : nums2) {

    while (!stack.isEmpty() && num > stack.peek()) {
        map.put(stack.pop(), num);
    }

    stack.push(num);
}
```

This **monotonic-stack template** is extremely important because it appears in many problems involving:

- Next Greater Element
- Next Smaller Element
- Daily Temperatures
- Stock Span
- Largest Rectangle in Histogram
- Circular Next Greater Element
