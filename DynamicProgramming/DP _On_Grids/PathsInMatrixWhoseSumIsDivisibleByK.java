/*
 🔹 Problem: 2435. Paths in Matrix Whose Sum Is Divisible by K
 🔹 Platform: LeetCode
 🔹 Difficulty: Hard
 🔹 Topics: Dynamic Programming, Modular Arithmetic, Grid DP
 🔹 Link: https://leetcode.com/problems/paths-in-matrix-whose-sum-is-divisible-by-k/

 ------------------------------------------------------------
 🔸 Problem Statement:

You are on an m×n grid, starting at (0,0).  
You may move only RIGHT or DOWN.  
Each cell contains a value.  
You must count how many distinct paths end at (m−1,n−1)  
such that the **sum of all visited values is divisible by k**.

Return the result modulo 1e9+7.

 ------------------------------------------------------------
 🔸 Examples:

Example 1:
Input:  [[5,2,4],[3,0,5],[0,7,2]], k = 3
Output: 2

Example 2:
Input:  [[0,0]], k = 5
Output: 1

Example 3:
Input:  [[7,3,4,9],[2,3,6,2],[2,3,7,0]], k = 1
Output: 10

 ------------------------------------------------------------
 🔸 Constraints:
 • 1 <= m,n <= 5×10⁴  
 • 1 <= m*n <= 5×10⁴  
 • k <= 50  
 • 0 <= grid[i][j] <= 100  

 ------------------------------------------------------------
 🔹 Approach Summary:

You provided four approaches:
1. Recursive + Memo (3D DP)  
2. 3D Tabulation DP  
3. 2D Rolling DP (old/now)  
4. Fully optimized 1D-like rolling DP using two 2D layers → **your final intended solution**

Below: full explanatory blocks.  
Only the fourth approach remains uncommented.

*/




/*
 ------------------------------------------------------------
 🔹 Approach 1: DFS + Memo (3D DP)  (Commented)

   • This uses recursion with memoization.
   • State: (i, j, r) meaning:
        - At position (i,j)
        - Current remainder r = sum % k
   • Transition:
        - Move down → rec(i+1, j, new_r)
        - Move right → rec(i, j+1, new_r)
   • Base case:
        - If at bottom-right:
               return 1 if remainder == 0 else 0
   • dp[i][j][r] caches results to avoid recomputation.
   • Time:  O(m*n*k)
   • Space: O(m*n*k)
 ------------------------------------------------------------

class Solution {
    public int numberOfPaths(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int[][][] dp = new int[m][n][k];
        for (int[][] a : dp) for (int[] b : a) Arrays.fill(b, -1);
        return solve(grid, k, 0, 0, m, n, 0, dp);
    }

    private int solve(int[][] grid, int k, int i, int j, int m, int n, int sum, int[][][] dp) {
        sum = (sum + grid[i][j]) % k;

        if (i == m-1 && j == n-1)
            return dp[i][j][sum] = (sum == 0 ? 1 : 0);

        if (dp[i][j][sum] != -1) return dp[i][j][sum];

        long res = 0;
        if (i+1 < m) res += solve(grid,k,i+1,j,m,n,sum,dp);
        if (j+1 < n) res += solve(grid,k,i,j+1,m,n,sum,dp);

        return dp[i][j][sum] = (int)(res % 1000000007);
    }
}
*/




/*
 ------------------------------------------------------------
 🔹 Approach 2: 3D Tabulation DP (Commented)

   • dp[i][j][r] = number of ways to reach cell (i,j) with remainder r.
   • Initialization:
         dp[0][0][grid[0][0] % k] = 1
   • Transition:
         From left (i, j-1)
         From up   (i-1, j)
       Both transitions add their ways to current dp[i][j][new_r].
   • The table fills row-by-row, column-by-column.
   • Final answer: dp[m-1][n-1][0]
   • Time:  O(m*n*k)
   • Space: O(m*n*k)
 ------------------------------------------------------------

class Solution {
    public int numberOfPaths(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int[][][] dp = new int[m][n][k];

        dp[0][0][grid[0][0] % k] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int r = 0; r < k; r++) {
                    int curr = dp[i][j][r];
                    if (curr == 0) continue;

                    if (i+1 < m) {
                        int nr = (r + grid[i+1][j]) % k;
                        dp[i+1][j][nr] = (dp[i+1][j][nr] + curr) % 1000000007;
                    }
                    if (j+1 < n) {
                        int nr = (r + grid[i][j+1]) % k;
                        dp[i][j+1][nr] = (dp[i][j+1][nr] + curr) % 1000000007;
                    }
                }
            }
        }

        return dp[m-1][n-1][0];
    }
}
*/




/*
 ------------------------------------------------------------
 🔹 Approach 3: Rolling DP with two 2D layers (Commented)

   • Reduces space from O(m*n*k) to O(n*k).
   • Maintains:
         old[j][r] = ways to reach previous row's cell j with remainder r.
         now[j][r] = ways to reach current row's cell j with remainder r.
   • Process:
         - Build first row directly.
         - For each subsequent row:
             • Update column 0 separately (only comes from above).
             • For general cell (i,j):
                   from top: old[j][*]
                   from left: now[j-1][*]
   • Time:  O(m*n*k)
   • Space: O(n*k)
 ------------------------------------------------------------

class Solution {
    public int numberOfPaths(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int[][] old = new int[n][k];

        int sum = 0;
        for (int j = 0; j < n; j++) {
            sum = (sum + grid[0][j]) % k;
            old[j][sum] = 1;
        }

        for (int i = 1; i < m; i++) {
            int[][] now = new int[n][k];

            for (int r = 0; r < k; r++) {
                if (old[0][r] > 0)
                    now[0][(r + grid[i][0]) % k] = old[0][r];
            }

            for (int j = 1; j < n; j++) {
                for (int r = 0; r < k; r++) {
                    if (old[j][r] > 0)
                        now[j][(r + grid[i][j]) % k] =
                            (now[j][(r + grid[i][j]) % k] + old[j][r]) % 1000000007;

                    if (now[j-1][r] > 0)
                        now[j][(r + grid[i][j]) % k] =
                            (now[j][(r + grid[i][j]) % k] + now[j-1][r]) % 1000000007;
                }
            }

            old = now;
        }

        return old[n-1][0];
    }
}
*/




/*
 ------------------------------------------------------------
 🔹 Approach 4: Final Working Code — Optimized Rolling DP

   • Uses two 2D remainder DP layers (old & now), but swaps them instead of re-allocating.
   • This is the cleanest high-performance implementation you provided.
   • First row is precomputed by accumulating prefix sum % k.
   • For each row:
         - Reset "now"
         - Update column 0 using only the top transition
         - For each column j>0:
               from TOP: old[j][r]
               from LEFT: now[j-1][r]
         - Apply modulo after each update
   • This solution runs in:
         Time:  O(m*n*k)
         Space: O(n*k)
   • This is your final chosen solution — left uncommented exactly as given.
 ------------------------------------------------------------
*/

import java.util.Arrays;

public class PathsInMatrixWhoseSumIsDivisibleByK {
    public int numberOfPaths(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;

        int[][] old = new int[n][k];
        int[][] now = new int[n][k];

        int sum = 0;
        for (int j = 0; j < n; j++) {
            sum = (sum + grid[0][j]) % k;
            old[j][sum] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int[] row : now) Arrays.fill(row, 0);

            for (int r = 0; r < k; r++) {
                int pre = old[0][r];
                if (pre == 0) continue;
                now[0][(r + grid[i][0]) % k] = pre;
            }

            for (int j = 1; j < n; j++) {
                for (int r = 0; r < k; r++) {
                    if (old[j][r] > 0) {
                        int nr = (r + grid[i][j]) % k;
                        now[j][nr] = (now[j][nr] + old[j][r]) % 1000000007;
                    }
                    if (now[j-1][r] > 0) {
                        int nr = (r + grid[i][j]) % k;
                        now[j][nr] = (now[j][nr] + now[j-1][r]) % 1000000007;
                    }
                }
            }

            int[][] tmp = old;
            old = now;
            now = tmp;
        }

        return old[n-1][0];
    }
}

/*
 ------------------------------------------------------------
 🔹 End of File
 ------------------------------------------------------------
*/
