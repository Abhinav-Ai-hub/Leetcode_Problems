# LeetCode 739: Daily Temperatures

## Problem Statement

Given an array of integers `temperatures` representing the daily temperatures, return an array `answer` such that:

- `answer[i]` is the number of days you have to wait after the `iᵗʰ` day to get a warmer temperature.
- If there is no future day with a warmer temperature, keep `answer[i] = 0`.

---

## Examples

### Example 1

Input:
```
temperatures = [73,74,75,71,69,72,76,73]
```

Output:
```
[1,1,4,2,1,1,0,0]
```

---

### Example 2

Input:
```
temperatures = [30,40,50,60]
```

Output:
```
[1,1,1,0]
```

---

### Example 3

Input:
```
temperatures = [30,60,90]
```

Output:
```
[1,1,0]
```

---

## Intuition

For every day's temperature, we need to find the next future day having a higher temperature.

A brute-force solution checks every future day for each element, resulting in **O(n²)** time complexity.

Instead, we use a **Monotonic Decreasing Stack** to store the indices of temperatures whose next warmer day hasn't been found yet.

Whenever we encounter a warmer temperature, we resolve all previous colder temperatures by popping them from the stack and calculating the waiting days.

---

## Thought Process

- Traverse the temperature array from left to right.
- Maintain a stack storing the indices of unresolved temperatures.
- While the current temperature is greater than the temperature at the top index of the stack:
  - Pop the index.
  - Calculate the number of waiting days (`currentIndex - poppedIndex`).
- Push the current index onto the stack.
- Any indices left in the stack never encounter a warmer temperature, so their answers remain `0`.

---

## Algorithm

1. Create an answer array initialized with `0`.
2. Create an empty stack to store indices.
3. Traverse the array from left to right.
4. While:
   - Stack is not empty, and
   - Current temperature is greater than the temperature at the stack's top index:
     - Pop the index.
     - Store the difference of indices in the answer array.
5. Push the current index into the stack.
6. Return the answer array.

---

## Java Solution

```java
import java.util.Stack;

class Solution {
    public int[] dailyTemperatures(int[] temperatures) {

        int n = temperatures.length;
        int[] ans = new int[n];

        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i < n; i++) {

            while(!stack.isEmpty() &&
                    temperatures[i] > temperatures[stack.peek()]) {

                int index = stack.pop();
                ans[index] = i - index;
            }

            stack.push(i);
        }

        return ans;
    }
}
```

---

## Dry Run

Input:

```
temperatures = [73,74,75,71,69,72,76,73]
```

| Day | Temp | Stack (Indices) | Action | Answer |
|-----|------|-----------------|--------|--------|
|0|73|0|Push 0|[0,0,0,0,0,0,0,0]|
|1|74|1|74 > 73 → Pop 0 → ans[0]=1|[1,0,0,0,0,0,0,0]|
|2|75|2|75 > 74 → Pop 1 → ans[1]=1|[1,1,0,0,0,0,0,0]|
|3|71|2,3|Push|[1,1,0,0,0,0,0,0]|
|4|69|2,3,4|Push|[1,1,0,0,0,0,0,0]|
|5|72|2,5|Pop 4 → ans[4]=1<br>Pop 3 → ans[3]=2|[1,1,0,2,1,0,0,0]|
|6|76|6|Pop 5 → ans[5]=1<br>Pop 2 → ans[2]=4|[1,1,4,2,1,1,0,0]|
|7|73|6,7|Push|Final Answer|

Final Output:

```
[1,1,4,2,1,1,0,0]
```

---

## Complexity Analysis

- **Time Complexity:** `O(n)`

Each index is pushed onto the stack once and popped at most once.

- **Space Complexity:** `O(n)`

In the worst case (strictly decreasing temperatures), all indices remain in the stack.

---

## Key Takeaways

- Whenever a problem asks for the **Next Greater Element**, think **Monotonic Stack**.
- Store **indices**, not values, because we need to calculate distances.
- Each element is pushed and popped at most once, giving an efficient **O(n)** solution.
- A Monotonic Stack avoids unnecessary repeated comparisons, making it ideal for "next greater/smaller" problems.
