# LeetCode 779: K-th Symbol in Grammar

## Problem Description

We build a sequence of rows according to the following rules:

* Row 1 contains the symbol `0`.
* Every `0` in the previous row becomes `01`.
* Every `1` in the previous row becomes `10`.

Given two integers `n` and `k`, return the `k`-th symbol in row `n`.

Positions are numbered starting from `1`.

### Example 1

**Input:**

```text
n = 1, k = 1
```

**Output:**

```text
0
```

### Example 2

**Input:**

```text
n = 2, k = 2
```

**Output:**

```text
1
```

**Explanation:**

Row 2 is `01`, so its second symbol is `1`.

---

## 1. Hint — How to Recognize Recursion

The sequence is generated from the previous row:

```text
Row 1: 0
Row 2: 01
Row 3: 0110
Row 4: 01101001
```

Instead of generating the entire row, we can determine which symbol in the previous row produced the symbol at position `k`.

Every parent generates two children:

```text
0 → 01
1 → 10
```

Therefore:

* The first child preserves the parent's symbol.
* The second child is the opposite of the parent's symbol.

This suggests **recursion using the parent-child relationship**.

## 2. Intuition

Each parent generates exactly two children.

For a position `k`, its parent is located at:

```text
parent position = ceil(k / 2)
```

In Java:

```java
(k + 1) / 2
```

We recursively find the parent's symbol in the previous row.

Then:

* If `k` is odd, return the parent's symbol.
* If `k` is even, return the opposite of the parent's symbol.

This avoids constructing the entire sequence.

## 3. Algorithm

1. If `n == 1`, return `0`.
2. Calculate the parent position using `(k + 1) / 2`.
3. Recursively find the parent's symbol in row `n - 1`.
4. If `k` is odd, return the parent's symbol.
5. Otherwise, return `1 - parent`.

## 4. Java Solution

```java
class Solution {
    public int kthGrammar(int n, int k) {
      return  kthGrammarhelper( n,  k,0);
        
    }
    public int kthGrammarhelper(int n, int k,int flag){
        if(n==1){
            return flag%2==0? 0:1; // conditional that if flag is even keep it 0 and if one then flip it to 1
        }
        if(k%2==0){
           return kthGrammarhelper(n-1,k/2,flag+1);
        }
         return kthGrammarhelper(n-1,(k+1)/2,flag);
    }
}
```

## 5. Code Explanation

### Base Case

```java
if (n == 1) {
    return 0;
}
```

Row 1 contains only the symbol `0`, so we return `0`.

### Find the Parent

```java
int parent = kthGrammar(n - 1, (k + 1) / 2);
```

Every parent produces two children.

For example, if `k = 5`:

```text
Parent position = (5 + 1) / 2
                = 3
```

Therefore, the fifth symbol in the current row comes from the third symbol in the previous row.

### Odd Position

```java
if (k % 2 == 1) {
    return parent;
}
```

Odd positions correspond to the first child, which preserves the parent's symbol.

### Even Position

```java
return 1 - parent;
```

Even positions correspond to the second child, which flips the parent's symbol.

```text
If parent = 0, child = 1.
If parent = 1, child = 0.
```

## 6. Dry Run

Consider:

```text
n = 4
k = 5
```

Recursive calls:

```text
kthGrammar(4, 5)
    ↓
kthGrammar(3, 3)
    ↓
kthGrammar(2, 2)
    ↓
kthGrammar(1, 1)
```

The base case returns `0`.

Now evaluate the calls while returning:

| Call               | Position | Operation           | Result |
| ------------------ | -------: | ------------------- | -----: |
| `kthGrammar(1, 1)` |        1 | Base case           |      0 |
| `kthGrammar(2, 2)` |        2 | Flip the parent     |      1 |
| `kthGrammar(3, 3)` |        3 | Preserve the parent |      1 |
| `kthGrammar(4, 5)` |        5 | Preserve the parent |      1 |

**Final answer: `1`**

## 7. Complexity Analysis

**Time Complexity:** `O(n)`

Each recursive call decreases the row number by one until the base case is reached.

**Auxiliary Space Complexity:** `O(n)`

The recursion stack can contain up to `n` calls.

## 8. Pattern Recognition

When a sequence is recursively generated, ask:

* Can I identify the parent of the required element?
* Can I calculate the parent's position?
* Can I determine the current element from the parent?

If yes, try reducing the problem to a smaller input.

For this problem:

```text
Find the k-th symbol
        ↓
Find its parent position
        ↓
Recursively solve the previous row
        ↓
Preserve or flip the parent's symbol
        ↓
Return the answer
```

## Final Takeaway

**LeetCode 779 = Recursion + Parent Position + Odd/Even Rule**

* Base case: `n == 1` returns `0`.
* Parent position: `(k + 1) / 2`.
* Odd `k`: return the parent.
* Even `k`: return `1 - parent`.
