/*
───────────────────────────────────────────────────────────────
📘 Problem: 1706. Where Will the Ball Fall
💡 Difficulty: Medium
🧠 Topics: Simulation, Matrix, DFS
🔗 Link: https://leetcode.com/problems/where-will-the-ball-fall/
───────────────────────────────────────────────────────────────
🧩 Problem Statement:

You have a 2-D grid of size m x n representing a box.  
Each cell contains a diagonal board:
- `1` → redirects the ball to the right (`/`)
- `-1` → redirects the ball to the left (`\`)

We drop one ball at the top of each column.  
Each ball either:
- Falls out of the bottom, or
- Gets stuck (against a wall or in a "V" trap).

Return an array `answer` of length `n` where:
`answer[i]` = final column the ball exits from (or `-1` if stuck).

───────────────────────────────────────────────────────────────
🔒 Constraints:
• 1 <= m, n <= 100  
• grid[i][j] is 1 or -1
───────────────────────────────────────────────────────────────
*/

/*
───────────────────────────────────────────────────────────────
🥉 Approach 1 — Direct Simulation per Ball
───────────────────────────────────────────────────────────────
💡 Idea:
Simulate the path of each ball column by column and row by row.

For each ball starting at top column `c`:
1. For every row `r`:
   - If the board at `(r, c)` is 1 → the ball moves right:
       - If `c == n-1` (right wall) or next board `(r, c+1)` is -1 (forms V), → stuck.
       - Else `c++`.
   - Else if board is -1 → the ball moves left:
       - If `c == 0` (left wall) or previous board `(r, c-1)` is 1 (forms V), → stuck.
       - Else `c--`.
2. If the ball survives all rows, record final column `c`.
3. Else record `-1`.

Why it works:
Each ball moves deterministically and independently.  
We just simulate the full path until either the ball falls out or gets blocked.

🧩 Edge cases:
- Single column (ball will always get stuck).
- Alternating diagonals forming repeated Vs.

🧮 Complexity:
• Time: O(m * n) → each ball traverses at most `m` rows.  
• Space: O(1) → only uses constants apart from input/output.

✅ Clean, simple, optimal for constraints.
*/

class Solution {
    public int[] findBall(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[] res = new int[n];
        A: for (int ball = 0; ball < n; ball++) {
            int mov = ball;
            for (int row = 0; row < m; row++) {
                if (grid[row][mov] == 1) {
                    if (mov + 1 >= n || grid[row][mov + 1] == -1) {
                        res[ball] = -1;
                        continue A;
                    }
                    mov++;
                } else {
                    if (mov - 1 < 0 || grid[row][mov - 1] == 1) {
                        res[ball] = -1;
                        continue A;
                    }
                    mov--;
                }
            }
            res[ball] = mov;
        }
        return res;
    }
}

/*
───────────────────────────────────────────────────────────────
🥈 Approach 2 — DFS Recursion per Ball (Alternative)
───────────────────────────────────────────────────────────────
💡 Idea:
Simulate the falling process recursively.
Let dfs(r, c) → final column index if ball starts at (r, c).

Transition:
- If r == m → ball fell out → return c.
- If grid[r][c] == 1:
    - If c == n-1 || grid[r][c+1] == -1 → stuck → return -1
    - Else → return dfs(r+1, c+1)
- If grid[r][c] == -1:
    - If c == 0 || grid[r][c-1] == 1 → stuck → return -1
    - Else → return dfs(r+1, c-1)

Apply this for each top column.

🧩 Advantage:
- Cleaner recursive logic.
- Easy to trace for debugging.

🧮 Complexity:
• Time: O(m * n) same as iterative.
• Space: O(m) recursion stack.
*/

/*
class Solution {
    public int[] findBall(int[][] grid) {
        int n = grid[0].length;
        int[] res = new int[n];
        for (int c = 0; c < n; c++) res[c] = dfs(grid, 0, c);
        return res;
    }

    private int dfs(int[][] grid, int r, int c) {
        if (r == grid.length) return c;
        if (grid[r][c] == 1) {
            if (c == grid[0].length - 1 || grid[r][c + 1] == -1) return -1;
            return dfs(grid, r + 1, c + 1);
        } else {
            if (c == 0 || grid[r][c - 1] == 1) return -1;
            return dfs(grid, r + 1, c - 1);
        }
    }
}
*/

/*
───────────────────────────────────────────────────────────────
🥇 Approach 3 — Dynamic Programming (Memoized DFS)
───────────────────────────────────────────────────────────────
💡 Idea:
Since many balls follow similar subpaths, we can cache results for subproblems (r, c).

dp[r][c] = final column after starting at (r, c)  
(= -1 if stuck)

Transition same as recursive version, but store and reuse computed values.

🧩 Advantage:
- Avoids recomputation for overlapping paths when m, n large.
- Helpful for repeated grid simulations.

🧮 Complexity:
• Time: O(m * n) → each cell visited once.
• Space: O(m * n) → memo + recursion stack.
*/

/*
class Solution {
    public int[] findBall(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];
        for (int[] row : dp) Arrays.fill(row, Integer.MIN_VALUE);

        int[] res = new int[n];
        for (int c = 0; c < n; c++) res[c] = dfs(grid, dp, 0, c);
        return res;
    }

    private int dfs(int[][] g, int[][] dp, int r, int c) {
        if (r == g.length) return c;
        if (dp[r][c] != Integer.MIN_VALUE) return dp[r][c];

        int n = g[0].length;
        int dir = g[r][c];
        int nc = c + dir;

        if (nc < 0 || nc >= n || g[r][nc] != dir) return dp[r][c] = -1;
        return dp[r][c] = dfs(g, dp, r + 1, nc);
    }
}
*/

/*
───────────────────────────────────────────────────────────────
🧮 Final Complexity Summary:
───────────────────────────────────────────────────────────────
Approach 1 (Simulation):  
• Time: O(m * n)  
• Space: O(1)

Approach 2 (DFS Recursion):  
• Time: O(m * n)  
• Space: O(m)

Approach 3 (Memoized DFS):  
• Time: O(m * n)  
• Space: O(m * n)

✅ Final Choice: Approach 1 — clean, iterative, no overhead.
───────────────────────────────────────────────────────────────
*/
