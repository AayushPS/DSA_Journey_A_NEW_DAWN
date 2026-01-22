package BinaryTree;

/*
 🔹 Problem: 1026. Maximum Difference Between Node and Ancestor
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Tree, Depth-First Search, Binary Tree
 🔹 Link: https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/

 ------------------------------------------------------------
 📝 Problem Statement:

Given the root of a binary tree, find the maximum value v for which there exist 
different nodes a and b where v = |a.val - b.val| and a is an ancestor of b.

A node a is an ancestor of b if either: any child of a is equal to b or any 
child of a is an ancestor of b.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: root = [8,3,10,1,6,null,14,null,null,4,7,13]
Output: 7
Explanation: We have various ancestor-node differences, some of which are 
given below:
|8 - 3| = 5
|3 - 7| = 4
|8 - 1| = 7
|10 - 13| = 3
Among all possible differences, the maximum value of 7 is obtained by |8 - 1| = 7.

Example 2:
Input: root = [1,null,2,null,0,3]
Output: 3

 ------------------------------------------------------------
 ⚠️ Constraints:
 • The number of nodes in the tree is in the range [2, 5000]
 • 0 <= Node.val <= 10^5

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Find maximum absolute difference between ancestor and descendant.

🔑 **Key Insight:**
   - For any node, track min and max values seen on path from root
   - Maximum difference at current node = max(|val - min|, |val - max|)
   - Simplifies to: max(val, max) - min(val, min) (always non-negative)
   - Pass updated min/max down to children

📍 **Approach (DFS with Min/Max Tracking):**
   - DFS traversal passing current path's min and max values
   - At each node, calculate difference with path min and max
   - Update min/max before passing to children
   - Track global maximum difference
   - Time: O(n), Space: O(h) for recursion stack

/*
 ------------------------------------------------------------
 🔹 Approach (✅ DFS with Min/Max Tracking - Optimal)
   ⏱️ Time Complexity: O(n) - visit each node once
   💾 Space Complexity: O(h) - recursion stack (h = tree height)
   
   🧠 **Key Insight:**
   The maximum difference between a node and its ancestor is determined by 
   comparing the node with the minimum and maximum values on the path from 
   root to that node. We don't need to track all ancestors, just min and max.
   
   💡 **Why it works:**
   - Every ancestor of current node is on path from root
   - Maximum difference occurs with either minimum or maximum ancestor value
   - |current - min| or |current - max| captures all possible ancestor diffs
   - Can simplify: max(|val-min|, |max-val|) = max(max-min, val-min, max-val)
   - But actually: max(val,max) - min(val,min) covers all cases
   
   🎯 **Algorithm:**
   1. Start DFS from root with currMin = currMax = root.val
   2. At each node:
      - Calculate diff = max(|val-currMin|, |currMax-val|)
      - Update: newMax = max(val, currMax), newMin = min(val, currMin)
      - Recursively process left and right with updated min/max
      - Return max(diff, maxLeft, maxRight)
   3. Base case: null node returns 0
   
   📐 **Why Absolute Values Simplify:**
   Since we track both min and max on path:
   - If current > max: difference is current - max
   - If current < min: difference is min - current
   - If min ≤ current ≤ max: difference is max(current-min, max-current)
   - All cases covered by: max(|current-min|, |max-current|)
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
public class MaxDiffAncestor {
    public int maxAncestorDiff(TreeNode root) {
        // Initialize with root value as both min and max
        return helper(root.val, root.val, root);
    }
    
    /**
     * DFS helper that tracks min and max values on current path
     * 
     * @param currMax - maximum value seen on path from root to current node
     * @param currMin - minimum value seen on path from root to current node
     * @param root - current node being processed
     * @return maximum ancestor-descendant difference in subtree
     */
    private int helper(int currMax, int currMin, TreeNode root) {
        // Base case: reached null node
        if(root == null) {
            return 0;
        }
        
        int val = root.val;
        
        // Calculate difference between current node and path extremes
        // This gives max diff between current node and any ancestor
        int diff = Math.max(Math.abs(val - currMin), Math.abs(currMax - val));
        
        // Update min and max for children
        int newMax = Math.max(val, currMax);
        int newMin = Math.min(val, currMin);
        
        // Recursively process left and right subtrees with updated bounds
        int leftMax = helper(newMax, newMin, root.left);
        int rightMax = helper(newMax, newMin, root.right);
        
        // Return maximum difference found in this subtree
        return Math.max(diff, Math.max(leftMax, rightMax));
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: root = [8,3,10,1,6,null,14,null,null,4,7,13]

Tree structure:
           8
          / \
         3   10
        / \    \
       1   6    14
          / \   /
         4   7 13

DFS Traversal:

Node 8 (root): currMax=8, currMin=8
- val=8, diff=max(|8-8|, |8-8|)=0
- newMax=8, newMin=8
- Recurse left(3) and right(10)

Node 3: currMax=8, currMin=8
- val=3, diff=max(|3-8|, |8-3|)=5
- newMax=8, newMin=3
- Recurse left(1) and right(6)

Node 1: currMax=8, currMin=3
- val=1, diff=max(|1-3|, |8-1|)=7 ✓
- newMax=8, newMin=1
- Recurse left(null) and right(null)
- Return 7

Node 6: currMax=8, currMin=3
- val=6, diff=max(|6-3|, |8-6|)=3
- newMax=8, newMin=3
- Recurse left(4) and right(7)

Node 4: currMax=8, currMin=3
- val=4, diff=max(|4-3|, |8-4|)=4
- newMax=8, newMin=3
- Return 4

Node 7: currMax=8, currMin=3
- val=7, diff=max(|7-3|, |8-7|)=4
- newMax=8, newMin=3
- Return 4

Back to Node 6: max(3, 4, 4)=4

Back to Node 3: max(5, 7, 4)=7

Node 10: currMax=8, currMin=8
- val=10, diff=max(|10-8|, |10-8|)=2
- newMax=10, newMin=8
- Recurse right(14)

Node 14: currMax=10, currMin=8
- val=14, diff=max(|14-8|, |14-10|)=6
- newMax=14, newMin=8
- Recurse left(13)

Node 13: currMax=14, currMin=8
- val=13, diff=max(|13-8|, |14-13|)=5
- Return 5

Back to Node 14: max(6, 5)=6

Back to Node 10: max(2, 6)=6

Back to Node 8: max(0, 7, 6)=7

Final Result: 7 ✅

The maximum difference is 7, from nodes 8 and 1.

 ------------------------------------------------------------
 🔍 Understanding Why Min/Max Tracking Works:

Question: Why track only min and max, not all ancestors?
Answer: Maximum difference always involves an extreme value!

Proof by cases:
For current node with value V and ancestors with values A₁, A₂, ..., Aₖ

Let min = min(A₁, ..., Aₖ) and max = max(A₁, ..., Aₖ)

Case 1: V > max
- Largest difference: V - min (maximizes distance)
- This is captured by |V - min|

Case 2: V < min
- Largest difference: max - V (maximizes distance)
- This is captured by |max - V|

Case 3: min ≤ V ≤ max
- Largest difference: max(V - min, max - V)
- Both captured by max(|V-min|, |max-V|)

Therefore, only min and max needed! ✓

 ------------------------------------------------------------
 🔍 Alternative Implementation (Cleaner):

Since |a - b| = max(a,b) - min(a,b), we can simplify:

```java
public int maxAncestorDiff(TreeNode root) {
    return helper(root, root.val, root.val);
}

private int helper(TreeNode node, int min, int max) {
    if(node == null) {
        // At leaf, return difference between path extremes
        return max - min;
    }
    
    // Update min and max with current node
    min = Math.min(min, node.val);
    max = Math.max(max, node.val);
    
    // Return max difference from both subtrees
    int leftDiff = helper(node.left, min, max);
    int rightDiff = helper(node.right, min, max);
    
    return Math.max(leftDiff, rightDiff);
}
```

This version:
- Calculates difference at leaf nodes (max - min on path)
- Cleaner logic: no absolute values needed
- Same time/space complexity
- More elegant code

 ------------------------------------------------------------
 🔍 Edge Cases:

Case 1: Two nodes only (root and one child)
root = [1, 2]
- Path: 1 → 2
- Difference: |1-2| = 1
- Result: 1 ✓

Case 2: Linear tree (all left or all right)
root = [5, 3, null, 1]
Tree: 5 → 3 → 1
- At node 1: min=1, max=5
- Difference: 5-1 = 4
- Result: 4 ✓

Case 3: All same values
root = [5, 5, 5, 5, 5]
- All differences = 0
- Result: 0 ✓

Case 4: Two branches with different ranges
root = [10, 1, 20]
Tree:    10
        /  \
       1    20
- Left: |10-1| = 9
- Right: |20-10| = 10
- Result: 10 ✓

Case 5: Deep tree with alternating values
root = [0, 100, null, 0, null, 100]
- Maximum path difference: 100-0 = 100
- Result: 100 ✓

Case 6: Maximum node values (10^5)
root = [100000, 0]
- Difference: 100000-0 = 100000
- Fits in int (max int ≈ 2.1×10^9)
- Result: 100000 ✓

 ------------------------------------------------------------
 🔍 Common Mistakes:

❌ Mistake 1: Comparing only with immediate parent
Wrong: Track only parent value
Correct: Track min/max of ALL ancestors on path
Reason: Any ancestor can create maximum difference

❌ Mistake 2: Not updating min/max before recursing
Wrong: Recurse with old min/max, then update
Correct: Update min/max, then recurse with new values
Reason: Children need to know about current node

❌ Mistake 3: Forgetting base case
Wrong: No check for null
Correct: Return 0 (or max-min) at null
Reason: Prevents null pointer exception

❌ Mistake 4: Only checking one direction
Wrong: Only check |val - min| or only |val - max|
Correct: Check both, take maximum
Reason: Don't know which extreme gives larger difference

❌ Mistake 5: Using global variable incorrectly
Wrong: Single global max without proper updates
Correct: Return max from recursion or use global with care
Reason: Need to compare across different subtrees

 ------------------------------------------------------------
 🔍 Complexity Analysis:

Time Complexity: O(n)
- Visit each node exactly once
- At each node: O(1) operations
- Total: O(n) where n = number of nodes

Space Complexity: O(h)
- Recursion stack depth = tree height
- Best case (balanced): O(log n)
- Worst case (skewed): O(n)
- Average case: O(log n) for random tree

Why O(n) is optimal:
- Must visit each node to find all ancestor-descendant pairs
- Cannot skip any node (any could be in optimal pair)
- Linear time is best possible

 ------------------------------------------------------------
 🔍 Why This Problem is Important:

Key concepts demonstrated:
1. DFS with state propagation (min/max tracking)
2. Path-based tree problems
3. Optimization through mathematical insight
4. Reducing problem constraints (only track extremes)

Similar problems using same pattern:
- 124. Binary Tree Maximum Path Sum
- 543. Diameter of Binary Tree
- 687. Longest Univalue Path
- 1120. Maximum Average Subtree

Real-world applications:
- Financial analysis (tracking price ranges)
- Network routing (latency bounds)
- Genealogy (generational differences)
- Version control (change magnitude tracking)

⚡ Performance Analysis:
This DFS approach efficiently handles maximum constraints:
- Tree with up to 5000 nodes
- Single DFS traversal: exactly n visits
- Per-node operations: 4-5 comparisons
- Total: ~20,000-25,000 operations for max input
- Execution time: <1ms
- Space: recursion stack up to 5000 levels (worst case)
- Why this is optimal:
  • Cannot avoid visiting all nodes
  • Each node processed in O(1)
  • Only stores O(h) state on stack
  • No auxiliary data structures needed
- The min/max tracking insight:
  • Reduces O(k) ancestor comparisons to O(1)
  • Where k = depth of current node
  • Saves k×n operations across entire tree
  • Critical optimization for deep trees
- Alternative approaches are worse:
  • Store all ancestors: O(h) space per node = O(n×h) total
  • Compare all pairs: O(n²) time
  • This solution: O(n) time, O(h) space ✓
- This demonstrates:
  • Power of mathematical reduction (all ancestors → just min/max)
  • Efficient state propagation in tree DFS
  • When single-pass algorithms are sufficient
  • Clean recursive problem decomposition
 ------------------------------------------------------------
*/