# Binary Tree 🌳

## What This Topic Covers
Binary tree data structure and related algorithms. This section covers:
- Binary tree construction and traversal
- Tree property validation
- Path-based problems
- Level-order and depth-first operations

## Common Problems & Patterns
- **Tree Traversals** - Inorder, preorder, postorder (recursive & iterative)
- **Level Order Traversal** - BFS-based tree exploration
- **Path Sum Problems** - Root-to-leaf and any-path sums
- **Tree Diameter** - Longest path between any two nodes
- **Lowest Common Ancestor** - Finding common ancestors
- **Tree Serialization** - Converting tree to/from string
- **Binary Tree Views** - Left, right, top, bottom views


## Problems Solved

| Problem | Platform | Difficulty | Summary |
|---------|----------|------------|----------|
| [2872. Maximum Number of K-Divisible Components](./MaximumNumberOfKDivisibleComponents.java) | LeetCode | Hard | In an undirected tree, remove any set of edges such that every resulting connected component has a total node-value sum divisible by k. Return the maximum possible number of connected components. |
| [1161. Maximum Level Sum of a Binary Tree](./MaxLevelSumBinaryTree.java) | LeetCode | Medium | Level Order Traversal to find sum of each level and obtain maximal sum level |
| [1339. Maximum Product of Splitted Binary Tree](./MaxProductSplittedBinaryTree.java) | LeetCode | Medium | Every subtree's root stores its tree's sum and uses prefix idea to get maximum splitted product | 
| [1123. Lowest Common Ancestor of Deepest Leaves](./LCADeepestLeaves.java) | LeetCode | Medium | use pair that stores lca and its depth, and each node returns pair with max depth, but if both branches are giving same depth, then give pair that gives itself and depth|
| [1026. Maximum Difference Between Node and Ancestor](./MaxDiffAncestor.java) | LeetCode | Medium | find min and max of subtree and evaluate for each subtree from root | 

## Implementation Notes
Solutions are written in **Java** with clean, optimized implementations focusing on:
- Proper tree node structure and management
- Recursive and iterative approaches
- Space-efficient traversal techniques
- Edge case handling (null nodes, single nodes)

---
*Part of DSA Journey: A New Dawn 🌅*