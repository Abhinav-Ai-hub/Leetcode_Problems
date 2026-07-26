# Valid Parentheses

## Problem Statement

Given a string `s` containing just the characters:

- '('
- ')'
- '{'
- '}'
- '['
- ']'

Determine if the input string is valid.

A string is valid if:

1. Every opening bracket has a corresponding closing bracket.
2. Brackets close in the correct order.
3. Every closing bracket matches the most recent unmatched opening bracket.

---

## Examples

### Example 1

Input:
```
s = "()"
```

Output:
```
true
```

---

### Example 2

Input:
```
s = "()[]{}"
```

Output:
```
true
```

---

### Example 3

Input:
```
s = "(]"
```

Output:
```
false
```

---

### Example 4

Input:
```
s = "([)]"
```

Output:
```
false
```

---

### Example 5

Input:
```
s = "{[]}"
```

Output:
```
true
```

---

## Intuition

The latest opening bracket must always be matched first.

This follows the **Last In, First Out (LIFO)** principle, making a **Stack** the ideal data structure.

### Thought Process

- Whenever an opening bracket is encountered, push it onto the stack.
- Whenever a closing bracket appears:
  - If the stack is empty, the string is invalid.
  - Pop the top element.
  - Check whether it matches the corresponding opening bracket.
- After processing the entire string:
  - If the stack is empty, every opening bracket found a matching closing bracket.
  - Otherwise, the string is invalid.

---

## Algorithm

1. Create an empty stack.
2. Traverse each character of the string.
3. If it is an opening bracket, push it.
4. Otherwise:
   - If the stack is empty, return `false`.
   - Pop the top.
   - Verify the popped bracket matches the current closing bracket.
5. After traversal, return `stack.isEmpty()`.

---

## Java Solution

```java
import java.util.Stack;

class Solution {
    public boolean isValid(String s) {

        Stack<Character> stack = new Stack<>();

        for(char ch : s.toCharArray()) {

            if(ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            }
            else {

                if(stack.isEmpty())
                    return false;

                char top = stack.pop();

                if(ch == ')' && top != '(')
                    return false;

                if(ch == '}' && top != '{')
                    return false;

                if(ch == ']' && top != '[')
                    return false;
            }
        }

        return stack.isEmpty();
    }
}
```

---

## Complexity Analysis

- **Time Complexity:** `O(n)`  
  Each character is pushed and popped at most once.

- **Space Complexity:** `O(n)`  
  In the worst case, all opening brackets are stored in the stack.

---

## Key Takeaways

- Matching symbols → **Stack**
- Nested structures → **Stack**
- Latest opening should close first → **LIFO**
- Always check:
  - Empty stack before popping.
  - Matching bracket type.
  - Stack is empty at the end.
