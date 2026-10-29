# 1922. Count Good Numbers

## Problem Description

A digit string is considered good if the digits at even indices are even digits and the digits at odd indices are prime digits.

* At even indices, the digit must be one of `0, 2, 4, 6, 8`.
* At odd indices, the digit must be one of `2, 3, 5, 7`.

Here, indexing starts from `0`.

Given an integer `n`, return the total number of good digit strings of length `n`.

Since the answer can be very large, return it modulo `10^9 + 7`.

### Example 1

**Input:**

```text
n = 1
```

**Output:**

```text
5
```

**Explanation:**

There are five possible good strings of length one:

`0`, `2`, `4`, `6`, `8`.

The only index is `0`, which is even.

### Example 2

**Input:**

```text
n = 4
```

**Output:**

```text
400
```

**Explanation:**

The digits at indices `0` and `2` must be even, giving five choices at each position.

The digits at indices `1` and `3` must be prime, giving four choices at each position.

Therefore:

`5 × 4 × 5 × 4 = 400`

### Example 3

**Input:**

```text
n = 50
```

**Output:**

```text
564908303
```

---

## Approach: Counting + Binary Exponentiation

### Intuition

Every position has a fixed number of valid choices.

For an even index, there are five choices:

`0, 2, 4, 6, 8`

For an odd index, there are four choices:

`2, 3, 5, 7`

The choices at different positions are independent. Therefore, the multiplication principle tells us to multiply the number of choices at all positions.

For a string of length `n`:

* Number of even-indexed positions = `(n + 1) / 2`
* Number of odd-indexed positions = `n / 2`

Integer division is used in both expressions.

Consequently:

```text
answer = 5^((n + 1) / 2) × 4^(n / 2)
```

We take the result modulo `1,000,000,007`.

### Why Binary Exponentiation?

The value of `n` can be extremely large.

Calculating a power by repeatedly multiplying the base would take linear time in the exponent.

Binary exponentiation calculates the power by repeatedly halving the exponent, reducing the time complexity to logarithmic time.

---

## Algorithm

1. Calculate the number of even-indexed positions:
   `evenPositions = (n + 1) / 2`
2. Calculate the number of odd-indexed positions:
   `oddPositions = n / 2`
3. Calculate `5^evenPositions` using binary exponentiation.
4. Calculate `4^oddPositions` using binary exponentiation.
5. Multiply both results and take the modulo `1,000,000,007`.
6. Return the result.

---

## Java Solution

```java
class Solution {

    private static final long MOD = 1_000_000_007L;

    public int countGoodNumbers(long n) {

        long evenPositions = (n + 1) / 2;
        long oddPositions = n / 2;

        long evenWays = power(5, evenPositions);
        long oddWays = power(4, oddPositions);

        return (int) ((evenWays * oddWays) % MOD);
    }

    private long power(long base, long exponent) {

        if (exponent == 0) {
            return 1;
        }

        long half = power(base, exponent / 2);
        long result = (half * half) % MOD;

        if (exponent % 2 == 1) {
            result = (result * base) % MOD;
        }

        return result;
    }
}
```

---

## Dry Run

Consider:

```text
n = 4
```

### Step 1: Count the positions

Even-indexed positions:

`(4 + 1) / 2 = 2`

Odd-indexed positions:

`4 / 2 = 2`

### Step 2: Calculate the choices

The even-indexed positions each have five choices:

`evenWays = 5^2 = 25`

The odd-indexed positions each have four choices:

`oddWays = 4^2 = 16`

### Step 3: Calculate the answer

`answer = (25 × 16) % 1,000,000,007`

`answer = 400`

Therefore, the answer is:

```text
400
```

---

## Binary Exponentiation Explained

The recursive function calculates:

`power(base, exponent)`

It follows these rules:

### Base Case

When the exponent is zero:

```java
if (exponent == 0) {
    return 1;
}
```

This is because any nonzero base raised to the power zero equals one.

### Recursive Case

First, calculate the power using half the exponent:

```java
long half = power(base, exponent / 2);
```

Then square the result:

```java
long result = (half * half) % MOD;
```

If the exponent is odd, multiply once more by the base:

```java
if (exponent % 2 == 1) {
    result = (result * base) % MOD;
}
```

This works because:

* If the exponent is even, `base^exponent = base^(exponent/2) × base^(exponent/2)`.
* If the exponent is odd, `base^exponent = base^(exponent/2) × base^(exponent/2) × base`, using integer division.

---

## Complexity Analysis

Let `n` be the length of the digit string.

### Time Complexity

We calculate two powers using binary exponentiation.

Each power takes logarithmic time.

**Time Complexity: O(log n)**

### Auxiliary Space Complexity

The recursive power function has a recursion depth of `O(log n)`.

**Auxiliary Space Complexity: O(log n)**

---

## Pattern Recognition

This problem combines two useful ideas.

### 1. Multiplication Principle

When each position can be chosen independently, multiply the number of choices available at each position.

### 2. Binary Exponentiation

Whenever a problem asks you to calculate a very large power, consider binary exponentiation rather than repeated multiplication.

### Key Takeaway

The problem does not require generating the digit strings.

Count the valid choices at even and odd indices, express the answer as a product of powers, and use binary exponentiation to calculate those powers efficiently.
