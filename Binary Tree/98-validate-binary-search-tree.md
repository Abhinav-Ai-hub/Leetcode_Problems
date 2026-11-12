# 1361. Validate Binary Tree Nodes

## Problem Description

You have `n` binary tree nodes numbered from `0` to `n - 1`.

You are given two integer arrays:

- `leftChild[i]`: The left child of node `i`, or `-1` if there is no left child.
- `rightChild[i]`: The right child of node `i`, or `-1` if there is no right child.

Return `true` if all the nodes form exactly one valid binary tree; otherwise, return `false`.

### Example 1

**Input:**
```text
n = 4
leftChild = [1, -1, 3, -1]
rightChild = [2, -1, -1, -1]
```

**Output:**
```text
true
```

**Explanation:**

The tree looks like:

```text
       0
      / \
     1   2
    /
   3
```

All four nodes are connected, node `0` is the only root, and every other node has exactly one parent.

### Example 2

**Input:**
```text
n = 4
leftChild = [1, -1, 3, -1]
rightChild = [2, 3, -1, -1]
```

**Output:**
```text
false
```

**Explanation:**

Node `3` has two parents: node `0` and node `2`.

A valid binary tree cannot have multiple parents for the same node.

---

## Intuition

The first question we should ask is:

**What conditions must be satisfied for a collection of nodes to represent exactly one binary tree?**

There are three important conditions.

### 1. Exactly One Root

A root node is a node that does not have any parent.

We can identify it using the concept of **indegree**.

Indegree represents the number of incoming edges to a node.

For a valid binary tree:

- Root must have indegree `0`.
- Every other node must have indegree `1`.

Therefore, exactly one node must have indegree `0`.

### 2. Every Node Must Have At Most One Parent

A binary tree cannot contain a node with multiple parents.

If any node has an indegree greater than `1`, we immediately return `false`.

### 3. Every Node Must Be Connected

Even if we find exactly one root and no node has multiple parents, we still need to verify that every node belongs to the same tree.

We perform DFS starting from the root using a stack.

During traversal:

- Mark every visited node.
- If a node is encountered again, return `false`.
- Count the number of visited nodes.

At the end, if the visited node count equals `n`, all nodes belong to the same tree.

Otherwise, some nodes are disconnected.

---

## Algorithm

### Step 1: Calculate Indegree

Create an array:

```java
int[] indegree = new int[n];
```

Traverse all nodes.

For every left or right child that is not `-1`, increase its indegree.

### Step 2: Identify the Root

Traverse the indegree array.

- If indegree is `0`, identify that node as the root.
- If more than one node has indegree `0`, return `false`.
- If any node has indegree greater than `1`, return `false`.
- If no root exists, return `false`.

### Step 3: Perform DFS Using Stack

Create:

```java
Stack<Integer> stack = new Stack<>();
boolean[] visited = new boolean[n];
```

Push the root into the stack.

While the stack is not empty:

1. Pop a node.
2. If it is already visited, return `false`.
3. Mark it as visited.
4. Increment the visited node count.
5. Push its left and right children if they exist.

### Step 4: Check Connectivity

Finally, check:

```java
return count == n;
```

If every node was visited, return `true`.

Otherwise, return `false`.

---

## Java Solution

```java
class Solution {
       boolean isValidBSThelper(TreeNode node, Integer lower,Integer upper){
    
        if(node == null){
            return true;
        }

       
        if((upper != null && node.val>= upper) || (lower!=null && node.val<=lower)){
            return false;
        }

        boolean left=isValidBSThelper(node.left,lower,node.val);

        if(!left){
            return false;
        }
    return isValidBSThelper(node.right,node.val,upper);

    }
     public boolean isValidBST(TreeNode root) {
        if(root.left==null && root.right == null){
            return true;
        }
    
        return isValidBSThelper(root,null,null);
        
        
    }
}
```

---

## Dry Run

Consider:

```text
n = 4

leftChild  = [1, -1, 3, -1]
rightChild = [2, -1, -1, -1]
```

### Step 1: Calculate Indegree

| Node | Parent | Indegree |
|---|---|---|
| 0 | None | 0 |
| 1 | 0 | 1 |
| 2 | 0 | 1 |
| 3 | 2 | 1 |

The indegree array becomes:

```text
[0, 1, 1, 1]
```

### Step 2: Find Root

Node `0` has indegree `0`.

Therefore:

```text
root = 0
```

There is exactly one root, and no node has more than one parent.

### Step 3: DFS Using Stack

Initially:

```text
Stack = [0]
Visited = [false, false, false, false]
Count = 0
```

**Iteration 1:**

Pop node `0`.

Push its children `1` and `2`.

```text
Stack = [1, 2]
Visited = [true, false, false, false]
Count = 1
```

**Iteration 2:**

Pop node `2`.

Its left child is `3`.

```text
Stack = [1, 3]
Visited = [true, false, true, false]
Count = 2
```

**Iteration 3:**

Pop node `3`.

It has no children.

```text
Stack = [1]
Visited = [true, false, true, true]
Count = 3
```

**Iteration 4:**

Pop node `1`.

It has no children.

```text
Stack = []
Visited = [true, true, true, true]
Count = 4
```

### Step 4: Final Check

```java
return count == n;
```

```text
4 == 4
```

**Output: true**

All nodes are connected to exactly one root.

---

## Complexity Analysis

Let `n` be the number of nodes.

### Time Complexity: O(n)

- Calculating indegree: O(n).
- Finding the root: O(n).
- DFS traversal: O(n).

Therefore:

**Overall Time Complexity = O(n)**

### Space Complexity: O(n)

- Indegree array: O(n).
- Visited array: O(n).
- Stack: O(n) in the worst case.

Therefore:

**Overall Space Complexity = O(n)**

---

## Key Takeaways

1. Use **indegree** to identify the root.
2. A valid binary tree must have exactly one root.
3. Every node must have at most one parent.
4. Use DFS or BFS to check connectivity.
5. A visited array helps detect repeated visits and cycles.
6. The number of visited nodes must equal `n`.

### Pattern Recognition

Whenever a problem gives you nodes and their left/right child relationships and asks whether they form one valid tree, think about:

**Indegree + Root Identification + DFS/BFS + Visited Array**

This combination helps validate whether the given structure forms exactly one binary tree.
