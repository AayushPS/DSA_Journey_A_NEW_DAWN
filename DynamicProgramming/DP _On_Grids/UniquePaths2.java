/*
 🔹 Problem: 63. Unique Paths II
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Dynamic Programming
 🔹 Link: https://leetcode.com/problems/unique-paths-ii/

 ------------------------------------------------------------
 🔸 Problem Statement:

A robot moves in an m x n grid from top-left to bottom-right,  
only moving RIGHT or DOWN.

Some cells are blocked by obstacles (represented as 1).  
Robot cannot step on obstacles.

Return the number of unique valid paths from start to finish.

 ------------------------------------------------------------
 🔸 Examples:

Example 1:
Input:  [[0,0,0],[0,1,0],[0,0,0]]
Output: 2

Example 2:
Input:  [[0,1],[0,0]]
Output: 1

 ------------------------------------------------------------
 🔸 Constraints:
 • 1 <= m,n <= 100
 • obstacleGrid[i][j] ∈ {0,1}

 ------------------------------------------------------------
 🔹 Approach Summary:

You provided three approaches:
1. Recursive + Memoization  
2. Iterative 2D DP  
3. 1D DP (space optimized) → this is your final chosen solution

All are included with full explanation blocks.  
Only the 1D DP remains uncommented.

*/




/*
 ------------------------------------------------------------
 🔹 Approach 1: Recursive + Memoization (Commented)

   • This uses DFS with memo.
   • From each cell (i,j), try moving:
       - Down: (i+1, j)
       - Right: (i, j+1)
   • If an obstacle is encountered, return 0.
   • Memo table stores results for each (i,j) to avoid recomputation.
   • When reaching the bottom-right cell, return 1 (valid path).
   • Time Complexity: O(m*n)
   • Space Complexity: O(m*n) due to recursion + memo
 ------------------------------------------------------------
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid[0][0] == 1) return 0;
        int m = obstacleGrid.length, n = obstacleGrid[0].length;

        int[][] dp = new int[m][n];
        for (int[] row : dp) Arrays.fill(row, -1);

        return dfs(0, 0, m, n, obstacleGrid, dp);
    }

    private int dfs(int i, int j, int m, int n, int[][] grid, int[][] dp) {
        if (i == m - 1 && j == n - 1) return 1;
        if (dp[i][j] != -1) return dp[i][j];

        int ways = 0;

        if (i + 1 < m && grid[i + 1][j] == 0)
            ways += dfs(i + 1, j, m, n, grid, dp);

        if (j + 1 < n && grid[i][j + 1] == 0)
            ways += dfs(i, j + 1, m, n, grid, dp);

        return dp[i][j] = ways;
    }
}
*/




/*
 ------------------------------------------------------------
 🔹 Approach 2: Iterative 2D DP (Commented)

   • Classic grid DP.
   • dp[i][j] = number of ways to reach cell (i,j).
   • If cell is obstacle → dp[i][j] = 0.
   • First row & first column must be initialized carefully,
     stopping when an obstacle blocks the path.
   • Recurrence:
         dp[i][j] = dp[i-1][j] + dp[i][j-1]
   • Final answer at dp[m-1][n-1].
   • Time Complexity: O(m*n)
   • Space Complexity: O(m*n)
 ------------------------------------------------------------
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid[0][0] == 1) return 0;

        int m = obstacleGrid.length, n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];

        dp[0][0] = 1;

        for (int i = 1; i < m; i++) {
            if (obstacleGrid[i][0] == 0) dp[i][0] = 1;
            else break;
        }

        for (int j = 1; j < n; j++) {
            if (obstacleGrid[0][j] == 0) dp[0][j] = 1;
            else break;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) continue;
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }

        return dp[m-1][n-1];
    }
}
*/




/*
 ------------------------------------------------------------
 🔹 Approach 3: Space-Optimized 1D DP  (Final Working Code)

   • Uses a rolling 1D DP array.
   • old[j] = number of ways to reach cell in previous row.
   • now[j] = number of ways in current row.
   • First row is initialized based on obstacles.
   • A "stoneOn0" flag ensures that once an obstacle appears
     in column 0 for any row, all future rows have 0 ways in col 0.
   • Transitions:
         now[j] = old[j] + now[j-1]
     but only if grid cell is not an obstacle.
   • This is your original code (kept exactly as given).
   • Time Complexity: O(m*n)
   • Space Complexity: O(n)
 ------------------------------------------------------------
*/

public class UniquePaths2{
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if(obstacleGrid[0][0]==1) return 0;

        int m = obstacleGrid.length , n = obstacleGrid[0].length;
        int[] old = new int[n];

        for(int j = 0; j<n; j++){
            if(obstacleGrid[0][j]==0) old[j] = 1;
            else break;
        }

        boolean stoneOn0 = false;

        for(int i = 1; i<m; i++){
            int[] now = new int[n];

            if(obstacleGrid[i][0]==1) stoneOn0 = true;
            if(stoneOn0) now[0] = 0;
            else now[0] = 1;

            for(int j = 1; j<n; j++){
                if(obstacleGrid[i][j]==1) continue;
                now[j] = old[j] + now[j-1];
            }

            old = now;
        }

        return old[n-1];
    }
}

/*
 ------------------------------------------------------------
 🔹 End of File
 ------------------------------------------------------------
*/
