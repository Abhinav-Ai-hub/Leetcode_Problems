# 700. Search in a Binary Search Tree

## Problem Description

You are given the root of a Binary Search Tree (BST) and an integer `val`.

Find the node in the BST whose value equals `val` and return the subtree rooted at that node.

If such a node does not exist, return `null`.

### Example 1

**Input:**

```text
root = [4,2,7,1,3]
val = 2
```

**Output:**

```text
[2,1,3]
```

**Explanation:**

The node containing value `2` is found.

We return the subtree rooted at that node.

### Example 2

**Input:**

```text
root = [4,2,7,1,3]
val = 5
```

**Output:**

```text
null
```

**Explanation:**

The value `5` does not exist in the BST.

---

## Approach: Binary Search Tree + Recursion

### Intuition

A Binary Search Tree follows a specific ordering property:

* Every value in the left subtree is smaller than the current node.
* Every value in the right subtree is greater than the current node.

We can use this property to avoid searching the entire tree.

At every node:

1. If the current node is `null`, the target does not exist.
2. If the current node's value equals the target, return the current node.
3. If the target is smaller, recursively search the left subtree.
4. If the target is greater, recursively search the right subtree.

This allows us to eliminate one subtree at every step.

### Why Recursion?

Each subtree is itself a Binary Search Tree.

Therefore, we can apply the exact same searching logic to the left or right subtree.

This is a natural recursive problem.

---

## Algorithm

1. Start from the root.
2. If the current node is `null`, return `null`.
3. If the current node's value equals the target, return the current node.
4. If the target is smaller than the current node's value:

   * Recursively search the left subtree.
5. Otherwise:

   * Recursively search the right subtree.
6. Return the result of the recursive call.

---

## Java Solution

```java
class Solution {

    public TreeNode searchBST(TreeNode root, int val) {

        if (root == null) {
            return null;
        }

        if (root.val == val) {
            return root;
        }

        if (val < root.val) {
            return searchBST(root.left, val);
        }

        return searchBST(root.right, val);
    }
}
```

---

## Dry Run

Consider the BST:

```text
          4
         / \
        2   7
       / \
      1   3
```

Target:

```text
val = 2
```

### Step 1: Start from Root

```text
root.val = 4
```

Since `2 < 4`, move to the left subtree.

```java
searchBST(root.left, 2);
```

### Step 2: Examine the Left Child

```text
root.val = 2
```

Since `2 == 2`, the target has been found.

Return the current node.

### Final Output

```text
      2
     / \
    1   3
```

The returned value is the subtree rooted at node `2`.

---

## Understanding TreeNode

A TreeNode is a user-defined class used to create individual nodes of a binary tree.

A typical TreeNode definition looks like this:

```java
public class TreeNode {

    int val;

    TreeNode left;

    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}
```

Each node contains:

* `val`: Stores the value of the node.
* `left`: Reference to the left child.
* `right`: Reference to the right child.

When we write:

```java
return root;
```

We return the reference to the actual TreeNode object.

We are not creating a new tree or copying its values.

---

## Complexity Analysis

Let `h` be the height of the BST.

### Time Complexity

At every recursive call, we move either left or right.

We never need to explore both subtrees.

Therefore:

**O(h)**

For a balanced BST:

**O(log n)**

For a completely skewed BST:

**O(n)**

### Auxiliary Space Complexity

The recursive call stack depends on the height of the tree.

**O(h)**

* Balanced BST: O(log n)
* Skewed BST: O(n)

---

## Pattern Recognition

Think of BST-based searching when:

1. The problem explicitly gives you a Binary Search Tree.
2. You need to search for a particular value.
3. You know the BST ordering property.
4. You can eliminate an entire subtree based on a comparison.

### Key Takeaway

Do not traverse the entire tree unnecessarily.

Use the BST property:

* Smaller target → Left.
* Greater target → Right.
* Equal target → Return current node.

The most important idea is that every subtree is itself a BST, which makes recursion a natural solution.
