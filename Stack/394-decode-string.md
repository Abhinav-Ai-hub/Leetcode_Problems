# LeetCode 394: Decode String

## Problem Statement

Given an encoded string, return its decoded string.

The encoding rule is:

```text
k[encoded_string]
```

This means that the string inside the brackets is repeated exactly `k` times.

The input string contains lowercase English letters, digits, and brackets `[` and `]`.

The encoded string is always valid.

---

## Examples

### Example 1

Input:

```text
s = "3[a]2[bc]"
```

Output:

```text
"aaabcbc"
```

Explanation:

```text
3[a] → aaa
2[bc] → bcbc

Result:

aaabcbc
```

---

### Example 2

Input:

```text
s = "3[a2[c]]"
```

Output:

```text
"accaccacc"
```

Explanation:

First decode:

```text
2[c] → cc
```

Therefore:

```text
a2[c] → acc
```

Then:

```text
3[acc] → accaccacc
```

---

### Example 3

Input:

```text
s = "2[abc]3[cd]ef"
```

Output:

```text
"abcabccdcdcdef"
```

---

## Intuition

The important observation is that the problem contains **nested brackets**.

For example:

```text
3[a2[c]]
```

We cannot decode the outer expression immediately.

We first need to decode:

```text
2[c]
```

Then use that result inside:

```text
3[a...]
```

This follows the **Last In, First Out (LIFO)** principle.

The most recently opened bracket must be closed and processed first.

Therefore, a **Stack** is the natural data structure.

---

## Thought Process

We need to keep track of two things whenever we encounter `[`:

1. The number before `[`.
2. The string that existed before `[`

For example:

```text
3[a2[c]]
```

When we encounter:

```text
3[
```

we store:

```text
count = 3
previous string = ""
```

Then we process:

```text
a
```

Current string:

```text
a
```

Then we encounter:

```text
2[
```

Now we store:

```text
count = 2
previous string = "a"
```

Then:

```text
c
```

Current string:

```text
c
```

When we encounter `]`:

```text
2[c]
```

becomes:

```text
cc
```

We restore the previous string:

```text
a + cc
```

giving:

```text
acc
```

Then the outer `]` gives:

```text
3[acc]
```

which becomes:

```text
accaccacc
```

---

## Why Do We Need Two Stacks?

We need to remember two different things.

### Stack 1: Count Stack

Stores repetition counts.

```text
countStack
```

For:

```text
3[a2[c]]
```

we may have:

```text
3
2
```

---

### Stack 2: String Stack

Stores the string that existed before each `[`.

```text
stringStack
```

For example:

```text
""
"a"
```

So we maintain:

```text
countStack  → repetition numbers

stringStack → previous strings
```

When we encounter `]`, we pop from both stacks.

---

## Algorithm

1. Create:
   - `countStack`
   - `stringStack`
2. Maintain:
   - `number` → current repetition count.
   - `current` → current string being constructed.
3. Traverse the string character by character.

### If the character is a digit

Build the complete number.

For example:

```text
1 → 12
```

using:

```java
number = number * 10 + digit;
```

This allows multi-digit numbers such as:

```text
12[a]
```

---

### If the character is `[` 

Store the current state:

```java
countStack.push(number);
stringStack.push(current);
```

Then reset:

```java
number = 0;
current = "";
```

---

### If the character is `]`

Retrieve the previous state:

```java
int count = countStack.pop();
String previous = stringStack.pop();
```

Repeat the current string `count` times.

Then combine:

```text
previous + repeated current
```

---

### If the character is a letter

Add it to the current string.

---

## Java Solution

```java
class Solution {
    public String decodeString(String s) {

        Stack<Integer> number = new Stack<>();
        Stack<String> prevstring = new Stack<>();

        String current = "";
        int num = 0;

        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);

            if (Character.isDigit(ch)) {

                num = num * 10 + (ch - '0');

            } 
            else if (ch == '[') {

                number.push(num);
                prevstring.push(current);

                num = 0;
                current = "";

            } 
            else if (ch == ']') {

                int repeat = number.pop();
                String previous = prevstring.pop();

                current = previous + current.repeat(repeat);

            } 
            else {

                current += ch;
            }
        }

        return current;
    }
}
```

---

## Dry Run

Input:

```text
s = "3[a2[c]]"
```

### Read `3`

```text
number = 3
```

---

### Read `[` 

Push:

```text
countStack = [3]
stringStack = [""]
```

Reset:

```text
number = 0
current = ""
```

---

### Read `a`

```text
current = "a"
```

---

### Read `2`

```text
number = 2
```

---

### Read `[` 

Push:

```text
countStack = [3,2]
stringStack = ["","a"]
```

Reset:

```text
number = 0
current = ""
```

---

### Read `c`

```text
current = "c"
```

---

### Read `]`

Pop:

```text
count = 2
previous = "a"
```

Repeat:

```text
"c" × 2 = "cc"
```

Combine:

```text
"a" + "cc"
```

Therefore:

```text
current = "acc"
```

---

### Read final `]`

Pop:

```text
count = 3
previous = ""
```

Repeat:

```text
"acc" × 3
```

Result:

```text
"accaccacc"
```

---

## Complexity Analysis

Let `n` be the length of the input string and `k` represent the size of the decoded output.

### Time Complexity

```text
O(k)
```

We ultimately have to construct the decoded string, whose size can be much larger than the input.

---

### Space Complexity

```text
O(k + n)
```

The stacks store nested states, and the decoded strings require space proportional to the output.

---

## Key Takeaways

### Pattern Recognition

Whenever you see:

```text
Nested brackets
+
Process innermost expression first
```

think:

```text
STACK
```

### Core Idea

When we encounter:

```text
[
```

save:

```text
count
previous string
```

When we encounter:

```text
]
```

restore them and decode the current section.

### The Mental Model

```text
Opening [

Save current state
        ↓
Process inner expression
        ↓
Closing ]
        ↓
Restore previous state
        ↓
Build decoded string
```

### Most Important Insight

The stack is not storing just characters.

It stores the **state of the outer expression** while we temporarily work on the inner expression.

For:

```text
3[a2[c]]
```

think:

```text
Outer:
3[ ........ ]
   ↓
   Inner:
   2[c]
```

The inner expression must finish first.

That is exactly why **Stack + LIFO** works so naturally here.
```
