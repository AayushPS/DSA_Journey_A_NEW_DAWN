/*
 🔹 Problem: 1594. Maximum Non-Negative Product in a Matrix
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Dynamic Programming, Grid DP, Min-Max Product Tracking
 🔹 Link: https://leetcode.com/problems/maximum-non-negative-product-in-a-matrix/

 ------------------------------------------------------------
 🔸 Problem:

Given an m×n integer grid, starting at (0,0), movement is allowed only  
to the right or down. Among all possible paths to (m−1,n−1), determine  
the maximum non-negative product of all elements along a path.

If the maximum product is negative, return -1.  
Otherwise, return the product modulo 1e9+7.

 ------------------------------------------------------------
 🔸 Examples:

Example 1:
Input:  [[-1,-2,-3],[-2,-3,-3],[-3,-3,-2]]
Output: -1

Example 2:
Input:  [[1,-2,1],[1,-2,1],[3,-4,1]]
Output: 8

Example 3:
Input:  [[1,3],[0,-4]]
Output: 0

 ------------------------------------------------------------
 🔸 Constraints:

• 1 <= m,n <= 15  
• -4 <= grid[i][j] <= 4  
• Movement allowed only right or down  

 ------------------------------------------------------------
 🔹 Approach: Recursive DP with Min/Max Product Tracking

The product can become positive or negative depending on the parity of  
negative numbers along the path. Therefore, for each cell (i,j), two  
values must be maintained:

• Maximum product achievable from (i,j) to bottom-right  
• Minimum product achievable from (i,j) to bottom-right

Key points:

• At each cell, both max and min values from the next moves (right, down)  
  must be multiplied by the current value because a negative number can  
  turn a large negative into a positive.

• DFS + memoization is used to compute both values for each cell exactly once.

• Base case at (m−1,n−1):  
      max = grid[i][j]  
      min = grid[i][j]

• If both possible moves lead outside the grid, their results are ignored.

• After computing dp[0][0],  
      if max < 0 → return -1  
      else return max % MOD.

Complexity:
• Time:  O(m·n)  
• Space: O(m·n)  

 ------------------------------------------------------------
*/

public class MaximumNonNegativeProductInAMatrix {
    private static final long MOD = 1000000007;

    public int maxProductPath(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        long[][][] dp = new long[m][n][];
        long[] result = compute(grid, 0, 0, m, n, dp);

        long max = result[0];
        if (max < 0) return -1;
        return (int)(max % MOD);
    }

    private long[] compute(int[][] grid, int i, int j, int m, int n, long[][][] dp) {
        if (i >= m || j >= n)
            return null;

        if (i == m - 1 && j == n - 1) {
            long val = grid[i][j];
            return new long[]{val, val};
        }

        if (dp[i][j] != null)
            return dp[i][j];

        long[] down = compute(grid, i + 1, j, m, n, dp);
        long[] right = compute(grid, i, j + 1, m, n, dp);

        long maxProd = Long.MIN_VALUE;
        long minProd = Long.MAX_VALUE;
        long curr = grid[i][j];

        if (down != null) {
            maxProd = Math.max(maxProd, down[0] * curr);
            maxProd = Math.max(maxProd, down[1] * curr);
            minProd = Math.min(minProd, down[0] * curr);
            minProd = Math.min(minProd, down[1] * curr);
        }

        if (right != null) {
            maxProd = Math.max(maxProd, right[0] * curr);
            maxProd = Math.max(maxProd, right[1] * curr);
            minProd = Math.min(minProd, right[0] * curr);
            minProd = Math.min(minProd, right[1] * curr);
        }

        dp[i][j] = new long[]{maxProd, minProd};
        return dp[i][j];
    }
}

/*
 ------------------------------------------------------------
 🔹 End of File
 ------------------------------------------------------------
*/
