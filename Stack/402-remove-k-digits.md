# LeetCode 402: Remove K Digits

## Problem Statement

Given a string `num` representing a non-negative integer and an integer `k`, remove exactly `k` digits from the number so that the resulting number is the smallest possible.

Return the resulting number as a string.

If the resulting number contains leading zeroes, remove them.

If all digits are removed, return `"0"`.

---

## Example 1

### Input

```text
num = "1432219"
k = 3
```

### Output

```text
"1219"
```

### Explanation

We need to remove 3 digits.

Remove:

```text
4
3
2
```

The remaining number is:

```text
1219
```

---

## Example 2

### Input

```text
num = "10200"
k = 1
```

### Output

```text
"200"
```

Explanation:

Remove `1`:

```text
10200
 ↓
0200
```

Remove the leading zero:

```text
200
```

---

## Example 3

### Input

```text
num = "10"
k = 2
```

### Output

```text
"0"
```

All digits are removed.

---

# Intuition

The goal is to make the number as small as possible.

Consider:

```text
43
```

If we have to remove one digit, which one should we remove?

Remove `4`:

```text
43 → 3
```

Remove `3`:

```text
43 → 4
```

Clearly:

```text
3 < 4
```

So we should remove the larger digit when it appears before a smaller digit.

Therefore, whenever:

```text
previous digit > current digit
```

we should try to remove the previous digit.

---

# Why Stack?

Suppose we are processing:

```text
143
```

First:

```text
1
```

Stack:

```text
[1]
```

Then:

```text
4
```

Since:

```text
1 < 4
```

we keep it.

```text
[1,4]
```

Now we encounter:

```text
3
```

Compare with the most recently inserted digit:

```text
4 > 3
```

Therefore, remove `4`.

```text
[1]
```

Now compare again:

```text
1 < 3
```

So stop removing and push `3`.

```text
[1,3]
```

The Stack lets us repeatedly check the **most recently kept digit**.

Therefore:

> This is a Monotonic Stack problem.

---

# Monotonic Stack

The stack is maintained in increasing order.

For example:

```text
1
3
5
7
```

If we receive:

```text
4
```

then:

```text
7 > 4
```

Remove `7`.

```text
1
3
5
```

Again:

```text
5 > 4
```

Remove `5`.

```text
1
3
```

Now:

```text
3 < 4
```

Stop.

Push `4`.

```text
1
3
4
```

This is why it is called a:

```text
Monotonic Increasing Stack
```

---

# Algorithm

1. Create an empty Stack.
2. Traverse every digit in `num`.
3. For the current digit:
   - While the stack is not empty.
   - `k > 0`.
   - The stack top is greater than the current digit:
     - Pop the stack.
     - Decrease `k`.
4. Push the current digit.
5. If `k` is still greater than `0`, remove digits from the end.
6. Convert the Stack to a string.
7. Remove leading zeroes.
8. If the result is empty, return `"0"`.

---

# Core Code

The most important part is:

```java
while (!stack.isEmpty()
        && k > 0
        && stack.peek() > digit) {

    stack.pop();
    k--;
}
```

Think of this as:

```text
        current digit
             ↓
      Compare with top
             ↓
      top > current?
          /       \
        YES        NO
         ↓          ↓
       POP        PUSH
         ↓
    k decreases
         ↓
       Repeat
```

---

# Dry Run

Consider:

```text
num = "1432219"
k = 3
```

### Step 1: `1`

```text
Stack = [1]
k = 3
```

---

### Step 2: `4`

```text
1 < 4
```

Push:

```text
Stack = [1,4]
k = 3
```

---

### Step 3: `3`

Compare:

```text
4 > 3
```

Remove `4`:

```text
Stack = [1]
k = 2
```

Now:

```text
1 < 3
```

Push:

```text
Stack = [1,3]
```

---

### Step 4: `2`

Compare:

```text
3 > 2
```

Remove `3`:

```text
Stack = [1]
k = 1
```

Now:

```text
1 < 2
```

Push:

```text
Stack = [1,2]
```

---

### Step 5: `2`

Compare:

```text
2 > 2
```

False.

Push:

```text
Stack = [1,2,2]
k = 1
```

---

### Step 6: `1`

Compare:

```text
2 > 1
```

Remove `2`:

```text
Stack = [1,2]
k = 0
```

Now we cannot remove anything else.

Push `1`:

```text
Stack = [1,2,1]
```

---

### Step 7: `9`

Since:

```text
k = 0
```

we cannot remove anything.

Push:

```text
Stack = [1,2,1,9]
```

Final result:

```text
1219
```

---

# What If `k` Is Still Left?

Consider:

```text
num = "12345"
k = 2
```

The number is already increasing:

```text
1 < 2 < 3 < 4 < 5
```

So the condition:

```java
stack.peek() > digit
```

never becomes true.

Stack:

```text
[1,2,3,4,5]
```

But we still have:

```text
k = 2
```

We need to remove two digits.

Remove from the end:

```text
12345
   ↓
123
```

Therefore:

```java
while (k > 0) {
    stack.pop();
    k--;
}
```

---

# Why Remove From the End?

Suppose:

```text
12345
```

We need to remove one digit.

Removing:

```text
1 → 2345
```

Removing:

```text
5 → 1234
```

Clearly:

```text
1234 < 2345
```

Therefore, when the number is already monotonically increasing, removing from the **end** produces the smallest result.

---

# Leading Zeroes

Consider:

```text
num = "10200"
k = 1
```

Remove `1`:

```text
0200
```

But we should return:

```text
200
```

So after creating the result, remove leading zeroes.

```java
while (i < result.length()
        && result.charAt(i) == '0') {

    i++;
}
```

If nothing remains:

```java
return "0";
```

---

# Java Solution

```java
import java.util.Stack;

class Solution {
    public String removeKdigits(String num, int k) {

        Stack<Character> stack = new Stack<>();

        for (char digit : num.toCharArray()) {

            while (!stack.isEmpty()
                    && k > 0
                    && stack.peek() > digit) {

                stack.pop();
                k--;
            }

            stack.push(digit);
        }

        // Remove remaining digits from the end
        while (k > 0) {
            stack.pop();
            k--;
        }

        StringBuilder result = new StringBuilder();

        for (char digit : stack) {
            result.append(digit);
        }

        // Remove leading zeroes
        int i = 0;

        while (i < result.length()
                && result.charAt(i) == '0') {

            i++;
        }

        result = new StringBuilder(result.substring(i));

        if (result.length() == 0) {
            return "0";
        }

        return result.toString();
    }
}
```

---

# Complexity Analysis

## Time Complexity

```text
O(n)
```

Every digit is:

- pushed at most once
- popped at most once

So even though there is a `while` loop inside the `for` loop, the total number of stack operations is still `O(n)`.

---

## Space Complexity

```text
O(n)
```

The Stack can contain up to `n` digits.

---

# Key Takeaways

## Pattern Recognition

When you see:

```text
Remove K elements
+
Make the remaining sequence smallest/largest
+
Compare current element with previous elements
```

Think:

```text
MONOTONIC STACK
```

---

## Core Rule

```java
while (k > 0 && stack.peek() > current) {
    stack.pop();
    k--;
}
```

Then:

```java
stack.push(current);
```

---

## Mental Model

Think:

> **"I want my number to increase from left to right as much as possible."**

So whenever I see:

```text
larger → smaller
```

I remove the larger digit.

```text
7 5
↑ ↑
larger → smaller

Remove 7

5
```

If this happens multiple times:

```text
9 7 5 3
      ↑
    current
```

we keep popping:

```text
9 7 5
↓
9 7
↓
9
```

until the stack top is smaller than or equal to the current digit, or `k` becomes zero.

> **The key idea:** Keep the smallest possible prefix. The monotonic stack gives us an efficient way to remove a larger previous digit whenever a smaller current digit arrives.
