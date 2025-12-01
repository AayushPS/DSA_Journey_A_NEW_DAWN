/*
 🔹 Problem: 3363. Find the Maximum Number of Fruits Collected
 🔹 Platform: LeetCode
 🔹 Difficulty: Hard
 🔹 Topics: Dynamic Programming, Grid DP, Path Optimization
 🔹 Link: https://leetcode.com/problems/find-the-maximum-number-of-fruits-collected/

 ------------------------------------------------------------
 📝 Problem Statement:

There is a game dungeon with an n x n grid of rooms. You are given
fruits[i][j] = number of fruits in room (i, j).

Three children start at:
 • Child A → (0, 0)
 • Child B → (0, n - 1)
 • Child C → (n - 1, 0)

All children will make exactly n - 1 moves and must end at (n - 1, n - 1).

Movement rules:
 • Child A may move to (i+1,j), (i,j+1), or (i+1,j+1).
 • Child B may move to (i+1,j), (i+1,j+1), or (i+1,j-1).
 • Child C may move to (i,j+1), (i+1,j+1), or (i-1,j+1).

When a child enters a room, they collect all fruits.
If multiple enter the same room, fruits counted once.

Return the maximum fruits collectible.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: [[1,2,3,4],[5,6,8,7],[9,10,11,12],[13,14,15,16]]
Output: 100

Example 2:
Input: [[1,1],[1,1]]
Output: 4

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 2 ≤ n ≤ 1000
 • 0 ≤ fruits[i][j] ≤ 1000
 • fruits is n x n square

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Maximize combined fruit collection of the three constrained paths.

📍 **Approach 1 (Top-down DP with Memoization):**
   - DFS explores all valid moves, memo stores max future fruit.
   - Simple but recursion-heavy and slower in practice.
   - Good for conceptual understanding.

📍 **Approach 2 (Bottom-up DP - Optimized):**
   - Iterative DP simulating transitions for both secondary children.
   - Uses tabulation for maximum efficiency.
   - Eliminates recursion overhead.
   - **Chosen as the most optimal approach.**

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - Top-down DP with Memoization)
   ⏱️ Time Complexity: O(n²)
   💾 Space Complexity: O(n²)
   
   🧠 Key Insight:
   Define DP[i][j] = max fruits collectable starting at (i, j) under allowed transitions.

   💡 Why it works:
   Memo ensures each state evaluated once, exploring only valid child moves.
 -------------------------------------------------------------

/*
// Java
class Solution {
    public int maxCollectedFruits(int[][] fruits) {
        int n = fruits.length;
        int mid = 0;
        int[][] dp = new int[n][n]; 
        for (int[] row : dp) Arrays.fill(row, -1);

        for (int i = 0; i < n; i++) mid += fruits[i][i];

        int left = travelDown(fruits, 0, n - 1, n, dp);
        int down = travelRight(fruits, n - 1, 0, n, dp);

        return mid + left + down;
    }

    private int travelDown(int[][] f, int i, int j, int n, int[][] dp) {
        if (i >= n - 1 || j >= n || i >= j) return 0;
        if (dp[i][j] != -1) return dp[i][j];
        int now = f[i][j];
        return dp[i][j] = now + Math.max(
            travelDown(f, i + 1, j, n, dp),
            Math.max(travelDown(f, i + 1, j - 1, n, dp), travelDown(f, i + 1, j + 1, n, dp))
        );
    }

    private int travelRight(int[][] f, int i, int j, int n, int[][] dp) {
        if (j >= n - 1 || i >= n || i <= j) return 0;
        if (dp[i][j] != -1) return dp[i][j];
        int now = f[i][j];
        return dp[i][j] = now + Math.max(
            travelRight(f, i + 1, j + 1, n, dp),
            Math.max(travelRight(f, i, j + 1, n, dp), travelRight(f, i - 1, j + 1, n, dp))
        );
    }
}
*/
/*
 ------------------------------------------------------------
 🔹 Approach 2 (✅ Bottom-up DP - Most Optimal)
   - Time Complexity: O(n²)
   - Space Complexity: O(n²)
   
   🧠 Key Insight:
   Convert recursive transitions to bottom-up transitions to compute maximum path values
   for the two non-diagonal children.

   💡 Why it works:
   - Avoids recursion depth limit
   - Better cache locality
   - Handles 1000×1000 grid efficiently
 ------------------------------------------------------------
*/

public class FindTheMaximumNumberOfFruitsCollected {

    public int maxCollectedFruits(int[][] fruits) {
        int n = fruits.length;
        int mid = 0;

        int[][] dp = new int[n][n];

        // diagonal child (0,0) → (n-1,n-1)
        for (int i = 0; i < n; i++) {
            mid += fruits[i][i];
        }

        // DP for child at (0, n-1)
        for (int i = n - 2; i >= 0; i--) {
            for (int j = n - 1; j > i; j--) {
                if (j + 1 < n) dp[i][j] = Math.max(dp[i][j], dp[i + 1][j + 1]);
                if (i + 1 < j) dp[i][j] = Math.max(dp[i][j], dp[i + 1][j]);
                if (i + 1 < j - 1) dp[i][j] = Math.max(dp[i][j], dp[i + 1][j - 1]);
                dp[i][j] += fruits[i][j];
            }
        }

        // DP for child at (n-1, 0)
        for (int j = n - 2; j >= 0; j--) {
            for (int i = n - 1; i > j; i--) {
                if (i + 1 < n) dp[i][j] = Math.max(dp[i][j], dp[i + 1][j + 1]);
                if (j + 1 < i) dp[i][j] = Math.max(dp[i][j], dp[i][j + 1]);
                if (j + 1 < i - 1) dp[i][j] = Math.max(dp[i][j], dp[i - 1][j + 1]);
                dp[i][j] += fruits[i][j];
            }
        }

        return mid + dp[0][n - 1] + dp[n - 1][0];
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

Input:
[[1,2,3,4],
 [5,6,8,7],
 [9,10,11,12],
 [13,14,15,16]]

Diagonal sum = 1 + 6 + 11 + 16 = 34

dp[0][3] = best path of child from top-right = 29  
dp[3][0] = best path of child from bottom-left = 37  

Final = 34 + 29 + 37 = 100

 ------------------------------------------------------------
*/
