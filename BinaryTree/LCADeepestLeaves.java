package BinaryTree;

/*
 🔹 Problem: 1123. Lowest Common Ancestor of Deepest Leaves
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Tree, Depth-First Search, Breadth-First Search, Binary Tree, Hash Table
 🔹 Link: https://leetcode.com/problems/lowest-common-ancestor-of-deepest-leaves/

 ------------------------------------------------------------
 📝 Problem Statement:

Given the root of a binary tree, return the lowest common ancestor of its 
deepest leaves.

Recall that:
• The node of a binary tree is a leaf if and only if it has no children
• The depth of the root of the tree is 0. If the depth of a node is d, the 
  depth of each of its children is d + 1.
• The lowest common ancestor of a set S of nodes, is the node A with the 
  largest depth such that every node in S is in the subtree with root A.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: root = [3,5,1,6,2,0,8,null,null,7,4]
Output: [2,7,4]
Explanation: We return the node with value 2, colored in yellow in the diagram.
The nodes coloured in blue are the deepest leaf-nodes of the tree.
Note that nodes 6, 0, and 8 are also leaf nodes, but the depth of them is 2, 
but the depth of nodes 7 and 4 is 3.

Example 2:
Input: root = [1]
Output: [1]
Explanation: The root is the deepest node in the tree, and it's the lca of itself.

Example 3:
Input: root = [0,1,3,null,2]
Output: [2]
Explanation: The deepest leaf node in the tree is 2, the lca of one node is itself.

 ------------------------------------------------------------
 ⚠️ Constraints:
 • The number of nodes in the tree will be in the range [1, 1000]
 • 0 <= Node.val <= 1000
 • The values of the nodes in the tree are unique

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Find the LCA (Lowest Common Ancestor) of all deepest leaves.

📍 **Approach 1 (Two-Pass DFS):**
   - First pass: Find maximum depth of tree
   - Second pass: Find all deepest leaves at max depth
   - Third operation: Find LCA of these deepest leaves
   - Time: O(n), Space: O(n) for storing leaves + O(h) recursion
   - Straightforward but requires multiple passes

📍 **Approach 2 (BFS to Find Deepest Level):**
   - Use level-order traversal to find deepest level
   - Track all nodes at deepest level
   - Find LCA of these nodes using parent pointers or path tracking
   - Time: O(n), Space: O(n)
   - Good for iterative solutions

📍 **Approach 3 (Single-Pass DFS with Pair - Optimized):**
   - Return both node and depth in single DFS traversal
   - For each node, compare depths of left and right subtrees
   - If depths equal: current node is LCA, return it with depth
   - If depths differ: return subtree with greater depth
   - Time: O(n), Space: O(h) recursion only
   - Elegant single-pass solution with optimal space

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - Two-Pass DFS)
   ⏱️ Time Complexity: O(n) - multiple tree traversals
   💾 Space Complexity: O(n) - storing deepest leaves + O(h) recursion
   
   🧠 **Key Insight:**
   Separate concerns: first find max depth, then find deepest leaves, 
   then compute their LCA.
   
   💡 **Why it works:**
   - First DFS finds maximum depth in tree
   - Second DFS collects all nodes at max depth
   - Standard LCA algorithm finds their common ancestor
   - Clear separation of concerns but requires multiple passes
 ------------------------------------------------------------

// import java.util.*;
// 
// class Solution {
//     private int maxDepth = 0;
//     private List<TreeNode> deepestLeaves = new ArrayList<>();
//     
//     public TreeNode lcaDeepestLeaves(TreeNode root) {
//         // Pass 1: Find max depth
//         findMaxDepth(root, 0);
//         
//         // Pass 2: Collect deepest leaves
//         collectDeepestLeaves(root, 0);
//         
//         // Pass 3: Find LCA of deepest leaves
//         return findLCA(root, new HashSet<>(deepestLeaves));
//     }
//     
//     private void findMaxDepth(TreeNode node, int depth) {
//         if(node == null) return;
//         maxDepth = Math.max(maxDepth, depth);
//         findMaxDepth(node.left, depth + 1);
//         findMaxDepth(node.right, depth + 1);
//     }
//     
//     private void collectDeepestLeaves(TreeNode node, int depth) {
//         if(node == null) return;
//         if(depth == maxDepth) {
//             deepestLeaves.add(node);
//             return;
//         }
//         collectDeepestLeaves(node.left, depth + 1);
//         collectDeepestLeaves(node.right, depth + 1);
//     }
//     
//     private TreeNode findLCA(TreeNode node, Set<TreeNode> targets) {
//         if(node == null || targets.contains(node)) return node;
//         
//         TreeNode left = findLCA(node.left, targets);
//         TreeNode right = findLCA(node.right, targets);
//         
//         if(left != null && right != null) return node;
//         return left != null ? left : right;
//     }
// }

 ------------------------------------------------------------
 🔹 Approach 2 (Commented - BFS Level Order)
   ⏱️ Time Complexity: O(n) - level order traversal
   💾 Space Complexity: O(n) - queue can hold entire level
   
   🧠 **Key Insight:**
   Use BFS to process level by level. The last level processed contains
   all deepest leaves. Then find their LCA.
   
   💡 **Why it works:**
   - BFS processes nodes level by level
   - Keep overwriting current level until no more levels
   - Last level stored contains all deepest nodes
   - Find LCA of these nodes using standard technique
   - Iterative approach, good for interviews
 ------------------------------------------------------------

// import java.util.*;
// 
// class Solution {
//     public TreeNode lcaDeepestLeaves(TreeNode root) {
//         if(root == null) return null;
//         
//         Queue<TreeNode> queue = new LinkedList<>();
//         queue.offer(root);
//         List<TreeNode> currentLevel = new ArrayList<>();
//         
//         // BFS to find deepest level
//         while(!queue.isEmpty()) {
//             int size = queue.size();
//             currentLevel.clear();
//             
//             for(int i = 0; i < size; i++) {
//                 TreeNode node = queue.poll();
//                 currentLevel.add(node);
//                 
//                 if(node.left != null) queue.offer(node.left);
//                 if(node.right != null) queue.offer(node.right);
//             }
//         }
//         
//         // Find LCA of deepest level nodes
//         return findLCA(root, new HashSet<>(currentLevel));
//     }
//     
//     private TreeNode findLCA(TreeNode node, Set<TreeNode> targets) {
//         if(node == null || targets.contains(node)) return node;
//         
//         TreeNode left = findLCA(node.left, targets);
//         TreeNode right = findLCA(node.right, targets);
//         
//         if(left != null && right != null) return node;
//         return left != null ? left : right;
//     }
// }

/*
 ------------------------------------------------------------
 🔹 Approach 3 (✅ Single-Pass DFS with Pair - Most Optimal)
   ⏱️ Time Complexity: O(n) - single DFS traversal
   💾 Space Complexity: O(h) - recursion stack only, no extra storage
   
   🧠 **Key Insight:**
   Combine depth calculation and LCA finding in single traversal.
   Return both the LCA node and the maximum depth from each subtree.
   
   💡 **Why it works:**
   - Post-order DFS: process children before parent
   - Each recursive call returns a Pair(node, depth)
   - Base case: null returns (null, depth+1) to indicate no node found
   - For each node, get results from left and right subtrees
   - Three cases based on depth comparison:
     1. Left depth == Right depth: Both subtrees equally deep
        → Current node is LCA of all deepest leaves
        → Return (current node, left.depth)
     2. Left depth > Right depth: Deepest leaves only in left subtree
        → LCA must be in left subtree
        → Return left Pair unchanged
     3. Right depth > Left depth: Deepest leaves only in right subtree
        → LCA must be in right subtree
        → Return right Pair unchanged
   
   🎯 **Key Innovation:**
   - Single pass combines depth calculation with LCA finding
   - Pair structure elegantly carries both pieces of information upward
   - No need to store intermediate results or make multiple passes
   - Space optimal: only recursion stack, no auxiliary data structures
 ------------------------------------------------------------
*/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
public class LCADeepestLeaves {
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        return answer(root, 0).node;
    }
    
    // Returns Pair containing LCA node and maximum depth in subtree
    private Pair answer(TreeNode root, int depth) {
        // Base case: null node
        if(root == null) {
            return new Pair(null, depth);
        }
        
        // Recursively get results from left and right subtrees
        Pair left = answer(root.left, depth + 1);
        Pair right = answer(root.right, depth + 1);
        
        // Case 1: Both subtrees have same depth
        // Current node is LCA of all deepest leaves
        if(left.depth == right.depth) {
            return new Pair(root, left.depth);
        } 
        // Case 2: Left subtree is deeper
        // LCA is in left subtree
        else if(left.depth > right.depth) {
            return left;
        } 
        // Case 3: Right subtree is deeper
        // LCA is in right subtree
        else {
            return right;
        }
    }
    
    // Helper class to store both node and depth
    private static class Pair {
        TreeNode node;
        int depth;
        
        Pair(TreeNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: root = [3,5,1,6,2,0,8,null,null,7,4]

Tree structure:
           3 (depth 0)
          / \
         5   1 (depth 1)
        / \ / \
       6  2 0  8 (depth 2)
         / \
        7   4 (depth 3) ← deepest leaves

Processing (post-order):

1. Node 6 (leaf):
   - left = null, depth = 3
   - right = null, depth = 3
   - Both null, equal depth → return (6, 3)

2. Node 7 (leaf):
   - left = null, depth = 4
   - right = null, depth = 4
   - Both null, equal depth → return (7, 4)

3. Node 4 (leaf):
   - left = null, depth = 4
   - right = null, depth = 4
   - Both null, equal depth → return (4, 4)

4. Node 2:
   - left = (7, 4)
   - right = (4, 4)
   - Equal depths (4 == 4) → return (2, 4) ✓
   - Node 2 is LCA of leaves 7 and 4

5. Node 5:
   - left = (6, 3)
   - right = (2, 4)
   - Right deeper (4 > 3) → return (2, 4)
   - LCA is in right subtree (node 2)

6. Node 0 (leaf):
   - Both children null → return (0, 3)

7. Node 8 (leaf):
   - Both children null → return (8, 3)

8. Node 1:
   - left = (0, 3)
   - right = (8, 3)
   - Equal depths (3 == 3) → return (1, 3)
   - But depth 3 < depth 4, so not final answer

9. Node 3 (root):
   - left = (2, 4) from node 5 subtree
   - right = (1, 3) from node 1 subtree
   - Left deeper (4 > 3) → return (2, 4)

Final Result: Node 2 ✅

Explanation: Nodes 7 and 4 are deepest (depth 3), and node 2 is their LCA.

 ------------------------------------------------------------
 🔍 Understanding the Three Cases:

Case 1: Equal Depths (left.depth == right.depth)
Tree:     X
         / \
        A   B
       /     \
      D       E

If A and B subtrees have same max depth, X is the LCA.
Both deepest leaves are equally deep from X.
Return (X, depth).

Case 2: Left Deeper (left.depth > right.depth)
Tree:     X
         / \
        A   B
       / \
      D   E

Left subtree contains all deepest leaves.
LCA must be in left subtree.
Return left Pair unchanged.

Case 3: Right Deeper (right.depth > left.depth)
Tree:     X
         / \
        A   B
           / \
          D   E

Right subtree contains all deepest leaves.
LCA must be in right subtree.
Return right Pair unchanged.

 ------------------------------------------------------------
 🔍 Why Base Case Returns depth (not depth+1):

Base case: if(root == null) return new Pair(null, depth);

Why return depth and not depth+1?
- When we call answer(root.left, depth+1), depth is already incremented
- If root.left is null, we return depth (which is already parent_depth+1)
- This represents: "deepest leaf in null subtree is at parent's level"
- When parent compares, this depth represents the hypothetical leaf depth

Alternative interpretation (clearer):
- Return depth represents: "if this subtree had a leaf, it would be at this depth"
- For null: no actual leaf, but level is depth
- For leaf: actual leaf at this depth
- For internal node: deepest leaf in subtree is at returned depth

 ------------------------------------------------------------
 🔍 Edge Cases:

Case 1: Single node tree
root = [1]
- left = (null, 1), right = (null, 1)
- Equal depths → return (1, 1)
- Root is both deepest leaf and its own LCA ✓

Case 2: Only left child (skewed tree)
root = [1, 2, null, 3]
Tree: 1
     /
    2
   /
  3
- Node 3 is deepest
- Processing upward: 3→2→1
- At each level, only one subtree deeper
- Returns (3, 3) to root
- Result: node 3 (deepest leaf is its own LCA) ✓

Case 3: Balanced tree with all leaves at same depth
root = [1, 2, 3]
Tree:   1
       / \
      2   3
- Both leaves at depth 1
- left = (2, 2), right = (3, 2)
- Equal depths at root → return (1, 2)
- Root is LCA of all deepest leaves ✓

Case 4: One deep branch, one shallow branch
root = [1, 2, 3, 4]
Tree:   1
       / \
      2   3
     /
    4
- Node 4 is deepest (depth 2)
- Node 2 subtree: depth 3
- Node 3 subtree: depth 2
- At root: left.depth (3) > right.depth (2)
- Return left → node 2 (but actually should be 4)
- Wait, let me recalculate...
  - Node 4: (4, 3)
  - Node 2: left=(4,3), right=(null,3) → equal → (2,3)
  - Node 3: (3, 2)
  - Root: left=(2,3), right=(3,2) → left deeper → return (2,3)
  - But node 2 is not a leaf...
  
Actually, this is correct! Node 4 is the deepest leaf, but we return the LCA.
Since node 2 subtree contains the deepest leaf (4) and right subtree doesn't,
the LCA of deepest leaves is... wait, 4 is the only deepest leaf.
The LCA of a single node is itself. So we should return 4.

Let me re-examine the logic:
- Node 2: left child (4) returns (4,3), right child null returns (null,3)
- Equal depths, so return (2, 3)? That's wrong!

Oh! The base case should handle this:
if(root == null) return new Pair(null, depth);

When right child is null with depth 3, and left returns (4,3),
we compare depths 3 == 3, so return (2,3). But 4 is a leaf, not 2!

The issue is: when one child is null and other is not null,
we should return the non-null child's result, not current node.

Actually, wait. Let's trace more carefully:
- answer(node4, 2): left=null→(null,3), right=null→(null,3)
  → equal depths → return (4, 3) ✓
- answer(node2, 1): left=(4,3), right=(null,2)
  → left.depth (3) > right.depth (2) → return (4, 3) ✓

Ah! I see the issue. When I said "right=(null, 3)", I was wrong.
When we call answer(null, 2), it returns (null, 2), not (null, 3)!

Let me retrace:
- answer(node4, 2): base case no, recurse
  - left = answer(null, 3) = (null, 3)
  - right = answer(null, 3) = (null, 3)
  - 3 == 3 → return (4, 3) ✓
- answer(node2, 1): 
  - left = answer(node4, 2) = (4, 3)
  - right = answer(null, 2) = (null, 2)
  - 3 > 2 → return (4, 3) ✓
- answer(node3, 1):
  - left = answer(null, 2) = (null, 2)
  - right = answer(null, 2) = (null, 2)
  - 2 == 2 → return (3, 2) ✓
- answer(root1, 0):
  - left = (4, 3)
  - right = (3, 2)
  - 3 > 2 → return (4, 3) ✓

Perfect! Node 4 is correctly returned. ✓

⚡ Performance Analysis:
The single-pass DFS approach efficiently handles maximum constraints:
- Tree with up to 1000 nodes
- Single DFS traversal: exactly n node visits
- At each node: O(1) operations (depth comparison, Pair creation)
- Total time: O(n) ≈ 1000 operations for max input
- Space complexity breakdown:
  • Recursion stack: O(h) where h is tree height
  • Balanced tree: O(log n) ≈ 10 for 1000 nodes
  • Skewed tree: O(n) = 1000 (worst case)
  • Pair objects: O(1) per recursion level (garbage collected)
- Comparison with multi-pass approaches:
  • Two-pass: 2n visits + O(n) space for storing leaves
  • Single-pass: n visits + O(h) space only
  • Space savings: significant when tree is balanced
- The Pair pattern is elegant and reusable:
  • Clean way to return multiple values without arrays
  • Self-documenting code (node and depth clearly named)
  • Commonly used in tree problems requiring multiple return values
- This problem demonstrates post-order DFS power:
  • Process children before parent
  • Combine information from subtrees at parent
  • Single pass achieves what normally takes multiple passes
 ------------------------------------------------------------
*/
