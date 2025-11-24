/*
 🔹 Problem: 62. Unique Paths
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Dynamic Programming, Combinatorics
 🔹 Link: https://leetcode.com/problems/unique-paths/

 ------------------------------------------------------------
 🔸 Problem Statement:

A robot is on an m x n grid, starting at the top-left corner (0,0) and 
must reach the bottom-right corner (m-1,n-1). It may only move **right** or **down**.

Return the number of unique paths possible.

 ------------------------------------------------------------
 🔸 Examples:

Example 1:
Input: m = 3, n = 7
Output: 28

Example 2:
Input: m = 3, n = 2
Output: 3

 ------------------------------------------------------------
 🔸 Constraints:
 • 1 <= m, n <= 100
 • The answer ≤ 2 * 10⁹

 ------------------------------------------------------------
 🔹 Approach Summary:

The number of ways to reach a cell depends on:
- Ways from above → dp[i-1][j]
- Ways from left  → dp[i][j-1]

This forms a classic grid DP.

You provided:
✔ A backward 2D DP  
✔ A forward 2D DP  
✔ A space-optimized **1D DP** (best)

We keep the two 2D solutions commented, and the optimal 1D version is the final working code.

 ------------------------------------------------------------
 🔹 Approach 1: Backward 2D DP (Commented)

class Solution {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        dp[m-1][n-1] = 1;

        for (int j = n-2; j >= 0; j--) dp[m-1][j] = 1;
        for (int i = m-2; i >= 0; i--) dp[i][n-1] = 1;

        for(int i = m-2; i >= 0; i--) {
            for(int j = n-2; j >= 0; j--) {
                dp[i][j] = dp[i+1][j] + dp[i][j+1];
            }
        }
        return dp[0][0];
    }
}

 ------------------------------------------------------------
 🔹 Approach 2: Forward 2D DP (Commented)

class Solution {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        dp[0][0] = 1;
        for (int j = 1; j < n; j++) dp[0][j] = 1;
        for (int i = 1; i < m; i++) dp[i][0] = 1;

        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }

        return dp[m-1][n-1];
    }
}

 ------------------------------------------------------------
 🔹 Final Working Code (Optimal 1D DP)

*/

import java.util.*;

class Solution {
    public int uniquePaths(int m, int n) {
        int[] olddp = new int[n];

        // First row is all 1's
        for (int j = 0; j < n; j++) {
            olddp[j] = 1;
        }

        for(int i = 1; i < m; i++){
            int[] newdp = new int[n];
            newdp[0] = 1; // first column always 1

            for(int j = 1; j < n; j++){
                newdp[j] = olddp[j] + newdp[j-1];
            }
            olddp = newdp;
        }

        return olddp[n-1];
    }
}

/*
 ------------------------------------------------------------
 🔹 End of File
 ------------------------------------------------------------
*/
    