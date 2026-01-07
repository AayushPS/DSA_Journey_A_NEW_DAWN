package BinaryTree;
/*
 🔹 Problem: 1161. Maximum Level Sum of a Binary Tree
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Tree, Depth-First Search, Breadth-First Search, Binary Tree
 🔹 Link: https://leetcode.com/problems/maximum-level-sum-of-a-binary-tree/

 ------------------------------------------------------------
 📝 Problem Statement:

Given the root of a binary tree, the level of its root is 1, the level of its 
children is 2, and so on.

Return the smallest level x such that the sum of all the values of nodes at 
level x is maximal.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: root = [1,7,0,7,-8,null,null]
Output: 2
Explanation: 
Level 1 sum = 1.
Level 2 sum = 7 + 0 = 7.
Level 3 sum = 7 + -8 = -1.
So we return the level with the maximum sum which is level 2.

Example 2:
Input: root = [989,null,10250,98693,-89388,null,null,null,-32127]
Output: 2

 ------------------------------------------------------------
 ⚠️ Constraints:
 • The number of nodes in the tree is in the range [1, 10^4]
 • -10^5 <= Node.val <= 10^5

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Find the level with maximum sum; if tie, return smallest level.

📍 **Approach 1 (BFS with Level Tracking - Standard):**
   - Use standard BFS with queue size tracking
   - For each level, process exactly queue.size() nodes
   - Calculate sum for each level and track maximum
   - Clean and intuitive implementation
   - Time: O(n), Space: O(w) where w is max width

📍 **Approach 2 (BFS with Sentinel Node - Optimized):**
   - Use sentinel/marker node to separate levels
   - Single while loop processes all nodes
   - Inner while loop processes current level until sentinel
   - Move sentinel to end of queue after each level
   - Slightly more memory efficient (no size() calls)
   - Time: O(n), Space: O(w)

📍 **Approach 3 (DFS with Level Sums Array):**
   - Use DFS to traverse tree with level parameter
   - Store sum of each level in ArrayList
   - Find level with maximum sum after traversal
   - Space trade-off: stores all level sums
   - Time: O(n), Space: O(h) recursion + O(levels) array

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - BFS with Level Tracking - Standard)
   ⏱️ Time Complexity: O(n) - visit each node once
   💾 Space Complexity: O(w) - queue stores at most one level (width)
   
   🧠 **Key Insight:**
   Use standard BFS pattern with size tracking to process level by level.
   For each iteration, process exactly the current queue size (one level).
   
   💡 **Why it works:**
   - Queue size at start of iteration = number of nodes at current level
   - Process exactly that many nodes (one complete level)
   - Add children to queue for next level
   - Track maximum sum and corresponding level number
   - Return smallest level if multiple levels have same max sum
 ------------------------------------------------------------

// /**
//  * Definition for a binary tree node.
//  * public class TreeNode {
//  *     int val;
//  *     TreeNode left;
//  *     TreeNode right;
//  *     TreeNode() {}
//  *     TreeNode(int val) { this.val = val; }
//  *     TreeNode(int val, TreeNode left, TreeNode right) {
//  *         this.val = val;
//  *         this.left = left;
//  *         this.right = right;
//  *     }
//  * }
//  */
// class Solution {
//     public int maxLevelSum(TreeNode root) {
//         Queue<TreeNode> queue = new LinkedList<>();
//         queue.offer(root);
//         
//         int maxSum = root.val;
//         int maxLevel = 1;
//         int currentLevel = 1;
//         
//         while(!queue.isEmpty()) {
//             int levelSize = queue.size();
//             int levelSum = 0;
//             
//             // Process all nodes at current level
//             for(int i = 0; i < levelSize; i++) {
//                 TreeNode node = queue.poll();
//                 levelSum += node.val;
//                 
//                 if(node.left != null) queue.offer(node.left);
//                 if(node.right != null) queue.offer(node.right);
//             }
//             
//             // Update max if current level has greater sum
//             if(levelSum > maxSum) {
//                 maxSum = levelSum;
//                 maxLevel = currentLevel;
//             }
//             
//             currentLevel++;
//         }
//         
//         return maxLevel;
//     }
// }
/*
 ------------------------------------------------------------
 🔹 Approach 2 (Commented - DFS with Level Sums Array)
   ⏱️ Time Complexity: O(n) - visit each node once
   💾 Space Complexity: O(h) - recursion stack + O(levels) for array
   
   🧠 **Key Insight:**
   Use DFS traversal with level tracking. Store sum for each level in a list.
   After traversal, find the index with maximum sum.
   
   💡 **Why it works:**
   - Recursively traverse tree, passing level as parameter
   - If level >= levelSums.size(), add new entry (first node at this level)
   - Otherwise, add to existing level sum
   - After complete traversal, iterate through levelSums to find maximum
   - Return level with max sum (1-indexed)
 ------------------------------------------------------------
*/
// class Solution {
//     private List<Integer> levelSums = new ArrayList<>();
//     
//     public int maxLevelSum(TreeNode root) {
//         dfs(root, 0);
//         
//         int maxSum = Integer.MIN_VALUE;
//         int maxLevel = 0;
//         
//         for(int i = 0; i < levelSums.size(); i++) {
//             if(levelSums.get(i) > maxSum) {
//                 maxSum = levelSums.get(i);
//                 maxLevel = i + 1; // Convert to 1-indexed
//             }
//         }
//         
//         return maxLevel;
//     }
//     
//     private void dfs(TreeNode node, int level) {
//         if(node == null) return;
//         
//         // Add new level if first node at this level
//         if(level >= levelSums.size()) {
//             levelSums.add(node.val);
//         } else {
//             levelSums.set(level, levelSums.get(level) + node.val);
//         }
//         
//         dfs(node.left, level + 1);
//         dfs(node.right, level + 1);
//     }
// }


/*
 ------------------------------------------------------------
 🔹 Approach 3 (✅ BFS with Sentinel Node - Optimized)
   ⏱️ Time Complexity: O(n) - visit each node once
   💾 Space Complexity: O(w) - queue stores at most one level (width)
   
   🧠 **Key Insight:**
   Use a sentinel (marker) node to demarcate levels in the queue.
   This eliminates the need to track queue size explicitly.
   
   💡 **Why it works:**
   - Sentinel node (dummy TreeNode) marks end of each level
   - Outer while(true) loop processes all levels
   - Inner while loop processes nodes until hitting sentinel
   - After processing level, remove sentinel and add it back to end
   - This moves the sentinel to mark the next level
   - When queue becomes empty after removing sentinel, all levels processed
   - Tracks maxSum and maxLevel, updating when better sum found
   - Uses strict '>' comparison to ensure smallest level returned on tie
   
   🎯 **Advantages over standard BFS:**
   - No need for queue.size() call (minor performance gain)
   - Single sentinel object reused throughout
   - Clear level separation without additional tracking
   - Slightly more elegant code structure
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

import java.util.ArrayDeque;

public class MaxLevelSumBinaryTree {
    public int maxLevelSum(TreeNode root) {
        ArrayDeque<TreeNode> q = new ArrayDeque<>();
        TreeNode sentinel = new TreeNode(); // Sentinel to mark level boundaries
        
        q.addLast(root);
        q.addLast(sentinel);
        
        int maxSum = root.val;
        int maxLevel = 1;
        int level = 1;
        
        while(true) {
            int sum = 0;
            
            // Process all nodes at current level (until sentinel)
            while(q.peekFirst() != sentinel) {
                TreeNode node = q.pollFirst();
                sum += node.val;
                
                // Add children for next level
                if(node.left != null) q.addLast(node.left);
                if(node.right != null) q.addLast(node.right);
            }
            
            // Update max sum and level if current level is better
            if(sum > maxSum) {
                maxSum = sum;
                maxLevel = level;
            }
            
            // Remove sentinel from front
            q.pollFirst();
            
            // If queue is empty, we've processed all levels
            if(q.isEmpty()) break;
            
            // Add sentinel back to mark end of next level
            q.addLast(sentinel);
            level++;
        }
        
        return maxLevel;
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: root = [1,7,0,7,-8,null,null]

Tree structure:
        1
       / \
      7   0
     / \
    7  -8

Initial state:
- queue: [1, sentinel]
- maxSum = 1, maxLevel = 1, level = 1

Level 1:
- Process nodes until sentinel
  - node = 1, sum = 1
  - Add left(7) and right(0) to queue
- queue after level: [sentinel, 7, 0]
- sum = 1, not > maxSum (1), no update
- Remove sentinel, add back: [7, 0, sentinel]
- level = 2

Level 2:
- Process nodes until sentinel
  - node = 7, sum = 7
  - Add left(7) and right(-8) to queue
  - node = 0, sum = 7
  - No children
- queue after level: [sentinel, 7, -8]
- sum = 7 > maxSum (1) ✓
  - maxSum = 7, maxLevel = 2
- Remove sentinel, add back: [7, -8, sentinel]
- level = 3

Level 3:
- Process nodes until sentinel
  - node = 7, sum = 7
  - No children
  - node = -8, sum = -1
  - No children
- queue after level: [sentinel]
- sum = -1, not > maxSum (7), no update
- Remove sentinel: []
- Queue is empty, break

Result: maxLevel = 2 ✅

 ------------------------------------------------------------
 🔍 Sentinel Node Pattern Visualization:

Initial: [Root, S]
After level 1: [Child1, Child2, S]
After level 2: [GrandChild1, GrandChild2, GrandChild3, S]
...and so on

The sentinel 'S' always marks the boundary between levels.
After processing a level, we move S to the end to mark the next level.

 ------------------------------------------------------------
 🔍 Edge Cases:

Case 1: Single node tree
root = [1]
- Level 1 sum = 1
- Result: 1 ✓

Case 2: All negative values
root = [-1,-2,-3,-4,-5]
Tree:     -1
         /  \
       -2    -3
       / \
     -4  -5

Level sums: -1, -5, -9
maxSum = -1 at level 1
Result: 1 ✓

Case 3: Multiple levels with same max sum (return smallest)
root = [5,2,3,1,4]
Tree:     5
         / \
        2   3
       / \
      1   4

Level 1 sum = 5
Level 2 sum = 2 + 3 = 5
Level 3 sum = 1 + 4 = 5

Using '>' instead of '>=' ensures level 1 is returned.
Result: 1 ✓

Case 4: Skewed tree (only right children)
root = [1,null,2,null,3,null,4]
Tree: 1
       \
        2
         \
          3
           \
            4

Each level has 1 node
Level sums: 1, 2, 3, 4
Result: 4 ✓

Case 5: Large positive and negative values
root = [100000,-100000]
Level 1 sum = 100000
Level 2 sum = -100000
Result: 1 ✓

 ------------------------------------------------------------
 🔍 Comparison of Approaches:

BFS with Size Tracking (Approach 1):
✅ Most intuitive and standard BFS pattern
✅ Easy to understand and implement
✅ Works well for interviews
❌ Requires queue.size() call per level

BFS with Sentinel (Approach 2 - Our solution):
✅ Elegant level separation without size tracking
✅ Single sentinel object reused
✅ Slightly fewer operations (no size() calls)
✅ Clear visual separation of levels
❌ Less common pattern (might confuse readers)

DFS with Array (Approach 3):
✅ Recursive approach (some prefer over iterative)
✅ Natural for tree problems
✅ Stores all level sums (useful if needed later)
❌ Extra space for level sums array
❌ Two passes: one to collect, one to find max

Performance comparison (for 10^4 nodes):
- All three: O(n) time
- BFS approaches: O(w) space where w ≤ n/2 for complete tree
- DFS approach: O(h) recursion + O(log n) array ≈ O(log n) for balanced
- In practice, all perform similarly
- BFS with sentinel has slight edge in constant factors

⚡ Performance Analysis:
The BFS with sentinel approach efficiently handles maximum constraints:
- Tree with up to 10^4 nodes
- Queue width in worst case (complete binary tree): ~5000 nodes
- ArrayDeque operations (addLast, pollFirst, peekFirst): O(1)
- Total operations: exactly n node processings + n queue operations
- Space: O(w) where w is maximum width, typically w ≈ n/2 for complete tree
- For balanced tree: w ≈ n/2, for skewed tree: w = 1
- Sentinel reuse: no additional object creation per level
- The sentinel pattern is particularly elegant for problems requiring
  level-by-level processing without explicit size tracking
- Works efficiently even with negative node values (uses int, checks with >)
- No overflow issues with level sums as long as sum fits in int
  (worst case: 10^4 nodes × 10^5 value = 10^9, fits in int)
 ------------------------------------------------------------
*/