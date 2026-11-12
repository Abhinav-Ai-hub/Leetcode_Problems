# 450. Delete Node in a BST

## Problem Description

Given the root of a Binary Search Tree (BST) and an integer `key`, delete the node with the given key from the BST.

Return the root of the modified BST.

The BST must maintain its properties after deletion.

### BST Properties

1. All nodes in the left subtree contain values smaller than the root.
2. All nodes in the right subtree contain values greater than the root.
3. Both left and right subtrees must also be valid BSTs.

### Example

**Input:**

```text
root = [5,3,6,2,4,null,7]
key = 3
```

**Output:**

```text
[5,4,6,2,null,null,7]
```

**Explanation:**

We need to delete node `3`.

Since node `3` has two children, we replace it with its inorder successor, which is `4`.

Then we delete the original node `4`.

The resulting BST remains valid.

---

## Intuition

The first thing to understand is that deleting a node from a BST is different from deleting a node from a normal binary tree.

We cannot simply remove a node because doing so might violate the BST property.

We need to consider three different situations.

### Case 1: Node Has No Children

If the node is a leaf node, we can simply delete it.

In Java, we return:

```java
return null;
```

The parent will update its corresponding child reference to `null`.

### Case 2: Node Has One Child

If the node has only one child, we can directly replace the node with that child.

If the left child is null:

```java
return root.right;
```

If the right child is null:

```java
return root.left;
```

This maintains the BST property.

### Case 3: Node Has Two Children

This is the most important case.

We cannot simply delete the node because it has two subtrees.

Instead, we use the **Inorder Successor**.

The inorder successor is the smallest value in the right subtree.

To find it:

1. Move to the right child.
2. Keep moving left until there is no left child.
3. The node reached is the inorder successor.

Replace the current node's value with the successor's value.

Finally, recursively delete the original successor node from the right subtree.

---

## Algorithm

### Step 1: Search for the Node

Use BST properties to find the node.

- If `key < root.val`, search in the left subtree.
- If `key > root.val`, search in the right subtree.
- Otherwise, the current node is the node to delete.

### Step 2: Handle the Deletion Cases

If the node has no left child, return its right child.

If the node has no right child, return its left child.

If it has two children:

- Find the inorder successor.
- Copy its value into the current node.
- Delete the original successor from the right subtree.

### Step 3: Return the Modified Root

Every recursive call returns the updated root of its subtree.

This is why we write:

```java
root.left = deleteNode(root.left, key);
```

and:

```java
root.right = deleteNode(root.right, key);
```

These assignments reconnect the modified subtrees to their parent.

---

## Java Solution

```java
class Solution {

    public TreeNode deleteNode(TreeNode root, int key) {

        // Base case: Node not found
        if (root == null) {
            return null;
        }

        // Search in the left subtree
        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        }

        // Search in the right subtree
        else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        }

        // Node found
        else {

            // Case 1: No left child
            if (root.left == null) {
                return root.right;
            }

            // Case 2: No right child
            if (root.right == null) {
                return root.left;
            }

            // Case 3: Two children

            // Find inorder successor
            TreeNode successor = root.right;

            while (successor.left != null) {
                successor = successor.left;
            }

            // Replace current node's value
            root.val = successor.val;

            // Delete the original successor
            root.right = deleteNode(root.right, successor.val);
        }

        return root;
    }
}
```

---

## Dry Run

Consider the following BST:

```text
          5
         / \
        3   6
       / \   \
      2   4   7
```

We need to delete:

```text
key = 5
```

### Step 1: Find the Node

The root value is `5`.

Since:

```text
key == root.val
```

We have found the node to delete.

### Step 2: Check Its Children

Node `5` has:

- Left child = `3`
- Right child = `6`

Therefore, we have the third case: the node has two children.

### Step 3: Find the Inorder Successor

Start from the right child (`6`).

Keep moving left.

```text
          5
         / \
        3   6
       / \   \
      2   4   7
```

Node `6` has no left child.

Therefore:

```text
successor = 6
```

### Step 4: Replace the Current Node

Replace the value of node `5` with `6`.

```text
          6
         / \
        3   6
       / \   \
      2   4   7
```

Now we have two nodes with value `6`, so we must delete the original successor.

### Step 5: Delete the Original Successor

Call:

```java
root.right = deleteNode(root.right, successor.val);
```

The original node `6` has no left child.

Therefore, return its right child (`7`).

### Final BST

```text
          6
         / \
        3   7
       / \
      2   4
```

The BST property is maintained.

---

## Why Do We Write root.left = deleteNode(root.left, key)?

This is an important recursion concept.

Consider:

```java
root.left = deleteNode(root.left, key);
```

The recursive function returns the updated root of the left subtree.

We assign that returned reference back to `root.left` so that the parent points to the modified subtree.

The same logic applies to:

```java
root.right = deleteNode(root.right, key);
```

**Remember:** In recursive tree problems, the return value often represents the updated root of the subtree.

---

## Complexity Analysis

Let `h` represent the height of the BST.

### Time Complexity: O(h)

Searching for the node takes O(h).

Finding the inorder successor takes at most O(h).

Deleting the successor also takes O(h) in the worst case.

These operations happen along paths in the tree, so the overall complexity remains O(h).

- Balanced BST: O(log n)
- Skewed BST: O(n)

### Space Complexity: O(h)

The recursive calls consume stack space proportional to the height of the tree.

- Balanced BST: O(log n)
- Skewed BST: O(n)

---

## Key Takeaways

1. BST properties help us search efficiently.
2. A leaf node can be deleted by returning `null`.
3. A node with one child can be replaced by that child.
4. A node with two children can be replaced using its inorder successor.
5. The inorder successor is the minimum value in the right subtree.
6. Recursive calls return the modified root of each subtree.

### Pattern Recognition

Whenever a problem asks you to delete a node from a BST, think:

**Search using BST properties → Handle three deletion cases → Use inorder successor when there are two children → Return the updated subtree root.**
