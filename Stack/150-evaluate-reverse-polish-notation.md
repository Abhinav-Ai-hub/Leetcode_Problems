# LeetCode 150: Evaluate Reverse Polish Notation

## Problem Statement

You are given an array of strings `tokens` representing an arithmetic expression in **Reverse Polish Notation (RPN)**.

Evaluate the expression and return its value.

Valid operators are:

- `+`
- `-`
- `*`
- `/`

Each operand may be an integer or another expression.

Division between two integers should truncate toward zero.

It is guaranteed that the expression is valid and there will not be any division by zero.

---

## Examples

### Example 1

Input:

```
tokens = ["2","1","+","3","*"]
```

Output:

```
9
```

Explanation:

```
((2 + 1) * 3) = 9
```

---

### Example 2

Input:

```
tokens = ["4","13","5","/","+"]
```

Output:

```
6
```

Explanation:

```
4 + (13 / 5) = 6
```

---

### Example 3

Input:

```
tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
```

Output:

```
22
```

---

## Intuition

In Reverse Polish Notation, operators always appear **after** their operands.

Whenever we encounter a number, we store it because it may be used later.

Whenever we encounter an operator, it must operate on the **two most recently stored operands**.

This is exactly how a **Stack (LIFO)** works.

---

## Thought Process

- Traverse each token.
- If it is a number, push it onto the stack.
- If it is an operator:
  - Pop the top two numbers.
  - Perform the operation.
  - Push the result back.
- After processing every token, only one value remains in the stack.
- That value is the final answer.

---

## Algorithm

1. Create an empty stack.
2. Traverse every token.
3. If the token is a number:
   - Convert it to an integer.
   - Push it into the stack.
4. Otherwise:
   - Pop the second operand (`b`).
   - Pop the first operand (`a`).
   - Compute `a operator b`.
   - Push the result back.
5. Return the remaining element from the stack.

---

## Java Solution

```java
import java.util.Stack;

class Solution {
    public int evalRPN(String[] tokens) {

        Stack<Integer> stack = new Stack<>();

        for(String token : tokens) {

            if(token.equals("+")) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(a + b);
            }
            else if(token.equals("-")) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(a - b);
            }
            else if(token.equals("*")) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(a * b);
            }
            else if(token.equals("/")) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(a / b);
            }
            else {
                stack.push(Integer.parseInt(token));
            }
        }

        return stack.pop();
    }
}
```

---

## Dry Run

Input:

```
tokens = ["2","1","+","3","*"]
```

| Token | Stack | Action |
|-------|-------|--------|
|2|[2]|Push|
|1|[2,1]|Push|
|+|[3]|Pop 1 & 2 → 2+1=3|
|3|[3,3]|Push|
|*|[9]|Pop 3 & 3 → 3×3=9|

Final Answer:

```
9
```

---

## Complexity Analysis

- **Time Complexity:** `O(n)`

Each token is processed exactly once.

- **Space Complexity:** `O(n)`

In the worst case, all operands are stored in the stack.

---

## Key Takeaways

- Reverse Polish Notation naturally follows the **LIFO** principle.
- Always pop the operands in this order:
  - First pop → **second operand (`b`)**
  - Second pop → **first operand (`a`)**
- Perform the operation as:

```text
a operator b
```

**Not**

```text
b operator a
```

This is especially important for subtraction (`-`) and division (`/`).

- Every operand is pushed once and every result is pushed once, making the solution **O(n)**.
```
