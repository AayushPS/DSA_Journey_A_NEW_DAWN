/*
 🔹 Problem: 64. Minimum Path Sum
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Dynamic Programming, Grid DP
 🔹 Link: https://leetcode.com/problems/minimum-path-sum/

 ------------------------------------------------------------
 🔸 Problem Statement:

Given an m×n grid of non-negative numbers, find a path from (0,0) to (m−1,n−1)  
moving only RIGHT or DOWN, such that the **sum of all values on the path is minimized**.

 ------------------------------------------------------------
 🔸 Examples:

Example 1:
Input:  [[1,3,1],[1,5,1],[4,2,1]]
Output: 7

Example 2:
Input:  [[1,2,3],[4,5,6]]
Output: 12

 ------------------------------------------------------------
 🔸 Constraints:
 • 1 <= m,n <= 200  
 • 0 <= grid[i][j] <= 200  

 ------------------------------------------------------------
 🔹 Approach Summary:

You provided four approaches:
1. Top-down DFS + Memo  
2. 2D Tabulation DP  
3. Rolling 1D DP  
4. Fully optimized 1D DP with swapping → **final working version**

Each approach has a full explanation block.  
Only the last approach is left uncommented.

*/




/*
 ------------------------------------------------------------
 🔹 Approach 1: DFS + Memoization (Commented)

   • Classic recursion with memoization.
   • At each cell (i,j), the minimum path sum equals:
         grid[i][j] + min( right , down )
   • If you exceed grid boundaries, return a very large number (simulate infinity).
   • Base case:
         At bottom-right → return its own value.
   • Memo table stores results for each cell to avoid recomputation.
   • Time Complexity:  O(m*n)
   • Space Complexity: O(m*n) due to memo + recursion stack
 ------------------------------------------------------------

class Solution {
    private static final int MAX = 400 * 200;

    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];
        for (int[] row : dp) Arrays.fill(row, -1);
        return solve(grid, 0, 0, m, n, dp);
    }

    private int solve(int[][] grid, int i, int j, int m, int n, int[][] dp) {
        if (i == m-1 && j == n-1)
            return grid[i][j];

        if (dp[i][j] != -1)
            return dp[i][j];

        int right = (j+1 < n) ? solve(grid, i, j+1, m, n, dp) : MAX;
        int down  = (i+1 < m) ? solve(grid, i+1, j, m, n, dp) : MAX;

        return dp[i][j] = grid[i][j] + Math.min(right, down);
    }
}
*/




/*
 ------------------------------------------------------------
 🔹 Approach 2: 2D Bottom-Up DP (Commented)

   • dp[i][j] = minimum path sum to reach cell (i,j).
   • First row:
        dp[0][j] = dp[0][j-1] + grid[0][j]
   • First column:
        dp[i][0] = dp[i-1][0] + grid[i][0]
   • General recurrence:
        dp[i][j] = grid[i][j] + min(dp[i-1][j], dp[i][j-1])
   • Answer is dp[m-1][n-1].
   • Time Complexity:  O(m*n)
   • Space Complexity: O(m*n)
 ------------------------------------------------------------

class Solution {
    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];

        dp[0][0] = grid[0][0];

        for (int j = 1; j < n; j++)
            dp[0][j] = grid[0][j] + dp[0][j-1];

        for (int i = 1; i < m; i++)
            dp[i][0] = grid[i][0] + dp[i-1][0];

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = grid[i][j] +
                    Math.min(dp[i-1][j], dp[i][j-1]);
            }
        }

        return dp[m-1][n-1];
    }
}
*/




/*
 ------------------------------------------------------------
 🔹 Approach 3: Rolling 1D DP (Commented)

   • Maintain:
         old[j] = minimum path sum from previous row
         now[j] = minimum path sum for current row
   • First row is computed directly by cumulative sums.
   • For each next row:
         now[0] = old[0] + grid[i][0]
         now[j] = grid[i][j] + min(old[j], now[j-1])
   • Reduces memory usage to O(n).
   • Time Complexity:  O(m*n)
   • Space Complexity: O(n)
 ------------------------------------------------------------

class Solution {
    private static final int MAX = 400 * 200;

    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[] old = new int[n];

        old[0] = grid[0][0];

        for (int j = 1; j < n; j++)
            old[j] = grid[0][j] + old[j-1];

        for (int i = 1; i < m; i++) {
            int[] now = new int[n];
            now[0] = old[0] + grid[i][0];

            for (int j = 1; j < n; j++)
                now[j] = grid[i][j] + Math.min(old[j], now[j-1]);

            old = now;
        }

        return old[n-1];
    }
}
*/




/*
 ------------------------------------------------------------
 🔹 Approach 4: Final Working Code — Optimized 1D DP with swapping

   • Uses two 1D arrays (old and now) but swaps them instead of reallocating.
   • First row is computed as prefix sum.
   • For each subsequent row:
         - Reset 'now'
         - now[0] = old[0] + grid[i][0]
         - now[j] = grid[i][j] + min(old[j], now[j-1])
   • Swapping minimizes allocations and improves memory locality.
   • Time Complexity:  O(m*n)
   • Space Complexity: O(n)
   • This is your final chosen solution — left UNCOMMENTED.
 ------------------------------------------------------------
*/

import java.util.Arrays;

public class MaximumPathSum {
    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        int[] old = new int[n];
        int[] now = new int[n];

        old[0] = grid[0][0];

        for (int j = 1; j < n; j++)
            old[j] = grid[0][j] + old[j-1];

        for (int i = 1; i < m; i++) {
            Arrays.fill(now, 0);

            now[0] = old[0] + grid[i][0];

            for (int j = 1; j < n; j++)
                now[j] = grid[i][j] + Math.min(old[j], now[j-1]);

            int[] tmp = old;
            old = now;
            now = tmp;
        }

        return old[n-1];
    }
}

/*
 ------------------------------------------------------------
 🔹 End of File
 ------------------------------------------------------------
*/
