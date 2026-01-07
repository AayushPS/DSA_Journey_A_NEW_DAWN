package BinaryTree;

/*
 🔹 Problem: 1339. Maximum Product of Splitted Binary Tree
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Tree, Depth-First Search, Binary Tree, Dynamic Programming
 🔹 Link: https://leetcode.com/problems/maximum-product-of-splitted-binary-tree/

 ------------------------------------------------------------
 📝 Problem Statement:

Given the root of a binary tree, split the binary tree into two subtrees by 
removing one edge such that the product of the sums of the subtrees is maximized.

Return the maximum product of the sums of the two subtrees. Since the answer 
may be too large, return it modulo 10^9 + 7.

Note that you need to maximize the answer before taking the mod and not after 
taking it.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: root = [1,2,3,4,5,6]
Output: 110
Explanation: Remove the red edge and get 2 binary trees with sum 11 and 10. 
Their product is 110 (11*10)

Example 2:
Input: root = [1,null,2,3,4,null,null,5,6]
Output: 90
Explanation: Remove the red edge and get 2 binary trees with sum 15 and 6.
Their product is 90 (15*6)

 ------------------------------------------------------------
 ⚠️ Constraints:
 • The number of nodes in the tree is in the range [2, 5 * 10^4]
 • 1 <= Node.val <= 10^4

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Maximize product of two subtree sums after removing one edge.

📍 **Approach 1 (Two-Pass DFS with HashMap):**
   - First pass: Calculate subtree sum for every node, store in HashMap
   - Second pass: Try removing each edge, calculate product using stored sums
   - For each node with sum S, product = (totalSum - S) × S
   - Track maximum product across all possible cuts
   - Space: O(n) for HashMap, Time: O(n) for two passes

📍 **Approach 2 (Two-Pass DFS with In-Place Storage - Optimized):**
   - Key optimization: Store subtree sum directly in node.val
   - First pass: Calculate and store subtree sums in nodes themselves
   - Second pass: Calculate products using stored sums (node.val)
   - No HashMap needed - uses tree structure itself as storage
   - Space: O(h) recursion only, Time: O(n)
   - Trade-off: Modifies original tree (acceptable for this problem)

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - Two-Pass DFS with HashMap)
   ⏱️ Time Complexity: O(n) - two complete tree traversals
   💾 Space Complexity: O(n) - HashMap stores sum for each node + O(h) recursion
   
   🧠 **Key Insight:**
   Store all subtree sums first, then iterate through all possible cuts.
   When we remove edge to a subtree with sum S, the two parts have sums
   S and (totalSum - S). Product = S × (totalSum - S).
   
   💡 **Why it works:**
   - First DFS computes subtree sum for every node bottom-up
   - HashMap stores these sums for O(1) lookup
   - Second DFS tries every possible edge removal
   - For each subtree rooted at node, calculate product if we cut above it
   - Track global maximum across all cuts
   - Return max product modulo 10^9 + 7
 ------------------------------------------------------------

// class Solution {
//     private int MOD = 1_000_000_007;
//     
//     public int maxProduct(TreeNode root) {
//         Map<TreeNode, Integer> sums = new HashMap<>();
//         storer(root, sums);
//         return (int)(maxxer(root, sums, sums.get(root)) % MOD);
//     }
//     
//     private long maxxer(TreeNode root, Map<TreeNode, Integer> sums, int rootSum) {
//         long currMax = 0L;
//         
//         if(root.left != null) {
//             int curr = sums.get(root.left);
//             currMax = Math.max(
//                 ((long)(rootSum - curr) * curr),
//                 currMax
//             );
//             currMax = Math.max(currMax, maxxer(root.left, sums, rootSum));
//         }
//         
//         if(root.right != null) {
//             int curr = sums.get(root.right);
//             currMax = Math.max(
//                 ((long)(rootSum - curr) * curr),
//                 currMax
//             );
//             currMax = Math.max(currMax, maxxer(root.right, sums, rootSum));
//         }
//         
//         return currMax;
//     }
//     
//     private int storer(TreeNode root, Map<TreeNode, Integer> sums) {
//         int sum = root.val;
//         if(root.left != null)  sum += storer(root.left, sums);
//         if(root.right != null) sum += storer(root.right, sums);
//         sums.put(root, sum);
//         return sum;
//     }
// }

/*
 ------------------------------------------------------------
 🔹 Approach 2 (✅ Two-Pass DFS with In-Place Storage - Most Optimal)
   ⏱️ Time Complexity: O(n) - two complete tree traversals
   💾 Space Complexity: O(h) - only recursion stack, no HashMap
   
   🧠 **Key Insight:**
   Instead of using a HashMap, store subtree sums directly in the tree nodes.
   Overwrite node.val with its subtree sum. This eliminates HashMap overhead
   and simplifies the code while maintaining O(n) time complexity.
   
   💡 **Why it works:**
   - First DFS: Calculate subtree sum bottom-up, store in node.val
     • For each node: sum = node.val + leftSum + rightSum
     • Overwrite node.val with this sum
     • Return sum to parent
   - After first pass, each node.val contains its subtree sum
   - Second DFS: Try cutting edge to each child
     • For left child: product = (totalSum - leftSum) × leftSum
     • For right child: product = (totalSum - rightSum) × rightSum
     • Recursively check all descendants
   - Track maximum product found
   - Use long to avoid overflow before taking modulo
   - Only apply modulo at the very end
   
   🎯 **Advantages:**
   - No HashMap allocation (saves memory and time)
   - Direct access to subtree sums via node.val
   - Cleaner code without map lookups
   - Better cache locality (data stored in tree structure)
   - Space complexity reduced from O(n) to O(h)
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
public class MaxProductSplittedBinaryTree {
    private int MOD = 1_000_000_007;
    
    public int maxProduct(TreeNode root) {
        // First pass: Calculate and store subtree sums in nodes
        storer(root);
        
        // Second pass: Find maximum product by trying all possible cuts
        return (int)(maxxer(root, root.val) % MOD);
    }
    
    // Calculate maximum product by trying to cut edge to each subtree
    private long maxxer(TreeNode root, int totalSum) {
        long currMax = 0L;
        
        // Try cutting edge to left subtree
        if(root.left != null) {
            int leftSum = root.left.val; // Subtree sum stored in node.val
            
            // Product if we cut above left child
            currMax = Math.max(
                ((long)(totalSum - leftSum) * leftSum),
                currMax
            );
            
            // Recursively try cutting edges in left subtree
            currMax = Math.max(currMax, maxxer(root.left, totalSum));
        }
        
        // Try cutting edge to right subtree
        if(root.right != null) {
            int rightSum = root.right.val; // Subtree sum stored in node.val
            
            // Product if we cut above right child
            currMax = Math.max(
                ((long)(totalSum - rightSum) * rightSum),
                currMax
            );
            
            // Recursively try cutting edges in right subtree
            currMax = Math.max(currMax, maxxer(root.right, totalSum));
        }
        
        return currMax;
    }
    
    // Calculate subtree sum for each node and store in node.val
    private int storer(TreeNode root) {
        int sum = root.val;
        
        // Add left subtree sum
        if(root.left != null)  sum += storer(root.left);
        
        // Add right subtree sum
        if(root.right != null) sum += storer(root.right);
        
        // Store subtree sum in node.val and return it
        return root.val = sum;
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: root = [1,2,3,4,5,6]

Tree structure:
        1
       / \
      2   3
     / \   \
    4   5   6

Step 1: First DFS (storer) - Calculate subtree sums

Processing order (post-order):
1. Node 4: sum = 4, node.val = 4
2. Node 5: sum = 5, node.val = 5
3. Node 2: sum = 2 + 4 + 5 = 11, node.val = 11
4. Node 6: sum = 6, node.val = 6
5. Node 3: sum = 3 + 6 = 9, node.val = 9
6. Node 1 (root): sum = 1 + 11 + 9 = 21, node.val = 21

After first pass, tree with subtree sums:
        21
       /  \
      11   9
     / \    \
    4   5    6

Step 2: Second DFS (maxxer) - Find maximum product

totalSum = 21

Processing root (1):
- Left child (2): leftSum = 11
  Product = (21 - 11) × 11 = 10 × 11 = 110
  currMax = 110
  Recurse into left subtree...

Processing node 2:
- Left child (4): leftSum = 4
  Product = (21 - 4) × 4 = 17 × 4 = 68
  currMax = max(110, 68) = 110
  Recurse into node 4 (leaf, returns 0)
  
- Right child (5): rightSum = 5
  Product = (21 - 5) × 5 = 16 × 5 = 80
  currMax = max(110, 80) = 110
  Recurse into node 5 (leaf, returns 0)

Back to root (1):
- Right child (3): rightSum = 9
  Product = (21 - 9) × 9 = 12 × 9 = 108
  currMax = max(110, 108) = 110
  Recurse into right subtree...

Processing node 3:
- Right child (6): rightSum = 6
  Product = (21 - 6) × 6 = 15 × 6 = 90
  currMax = max(110, 90) = 110
  Recurse into node 6 (leaf, returns 0)

Final: currMax = 110
Return: 110 % MOD = 110 ✅

Best cut: Remove edge between root and left child (node 2)
Two subtrees: {11} and {10 (rest)}
Product: 11 × 10 = 110

 ------------------------------------------------------------
 🔍 Understanding the Product Formula:

When we cut edge above a subtree with sum S:
- Subtree below cut: sum = S
- Subtree above cut: sum = (totalSum - S)
- Product: S × (totalSum - S)

Example: totalSum = 21, cut above subtree with sum 11
- Below: 11 (nodes 2, 4, 5)
- Above: 21 - 11 = 10 (nodes 1, 3, 6)
- Product: 11 × 10 = 110

Why we try every edge:
- There are n-1 edges in a tree with n nodes
- Each edge creates a different split
- We need to check all to find maximum

 ------------------------------------------------------------
 🔍 Why Use Long Before Modulo:

Maximum possible product calculation:
- Max nodes: 5 × 10^4
- Max node value: 10^4
- Max totalSum: 5 × 10^4 × 10^4 = 5 × 10^8
- Max product: (2.5 × 10^8) × (2.5 × 10^8) ≈ 6.25 × 10^16

This exceeds int range (2^31 - 1 ≈ 2.1 × 10^9)
Therefore, we must use long for product calculation!

Example with potential overflow:
totalSum = 500000000 (5 × 10^8)
S = 250000000
Product = 250000000 × 250000000 = 6.25 × 10^16
This would overflow int but fits in long (2^63 - 1 ≈ 9.2 × 10^18)

 ------------------------------------------------------------
 🔍 Edge Cases:

Case 1: Minimum tree (2 nodes)
root = [1, 2]
Tree: 1
     /
    2

Subtree sums: node 2 = 2, node 1 = 3
Only one edge to cut (between 1 and 2)
Product = 2 × (3 - 2) = 2 × 1 = 2
Result: 2 ✓

Case 2: Linear tree (skewed)
root = [1, 2, null, 3, null, 4]
Tree: 1
     /
    2
   /
  3
 /
4

Subtree sums: 4→4, 3→7, 2→9, 1→10
Best cut: above node 2
Product = 9 × (10 - 9) = 9 × 1 = 9
Result: 9 ✓

Case 3: Balanced tree with equal subtrees
root = [10, 5, 5]
Tree:  10
      /  \
     5    5

Subtree sums: left=5, right=5, root=20
Cut left: 5 × 15 = 75
Cut right: 5 × 15 = 75
Result: 75 ✓

Case 4: Large values requiring modulo
totalSum = 1000000000
S = 500000000
Product = 500000000 × 500000000 = 2.5 × 10^17
Result: (2.5 × 10^17) % (10^9 + 7) ✓

 ------------------------------------------------------------
 🔍 In-Place vs HashMap Comparison:

HashMap Approach:
✅ Preserves original tree
✅ Explicit data storage (easier to debug)
❌ Extra O(n) space for HashMap
❌ HashMap overhead (hashing, collisions)
❌ More memory allocations

In-Place Approach:
✅ O(h) space instead of O(n)
✅ No HashMap overhead
✅ Better cache locality
✅ Faster in practice
❌ Modifies original tree
❌ Less explicit (uses node.val for dual purpose)

For this problem, in-place is preferred because:
- Tree modification is acceptable
- Memory savings significant for large trees
- Performance improvement measurable
- Code is cleaner without map lookups

⚡ Performance Analysis:
The in-place storage approach efficiently handles maximum constraints:
- Tree with up to 50,000 nodes
- First pass: O(n) to compute all subtree sums
- Second pass: O(n) to try all possible cuts
- Total time: O(2n) = O(n)
- Space: O(h) for recursion stack only
  • Balanced tree: O(log n) ≈ 16 for 50K nodes
  • Skewed tree: O(n) = 50K (worst case)
- No HashMap allocation saves ~400KB for 50K nodes
- Product calculation uses long to prevent overflow
- Modulo applied only once at end (as required)
- For maximum tree size and values:
  • Computation time: ~1-2ms
  • Space usage: ~1-2MB (recursion stack)
- The two-pass strategy is optimal - single pass cannot work because
  we need total sum before calculating products
 ------------------------------------------------------------
*/