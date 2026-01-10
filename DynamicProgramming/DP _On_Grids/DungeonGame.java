/*
 🔹 Problem: 174. Dungeon Game
 🔹 Platform: LeetCode
 🔹 Difficulty: Hard
 🔹 Topics: Dynamic Programming, Reverse Grid DP
 🔹 Link: https://leetcode.com/problems/dungeon-game/

 ------------------------------------------------------------
 🔸 Problem:

A knight moves in an m×n dungeon grid starting at (0,0) and can only move  
right or down. The value in each cell affects his health:

• Negative → health decreases  
• Positive → health increases  
• Zero → no change  

The knight dies if health ever becomes 0 or below.

Determine the minimum initial health required so that the knight can  
reach the princess at (m−1,n−1).

 ------------------------------------------------------------
 🔸 Examples:

Example 1:
Input:  [[-2,-3,3],[-5,-10,1],[10,30,-5]]
Output: 7

Example 2:
Input:  [[0]]
Output: 1

 ------------------------------------------------------------
 🔸 Constraints:

• 1 <= m,n <= 200  
• -1000 <= dungeon[i][j] <= 1000  
• Movement allowed only right or down  

 ------------------------------------------------------------
 🔹 Approach 1: Recursive DP with Memoization (Commented)

A recursive formulation starts from (0,0) and searches toward the bottom-right.  
At each state (i,j), the knight needs:

    required = min(required_down, required_right) - dungeon[i][j]

If required <= 0, return 1 because the knight must have at least 1 HP.

Memoization stores the required HP for each position.

Complexity:
• Time:  O(m·n)  
• Space: O(m·n)

 ------------------------------------------------------------

/*
public class DungeonGame {
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length, n = dungeon[0].length;
        int[][] dp = new int[m][n];
        for (int[] row : dp) Arrays.fill(row, Integer.MAX_VALUE);
        return solve(dungeon, 0, 0, m, n, dp);
    }

    private int solve(int[][] dungeon, int i, int j, int m, int n, int[][] dp) {
        if (i == m - 1 && j == n - 1) {
            if (dungeon[i][j] < 0) return -dungeon[i][j] + 1;
            return 1;
        }

        if (dp[i][j] != Integer.MAX_VALUE) return dp[i][j];

        int down = Integer.MAX_VALUE, right = Integer.MAX_VALUE;

        if (i + 1 < m) down = solve(dungeon, i + 1, j, m, n, dp);
        if (j + 1 < n) right = solve(dungeon, i, j + 1, m, n, dp);

        int need = Math.min(down, right) - dungeon[i][j];
        if (need <= 0) need = 1;

        return dp[i][j] = need;
    }
}
*/




/*
 ------------------------------------------------------------
 🔹 Approach 2: Full 2D Bottom-Up DP (Commented)

This version computes dp[i][j], the minimum health needed to *enter* cell (i,j)  
and still be able to reach the goal while never dropping to 0 HP.

Transition (processing from bottom-right upward):

    dp[i][j] = max(1, min(dp[i+1][j], dp[i][j+1]) - dungeon[i][j])

Base case:
    dp[m-1][n-1] = dungeon[end] < 0 ? -dungeon[end] + 1 : 1

Complexity:
• Time:  O(m·n)  
• Space: O(m·n)

 ------------------------------------------------------------

/*
public class DungeonGame {
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length, n = dungeon[0].length;
        int[][] dp = new int[m][n];

        dp[m-1][n-1] = dungeon[m-1][n-1] < 0 ? -dungeon[m-1][n-1] + 1 : 1;

        for (int j = n - 2; j >= 0; j--) {
            int need = dp[m-1][j+1] - dungeon[m-1][j];
            dp[m-1][j] = need <= 0 ? 1 : need;
        }

        for (int i = m - 2; i >= 0; i--) {
            int need = dp[i+1][n-1] - dungeon[i][n-1];
            dp[i][n-1] = need <= 0 ? 1 : need;
        }

        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                int need = Math.min(dp[i+1][j], dp[i][j+1]) - dungeon[i][j];
                dp[i][j] = need <= 0 ? 1 : need;
            }
        }

        return dp[0][0];
    }
}
*/




/*
 ------------------------------------------------------------
 🔹 Approach 3: Rolling 1D DP (Commented)

A 1D DP array is maintained for the "row below", and another temporary  
array is used for the current row.

Formulas remain identical to the 2D DP, but memory usage is reduced.

Complexity:
• Time:  O(m·n)  
• Space: O(n)

 ------------------------------------------------------------

/*
public class DungeonGame {
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length, n = dungeon[0].length;
        int[] dp = new int[n];

        dp[n-1] = dungeon[m-1][n-1] < 0 ? -dungeon[m-1][n-1] + 1 : 1;

        for (int j = n - 2; j >= 0; j--) {
            int need = dp[j+1] - dungeon[m-1][j];
            dp[j] = need <= 0 ? 1 : need;
        }

        for (int i = m - 2; i >= 0; i--) {
            int[] next = new int[n];

            int need = dp[n-1] - dungeon[i][n-1];
            next[n-1] = need <= 0 ? 1 : need;

            for (int j = n - 2; j >= 0; j--) {
                int x = Math.min(dp[j], next[j+1]) - dungeon[i][j];
                next[j] = x <= 0 ? 1 : x;
            }

            dp = next;
        }

        return dp[0];
    }
}
*/




/*
 ------------------------------------------------------------
 🔹 Approach 4: Optimized 1D DP with Array Swapping (Final Working Code)

This version keeps two 1D arrays (old and now) and swaps references  
after processing each row, avoiding additional allocations.

Transition rules remain the same:

    need = min(old[j], now[j+1]) - dungeon[i][j]
    need <= 0 ? 1 : need

Complexity:
• Time:  O(m·n)  
• Space: O(n)

 ------------------------------------------------------------
*/

import java.util.Arrays;

public class DungeonGame {
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length, n = dungeon[0].length;

        int[] old = new int[n];
        int[] now = new int[n];

        old[n-1] = dungeon[m-1][n-1] < 0 ? -dungeon[m-1][n-1] + 1 : 1;

        for (int j = n - 2; j >= 0; j--) {
            int need = old[j+1] - dungeon[m-1][j];
            old[j] = need <= 0 ? 1 : need;
        }

        for (int i = m - 2; i >= 0; i--) {
            Arrays.fill(now, 0);

            int need = old[n-1] - dungeon[i][n-1];
            now[n-1] = need <= 0 ? 1 : need;

            for (int j = n - 2; j >= 0; j--) {
                int v = Math.min(old[j], now[j+1]) - dungeon[i][j];
                now[j] = v <= 0 ? 1 : v;
            }

            int[] temp = old;
            old = now;
            now = temp;
        }

        return old[0];
    }
}

/*
 ------------------------------------------------------------
 🔹 End of File
 ------------------------------------------------------------
*/

