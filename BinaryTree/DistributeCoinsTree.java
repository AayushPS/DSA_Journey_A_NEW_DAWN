package BinaryTree;

/*
 🔹 Problem: 979. Distribute Coins in Binary Tree
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Tree, Depth-First Search, Binary Tree
 🔹 Link: https://leetcode.com/problems/distribute-coins-in-binary-tree/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given the root of a binary tree with n nodes where each node in the 
tree has node.val coins. There are n coins in total throughout the whole tree.

In one move, we may choose two adjacent nodes and move one coin from one node 
to another. A move may be from parent to child, or from child to parent.

Return the minimum number of moves required to make every node have exactly 
one coin.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: root = [3,0,0]
Output: 2
Explanation: From the root of the tree, we move one coin to its left child, 
and one coin to its right child.

Example 2:
Input: root = [0,3,0]
Output: 3
Explanation: From the left child of the root, we move two coins to the root 
[taking two moves]. Then, we move one coin from the root of the tree to the 
right child.

 ------------------------------------------------------------
 ⚠️ Constraints:
 • The number of nodes in the tree is n
 • 1 <= n <= 100
 • 0 <= Node.val <= n
 • The sum of all Node.val is n

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Minimize moves to distribute coins so each node has exactly 1.

🔑 **Key Insights:**
   - Total coins = total nodes (guaranteed by constraint)
   - Each subtree can compute excess/deficit of coins
   - Excess/deficit must pass through parent edge
   - Number of moves through edge = |excess or deficit|
   - Post-order DFS to aggregate from bottom-up

📍 **Approach (Post-Order DFS with Balance Tracking):**
   - For each subtree, calculate: coins_in_subtree - nodes_in_subtree
   - This gives excess (positive) or deficit (negative)
   - Moves through parent edge = |excess/deficit|
   - Sum all edge moves for total
   - Time: O(n), Space: O(h)

 ------------------------------------------------------------
 🔹 Understanding the Problem:

Key Observations:
1. Total coins = n, total nodes = n → perfect balance overall
2. Each node needs exactly 1 coin
3. Can only move coins between adjacent nodes (parent-child)
4. Optimal strategy: move coins to where they're needed

Subtree Balance Concept:
- Balance = (coins in subtree) - (nodes in subtree)
- Balance > 0: subtree has excess, must export
- Balance < 0: subtree has deficit, must import
- Balance = 0: subtree is self-sufficient
- |Balance| = number of coins crossing parent edge

/*
 ------------------------------------------------------------
 🔹 Approach (✅ Post-Order DFS with Balance Tracking - Optimal)
   ⏱️ Time Complexity: O(n) - visit each node once
   💾 Space Complexity: O(h) - recursion stack depth
   
   🧠 **Key Insight:**
   Think in terms of subtree balance (excess/deficit). The number of coins that 
   must flow through an edge equals the absolute value of the subtree's balance.
   
   💡 **Why it works:**
   - Post-order DFS: process children before parent
   - Each subtree returns its balance to parent
   - Parent must handle its children's imbalances through parent-child edges
   - Balance formula: subtree_coins - subtree_nodes
     • subtree_coins = root.val + left_balance + right_balance + 2 (the 2 are for the nodes themselves)
     • Actually: subtree_coins - subtree_nodes = root.val + left_balance + right_balance - 1
   - Moves at node = |left_balance| + |right_balance|
   - Return balance to parent for further processing
   
   🎯 **Algorithm:**
   1. DFS post-order traversal
   2. Base case: null returns 0 (no excess, no deficit)
   3. At each node:
      - Get left subtree balance
      - Get right subtree balance
      - Add to total moves: |left_balance| + |right_balance|
      - Calculate this subtree's balance: node.val + left + right - 1
      - Return balance to parent
   4. Total moves accumulated in global variable
   
   📐 **Balance Calculation Explained:**
   For a subtree rooted at node:
   - Coins available: node.val + left_balance + right_balance
     (node's coins + what children can provide)
   - Coins needed: 1 (for this node)
   - Net balance: available - needed = node.val + left + right - 1
   - Positive: has excess to give parent
   - Negative: needs coins from parent
   - Zero: perfectly balanced, no exchange with parent
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
public class DistributeCoinsTree {
    private int totalMoves = 0;
    
    public int distributeCoins(TreeNode root) {
        calculateBalance(root);
        return totalMoves;
    }
    
    /**
     * Calculate balance (excess/deficit) of coins in subtree
     * 
     * Returns: coins_in_subtree - nodes_in_subtree
     * - Positive: subtree has excess coins
     * - Negative: subtree needs coins
     * - Zero: subtree is balanced
     * 
     * Side effect: adds |balance| of each edge to totalMoves
     */
    private int calculateBalance(TreeNode root) {
        // Base case: null node has no coins, no nodes → balance = 0
        if(root == null) {
            return 0;
        }
        
        // Get balance from left subtree
        int leftBalance = calculateBalance(root.left);
        
        // Get balance from right subtree
        int rightBalance = calculateBalance(root.right);
        
        // Count moves through edges to children
        // Each coin crossing an edge counts as one move
        totalMoves += Math.abs(leftBalance) + Math.abs(rightBalance);
        
        // Calculate balance of current subtree
        // = coins_available - coins_needed
        // = (node.val + leftBalance + rightBalance) - 1
        // Positive: excess to give to parent
        // Negative: deficit, needs from parent
        return root.val + leftBalance + rightBalance - 1;
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: root = [0,3,0]

Tree structure:
      0
     / \
    3   0

Post-order DFS traversal:

Step 1: Process left child (node with val=3)
- root.val = 3
- left = null → leftBalance = 0
- right = null → rightBalance = 0
- totalMoves += |0| + |0| = 0
- Return: 3 + 0 + 0 - 1 = 2 (has 2 excess coins)

Step 2: Process right child (node with val=0)
- root.val = 0
- left = null → leftBalance = 0
- right = null → rightBalance = 0
- totalMoves += |0| + |0| = 0 (still 0)
- Return: 0 + 0 + 0 - 1 = -1 (needs 1 coin)

Step 3: Process root (node with val=0)
- root.val = 0
- leftBalance = 2 (from step 1)
- rightBalance = -1 (from step 2)
- totalMoves += |2| + |-1| = 0 + 2 + 1 = 3
- Return: 0 + 2 + (-1) - 1 = 0 (balanced)

Final Result: totalMoves = 3 ✅

Moves breakdown:
1. Move 1 coin from left child (3) to root → left child has 2
2. Move 1 coin from left child (2) to root → left child has 1, root has 1
3. Move 1 coin from root to right child → all nodes have 1

 ------------------------------------------------------------
 🔍 Understanding Balance Return Values:

Example configurations and their balances:

Case 1: Node with 3 coins, no children
- Coins: 3
- Nodes: 1
- Balance: 3 - 1 = 2 (excess of 2)
- Must give 2 coins to parent

Case 2: Node with 0 coins, no children
- Coins: 0
- Nodes: 1
- Balance: 0 - 1 = -1 (deficit of 1)
- Must receive 1 coin from parent

Case 3: Node with 1 coin, no children
- Coins: 1
- Nodes: 1
- Balance: 1 - 1 = 0 (perfectly balanced)
- No exchange with parent needed

Case 4: Subtree with total 5 coins, 3 nodes
- Coins: 5
- Nodes: 3
- Balance: 5 - 3 = 2 (excess of 2)
- Can provide 2 coins to parent

Case 5: Subtree with total 2 coins, 4 nodes
- Coins: 2
- Nodes: 4
- Balance: 2 - 4 = -2 (deficit of 2)
- Needs 2 coins from parent

 ------------------------------------------------------------
 🔍 Why |leftBalance| + |rightBalance| Counts Moves:

Question: Why does this formula work?
Answer: Each coin that crosses an edge is counted exactly once!

Explanation:
- leftBalance = excess/deficit in left subtree
- |leftBalance| = number of coins crossing left edge
- Similarly for rightBalance
- Each coin crossing parent-child edge counts as 1 move

Visual example:
      Parent
      /    \
   [+2]    [-1]
  (has 2   (needs 1
   excess)  coin)

Moves:
- 2 coins cross left edge (from child to parent)
- 1 coin crosses right edge (from parent to child)
- Total: |2| + |-1| = 3 moves

The parent receives 2, gives away 1, keeps 1 for itself.

 ------------------------------------------------------------
 🔍 Another Example:

Input: root = [1,0,0]

Tree:    1
        / \
       0   0

Process left (0):
- leftBalance = 0, rightBalance = 0
- moves = 0
- return: 0 + 0 + 0 - 1 = -1 (needs 1 coin)

Process right (0):
- leftBalance = 0, rightBalance = 0
- moves = 0
- return: 0 + 0 + 0 - 1 = -1 (needs 1 coin)

Process root (1):
- leftBalance = -1, rightBalance = -1
- moves = 0 + |-1| + |-1| = 2
- return: 1 + (-1) + (-1) - 1 = -2

Wait! Return is -2, but we're at root, so we ignore return.
Total moves = 2 ✅

Root gives 1 coin to left, but it only has 1!
Actually, this violates the constraint. Let me recalculate...

Oh! The tree structure should be valid. If root = [1,0,0] with constraint
that total coins = total nodes, we have 1 coin but 3 nodes. This violates
the constraint "The sum of all Node.val is n".

Let me use a valid example instead:

Input: root = [3,0,0]

Tree:    3
        / \
       0   0

Process left (0):
- return: -1 (needs 1 coin)

Process right (0):
- return: -1 (needs 1 coin)

Process root (3):
- leftBalance = -1, rightBalance = -1
- moves = 0 + |-1| + |-1| = 2
- return: 3 + (-1) + (-1) - 1 = 0 (balanced)

Total: 2 moves ✅
Root gives 1 coin to left, 1 coin to right, keeps 1.

 ------------------------------------------------------------
 🔍 Edge Cases:

Case 1: Single node with 1 coin
root = [1]
- No children, no moves needed
- Result: 0 ✓

Case 2: Two nodes (root and child)
root = [0, 2]
- Left child has 2, needs 1, has excess of 1
- Root has 0, needs 1, has deficit of 1
- 1 coin moves from child to parent
- Result: 1 ✓

Case 3: Linear tree (all left)
root = [0, 0, null, 3]
Tree: 0 → 0 → 3
- Bottom: has 2 excess
- Middle: has 0-1+2 = 1 excess
- Root: receives 1, balanced
- Moves: 2 (bottom to middle) + 1 (middle to root) = 3 ✓

Case 4: Perfect binary tree, all balanced
root = [1, 1, 1]
- Each node has exactly 1
- No moves needed
- Result: 0 ✓

Case 5: All coins at root
root = [3, 0, 0]
- Root distributes to both children
- Result: 2 ✓

Case 6: All coins at leaves
root = [0, 2, 2, 0, 0, 0, 0]
Tree:      0
         /   \
        2     2
       / \   / \
      0   0 0   0
- Leaves have 1 excess each
- Coins flow up to root
- Multiple moves through intermediate nodes
- Each coin crossing each edge counted ✓

 ------------------------------------------------------------
 🔍 Why Post-Order Traversal?

Question: Why process children before parent?
Answer: Parent needs children's balance to make decision!

Pre-order (wrong):
- Process parent first
- Don't know what children need
- Can't calculate correct balance

Post-order (correct):
- Process children first
- Know their excess/deficit
- Parent can calculate its own balance
- Can pass balance up to grandparent

This is classic bottom-up DP in trees!

 ------------------------------------------------------------
 🔍 Common Mistakes:

❌ Mistake 1: Counting moves per coin instead of per edge
Wrong: Track each coin's path
Correct: Count coins crossing each edge
Reason: Simpler, same result

❌ Mistake 2: Using pre-order traversal
Wrong: Process parent before children
Correct: Post-order (children before parent)
Reason: Need children's info to calculate parent's balance

❌ Mistake 3: Forgetting absolute value
Wrong: moves += leftBalance + rightBalance
Correct: moves += |leftBalance| + |rightBalance|
Reason: Negative balance (deficit) still requires moves

❌ Mistake 4: Not returning balance to parent
Wrong: Return 0 or return nothing
Correct: Return node.val + left + right - 1
Reason: Parent needs to know this subtree's balance

❌ Mistake 5: Trying to track actual coin movements
Wrong: Simulate actual distribution
Correct: Calculate theoretical flows
Reason: Balance approach is simpler and correct

 ------------------------------------------------------------
 🔍 Complexity Analysis:

Time Complexity: O(n)
- Visit each node exactly once
- O(1) work per node
- Total: O(n)

Space Complexity: O(h)
- Recursion stack depth = tree height
- Best case (balanced): O(log n)
- Worst case (skewed): O(n)
- No auxiliary data structures

Why O(n) is optimal:
- Must visit each node to know its coin count
- Cannot skip any node
- Linear time is best possible

 ------------------------------------------------------------
 🔍 Why This Problem is Important:

Key concepts demonstrated:
1. Post-order DFS for bottom-up aggregation
2. Balance/flow concept in trees
3. Edge-based thinking (not node-based)
4. Global state accumulation during recursion

Similar problems using same pattern:
- 1130. Minimum Cost Tree From Leaf Values
- 968. Binary Tree Cameras
- 337. House Robber III
- 124. Binary Tree Maximum Path Sum

Real-world applications:
- Resource distribution in networks
- Load balancing in distributed systems
- Flow optimization in graphs
- Supply chain management

⚡ Performance Analysis:
This post-order DFS approach efficiently handles maximum constraints:
- Tree with up to 100 nodes
- Single DFS traversal: exactly n visits
- Per-node operations: 2-3 additions, 2 absolute values
- Total: ~500 operations for max input
- Execution time: <1ms
- Space: recursion stack up to 100 levels (worst case)
- Why this is optimal:
  • Must process all nodes: Ω(n) lower bound
  • Each node processed in O(1)
  • No redundant work
  • Minimal space usage
- The balance concept is key insight:
  • Reduces complex movement tracking to simple arithmetic
  • Each edge considered once
  • No simulation needed
  • Mathematical elegance
- Alternative approaches are worse:
  • Simulate actual movements: complex, same result
  • BFS level-order: harder to track balances
  • Pre-order DFS: can't compute parent without children
- This demonstrates:
  • Power of mathematical abstraction (balance vs actual coins)
  • Bottom-up tree DP pattern
  • Edge-centric vs node-centric thinking
  • When post-order traversal is essential
 ------------------------------------------------------------
*/